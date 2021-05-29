package com.example.harajtask.domain.posts.model

//Would've used an inline (or value) class, but it's still experimental
data class PostId(val value: Long)

data class Post(
    val id: PostId,
    val title: String,
    val date: Long,
    val username: String,
    val thumbUrl: String,
    val city: String,
    val commentCount: Int,
    val body: String
)

fun Post.simplify() = SimplePost(
    id = id,
    title = title,
    date = date,
    username = username,
    thumbUrl = thumbUrl,
    city = city,
    commentCount = commentCount
)
