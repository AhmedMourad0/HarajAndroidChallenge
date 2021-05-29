package com.example.harajtask.posts.di

import com.example.harajtask.domain.posts.PostsRepository
import com.example.harajtask.posts.local.di.LocalBindingsModule
import com.example.harajtask.posts.local.di.LocalProvidersModule
import com.example.harajtask.posts.parser.di.ParserBindingsModule
import com.example.harajtask.posts.parser.di.ParserProvidersModule
import com.example.harajtask.posts.repository.PostsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(includes = [
    LocalBindingsModule::class,
    LocalProvidersModule::class,
    ParserBindingsModule::class,
    ParserProvidersModule::class,
    PostsBindingsModule::class
])
interface PostsModule

@Module
internal interface PostsBindingsModule {
    @Binds
    @Reusable
    fun bindPostsRepository(
        impl: PostsRepositoryImpl
    ): PostsRepository
}
