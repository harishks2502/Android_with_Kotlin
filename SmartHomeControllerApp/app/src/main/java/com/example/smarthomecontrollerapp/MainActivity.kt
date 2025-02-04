package com.example.smarthomecontrollerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.smarthomecontrollerapp.repository.SmartHomeRepository
import com.example.smarthomecontrollerapp.databinding.ActivityMainBinding
import com.example.smarthomecontrollerapp.workmanager.SyncWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var smartHomeRepository: SmartHomeRepository

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        println(smartHomeRepository.getDeviceStatus())


        val userRules = "Turn off Wi-Fi at night"

        val inputData = Data.Builder()
            .putString("userRules", userRules)
            .build()

        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setInputData(inputData)

            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DeviceSyncWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            syncWorkRequest
        )

        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData("DeviceSyncWork")
            .observe(this) { workInfos ->
                workInfos?.forEach { workInfo ->
                    Log.d("MainActivity", "Work Status: ${workInfo.state}")
                }
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
