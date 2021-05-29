package com.example.harajtask.posts.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.harajtask.posts.local.entities.*
import com.example.harajtask.posts.local.daos.*

@Database(entities = [PostEntity::class], version = 1)
internal abstract class PostsDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

    internal companion object {

        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getInstance(appCtx: Context) = INSTANCE ?: synchronized(PostsDatabase::class.java) {
            INSTANCE ?: buildDatabase(appCtx).also { INSTANCE = it }
        }

        private fun buildDatabase(appCtx: Context) = Room.databaseBuilder(
            appCtx,
            PostsDatabase::class.java,
            PostsContract.DATABASE_NAME
        ).build()
    }
}
