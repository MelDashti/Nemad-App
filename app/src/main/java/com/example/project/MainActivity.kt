package com.example.project

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.project.databinding.ActivityMainBinding
import com.example.project.ui.main.MainViewModel
import com.example.project.util.PreferenceKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        //bottom navigation setup

        binding.bottomNavigation.setupWithNavController(navController)

        //set nav destinations for bottom navigation buttons

        binding.bottomNavigation.setOnItemSelectedListener {
            Log.d("fdsaf", it.itemId.toString())
            if (it.itemId == R.id.mainFragment) {
                Log.d("fdsaf", "before clearing data")
                viewModel.clearAllData()
                //                navController.graph.clear()
            }

            NavigationUI.onNavDestinationSelected(it, navController) || onOptionsItemSelected(
                it
            )
        }
        binding.bottomNavigation.selectedItemId = R.id.mainFragment

        // checking for token and choosing starting nav graph
        val sharedPreferences =
            this.getSharedPreferences(PreferenceKeys.PREFERENCE_AUTH_KEY, Context.MODE_PRIVATE)
        val defaultValue = "empty"
        val token =
            sharedPreferences.getString(PreferenceKeys.PREFERENCE_AUTH_KEY, defaultValue)

        if (token == null || token == "empty") {
            graph.startDestination = R.id.navigation
        } else {
            Log.d("token ", token)
            graph.startDestination = R.id.mainFragment
        }
        navController.graph = graph

    }


    fun setBottomNavigationVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        binding.bottomNavigation.visibility = visibility
    }

}


// extend to base fragment if you wanna make bottom nav bar disappear

abstract class BaseFragment : Fragment() {

    protected open var bottomNavigationViewVisibility = View.VISIBLE

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // get the reference of the parent activity and call the setBottomNavigationVisibility method.
        if (activity is MainActivity) {
            val mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            (activity as MainActivity).setBottomNavigationVisibility(bottomNavigationViewVisibility)
        }
    }
}

