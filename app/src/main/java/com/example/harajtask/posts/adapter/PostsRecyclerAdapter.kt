package com.example.harajtask.posts.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harajtask.R
import com.example.harajtask.databinding.ItemPostBinding
import com.example.harajtask.domain.posts.model.SimplePost

typealias OnPostSelectedListener = (transitions: List<Pair<View, String>>, post: SimplePost) -> Unit

class PostsRecyclerAdapter(
    private val context: Context,
    diffCallback: DiffUtil.ItemCallback<SimplePost>,
    private val onPostSelectedListener: OnPostSelectedListener
) : PagingDataAdapter<SimplePost, PostsRecyclerAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(container.context)
            .inflate(R.layout.item_post, container, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        if (post != null) {
            holder.bind(post)
        } else {
            holder.bindPlaceHolder()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val glide = Glide.with(context)
        private val binding: ItemPostBinding = ItemPostBinding.bind(view)

        fun bind(item: SimplePost) {

            val relativeDate = DateUtils.getRelativeDateTimeString(
                context,
                item.date,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,
                0
            ).toString()

            binding.title.text = item.title.trim()
            binding.date.text = relativeDate
            binding.username.text = item.username.trim()
            binding.city.text = item.city.trim()
            binding.commentCount.text = context.getString(
                R.string.cd_comments_count_format,
                item.commentCount
            )

            if (item.commentCount > 0) {
                binding.commentCount.visibility = View.VISIBLE
                binding.commentCountIcon.visibility = View.VISIBLE
            } else {
                binding.commentCount.visibility = View.INVISIBLE
                binding.commentCountIcon.visibility = View.INVISIBLE
            }

            glide.load(item.thumbUrl)
                .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)))
                .error(R.drawable.ic_error)
                .into(binding.thumbnail)

            val transitions = listOf(
                binding.thumbnail to context.getString(R.string.thumb_image_transition, item.id.value),
                // There exists a bug in Android with TextView scaling in shared transitions
                // The community-accepted solution is too hacky for the purpose of this app
//                binding.title to context.getString(R.string.thumb_title_transition, item.id.value),
//                binding.city to context.getString(R.string.thumb_city_transition, item.id.value),
//                binding.cityIcon to context.getString(R.string.thumb_city_icon_transition, item.id.value),
//                binding.username to context.getString(R.string.thumb_username_transition, item.id.value),
//                binding.usernameIcon to context.getString(R.string.thumb_username_icon_transition, item.id.value)
            )

            transitions.forEach { (view, name) ->
                ViewCompat.setTransitionName(view, name)
            }

            itemView.setOnClickListener {
                onPostSelectedListener(transitions, item)
            }
        }

        fun bindPlaceHolder() {
            //Shimmer should be used here, but I'm running out of time, here's a TODO
            binding.title.text = ""
            binding.date.text = ""
            binding.username.text = ""
            binding.city.text = ""
            binding.commentCount.visibility = View.INVISIBLE
            binding.commentCountIcon.visibility = View.INVISIBLE
            binding.thumbnail.setImageDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)))
        }
    }
}
