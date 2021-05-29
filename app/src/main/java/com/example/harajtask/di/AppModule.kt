package com.example.harajtask.di

import android.content.Context
import com.example.harajtask.common.AssistedViewModelFactory
import com.example.harajtask.common.MainViewModel
import com.example.harajtask.posts.viewmodel.FindPostsViewModel
import com.example.harajtask.posts.viewmodel.PostDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ContextModule::class, AppBindingsModule::class])
interface AppModule

@Module
interface AppBindingsModule {

    @Binds
    fun bindMainViewModel(
        impl: MainViewModel.Factory
    ): AssistedViewModelFactory<MainViewModel>

    @Binds
    fun bindFindPostsViewModel(
        impl: FindPostsViewModel.Factory
    ): AssistedViewModelFactory<FindPostsViewModel>

    @Binds
    fun bindPostDetailsViewModel(
        impl: PostDetailsViewModel.Factory
    ): AssistedViewModelFactory<PostDetailsViewModel>
}

@Module
class ContextModule(private val appCtx: Context) {
    @Provides
    fun provideAppContext(): Context {
        return appCtx
    }
}
