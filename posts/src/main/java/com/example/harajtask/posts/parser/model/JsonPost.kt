package com.example.harajtask.posts.parser.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class JsonPost(
    val title: String,
    val date: Long,
    val username: String,
    @SerialName("thumbURL") val thumbUrl: String,
    val city: String,
    val commentCount: Int,
    val body: String
)
