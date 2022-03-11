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
import com.example.businessfinder.common.Constants
import com.example.businessfinder.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

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
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.colorAccent))
    }


    private fun checkStartDestination() {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        if (FirebaseAuth.getInstance().currentUser != null) graph.setStartDestination(R.id.profileFragment)
        else graph.setStartDestination(R.id.loginFragment)
        navController.graph = graph
    }

    private fun setupNavController() {
        val appBarConfiguration = AppBarConfiguration.Builder(Constants.DRAWER_LIST)
            .setOpenableLayout(binding.drawer)
            .build()
        binding.navView.setupWithNavController(navController)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
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

    fun updateToolbar(title: String) {
        binding.toolbar.title = title
    }

}