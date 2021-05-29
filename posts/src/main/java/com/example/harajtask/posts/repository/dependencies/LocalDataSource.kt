package com.example.harajtask.posts.repository.dependencies

import androidx.paging.PagingData
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.domain.posts.model.SimplePost
import kotlinx.coroutines.flow.Flow

internal interface LocalDataSource {
    suspend fun replaceAllPosts(posts: List<Post>)
    suspend fun findPostDetails(postId: PostId): Post?
    fun findPosts(pageSize: Int): Flow<PagingData<SimplePost>>
}
