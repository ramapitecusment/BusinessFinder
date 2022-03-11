package com.example.businessfinder.scenes.sphere

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.businessfinder.models.Sphere

class SpheresRecyclerViewAdapter(
    private val onClick: (sphereId: String) -> Unit
) : ListAdapter<Sphere, SpheresViewHolder>(SphereCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SpheresViewHolder.from(parent, onClick)

    override fun onBindViewHolder(holder: SpheresViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SphereCallback : DiffUtil.ItemCallback<Sphere>() {
    override fun areItemsTheSame(oldItem: Sphere, newItem: Sphere): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Sphere, newItem: Sphere): Boolean {
        return oldItem == newItem
    }
}