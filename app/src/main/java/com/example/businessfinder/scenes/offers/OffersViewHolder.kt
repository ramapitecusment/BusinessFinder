package com.example.businessfinder.scenes.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businessfinder.common.extensions.glideProfileFirebaseImage
import com.example.businessfinder.databinding.ItemOfferBinding
import com.example.businessfinder.models.OfferListItem

class OffersViewHolder(
    private val binding: ItemOfferBinding,
    private val onClick: (offerId: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(offer: OfferListItem) {
        with(binding) {
            offerCard.setOnClickListener { onClick(offer.id) }
            title.text = offer.sphere.name
            companyName.text = offer.user.companyName
            description.text = offer.offer.description
            companyPhoto.glideProfileFirebaseImage(offer.user.photoUrl)
        }
    }

    companion object {
        fun from(parent: ViewGroup, onClick: (offerId: String) -> Unit): OffersViewHolder = OffersViewHolder(
            ItemOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
        )
    }

}