package com.example.harajtask.posts.fakes

import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.posts.repository.dependencies.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRemoteDataSource : RemoteDataSource {
    val data = MutableStateFlow<List<Post>>(emptyList())
    override fun findPosts(): Flow<List<Post>> {
        return data
    }
}
