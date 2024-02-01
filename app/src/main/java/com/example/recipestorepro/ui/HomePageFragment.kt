package com.example.recipestorepro.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.recipestorepro.R

class HomePageFragment : Fragment(R.layout.home_page_fragment) {

    private var dropDownMenu: AutoCompleteTextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dropDownMenu = view.findViewById(R.id.autoCompleteTextView)
    }

    override fun onResume() {
        super.onResume()

        val recipeFilterStrings = resources.getStringArray(R.array.recipe_filter)
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.recipe_filter_dropdown_item,
            recipeFilterStrings
        )
        dropDownMenu?.setAdapter(arrayAdapter)
    }

}