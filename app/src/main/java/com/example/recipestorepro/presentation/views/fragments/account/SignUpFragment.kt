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
import com.example.recipestorepro.databinding.FragmentSignupBinding
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.account.SignUpViewModel
import com.example.recipestorepro.presentation.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToRegisterEvents()
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.createAccountButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEdTxSignUp.text.toString()
            val password = binding.passwordEdTxSignUp.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            signUpViewModel.createUser(
                name.trim(),
                email.trim(),
                password.trim(),
                confirmPassword.trim()
            )
        }
    }

    private fun subscribeToRegisterEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            signUpViewModel.registerState.collect { result ->
                when (result) {
                    is Result.Success -> {
                        hideProgressBar()
                        showResultMessage(result.resultMessage)
                        findNavController().navigate(R.id.action_signUpFragment_to_homePageFragment)
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
        binding.progressBarSignUp.isVisible = true
        binding.createAccountButton.isVisible = false
    }

    private fun hideProgressBar() {
        binding.progressBarSignUp.isVisible = false
        binding.createAccountButton.isVisible = true
    }
}