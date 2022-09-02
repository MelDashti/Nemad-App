package com.example.nemad

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.nemad.databinding.ActivityMainBinding
import com.example.nemad.repository.AuthRepository
import com.example.nemad.ui.main.MainActivityViewModel
import com.example.nemad.util.PreferenceKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainActivityViewModel by viewModels()

    var contentHasLoaded = false

    @Inject
    lateinit var authRepository: AuthRepository

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)


        val navController = findNavController(R.id.nav_host_fragment)

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController) || onOptionsItemSelected(
                it
            )
        }
        // checking for token and choosing starting nav graph
        val sharedPreferences =
            this.getSharedPreferences(PreferenceKeys.PREFERENCE_AUTH_KEY, Context.MODE_PRIVATE)
        val defaultValue = "empty"
        val token =
            sharedPreferences.getString(PreferenceKeys.PREFERENCE_AUTH_KEY, defaultValue)

        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        if (token == null || token == "empty") {
            graph.startDestination = R.id.navigation
            Log.d("helllo", "hahaah2")
            navController.graph = graph
            contentHasLoaded = true
        } else {
            Log.d("helllo", "hahaah4")
            lifecycleScope.launch {
                try {
                    val respone = authRepository.checkTokenValid()
                    Log.d("helllo", respone.code().toString())
                    if (respone.code() == 200) {
                        graph.startDestination = R.id.homeFragment
                        navController.graph = graph
                    }
                    contentHasLoaded = true
                } catch (e: Exception) {
                    graph.startDestination = R.id.navigation
                    navController.graph = graph
                    contentHasLoaded = true
                }

            }
        }

        setupSplashScreen(splashScreen)
    }


    private fun setupSplashScreen(splashScreen: SplashScreen) {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
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

