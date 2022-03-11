package com.example.businessfinder.scenes.request

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.extensions.bindAction
import com.example.businessfinder.databinding.FragmentRequestBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RequestFragment : BaseFragment<RequestViewModel, FragmentRequestBinding>(R.layout.fragment_request) {
    override val viewModel: RequestViewModel by viewModel()
    override val binding: FragmentRequestBinding by viewBinding(FragmentRequestBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.createOfferButton.setOnClickListener { viewModel.onCreateOfferClicked() }
        binding.searchOfferButton.setOnClickListener { viewModel.onSearchOfferClicked() }
    }

    private fun bindViewModel() {
        bindAction(viewModel.navigateOffersScreen) { }
        bindAction(viewModel.navigateProfileScreen) { }
    }
}