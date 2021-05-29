package com.example.harajtask.posts.fakes

import androidx.paging.PagingData
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.domain.posts.model.SimplePost
import com.example.harajtask.domain.posts.model.simplify
import com.example.harajtask.posts.repository.dependencies.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeLocalDataSource : LocalDataSource {

    private val data = MutableStateFlow<List<Post>>(emptyList())

    override suspend fun replaceAllPosts(posts: List<Post>) {
        data.value = posts
    }

    override suspend fun findPostDetails(postId: PostId): Post? {
        return data.value.firstOrNull { it.id == postId }
    }

    override fun findPosts(pageSize: Int): Flow<PagingData<SimplePost>> {
        return flowOf(PagingData.from(data.value.map(Post::simplify)))
    }
}
