package com.example.businessfinder

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
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

    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        @RequiresApi(Build.VERSION_CODES.P)
        get() = object : BiometricPrompt.AuthenticationCallback() {
            val graph = navController.navInflater.inflate(R.navigation.nav_graph)

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Log.d("Biometric", errString.toString())
                graph.setStartDestination(R.id.loginFragment)
                navController.graph = graph
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Log.d("Biometric", "Success: $result")
                graph.setStartDestination(R.id.profileFragment)
                navController.graph = graph
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkStartDestination()
        biometricAuthentication()
        setupNavController()
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.colorAccent))
    }

    private fun checkStartDestination() {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        if (FirebaseAuth.getInstance().currentUser != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && checkBiometricSupport()) {
                val biometricPrompt = BiometricPrompt.Builder(this)
                    .setTitle("Авторизация")
                    .setNegativeButton("Отмена", mainExecutor, { _, _ ->
                        graph.setStartDestination(R.id.loginFragment)
                    }).build()
                biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
            } else graph.setStartDestination(R.id.profileFragment)
        } else graph.setStartDestination(R.id.loginFragment)
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

    private fun biometricAuthentication() {

    }

    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            Log.d("Biometric", "Включите авторизацию с помощью биометрии")
            return false
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Biometric", "Нет разрешения для использования биометрической авторизации")
            return false
        }

        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) true
        else true
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Log.d("Biometric", "Авторизация по биометрии отменена пользователем")
        }
        return cancellationSignal as CancellationSignal
    }

    private fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

}