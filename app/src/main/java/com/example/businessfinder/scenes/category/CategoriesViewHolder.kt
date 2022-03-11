package com.example.businessfinder.scenes.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businessfinder.common.extensions.glideImage
import com.example.businessfinder.databinding.ItemCategoriesBinding
import com.example.businessfinder.models.Category

class CategoriesViewHolder(
    private val binding: ItemCategoriesBinding,
    private val onClick: (category: Category) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category) {
        with(binding) {
            categoryPhoto.glideImage(category.photoUrl)
            categoryText.text = category.name
            root.setOnClickListener { onClick(category) }
        }
    }

    companion object {
        fun from(parent: ViewGroup, onClick: (category: Category) -> Unit) = CategoriesViewHolder(
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
        )
    }
}