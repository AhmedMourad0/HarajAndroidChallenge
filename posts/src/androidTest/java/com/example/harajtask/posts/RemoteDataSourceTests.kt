package com.example.harajtask.posts

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.posts.parser.ParserImpl
import com.example.harajtask.posts.parser.model.JsonPost
import com.example.harajtask.posts.repository.dependencies.RemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RemoteDataSourceTests {

    private lateinit var source: RemoteDataSource
    private lateinit var posts: List<Post>

    @Before
    fun setup() {
        posts = randomPosts()
        val postsJson = Json {  }.encodeToString(
            ListSerializer(JsonPost.serializer()),
            posts.map(Post::toJsonPost)
        )
        source = ParserImpl(postsJson.byteInputStream())
    }

    @Test
    fun findPosts_readsTheJsonFromTheInputStreamAndParsesItIntoListOfPosts() = runBlocking {
        val parsed = source.findPosts().first()
        assertEquals(posts.size, parsed.size)
        assertTrue(parsed.all { p ->
            posts.any { it.equalsIgnoringId(p) }
        })
    }
}
