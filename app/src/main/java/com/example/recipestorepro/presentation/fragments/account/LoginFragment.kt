package com.example.recipestorepro.presentation.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.FragmentLoginBinding
import com.example.recipestorepro.presentation.fragments.BaseFragment
import com.example.recipestorepro.presentation.viewmodels.LoginViewModel
import com.example.recipestorepro.domain.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLoginEvents()
        setUpClickListener()
    }

    private fun setUpClickListener() {

        binding.loginButtonLogin.setOnClickListener {
            val email = binding.emailEdTxLogin.text.toString()
            val password = binding.passwordEdTxLogin.text.toString()

            loginViewModel.login(
                email.trim(),
                password.trim()
            )
        }
    }

    private fun subscribeToLoginEvents() = lifecycleScope.launch {
        loginViewModel.loginState.collect { result ->
            when (result) {
                is Result.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), result.resultMessage, Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_loginFragment_to_homePageFragment)
                }

                is Result.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), result.resultMessage, Toast.LENGTH_SHORT)
                        .show()
                }

                is Result.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBarLogin.isVisible = true
        binding.loginButtonLogin.isVisible = false
    }

    private fun hideProgressBar() {
        binding.progressBarLogin.isVisible = false
        binding.loginButtonLogin.isVisible = true
    }
}