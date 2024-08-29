package com.example.recipestorepro.presentation.views.fragments.account

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.FragmentSettingsBinding
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.account.SettingsViewModel
import com.example.recipestorepro.presentation.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListener()
        subscribeToCurrentUserEvents()
        subscribeToLogOutEvents()
        settingsViewModel.getCurrentUser()
    }

    private fun setUpClickListener() {
        binding.logoutButton.setOnClickListener {
            settingsViewModel.logout()
        }
    }

    private fun subscribeToCurrentUserEvents() = lifecycleScope.launch {
        settingsViewModel.currentUserState.collect { result ->
            when (result) {
                is Result.Success -> {
                    binding.nameTextView.text = result.data?.name.orEmpty()
                    binding.emailTextView.text = result.data?.email.orEmpty()
                }

                is Result.Error -> {
                    showResultMessage(result.resultMessage)
                }

                is Result.Loading -> {
                    binding.settingsProgressBar.isVisible = true
                }
            }
        }
    }

    private fun subscribeToLogOutEvents() = lifecycleScope.launch {
        settingsViewModel.logOutState.collect { result ->
            when (result) {
                is Result.Success -> {
                    hideProgressBar()
                    showResultMessage(result.resultMessage)
                    findNavController().navigate(R.id.action_settingsFragment_to_welcomeFragment)
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

    private fun showProgressBar() {
        binding.settingsProgressBar.isVisible = true
        binding.logoutButton.isVisible = false
    }

    private fun hideProgressBar() {
        binding.settingsProgressBar.isVisible = false
        binding.logoutButton.isVisible = true
    }
}