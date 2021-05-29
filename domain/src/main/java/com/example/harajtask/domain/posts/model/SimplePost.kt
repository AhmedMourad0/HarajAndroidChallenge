package com.example.harajtask.domain.posts.model

data class SimplePost(
    val id: PostId,
    val title: String,
    val date: Long,
    val username: String,
    val thumbUrl: String,
    val city: String,
    val commentCount: Int
)
