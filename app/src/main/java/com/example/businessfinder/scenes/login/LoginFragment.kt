package com.example.businessfinder.scenes.login

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.Navigator
import com.example.businessfinder.common.extensions.bind
import com.example.businessfinder.common.extensions.bindAction
import com.example.businessfinder.common.extensions.bindTextTwoWay
import com.example.businessfinder.common.extensions.hideKeyboard
import com.example.businessfinder.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {
    override val viewModel: LoginViewModel by viewModel()
    override val binding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        with(binding) {
            signInButton.setOnClickListener {
                viewModel.onSignInClicked()
                hideKeyboard()
            }
            signUpButton.setOnClickListener {
                viewModel.onSignUpClicked()
                hideKeyboard()
            }
        }
    }

    private fun bindViewModel() {
        with(binding) {
            with(viewModel) {
                bindTextTwoWay(emailFlow, tieLogin)
                bindTextTwoWay(passwordFlow, tiePassword)
                bind(emailErrorFlow) { tieLogin.error = it }
                bind(passwordErrorFlow) { tiePassword.error = it }
                bindAction(navigateProfileScreen) { Navigator.goToProfileScreenClearingStack(this@LoginFragment) }
                bindAction(navigateRegistrationScreen) { Navigator.goToRegistrationScreen(this@LoginFragment) }
            }
        }
    }

}