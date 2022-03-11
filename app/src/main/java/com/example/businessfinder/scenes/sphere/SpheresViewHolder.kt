package com.example.businessfinder.scenes.sphere

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businessfinder.databinding.ItemSpheresBinding
import com.example.businessfinder.models.Sphere

class SpheresViewHolder(
    private val binding: ItemSpheresBinding,
    private val onClick: (sphereId: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(sphere: Sphere) {
        binding.sphereButton.text = sphere.name
        binding.sphereButton.setOnClickListener { onClick(sphere.id) }
    }

    companion object {
        fun from(parent: ViewGroup, onClick: (sphereId: String) -> Unit): SpheresViewHolder = SpheresViewHolder(
            ItemSpheresBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
        )
    }

}