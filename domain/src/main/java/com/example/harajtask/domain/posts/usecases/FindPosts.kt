package com.example.harajtask.domain.posts.usecases

import androidx.paging.PagingData
import com.example.harajtask.domain.posts.PostsRepository
import com.example.harajtask.domain.posts.model.SimplePost
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FindPosts {
    fun execute(pageSize: Int): Flow<PagingData<SimplePost>>
}

@Reusable
internal class FindPostsImpl @Inject constructor(
    private val repository: PostsRepository
) : FindPosts {
    override fun execute(pageSize: Int): Flow<PagingData<SimplePost>> {
        return repository.findPosts(pageSize)
    }
}
