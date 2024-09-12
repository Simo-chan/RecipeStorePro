package com.example.recipestorepro.presentation.views.fragments.account

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.FragmentLoginBinding
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.account.LoginViewModel
import com.example.recipestorepro.presentation.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLoginEvents()
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.loginButtonLogin.setOnClickListener {
            val name = binding.nameEdTxLogin.text.toString()
            val email = binding.emailEdTxLogin.text.toString()
            val password = binding.passwordEdTxLogin.text.toString()

            loginViewModel.login(
                name.trim(),
                email.trim(),
                password.trim()
            )
        }
    }

    private fun subscribeToLoginEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            loginViewModel.loginState.collect { result ->
                when (result) {
                    is Result.Success -> {
                        hideProgressBar()
                        showResultMessage(result.resultMessage)
                        findNavController().navigate(R.id.action_loginFragment_to_homePageFragment)
                    }

                    is Result.Error -> {
                        hideProgressBar()
                        showResultMessage(result.resultMessage)
                    }

                    is Result.Loading -> {
                        showProgressBar()
                    }
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