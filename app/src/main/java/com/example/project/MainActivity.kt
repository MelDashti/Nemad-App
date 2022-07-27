package com.example.project

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.project.util.PreferenceKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)



        val sharedPreferences =
            this.getSharedPreferences(PreferenceKeys.PREFERENCE_AUTH_KEY, Context.MODE_PRIVATE)
        val defaultValue = "empty"
        val token = sharedPreferences.getString(PreferenceKeys.PREFERENCE_AUTH_KEY, defaultValue)
        Log.d("helllo", token.toString())

        if (token == null || token == "empty") {
            graph.setStartDestination(R.id.navigation)
        } else {
            Log.d("token ", token)
            graph.setStartDestination(R.id.mainFragment)
        }
        navController.graph = graph

    }

}