package com.example.businessfinder.scenes.sphere

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.Navigator
import com.example.businessfinder.common.extensions.bindRecyclerViewAdapter
import com.example.businessfinder.common.extensions.bindTitle
import com.example.businessfinder.databinding.FragmentSpheresBinding
import com.example.businessfinder.models.SearchOffer
import org.koin.androidx.viewmodel.ext.android.viewModel

class SphereFragment : BaseFragment<SphereViewModel, FragmentSpheresBinding>(R.layout.fragment_spheres) {
    private val args: SphereFragmentArgs by navArgs()

    override val viewModel: SphereViewModel by viewModel()
    override val binding: FragmentSpheresBinding by viewBinding(FragmentSpheresBinding::bind)

    private val onClick: (sphereId: String) -> Unit = { Navigator.goToOffersScreen(this, SearchOffer(sphereId = it)) }
    private val adapter = SpheresRecyclerViewAdapter(onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
        viewModel.start(args.categoryId, args.categoryName)
    }

    private fun bindView() {
        binding.spheresRecyclerView.adapter = adapter
    }

    private fun bindViewModel() {
        bindTitle(viewModel.title)
        bindRecyclerViewAdapter(viewModel.spheres, adapter)
    }
}