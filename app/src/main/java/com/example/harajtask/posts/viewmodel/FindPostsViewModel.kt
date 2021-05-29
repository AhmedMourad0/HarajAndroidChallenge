package com.example.harajtask.posts.viewmodel

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.harajtask.common.AssistedViewModelFactory
import com.example.harajtask.domain.posts.model.SimplePost
import com.example.harajtask.domain.posts.usecases.FindPosts
import dagger.Reusable
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class FindPostsViewModel(
    findPosts: FindPosts
) : ViewModel() {

    val state = findPosts.execute(PAGE_SIZE)
        .cachedIn(viewModelScope)
        .map<PagingData<SimplePost>, State> {
            State.Data(it)
        }.catch {
            emit(State.Error)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = State.Loading
        )

    sealed interface State {
        data class Data(val data: PagingData<SimplePost>) : State
        object Loading : State
        object Error : State
    }

    @Reusable
    class Factory @Inject constructor(
        private val findPosts: FindPosts
    ) : AssistedViewModelFactory<FindPostsViewModel> {
        override fun invoke(handle: SavedStateHandle): FindPostsViewModel {
            return FindPostsViewModel(findPosts)
        }
    }

    companion object {
        private const val PAGE_SIZE = 6
        fun defaultArgs(): Bundle? = null
    }
}
