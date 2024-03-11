package com.example.recipestorepro.presentation.fragments.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recipestorepro.databinding.FragmentTimerBinding
import com.example.recipestorepro.presentation.fragments.BaseFragment

class TimerFragment : BaseFragment<FragmentTimerBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimerBinding {
        return FragmentTimerBinding.inflate(inflater, container, false)
    }
}