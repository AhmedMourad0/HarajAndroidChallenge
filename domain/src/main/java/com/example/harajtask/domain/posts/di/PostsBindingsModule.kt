package com.example.harajtask.domain.posts.di

import com.example.harajtask.domain.posts.usecases.FindPostDetails
import com.example.harajtask.domain.posts.usecases.FindPostDetailsImpl
import com.example.harajtask.domain.posts.usecases.FindPosts
import com.example.harajtask.domain.posts.usecases.FindPostsImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal interface PostsBindingsModule {

    @Binds
    @Reusable
    fun bindFindPosts(
        impl: FindPostsImpl
    ): FindPosts

    @Binds
    @Reusable
    fun bindFindPostDetails(
        impl: FindPostDetailsImpl
    ): FindPostDetails
}
