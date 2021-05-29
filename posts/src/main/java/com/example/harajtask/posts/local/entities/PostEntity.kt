package com.example.harajtask.posts.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.harajtask.posts.local.PostsContract

@Entity(tableName = PostsContract.Post.TABLE_NAME)
internal data class PostEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PostsContract.Post.COL_ID)
    val id: Long = 0,

    @ColumnInfo(name = PostsContract.Post.COL_TITLE)
    val title: String,

    @ColumnInfo(name = PostsContract.Post.COL_USERNAME)
    val username: String,

    @ColumnInfo(name = PostsContract.Post.COL_THUMB_URL)
    val thumbUrl: String,

    @ColumnInfo(name = PostsContract.Post.COL_COMMENT_COUNT)
    val commentCount: Int,

    @ColumnInfo(name = PostsContract.Post.COL_CITY)
    val city: String,

    @ColumnInfo(name = PostsContract.Post.COL_DATE)
    val date: Long,

    @ColumnInfo(name = PostsContract.Post.COL_BODY)
    val body: String,
)
