package com.example.recipestorepro.presentation.views.fragments.account

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.FragmentWelcomeBinding
import com.example.recipestorepro.presentation.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.signUpButtonWelcome.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_signUpFragment)
        }

        binding.logInButtonWelcome.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }
}