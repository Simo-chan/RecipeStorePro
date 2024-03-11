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
import com.example.recipestorepro.databinding.FragmentSignupBinding
import com.example.recipestorepro.presentation.fragments.BaseFragment
import com.example.recipestorepro.presentation.viewmodels.SignUpViewModel
import com.example.recipestorepro.domain.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>() {

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignupBinding {
        return FragmentSignupBinding.inflate(inflater, container, false)
    }

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
        signUpViewModel.registerState.collect { result ->
            when (result) {
                is Result.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), result.resultMessage, Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_signUpFragment_to_homePageFragment)
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
        binding.progressBarSignUp.isVisible = true
        binding.createAccountButton.isVisible = false
    }

    private fun hideProgressBar() {
        binding.progressBarSignUp.isVisible = false
        binding.createAccountButton.isVisible = true
    }
}