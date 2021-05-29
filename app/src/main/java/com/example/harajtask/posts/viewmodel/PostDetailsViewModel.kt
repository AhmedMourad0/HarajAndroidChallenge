package com.example.harajtask.posts.viewmodel

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harajtask.common.AssistedViewModelFactory
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.domain.posts.usecases.FindPostDetails
import dagger.Reusable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class PostDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    findPostDetails: FindPostDetails
) : ViewModel() {

    private val postId: PostId = PostId(savedStateHandle.get<Long>(KEY_POST_ID)!!)

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    init {
        viewModelScope.launch {
            try {
                val p = findPostDetails.execute(postId)
                _state.value = if (p == null) State.NoData else State.Data(p)
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                } else {
                    _state.value = State.Error
                }
            }
        }
    }

    sealed interface State {
        data class Data(val item: Post) : State
        object NoData : State
        object Loading : State
        object Error : State
    }

    @Reusable
    class Factory @Inject constructor(
        private val findPostDetails: FindPostDetails
    ) : AssistedViewModelFactory<PostDetailsViewModel> {
        override fun invoke(handle: SavedStateHandle): PostDetailsViewModel {
            return PostDetailsViewModel(handle, findPostDetails)
        }
    }

    companion object {

        @VisibleForTesting
        internal const val KEY_POST_ID =
            "com.example.harajtask.posts.viewmodel.key.Post_ID"

        fun defaultArgs(postId: PostId): Bundle {
            return Bundle(1).apply {
                putLong(KEY_POST_ID, postId.value)
            }
        }
    }
}
