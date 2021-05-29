package com.example.harajtask.posts

import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.posts.parser.model.JsonPost
import java.util.*

fun randomPosts(randomId: Boolean = false): List<Post> {
    val list = mutableListOf<Post>()
    repeat((5..25).random()) {
        list.add(Post(
            id = if (randomId) PostId((0..Long.MAX_VALUE).random()) else PostId(0),
            title = UUID.randomUUID().toString(),
            date = (0..Long.MAX_VALUE).random(),
            username = UUID.randomUUID().toString(),
            thumbUrl = UUID.randomUUID().toString(),
            city = UUID.randomUUID().toString(),
            commentCount = (0..Int.MAX_VALUE).random(),
            body = UUID.randomUUID().toString(),
        ))
    }
    return list.distinct()
}

internal fun Post.toJsonPost() = JsonPost(
        title = title,
        date = date,
        username = username,
        thumbUrl = thumbUrl,
        city = city,
        commentCount = commentCount,
        body = body
)

fun Post.equalsIgnoringId(other: Post): Boolean {
    return other.copy(id = this.id) == this
}
