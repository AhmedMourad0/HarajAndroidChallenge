package com.example.harajtask.di

import com.example.harajtask.common.MainActivity
import com.example.harajtask.domain.di.DomainModule
import com.example.harajtask.posts.di.PostsModule
import com.example.harajtask.posts.view.FindPostsFragment
import com.example.harajtask.posts.view.PostDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    DomainModule::class,
    PostsModule::class,
    AppModule::class
])
@Singleton
internal interface ApplicationComponent {
    fun inject(target: MainActivity)
    fun inject(target: FindPostsFragment)
    fun inject(target: PostDetailsFragment)
}
