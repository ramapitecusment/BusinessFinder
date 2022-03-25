package com.example.businessfinder.scenes.category

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.Navigator
import com.example.businessfinder.common.extensions.bindRecyclerViewAdapter
import com.example.businessfinder.databinding.FragmentCategoriesBinding
import com.example.businessfinder.models.Category
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : BaseFragment<CategoriesViewModel, FragmentCategoriesBinding>(R.layout.fragment_categories) {
    override val viewModel: CategoriesViewModel by viewModel()
    override val binding: FragmentCategoriesBinding by viewBinding(FragmentCategoriesBinding::bind)

    private val onClick: (category: Category) -> Unit = { Navigator.goToSpheresScreen(this, it) }
    private val adapter = CategoriesRecyclerViewAdapter(onClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.categoriesRecyclerView.adapter = adapter
    }

    private fun bindViewModel() {
        bindRecyclerViewAdapter(viewModel.categories, adapter)
    }
}