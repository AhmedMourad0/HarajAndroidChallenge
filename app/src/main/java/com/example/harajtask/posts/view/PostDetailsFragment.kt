package com.example.harajtask.posts.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.harajtask.R
import com.example.harajtask.common.AssistedViewModelFactory
import com.example.harajtask.common.SimpleSavedStateViewModelFactory
import com.example.harajtask.databinding.FragmentPostDetailsBinding
import com.example.harajtask.di.injector
import com.example.harajtask.domain.posts.model.Post
import com.example.harajtask.domain.posts.model.PostId
import com.example.harajtask.posts.viewmodel.PostDetailsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

private const val DATE_TIME_FORMAT = "yyyy-MM-dd  HH:mm aa"

class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {

    @Inject
    lateinit var viewModelFactory: Provider<AssistedViewModelFactory<PostDetailsViewModel>>

    private val args: PostDetailsFragmentArgs by navArgs()

    private val viewModel: PostDetailsViewModel by viewModels {
        SimpleSavedStateViewModelFactory(
                this,
                viewModelFactory,
            PostDetailsViewModel.defaultArgs(PostId(args.postId))
        )
    }

    private var binding: FragmentPostDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
        requireContext().injector.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDetailsBinding.bind(view)
        prepareForSharedTransition()
        binding?.let { b ->
            val activity = requireActivity() as AppCompatActivity
            activity.setSupportActionBar(b.toolbar)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            b.toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
        initializeStateObservers()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_post_details, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {

                val post = viewModel.state.value as? PostDetailsViewModel.State.Data

                if (post != null) {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, post.item.title)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun populateUi(post: Post) {
        binding?.let { b ->

            Glide.with(requireContext())
                .load(post.thumbUrl)
                .error(R.drawable.ic_error)
                .into(b.thumbnail)

            b.date.text = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
                .format(Date(post.date))

            b.title.text = post.title
            b.username.text = post.username
            b.city.text = post.city
            b.body.text =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(post.body.trim(), Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(post.body.trim())
            }
        }
    }

    private fun prepareForSharedTransition() {
        binding?.let { b ->
            listOf(
                b.thumbnail to getString(R.string.thumb_image_transition, args.postId),
                // There exists a bug in Android with TextView scaling in shared transitions
                // The community-accepted solution is too hacky for the purpose of this app
//                b.title to getString(R.string.thumb_title_transition, args.postId),
//                b.city to getString(R.string.thumb_city_transition, args.postId),
//                b.cityIcon to getString(R.string.thumb_city_icon_transition, args.postId),
//                b.username to getString(R.string.thumb_username_transition, args.postId),
//                b.usernameIcon to getString(R.string.thumb_username_icon_transition, args.postId)
            ).forEach { (view, name) ->
                ViewCompat.setTransitionName(view, name)
            }
        }
    }

    private fun initializeStateObservers() {
        viewModel.state.onEach { state ->
            when (state) {
                is PostDetailsViewModel.State.Data -> populateUi(state.item)
                PostDetailsViewModel.State.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        R.string.something_went_wrong,
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigateUp()
                }
                PostDetailsViewModel.State.Loading -> {
                    // We could use Shimmer here, it would look good and
                    // work well with the shared transition, but I'm running out of time,
                    // so here's a TODO
                }
                PostDetailsViewModel.State.NoData -> {
                    Toast.makeText(
                        requireActivity(),
                        R.string.post_deleted_or_expired,
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigateUp()
                }
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
