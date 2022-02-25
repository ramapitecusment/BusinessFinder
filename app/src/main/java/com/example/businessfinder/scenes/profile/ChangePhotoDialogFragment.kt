package com.example.businessfinder.scenes.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.R
import com.example.businessfinder.databinding.DialogBottomChangePhotoBinding
import com.example.businessfinder.scenes.profile.ProfileFragment.Companion.SELECT_PHOTO_KEY
import com.example.businessfinder.scenes.profile.ProfileFragment.Companion.TAKE_PHOTO_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChangePhotoDialogFragment : BottomSheetDialogFragment() {
    private val binding: DialogBottomChangePhotoBinding by viewBinding(DialogBottomChangePhotoBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottom_change_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            takePhotoButton.setOnClickListener {
                setFragmentResult(TAKE_PHOTO_KEY, bundleOf())
                close()
            }
            selectPhotoButton.setOnClickListener {
                setFragmentResult(SELECT_PHOTO_KEY, bundleOf())
                close()
            }
            cancelButton.setOnClickListener { close() }
        }
    }

    private fun close() = dismiss()

    companion object {
        val TAG = ChangePhotoDialogFragment::class.java.simpleName
    }
}