package com.example.recipestorepro.presentation.fragments.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recipestorepro.databinding.FragmentTobuyListBinding
import com.example.recipestorepro.presentation.fragments.BaseFragment

class ToBuyListFragment : BaseFragment<FragmentTobuyListBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTobuyListBinding {
        return FragmentTobuyListBinding.inflate(inflater, container, false)
    }
}