package com.example.businessfinder.scenes.login

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.Navigator
import com.example.businessfinder.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {
    override val viewModel: LoginViewModel by viewModel()
    private val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        binding.signInButton.setOnClickListener {
            viewModel.onSignInClicked()
        }
        binding.signUpButton.setOnClickListener {
            Navigator.goToRegistrationScreen(this)
        }
    }

    private fun bindViewModel() {

    }

}