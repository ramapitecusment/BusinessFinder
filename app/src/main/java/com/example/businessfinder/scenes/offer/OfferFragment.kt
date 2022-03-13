package com.example.businessfinder.scenes.offer

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.extensions.glideProfileFirebaseImage
import com.example.businessfinder.databinding.FragmentOfferBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment : BaseFragment<OfferViewModel, FragmentOfferBinding>(R.layout.fragment_offer) {
    private val args: OfferFragmentArgs by navArgs()

    override val viewModel: OfferViewModel by viewModel()
    override val binding: FragmentOfferBinding by viewBinding(FragmentOfferBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        val _deadline = args.offerItem.offer.dayDeadline
        with(binding) {
            price.text = "${args.offerItem.offer.price} ₸"
            companySphere.text = args.offerItem.sphere.name
            companyName.text = args.offerItem.user.companyName
            description.text = args.offerItem.offer.description
            deadline.text = if (_deadline == 0) "В тот же день" else "$_deadline дней"
            companyPhoto.glideProfileFirebaseImage(args.offerItem.user.photoUrl)
            acceptOfferButton.setOnClickListener { viewModel.onAcceptClicked() }
            declineOfferButton.setOnClickListener { viewModel.onDeclineClicked() }
        }
    }

    private fun bindViewModel() {

    }
}