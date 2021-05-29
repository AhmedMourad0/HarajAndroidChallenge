package com.example.harajtask

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.fakes.FakeFindPostDetails
import com.example.harajtask.posts.viewmodel.PostDetailsViewModel
import com.example.harajtask.posts.viewmodel.PostDetailsViewModel.State
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostDetailsViewModelTests {

    private lateinit var post: Post
    private lateinit var vm: PostDetailsViewModel

    @Before
    fun setup() {
        val posts = randomPosts(randomId = true)
        post = posts.random()
        vm = PostDetailsViewModel(
            SavedStateHandle(
                mapOf(PostDetailsViewModel.KEY_POST_ID to post.id.value)
            ), FakeFindPostDetails(posts)
        )
    }

    @Test
    fun state_changesToDataWithThePostWhenInitialized(): Unit = runBlocking {
        assertEquals(
            State.Data(post),
            vm.state.take(1).first()
        )
    }
}
