package com.example.harajtask.posts

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.posts.local.LocalDataSourceImpl
import com.example.harajtask.posts.local.PostsDatabase
import com.example.harajtask.posts.local.toPost
import com.example.harajtask.posts.local.toPostEntity
import com.example.harajtask.posts.repository.dependencies.LocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalDataSourceTests {

    private lateinit var db: PostsDatabase
    private lateinit var source: LocalDataSource

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, PostsDatabase::class.java).build()
        source = LocalDataSourceImpl(db.postsDao())
    }

    @Test
    @Throws(Exception::class)
    fun replaceAllPosts_removesAllCachedPostsAndReplacesThemWithTheGivenOnes() = runBlocking {
        assertEquals(0, db.postsDao().findAllPosts().first().size)
        repeat(3) {
            val posts = randomPosts()
            source.replaceAllPosts(posts)
            val retrievedPosts = db.postsDao().findAllPosts().first()
            assertEquals(posts.size, retrievedPosts.size)
            assertTrue(posts.all { p ->
                retrievedPosts.any { it.toPost().equalsIgnoringId(p) }
            })
        }
    }

    @Test
    @Throws(Exception::class)
    fun findPostDetails_returnsThePostWithTheGivenPostId() = runBlocking {
        assertEquals(0, db.postsDao().findAllPosts().first().size)
        val posts = randomPosts()
        db.postsDao().replaceAllTransaction(posts.map(Post::toPostEntity))
        repeat(posts.indices.random().coerceAtLeast(3)) {
            val index = posts.indices.random()
            val retrieved = source.findPostDetails(PostId((index + 1).toLong()))
            assertNotNull(retrieved)
            assertTrue(retrieved!!.equalsIgnoringId(posts[index]))
        }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}
