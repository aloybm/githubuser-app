package com.aloysius.aplikasigithub.repository

import androidx.recyclerview.widget.DiffUtil
import com.aloysius.aplikasigithub.database.Favorite

class FavoriteDiffCallback(
    private val oldFavoriteList: List<Favorite>,
    private val newFavoriteList: List<Favorite>,
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size
    override fun getNewListSize(): Int = newFavoriteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].id == newFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldFavoriteList[oldItemPosition]
        val newNote = newFavoriteList[newItemPosition]
        return oldNote.username == newNote.username && oldNote.avatarUrl == newNote.avatarUrl
    }
}