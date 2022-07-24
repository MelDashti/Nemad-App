package com.example.internproject

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.internproject.util.PreferenceKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        val sharedPreferences =
            this.getSharedPreferences(PreferenceKeys.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        val defaultValue = "empty"
        val token = sharedPreferences.getString(PreferenceKeys.PREFERENCE_AUTH_KEY, defaultValue)

        if (token == null || token == "empty") {
            graph.setStartDestination(R.id.navigation)
        } else {
            Log.d("token ", token)
            graph.setStartDestination(R.id.nav_graph)
        }
        navController.graph = graph

    }

}