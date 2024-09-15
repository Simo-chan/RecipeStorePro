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
import com.example.recipestorepro.databinding.FragmentSettingsBinding
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.account.SettingsViewModel
import com.example.recipestorepro.presentation.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListeners()
        subscribeToAvatarEvents()
        subscribeToCurrentUserEvents()
        subscribeToLogOutEvents()
        settingsViewModel.getCurrentUser()
    }

    private fun setUpClickListeners() {
        binding.logoutButton.setOnClickListener {
            settingsViewModel.logout()
        }

        binding.feedBackTextView.setOnClickListener {
            val feedbackDialog = GiveFeedbackDialog()
            feedbackDialog.show(childFragmentManager, "feedbackDialog")
        }

        binding.avatarPic.setOnClickListener {
            lifecycleScope.launch {
                val currentIndex = settingsViewModel.currentAvatarIndex.first()
                settingsViewModel.updateAvatarIndex(
                    currentIndex,
                    avatarList.size
                )
            }
        }
    }

    private fun subscribeToCurrentUserEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            settingsViewModel.currentUserState.collect { result ->
                when (result) {
                    is Result.Success -> {
                        hideProgressBar()
                        binding.nameTextView.text = result.data?.name.orEmpty()
                        binding.emailTextView.text = result.data?.email.orEmpty()
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

    private fun subscribeToLogOutEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
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
    }

    private fun subscribeToAvatarEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            settingsViewModel.currentAvatarIndex.collect { index ->
                binding.avatarPic.setImageResource(avatarList[index])
            }
        }
    }

    private val avatarList = listOf(
        R.drawable.avatar_male,
        R.drawable.avatar_female,
        R.drawable.avatar_penguin,
        R.drawable.avatar_cookieman
    )

    private fun showProgressBar() {
        binding.settingsProgressBar.isVisible = true
        binding.logoutButton.isVisible = false
    }

    private fun hideProgressBar() {
        binding.settingsProgressBar.isVisible = false
        binding.logoutButton.isVisible = true
    }
}