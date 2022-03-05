package com.example.businessfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.businessfinder.common.Navigator.goToLoginScreen
import com.example.businessfinder.common.Navigator.goToProfileScreenClearingStack
import com.example.businessfinder.databinding.ActivityMainBinding
import com.example.businessfinder.services.UserService
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val navController: NavController
        get() = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkStartDestination()
        setupNavController()
    }


    private fun checkStartDestination() {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        if (FirebaseAuth.getInstance().currentUser != null) graph.setStartDestination(R.id.profileFragment)
        else graph.setStartDestination(R.id.loginFragment)
        navController.graph = graph
    }

    private fun setupNavController() {
        val appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.profileFragment))
            .setOpenableLayout(binding.drawer)
            .build()
        navController.let { binding.toolbar.setupWithNavController(it, appBarConfiguration) }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment) {
                binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                binding.toolbar.isVisible = false
            } else {
                binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                binding.toolbar.isVisible = true
            }
        }
    }

}