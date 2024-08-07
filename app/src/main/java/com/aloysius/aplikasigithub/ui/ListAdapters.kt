package com.aloysius.aplikasigithub.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aloysius.aplikasigithub.data.response.ItemsItem
import com.aloysius.aplikasigithub.databinding.ListRowUsersBinding
import com.bumptech.glide.Glide

class ListAdapters : ListAdapter<ItemsItem, ListAdapters.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ListRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra("username", user.login)
            context.startActivity(intent)
        }
    }

    inner class UserViewHolder(private val binding: ListRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.tvName.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.tvImg)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}