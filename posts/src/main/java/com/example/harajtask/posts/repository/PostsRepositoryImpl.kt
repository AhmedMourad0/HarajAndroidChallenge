package com.example.harajtask.posts.repository

import androidx.paging.PagingData
import com.example.harajtask.domain.posts.PostsRepository
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.domain.posts.model.SimplePost
import com.example.harajtask.posts.di.InternalApi
import com.example.harajtask.posts.parser.model.JsonPost
import com.example.harajtask.posts.parser.toPost
import com.example.harajtask.posts.repository.dependencies.LocalDataSource
import com.example.harajtask.posts.repository.dependencies.RemoteDataSource
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Reusable
internal class PostsRepositoryImpl @Inject constructor(
    @InternalApi private val remote: RemoteDataSource,
    @InternalApi private val local: LocalDataSource
) : PostsRepository {

    @ExperimentalCoroutinesApi
    override fun findPosts(pageSize: Int): Flow<PagingData<SimplePost>> {
        return remote.findPosts()
            .filter(List<Post>::isNotEmpty)
            .onEach { posts ->
                local.replaceAllPosts(posts)
            }.flatMapLatest {
                local.findPosts(pageSize)
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun findPostDetails(
        postId: PostId
    ) = withContext(Dispatchers.IO) {
        local.findPostDetails(postId)
    }
}
