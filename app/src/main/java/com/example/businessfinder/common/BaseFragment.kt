package com.example.businessfinder.common

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.businessfinder.common.extensions.bindAction

abstract class BaseFragment<TViewModel : BaseViewModel, VB: ViewBinding>(@LayoutRes lId: Int) : Fragment(lId) {
    protected abstract val viewModel: TViewModel
    protected abstract val binding: VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAction(viewModel.showToast) { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
    }
}