package com.aloysius.aplikasigithub.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aloysius.aplikasigithub.repository.FavoriteDiffCallback
import com.aloysius.aplikasigithub.database.Favorite
import com.aloysius.aplikasigithub.databinding.ListFavoriteBinding
import com.bumptech.glide.Glide

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorite = ArrayList<Favorite>()
    fun setListFavorite(listFavorite: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ListFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = listFavorite[position]
        holder.bind(favorite)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtra("username", favorite.username)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    inner class FavoriteViewHolder(private val binding: ListFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                tvNameFav.text = favorite.username
                Glide.with(binding.root)
                    .load(favorite.avatarUrl)
                    .circleCrop()
                    .into(binding.tvImgFav)
            }
        }
    }
}