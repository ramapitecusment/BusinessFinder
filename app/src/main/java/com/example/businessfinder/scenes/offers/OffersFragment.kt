package com.example.businessfinder.scenes.offers

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.Navigator
import com.example.businessfinder.common.extensions.bindRecyclerViewAdapter
import com.example.businessfinder.databinding.FragmentOffersBinding
import com.example.businessfinder.models.OfferListItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class OffersFragment : BaseFragment<OffersViewModel, FragmentOffersBinding>(R.layout.fragment_offers) {
    private val args: OffersFragmentArgs by navArgs()

    override val viewModel: OffersViewModel by viewModel()
    override val binding: FragmentOffersBinding by viewBinding(FragmentOffersBinding::bind)

    private val onClick: (offerItem: OfferListItem) -> Unit = { Navigator.goToOfferScreen(this, it) }
    private val adapter = OffersRecyclerViewAdapter(onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
        viewModel.start(args.searchOffer)
    }

    private fun bindView() {
        binding.offersRecyclerView.adapter = adapter
        binding.offersSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchTextChanged(newText ?: "")
                return true
            }

        })
    }

    private fun bindViewModel() {
        bindRecyclerViewAdapter(viewModel.offers, adapter)
    }

}