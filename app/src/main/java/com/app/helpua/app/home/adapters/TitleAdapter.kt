package com.app.helpua.app.home.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.helpua.R
import com.app.helpua.databinding.TitleItemBinding

class TitleAdapter(
    val navigate: () -> Unit,
    val showMenu: (view: View) -> Unit,
    val infoAction: () -> Unit,
    val signOut: () -> Unit
) :
    ListAdapter<TitleModel, TitleAdapter.ScreenshotsViewHolder>(
        ScreenshotsDiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotsViewHolder {
        val binding =
            TitleItemBinding.inflate(
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
        private val binding: TitleItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(model: TitleModel) = with(binding) {

            if (model.user != null) {
                tvUserName.text = model.user.displayName.toString()
                Log.d("USER:", model.toString())
                ivUser.setImageResource(R.drawable.sign_out_icon)
            } else {
                ivUser.setImageResource(R.drawable.circle_account_icon)

                tvUserName.setText(R.string.no_auth_text)
            }
            ivUserButton.setOnClickListener {
                if (model.user == null) {
                    navigate()
                } else {
                    signOut()
                }
            }

            btInfo.setOnClickListener {
                infoAction()
            }

            btSort.setOnClickListener {
                showMenu(it)
            }
        }


    }

    object ScreenshotsDiffCallback : DiffUtil.ItemCallback<TitleModel>() {
        override fun areItemsTheSame(
            oldItem: TitleModel,
            newItem: TitleModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: TitleModel,
            newItem: TitleModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}