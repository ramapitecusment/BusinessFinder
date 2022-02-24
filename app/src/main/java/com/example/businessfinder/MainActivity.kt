package com.example.businessfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.common.Navigator.goToLoginScreen
import com.example.businessfinder.common.Navigator.goToProfileScreenClearingStack
import com.example.businessfinder.databinding.ActivityMainBinding
import com.example.businessfinder.services.UserService
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val userService: UserService by inject()
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment?.navController
        val appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.profileFragment))
            .setOpenableLayout(binding.drawer)
            .build()
        navController?.let { binding.toolbar.setupWithNavController(it, appBarConfiguration) }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.isVisible = destination.id != R.id.loginFragment
        }
    }

    override fun onStart() {
        super.onStart()
        if (userService.user != null) goToProfileScreenClearingStack(navController!!)
        else goToLoginScreen(navController!!)
    }
}