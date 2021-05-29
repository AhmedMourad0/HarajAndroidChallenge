package com.example.harajtask.domain.di

import com.example.harajtask.domain.posts.di.PostsBindingsModule
import dagger.Module

@Module(includes = [
    PostsBindingsModule::class
])
interface DomainModule
