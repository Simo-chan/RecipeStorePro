package com.example.recipestorepro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recipestorepro.R
import com.example.recipestorepro.adapters.HomePageRecyclerAdapter
import com.example.recipestorepro.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setUpRecyclerView() {
        val adapter = HomePageRecyclerAdapter()
        binding.recipeRecyclerView.adapter = adapter

        adapter.onItemClick = {
            findNavController().navigate(R.id.action_homePageFragment_to_createNewRecipeFragment)
        }
    }


}