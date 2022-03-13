package com.example.businessfinder.scenes.offers

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.businessfinder.models.OfferListItem

class OffersRecyclerViewAdapter(
    private val onClick: (offerItem: OfferListItem) -> Unit
) : ListAdapter<OfferListItem, OffersViewHolder>(OffersCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OffersViewHolder.from(parent, onClick)

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class OffersCallback : DiffUtil.ItemCallback<OfferListItem>() {
    override fun areItemsTheSame(oldItem: OfferListItem, newItem: OfferListItem): Boolean {
        return oldItem.offer.id == newItem.offer.id
    }

    override fun areContentsTheSame(oldItem: OfferListItem, newItem: OfferListItem): Boolean {
        return oldItem == newItem
    }
}