package com.example.harajtask.posts.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harajtask.R
import com.example.harajtask.databinding.FragmentFindPostsBinding
import com.example.harajtask.di.injector
import com.example.harajtask.domain.posts.model.SimplePost
import com.example.harajtask.posts.viewmodel.FindPostsViewModel
import com.example.harajtask.common.AssistedViewModelFactory
import com.example.harajtask.common.SimpleSavedStateViewModelFactory
import com.example.harajtask.posts.adapter.PostsRecyclerAdapter
import com.example.harajtask.posts.viewmodel.PostDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

private val SIMPLE_POSTS_COMPARATOR = object : DiffUtil.ItemCallback<SimplePost>() {
    override fun areItemsTheSame(old: SimplePost, new: SimplePost): Boolean {
        return old.id == new.id
    }
    override fun areContentsTheSame(old: SimplePost, new: SimplePost): Boolean {
        return old == new
    }
}

class FindPostsFragment : Fragment(R.layout.fragment_find_posts) {

    @Inject
    lateinit var viewModelFactory: Provider<AssistedViewModelFactory<FindPostsViewModel>>

    private val viewModel: FindPostsViewModel by viewModels {
        SimpleSavedStateViewModelFactory(
                this,
                viewModelFactory,
            FindPostsViewModel.defaultArgs()
        )
    }

    private var binding: FragmentFindPostsBinding? = null

    private lateinit var adapter: PostsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireContext().injector.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFindPostsBinding.bind(view)
        initializePostsList()
        initializeStateObservers()
        binding?.let { b ->
            val activity = requireActivity() as AppCompatActivity
            activity.setSupportActionBar(b.appBar.toolbar)
            activity.supportActionBar?.setHomeButtonEnabled(true)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_drawer)
            b.appBar.toolbar.setNavigationOnClickListener {
                Toast.makeText(context, R.string.coming_soon, Toast.LENGTH_LONG).show()
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_find_posts, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(context, R.string.coming_soon, Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializePostsList() {
        adapter = PostsRecyclerAdapter(requireContext(), SIMPLE_POSTS_COMPARATOR) { transitions, post ->
            val extras = FragmentNavigatorExtras(*transitions.toTypedArray())
            val action = FindPostsFragmentDirections
                    .actionFindPostsFragmentToPostDetailsFragment(post.id.value)
            findNavController().navigate(action, extras)
        }
        binding?.let { b ->
            b.recycler.adapter = adapter
            b.recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            b.recycler.isVerticalScrollBarEnabled = true
            postponeEnterTransition()
            b.recycler.viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun initializeStateObservers() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is FindPostsViewModel.State.Data -> {
                        binding?.let { b ->
                            b.recycler.visibility = View.VISIBLE
                            b.error.root.visibility = View.GONE
                            b.loading.root.visibility = View.GONE
                        }
                        adapter.submitData(state.data.map {
                            it
                        })
                    }
                    FindPostsViewModel.State.Error -> {
                        binding?.let { b ->
                            b.recycler.visibility = View.GONE
                            b.loading.root.visibility = View.GONE
                            b.error.root.visibility = View.VISIBLE
                            b.error.errorMessage.setText(R.string.something_went_wrong)
                            b.error.errorIcon.setImageResource(R.drawable.ic_error)
                        }
                    }
                    FindPostsViewModel.State.Loading -> {
                        binding?.let { b ->
                            b.recycler.visibility = View.GONE
                            b.error.root.visibility = View.GONE
                            b.loading.root.visibility = View.VISIBLE
                        }
                        adapter.submitData(PagingData.empty())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
