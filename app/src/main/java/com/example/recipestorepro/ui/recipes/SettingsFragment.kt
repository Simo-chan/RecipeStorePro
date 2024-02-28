package com.example.recipestorepro.ui.recipes

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
import com.example.recipestorepro.databinding.FragmentSettingsBinding
import com.example.recipestorepro.utils.Constants.LOGGED_OUT
import com.example.recipestorepro.utils.Result
import com.example.recipestorepro.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListener()
        subscribeToCurrentUserEvents()
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getCurrentUser()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpClickListener() {
        binding.logoutButton.setOnClickListener {
            userViewModel.logout()
            findNavController().navigate(R.id.action_settingsFragment_to_welcomeFragment)
        }
    }

    private fun subscribeToCurrentUserEvents() = lifecycleScope.launch {
        userViewModel.currentUserState.collect { result ->
            when (result) {
                is Result.Success -> {
                    binding.nameTextView.text = result.data?.name ?: "No name"
                    binding.emailTextView.text = result.data?.email ?: "No email"
                }

                is Result.Error -> {
                    //Displays when logged out
                    Toast.makeText(requireContext(), LOGGED_OUT, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.settingsProgressBar.isVisible = true
                }
            }
        }
    }
}