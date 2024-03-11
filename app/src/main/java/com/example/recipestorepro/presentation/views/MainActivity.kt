package com.example.recipestorepro.presentation.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.data.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNavBar()
    }

    override fun onStart() {
        super.onStart()
        setStartDestination()
    }

    private fun setUpBottomNavBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavigationView.setupWithNavController(navController)

        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.welcomeFragment,
                R.id.loginFragment,
                R.id.signUpFragment -> bottomNavigationView.visibility =
                    View.GONE

                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }

    }

    private fun setStartDestination() {
        val navController = findNavController(R.id.navHostFragment)
        val navGraph = navController.graph
        val startDestination = if (sessionManager.isUserLoggedIn()) {
            R.id.homePageFragment
        } else {
            R.id.welcomeFragment
        }

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }
}
