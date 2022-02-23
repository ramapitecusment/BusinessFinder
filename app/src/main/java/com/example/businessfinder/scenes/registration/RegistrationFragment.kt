package com.example.businessfinder.scenes.registration

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<RegistrationViewModel>(R.layout.fragment_registration) {
    override val viewModel: RegistrationViewModel by viewModels()
    private val binding: FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)
}