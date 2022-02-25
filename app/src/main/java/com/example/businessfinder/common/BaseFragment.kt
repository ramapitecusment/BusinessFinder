package com.example.businessfinder.common

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.businessfinder.common.extensions.bindAction

abstract class BaseFragment<TViewModel : BaseViewModel>(@LayoutRes lId: Int) : Fragment(lId) {
    protected abstract val viewModel: TViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAction(viewModel.showToast) { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
    }
}