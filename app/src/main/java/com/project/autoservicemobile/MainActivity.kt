package com.project.autoservicemobile

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.TrafficStats
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.project.autoservicemobile.common.models.VibrateTypeEnum
import com.project.autoservicemobile.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MAIN = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //NavigationUI.setupWithNavController(navView, navController, false)
        navView.setupWithNavController(navController)

        if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Do your task on permission granted
            } else if (shouldShowRequestPermissionRationale("android.permission.POST_NOTIFICATIONS")) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS")
            }
        }
        else{
            // Below Android 13 You don't need to ask for notification permission.
        }

        initTrafficAnalyzer()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Granted
        } else {
            // Permission Denied / Cancel
        }
    }

    fun hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initTrafficAnalyzer(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(
                network: android.net.Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    updateTrafficInfo()
                }
            }

            override fun onLost(network: android.net.Network) {
                super.onLost(network)
                updateTrafficInfo()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun updateTrafficInfo() {
        val totalRxBytes = (TrafficStats.getTotalRxBytes() / 1024f).toString()
        val totalTxBytes = (TrafficStats.getTotalTxBytes() / 1024f).toString()

        Log.i("TrafficAnalyze", "Получено данных: $totalRxBytes Кбайт\nОтправлено данных: $totalTxBytes Кбайт")
    }

    fun goPreviousFragment(){
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }
        else{
            Log.w(TAG, "backStack is empty")
        }
    }

    companion object{
        private const val TAG = "MAIN_ACTIVITY"
    }
}
