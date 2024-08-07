package com.aloysius.aplikasigithub.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aloysius.aplikasigithub.data.response.FollowUserResponseItem
import com.aloysius.aplikasigithub.databinding.ListFollowBinding
import com.bumptech.glide.Glide

class FollowAdapter :
    ListAdapter<FollowUserResponseItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra("username", user.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    class MyViewHolder(private val binding: ListFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowUserResponseItem) {
            binding.tvNameUser.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.tvImgUser)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowUserResponseItem>() {
            override fun areItemsTheSame(
                oldItem: FollowUserResponseItem,
                newItem: FollowUserResponseItem,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FollowUserResponseItem,
                newItem: FollowUserResponseItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}