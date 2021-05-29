package com.example.harajtask.domain.posts

import androidx.paging.PagingData
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.domain.posts.model.SimplePost
import kotlinx.coroutines.flow.Flow

interface PostsRepository {
    fun findPosts(pageSize: Int): Flow<PagingData<SimplePost>>
    suspend fun findPostDetails(postId: PostId): Post?
}
