package com.example.businessfinder.scenes.profile

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.businessfinder.common.BaseViewModel
import com.example.businessfinder.common.Constants.FILE_PREFIX
import com.example.businessfinder.common.Constants.FILE_PROVIDER
import com.example.businessfinder.common.Constants.FILE_SUFFIX
import com.example.businessfinder.common.extensions.hasCameraPermission
import com.example.businessfinder.common.extensions.hasReadStoragePermission
import com.example.businessfinder.models.Result
import com.example.businessfinder.models.User
import com.example.businessfinder.services.StorageService
import com.example.businessfinder.services.UserService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class ProfileViewModel(
    private val userService: UserService,
    private val storageService: StorageService
) : BaseViewModel() {

    private var photoUri: Uri? = null

    val binFlow = MutableStateFlow("")
    val emailFlow = MutableStateFlow("")
    val photoUrlFlow = MutableStateFlow("")
    val companyNameFlow = MutableStateFlow("")

    val takePhotoFlow = MutableSharedFlow<Uri>()
    val selectPhotoFlow = MutableSharedFlow<Unit>()
    val launchChangePhotoDialogFlow = MutableSharedFlow<Unit>()

    val requestCameraPermissionFlow = MutableSharedFlow<Unit>()
    val requestReadStoragePermissionFlow = MutableSharedFlow<Unit>()

    private val userData = MutableStateFlow<User?>(null)

    val navigateLoginScreenFlow = MutableSharedFlow<Unit>()

    init {
        if (FirebaseAuth.getInstance().currentUser?.uid != null) {
            loadingResult()
            userService.getUser(FirebaseAuth.getInstance().currentUser!!.uid).onEach { snapshot ->
                userData.value = snapshot.toObject(User::class.java)
                successResult()
            }.launchIn(viewModelScope)
        } else viewModelScope.launch { navigateLoginScreenFlow.emit(Unit) }

        userData.onEach(::onUserReceived).launchIn(viewModelScope)
    }

    fun start() {

    }

    fun onExitClicked() {
        viewModelScope.launch {
            userService.signOut()
            navigateLoginScreenFlow.emit(Unit)
        }
    }

    private fun onUserReceived(user: User?) {
        if (user == null) return
        binFlow.value = user.bin
        emailFlow.value = user.email
        companyNameFlow.value = user.companyName
        photoUrlFlow.value = user.photoUrl
    }

    fun selectPhotoClicked() {
        viewModelScope.launch {
            if (appContext().hasReadStoragePermission()) selectPhotoFlow.emit(Unit)
            else requestReadStoragePermissionFlow.emit(Unit)
        }
    }

    fun takePhotoClicked() {
        viewModelScope.launch {
            if (appContext().hasCameraPermission() && appContext().hasReadStoragePermission()) {
                photoUri = FileProvider.getUriForFile(appContext(), FILE_PROVIDER, createFile())
                photoUri?.let { takePhotoFlow.emit(it) }
            } else if (!appContext().hasCameraPermission()) requestCameraPermissionFlow.emit(Unit)
            else if (!appContext().hasReadStoragePermission()) requestReadStoragePermissionFlow.emit(Unit)
        }
    }

    fun changePhotoClicked() {
        viewModelScope.launch {
            if (appContext().hasReadStoragePermission()) launchChangePhotoDialogFlow.emit(Unit)
            else requestReadStoragePermissionFlow.emit(Unit)
        }
    }

    fun onSelectedPhotoResult(uri: Uri?) {
        if (uri == null) return
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap: Bitmap? = Glide.with(appContext()).asBitmap()
                .load(uri)
                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get()
            ByteArrayOutputStream().use { stream ->
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArrayStream = ByteArrayInputStream(stream.toByteArray()).readBytes()
                storageService.uploadUserPhoto(byteArrayStream).collect {
                    when (it) {
                        is Result.Success -> onUploadImageSuccess(it.data)
                        is Result.Failure -> failureResult(it.msg)
                        is Result.Loading -> loadingResult()
                    }
                }
            }
        }
    }

    fun onTakenPhotoResult(success: Boolean) {
        if (success) onSelectedPhotoResult(photoUri)
    }

    fun cameraPermissionRequestResult(success: Boolean) {
        if (success) takePhotoClicked()
        else showToast("Необходимо предоставить доступ к камере")
    }

    fun storagePermissionRequestResult(success: Boolean) {
        if (success) changePhotoClicked()
        else showToast("Необходимо предоставить доступ к файловому хранилищу устройства")
    }

    private fun createFile(): File {
        val imagePath = appContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(FILE_PREFIX, FILE_SUFFIX, imagePath)
    }

    private fun onUploadImageSuccess(imagePath: String) {
        val user = userData.value?.copy() ?: return
        user.photoUrl = imagePath
        userService.updateUserFlow(user).onEach {
            when (it) {
                is Result.Success -> successResult()
                is Result.Failure -> failureResult(it.msg)
                is Result.Loading -> loadingResult()
            }
        }.launchIn(viewModelScope)
    }

}