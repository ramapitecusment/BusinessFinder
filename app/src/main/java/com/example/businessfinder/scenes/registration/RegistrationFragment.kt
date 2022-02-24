package com.example.businessfinder.scenes.registration

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<RegistrationViewModel>(R.layout.fragment_registration) {
    override val viewModel: RegistrationViewModel by viewModel()
    private val binding: FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        with(binding) {
            with(viewModel) {
                signUpButton.setOnClickListener {
                    onSignUpClicked()
                }
            }
        }
    }

    private fun bindViewModel() {

    }
}