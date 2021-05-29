package com.example.harajtask.posts

import androidx.paging.DifferCallback
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harajtask.domain.posts.PostsRepository
import com.example.harajtask.posts.fakes.FakeLocalDataSource
import com.example.harajtask.posts.fakes.FakeRemoteDataSource
import com.example.harajtask.posts.repository.PostsRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//Testing findPosts requires creating a custom PagingDataDiffer (which currently has a rough api)
// as a presenter, an official sample of which is not yet available
// which is an overkill for our case
@RunWith(AndroidJUnit4::class)
class PostsRepositoryTests {

    private lateinit var local: FakeLocalDataSource
    private lateinit var remote: FakeRemoteDataSource
    private lateinit var repo: PostsRepository

    @Before
    fun setup() {
        local = FakeLocalDataSource()
        remote = FakeRemoteDataSource()
        repo = PostsRepositoryImpl(remote, local)
    }

    @Test
    fun findPostDetails_readsTheJsonFromTheInputStreamAndParsesItIntoListOfPosts() = runBlocking {
        val posts = randomPosts(randomId = true)
        remote.data.value = posts
        local.replaceAllPosts(posts)
        repeat(posts.indices.random().coerceAtLeast(3)) {
            val post = posts.random()
            val retrieved = repo.findPostDetails(post.id)
            assertNotNull(retrieved)
            assertEquals(post, retrieved)
        }
    }
}
