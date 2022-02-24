package com.example.businessfinder.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.businessfinder.R

object Navigator {

    private val horizontalAnimationOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.animation_from_right)
        .setPopEnterAnim(R.anim.animation_from_left)
        .setExitAnim(R.anim.animation_to_left)
        .setPopExitAnim(R.anim.animation_to_right)
    private val options = horizontalAnimationOptions.build()

    fun goToMainScreen(f: Fragment) = f.navigateClearingStack(R.id.loginFragment)

    fun goToLoginScreen(f: Fragment) = f.navigateClearingStack(R.id.loginFragment)

    fun goToLoginScreen(navController: NavController) = navigateClearingStack(navController, R.id.loginFragment)

    fun goToRegistrationScreen(f: Fragment) = f.findNavController().navigate(R.id.registrationFragment, null, options)

    fun goToChatScreen(f: Fragment) = f.findNavController().navigate(R.id.chatFragment, null, options)

    fun goToChatChannelsScreen(f: Fragment) = f.findNavController().navigate(R.id.chatChannelsFragment, null, options)

    fun goToProfileScreen(f: Fragment) = f.findNavController().navigate(R.id.profileFragment, null, options)

    fun goToProfileScreenClearingStack(navController: NavController) = navigateClearingStack(navController, R.id.profileFragment)

    private fun Fragment.navigateClearingStack(@IdRes resId: Int, clear: Boolean = true, args: Bundle? = null) {
        val options = if (clear)
            NavOptions.Builder()
                .setPopUpTo(resId, true)
                .setLaunchSingleTop(true)
                .build()
        else null
        findNavController().navigate(resId, args, options)
    }

    private fun navigateClearingStack(navController: NavController, @IdRes resId: Int) {
        val options =
            NavOptions.Builder()
                .setPopUpTo(resId, true)
                .setLaunchSingleTop(true)
                .build()
        navController.navigate(resId, null, options)
    }

}