package com.example.harajtask.fakes

import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.domain.posts.usecases.FindPostDetails
import kotlinx.coroutines.flow.MutableStateFlow

class FakeFindPostDetails(
    initial: List<Post>
) : FindPostDetails {
    val posts = MutableStateFlow(initial)
    override suspend fun execute(postId: PostId): Post? {
        return posts.value.firstOrNull { it.id == postId }
    }
}
