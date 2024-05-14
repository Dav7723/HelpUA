package com.app.helpua.app.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.helpua.R
import com.app.helpua.app.GUMMANITAR_TYPE
import com.app.helpua.app.MEDICAL_TYPE
import com.app.helpua.app.PSYHIC_TYPE
import com.app.helpua.databinding.PostItemBinding
import com.app.helpua.domain.models.Post
import com.squareup.picasso.Picasso

class HomeAdapter(private val navigate: (post: Post) -> Unit) :
    ListAdapter<Post, HomeAdapter.ScreenshotsViewHolder>(
        ScreenshotsDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotsViewHolder {
        val binding =
            PostItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ScreenshotsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreenshotsViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    inner class ScreenshotsViewHolder(
        private val binding: PostItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(model: Post) = with(binding) {
            Picasso.get().load(if (model.image?.isEmpty() == true) "error" else model.image)
                .placeholder(R.color.gray)
                .error(R.drawable.default_holder)
                .into(ivImage)

            tvText.text = model.title
            when (model.type) {
                MEDICAL_TYPE -> {
                    tvText.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.medical_type_color
                        )
                    )
                }

                GUMMANITAR_TYPE -> {
                    tvText.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.gumanitar_type_color
                        )
                    )
                }

                PSYHIC_TYPE -> {
                    tvText.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.psyhical_type_color
                        )
                    )
                }
            }

            root.setOnClickListener {
                navigate(model)
            }


        }
    }

    object ScreenshotsDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(
            oldItem: Post,
            newItem: Post
        ): Boolean {
            return oldItem.image == newItem.image
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Post,
            newItem: Post
        ): Boolean {
            return oldItem == newItem
        }
    }
}