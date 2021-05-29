package com.example.harajtask.domain.posts.usecases

import com.example.harajtask.domain.posts.PostsRepository
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import dagger.Reusable
import javax.inject.Inject

interface FindPostDetails {
    suspend fun execute(postId: PostId): Post?
}

@Reusable
internal class FindPostDetailsImpl @Inject constructor(
    private val repository: PostsRepository
) : FindPostDetails {
    override suspend fun execute(postId: PostId): Post? {
        return repository.findPostDetails(postId)
    }
}
