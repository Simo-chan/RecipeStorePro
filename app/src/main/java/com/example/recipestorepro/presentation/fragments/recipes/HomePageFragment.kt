package com.example.recipestorepro.presentation.fragments.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.adapters.HomePageRecyclerAdapter
import com.example.recipestorepro.databinding.FragmentHomePageBinding
import com.example.recipestorepro.presentation.fragments.BaseFragment

class HomePageFragment : BaseFragment<FragmentHomePageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomePageBinding {
        return FragmentHomePageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        val recipeFilterStrings = resources.getStringArray(R.array.recipe_filter)
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.recipe_filter_dropdown_item,
            recipeFilterStrings
        )
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun setUpRecyclerView() {
        val adapter = HomePageRecyclerAdapter()
        binding.recipeRecyclerView.adapter = adapter

        adapter.onItemClick = {
            findNavController().navigate(R.id.action_homePageFragment_to_createNewRecipeFragment)
        }
    }
}