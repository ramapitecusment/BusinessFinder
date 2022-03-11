package com.example.businessfinder.scenes.category

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.businessfinder.models.Category

class CategoriesRecyclerViewAdapter(
    private val onClick: (category: Category) -> Unit
) : ListAdapter<Category, CategoriesViewHolder>(CategoryCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoriesViewHolder.from(parent, onClick)

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}