package com.example.harajtask.posts.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.domain.posts.model.SimplePost
import com.example.harajtask.posts.di.InternalApi
import com.example.harajtask.posts.local.daos.PostsDao
import com.example.harajtask.posts.local.entities.PostEntity
import com.example.harajtask.posts.repository.dependencies.LocalDataSource
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
internal class LocalDataSourceImpl @Inject constructor(
    @InternalApi private val postsDao: PostsDao
) : LocalDataSource {

    override suspend fun replaceAllPosts(
        posts: List<Post>
    ) = withContext(Dispatchers.IO) {
        postsDao.replaceAllTransaction(posts.map(Post::toPostEntity))
    }

    override suspend fun findPostDetails(
        postId: PostId
    ) = withContext(Dispatchers.IO) {
        postsDao.findPost(postId.value)?.toPost()
    }

    override fun findPosts(pageSize: Int): Flow<PagingData<SimplePost>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = { postsDao.findSimplePosts() }
        ).flow.map { pagingData ->
            pagingData.map(PostEntity::toSimplePost)
        }.flowOn(Dispatchers.IO)
    }
}
