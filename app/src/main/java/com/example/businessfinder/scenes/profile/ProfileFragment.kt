package com.example.businessfinder.scenes.profile

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.common.BaseFragment
import com.example.businessfinder.common.Constants.MIMETYPE_IMAGE
import com.example.businessfinder.common.Navigator
import com.example.businessfinder.common.extensions.bindAction
import com.example.businessfinder.common.extensions.bindImage
import com.example.businessfinder.common.extensions.bindText
import com.example.businessfinder.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel: ProfileViewModel by viewModel()
    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        viewModel.cameraPermissionRequestResult(it)
    }
    private val requestReadStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        viewModel.storagePermissionRequestResult(it)
    }

    private val selectPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.onSelectedPhotoResult(it)
    }
    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        viewModel.onTakenPhotoResult(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindViewModel()
        viewModel.start()
    }

    private fun bindView() {
        with(binding) {
            with(viewModel) {
                exit.setOnClickListener { onExitClicked() }
                offers.setOnClickListener {}
                partners.setOnClickListener {}
                changePhotoTextView.setOnClickListener { viewModel.changePhotoClicked() }
                with(childFragmentManager) {
                    setFragmentResultListener(TAKE_PHOTO_KEY, this@ProfileFragment) { _, _ ->
                        takePhotoClicked()
                    }
                    setFragmentResultListener(SELECT_PHOTO_KEY, this@ProfileFragment) { _, _ ->
                        selectPhotoClicked()
                    }
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
                bindImage(photoUrlFlow, userImageView)
                bindAction(takePhotoFlow) { takePhoto.launch(it) }
                bindAction(selectPhotoFlow) { selectPhoto.launch(MIMETYPE_IMAGE) }
                bindAction(requestCameraPermissionFlow) { requestCameraPermission() }
                bindAction(requestReadStoragePermissionFlow) { requestReadStoragePermission() }
                bindAction(navigateLoginScreenFlow) { Navigator.goToLoginScreen(this@ProfileFragment) }
                bindAction(launchChangePhotoDialogFlow) {
                    ChangePhotoDialogFragment().show(childFragmentManager, ChangePhotoDialogFragment.TAG)
                }
            }
        }
    }

    private fun requestReadStoragePermission() {
        requestReadStoragePermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun requestCameraPermission() {
        requestCameraPermission.launch(android.Manifest.permission.CAMERA)
    }

    companion object {
        const val TAKE_PHOTO_KEY = "TAKE_PHOTO_KEY"
        const val SELECT_PHOTO_KEY = "SELECT_PHOTO_KEY"
    }
}