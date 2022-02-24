package com.example.businessfinder.scenes.profile

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.extensions.bindImage
import com.example.businessfinder.common.extensions.bindText
import com.example.businessfinder.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel: ProfileViewModel by viewModel()
    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        with(binding) {
            with(viewModel) {
                partners.setOnClickListener {

                }
                offers.setOnClickListener {

                }
            }
        }
    }

    private fun bindViewModel() {
        with(binding) {
            with(viewModel) {
                bindText(binFlow, bin)
                bindText(emailFlow, email)
                bindText(companyNameFlow, companyName)
                bindImage(photoUrlFlow, profilePhoto)
            }
        }
    }
}