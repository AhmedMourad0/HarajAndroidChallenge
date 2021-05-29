package com.example.harajtask.posts.repository.dependencies

import com.example.harajtask.domain.posts.model.Post
import kotlinx.coroutines.flow.Flow

internal interface RemoteDataSource {
    fun findPosts(): Flow<List<Post>>
}
