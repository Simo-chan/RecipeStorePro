package com.example.recipestorepro.presentation.fragments.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recipestorepro.databinding.FragmentCreateNewRecipeBinding
import com.example.recipestorepro.presentation.fragments.BaseFragment

class CreateNewRecipeFragment : BaseFragment<FragmentCreateNewRecipeBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateNewRecipeBinding {
        return FragmentCreateNewRecipeBinding.inflate(inflater, container, false)
    }
}