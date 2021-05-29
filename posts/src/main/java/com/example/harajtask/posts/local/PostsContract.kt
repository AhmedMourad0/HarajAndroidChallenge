package com.example.harajtask.posts.local

internal object PostsContract {

    const val DATABASE_NAME = "posts"

    object Post {
        const val TABLE_NAME = "post"
        const val COL_ID = "id"
        const val COL_TITLE = "title"
        const val COL_USERNAME = "username"
        const val COL_THUMB_URL = "thumb_url"
        const val COL_COMMENT_COUNT = "comment_count"
        const val COL_CITY = "city"
        const val COL_DATE = "date"
        const val COL_BODY = "body"
    }
}
