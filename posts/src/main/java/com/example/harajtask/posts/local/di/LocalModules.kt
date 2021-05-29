package com.example.harajtask.posts.local.di

import android.content.Context
import com.example.harajtask.posts.di.InternalApi
import com.example.harajtask.posts.local.LocalDataSourceImpl
import com.example.harajtask.posts.local.PostsDatabase
import com.example.harajtask.posts.local.daos.PostsDao
import com.example.harajtask.posts.repository.dependencies.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
internal interface LocalBindingsModule {
    @Binds
    @Reusable
    @InternalApi
    fun bindLocalDataSource(
        impl: LocalDataSourceImpl
    ): LocalDataSource
}

@Module
internal object LocalProvidersModule {

    @Provides
    @Singleton
    @InternalApi
    @JvmStatic
    fun providePostsDatabase(appCtx: Context): PostsDatabase {
        return PostsDatabase.getInstance(appCtx)
    }

    @Provides
    @Reusable
    @InternalApi
    @JvmStatic
    fun providePostsDao(@InternalApi db: PostsDatabase): PostsDao {
        return db.postsDao()
    }
}
