package com.example.harajtask.posts.parser

import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.posts.parser.model.JsonPost

internal fun JsonPost.toPost(): Post = Post(
    id = PostId(0),
    title = title,
    username = username,
    thumbUrl = thumbUrl,
    commentCount = commentCount,
    city = city,
    date = date,
    body = body
)
