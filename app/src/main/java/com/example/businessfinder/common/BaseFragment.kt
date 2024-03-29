package com.example.businessfinder.common

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.extensions.bindAction

abstract class BaseFragment<TViewModel : BaseViewModel, VB : ViewBinding>(@LayoutRes lId: Int) : Fragment(lId) {
    protected abstract val viewModel: TViewModel
    protected abstract val binding: VB
    private var progressDialog: AlertDialog? = null
    protected val TAG = this::class.java.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAction(viewModel.showToast) { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        bindAction(viewModel.showLoading, ::onShowLoading)
        bindAction(viewModel.popBack) { findNavController().popBackStack() }
    }

    private fun onShowLoading(isLoading: Boolean) {
        progressDialog?.dismiss()
        if (isLoading) {
            val builder = AlertDialog.Builder(requireContext(), R.style.TransparentBottomSheetDialogTheme)
            builder.setView(layoutInflater.inflate(R.layout.loading_layout, null))
            progressDialog = builder.create()
            progressDialog?.show()
        }
    }

    protected fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        progressDialog?.dismiss()
        progressDialog = null
    }
}