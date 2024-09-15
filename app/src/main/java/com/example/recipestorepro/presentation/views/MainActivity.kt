package com.example.recipestorepro.presentation.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    private fun setUpBottomNavBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val startDestination = when (sessionManager.isUserLoggedIn()) {
            true -> R.id.homePageFragment
            false -> R.id.welcomeFragment
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navGraph = navController.navInflater.inflate(R.navigation.bottom_nav_graph)
        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
        bottomNavigationView.setupWithNavController(navController)

        navHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.welcomeFragment,
                R.id.loginFragment,
                R.id.signUpFragment,
                R.id.createNewRecipeFragment -> bottomNavigationView.visibility =
                    View.GONE

                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}
