package com.example.businessfinder.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.businessfinder.R
import com.example.businessfinder.models.Category
import com.example.businessfinder.models.SearchOffer
import com.example.businessfinder.scenes.chat.ChatFragmentArgs
import com.example.businessfinder.scenes.offers.OffersFragmentArgs
import com.example.businessfinder.scenes.sphere.SphereFragmentArgs

object Navigator {

    private val horizontalAnimationOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.animation_from_right)
        .setPopEnterAnim(R.anim.animation_from_left)
        .setExitAnim(R.anim.animation_to_left)
        .setPopExitAnim(R.anim.animation_to_right)
    private val options = horizontalAnimationOptions.build()

    fun goToLoginScreen(f: Fragment) = f.navigateClearingStack(R.id.loginFragment)

    fun goToRegistrationScreen(f: Fragment) = f.findNavController().navigate(R.id.registrationFragment, null, options)

    fun goToChatScreen(f: Fragment, otherUserUID: String) =
        f.findNavController().navigate(R.id.chatFragment, ChatFragmentArgs(otherUserUID).toBundle(), options)

    fun goToChatChannelsScreen(f: Fragment) = f.findNavController().navigate(R.id.chatChannelsFragment, null, options)

    fun goToProfileScreen(f: Fragment) = f.findNavController().navigate(R.id.profileFragment, null, options)

    fun goToProfileScreenClearingStack(navController: NavController) =
        navigateClearingStack(navController, R.id.profileFragment)

    fun goToProfileScreenClearingStack(f: Fragment) = f.navigateClearingStack(R.id.profileFragment)

    fun goToSpheresScreen(f: Fragment, category: Category) =
        f.findNavController()
            .navigate(R.id.sphereFragment, SphereFragmentArgs(category.id, category.name).toBundle(), options)

    fun goToOffersScreen(f: Fragment, searchOffer: SearchOffer) =
        f.findNavController().navigate(R.id.offersFragment, OffersFragmentArgs(searchOffer).toBundle(), options)

    private fun Fragment.navigateClearingStack(@IdRes resId: Int, args: Bundle? = null) {
        val options = NavOptions.Builder().setLaunchSingleTop(true).build()
        findNavController().popBackStack()
        findNavController().navigate(resId, args, options)
    }

    private fun navigateClearingStack(navController: NavController, @IdRes resId: Int) {
        val options = NavOptions.Builder().setLaunchSingleTop(true).build()
        navController.popBackStack()
        navController.navigate(resId, null, options)
    }

}