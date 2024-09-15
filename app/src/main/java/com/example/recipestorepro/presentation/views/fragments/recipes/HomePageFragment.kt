package com.example.recipestorepro.presentation.views.fragments.recipes

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.FragmentHomePageBinding
import com.example.recipestorepro.presentation.viewmodels.recipes.HomePageViewModel
import com.example.recipestorepro.presentation.views.adapters.HomePageRecyclerAdapter
import com.example.recipestorepro.presentation.views.fragments.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : BaseFragment<FragmentHomePageBinding>(FragmentHomePageBinding::inflate) {

    private val homePageViewModel: HomePageViewModel by activityViewModels()
    private val recipeAdapter = HomePageRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        subscribeToRecipes()
        setUpSearchView()
        setUpSwipeRefreshLayout()
        homePageViewModel.getAllRecipes()
    }

    override fun onResume() {
        super.onResume()
        setUpDropDownMenu()
    }

    override fun onDestroyView() {
        binding.recipeRecyclerView.adapter = null
        super.onDestroyView()
    }

    private fun setUpDropDownMenu() {
        val items = listOf(getString(R.string.recently_added), getString(R.string.favorites))
        val arrayAdapter = ArrayAdapter(
            requireContext(), R.layout.recipe_filter_dropdown_item, items
        )
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            if (selectedItem == getString(R.string.recently_added)) {
                homePageViewModel.getAllRecipes()
            } else {
                homePageViewModel.getFavoriteRecipes()
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.recipeRecyclerView.apply {
            adapter = recipeAdapter
            ItemTouchHelper(swipeToDelete).attachToRecyclerView(this)
        }

        recipeAdapter.onItemClick = {
            val action =
                HomePageFragmentDirections.actionHomePageFragmentToCreateNewRecipeFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun subscribeToRecipes() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            homePageViewModel.recipeData.collect {
                recipeAdapter.updateRecipeDataSet(it)
                isDataSetEmpty()
            }
        }
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    homePageViewModel.getAllRecipesByQuery(newText.orEmpty())
                }
                return false
            }
        }
        )
    }

    private val swipeToDelete = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val recipe = recipeAdapter.recipeDataSet[position]
            homePageViewModel.deleteRecipe(recipe.id)

            Snackbar.make(
                requireView(),
                resources.getString(R.string.recipe_deleted),
                Snackbar.LENGTH_LONG
            ).apply {
                setAction(resources.getString(R.string.undo)) {
                    homePageViewModel.undoDeletion(recipe)
                }
                show()
            }
        }
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            homePageViewModel.syncRecipes {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun isDataSetEmpty() {
        if (recipeAdapter.recipeDataSet.isEmpty()) binding.noRecipesFound.visibility =
            View.VISIBLE else binding.noRecipesFound.visibility = View.GONE
    }
}


