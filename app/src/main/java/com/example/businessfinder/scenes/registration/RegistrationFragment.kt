package com.example.businessfinder.scenes.registration

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.Navigator
import com.example.businessfinder.common.extensions.bind
import com.example.businessfinder.common.extensions.bindAction
import com.example.businessfinder.common.extensions.bindTextTwoWay
import com.example.businessfinder.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>(R.layout.fragment_registration) {
    override val viewModel: RegistrationViewModel by viewModel()
    override val binding: FragmentRegistrationBinding by viewBinding(FragmentRegistrationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
    }

    private fun bindView() {
        with(binding) {
            with(viewModel) {
                signUpButton.setOnClickListener { onSignUpClicked() }
            }
        }
    }

    private fun bindViewModel() {
        with(binding) {
            with(viewModel) {
                bindTextTwoWay(binFlow, tieBin)
                bindTextTwoWay(emailFlow, tieEmail)
                bindTextTwoWay(sphereIdFlow, tieSphereId)
                bindTextTwoWay(passwordFlow, tiePassword)
                bindTextTwoWay(companyNameFlow, tieCompanyName)
                bindTextTwoWay(phoneNumberFlow, tiePhoneNumber)
                bindTextTwoWay(passwordRepeatFlow, tiePasswordRepeat)
                bindAction(navigateProfileScreenFlow) { Navigator.goToProfileScreenClearingStack(this@RegistrationFragment.findNavController()) }
            }
        }
    }
}