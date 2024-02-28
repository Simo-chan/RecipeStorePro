package com.example.recipestorepro.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.FragmentSignupBinding
import com.example.recipestorepro.utils.Constants.ACCOUNT_CREATED
import com.example.recipestorepro.utils.Result
import com.example.recipestorepro.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_signup) {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToRegisterEvents()
        setUpClickListener()

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setUpClickListener() {

        binding.createAccountButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEdTxSignUp.text.toString()
            val password = binding.passwordEdTxSignUp.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            userViewModel.createUser(
                name.trim(),
                email.trim(),
                password.trim(),
                confirmPassword.trim()
            )
        }
    }

    private fun subscribeToRegisterEvents() = lifecycleScope.launch {
        userViewModel.registerState.collect { result ->
            when (result) {
                is Result.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), ACCOUNT_CREATED, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signUpFragment_to_homePageFragment)
                }

                is Result.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
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