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
import com.example.recipestorepro.databinding.FragmentLoginBinding
import com.example.recipestorepro.utils.Constants
import com.example.recipestorepro.utils.Result
import com.example.recipestorepro.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLoginEvents()
        setUpClickListener()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpClickListener() {

        binding.loginButtonLogin.setOnClickListener {
            val email = binding.emailEdTxLogin.text.toString()
            val password = binding.passwordEdTxLogin.text.toString()

            userViewModel.loginUser(
                email.trim(),
                password.trim()
            )
        }
    }

    private fun subscribeToLoginEvents() = lifecycleScope.launch {
        userViewModel.loginState.collect { result ->
            when (result) {
                is Result.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), Constants.LOGGED_IN, Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_loginFragment_to_homePageFragment)
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
        binding.progressBarLogin.isVisible = true
        binding.loginButtonLogin.isVisible = false
    }

    private fun hideProgressBar() {
        binding.progressBarLogin.isVisible = false
        binding.loginButtonLogin.isVisible = true
    }
}