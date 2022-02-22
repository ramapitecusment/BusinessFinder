package com.example.businessfinder.scenes.login

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {
    override val viewModel: LoginViewModel by viewModel()
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)


}