package com.example.harajtask.posts.parser.di

import com.example.harajtask.posts.di.InternalApi
import com.example.harajtask.posts.parser.InputStreamFactory
import com.example.harajtask.posts.parser.ParserImpl
import com.example.harajtask.posts.parser.openFileFromAssets
import com.example.harajtask.posts.repository.dependencies.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
internal interface ParserBindingsModule {
    @Binds
    @Reusable
    @InternalApi
    fun bindParser(
        impl: ParserImpl
    ): RemoteDataSource
}

@Module
internal object ParserProvidersModule {
    @Provides
    @InternalApi
    @Reusable
    @JvmStatic
    fun provideFileInputStream(): InputStreamFactory {
        return ::openFileFromAssets
    }
}

