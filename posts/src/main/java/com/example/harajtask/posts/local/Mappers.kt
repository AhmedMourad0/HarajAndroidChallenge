package com.example.harajtask.posts.local

import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.domain.posts.model.SimplePost
import com.example.harajtask.posts.local.entities.PostEntity

internal fun Post.toPostEntity(): PostEntity = PostEntity(
    title = title,
    username = username,
    thumbUrl = thumbUrl,
    commentCount = commentCount,
    city = city,
    date = date,
    body = body
)


internal fun PostEntity.toPost(): Post = Post(
    id = PostId(id),
    title = title,
    username = username,
    thumbUrl = thumbUrl,
    commentCount = commentCount,
    city = city,
    date = date,
    body = body
)


internal fun PostEntity.toSimplePost(): SimplePost = SimplePost(
    id = PostId(id),
    title = title,
    username = username,
    thumbUrl = thumbUrl,
    commentCount = commentCount,
    city = city,
    date = date
)
