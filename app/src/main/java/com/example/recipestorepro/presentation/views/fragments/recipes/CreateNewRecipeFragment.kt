package com.example.recipestorepro.presentation.views.fragments.recipes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.recipestorepro.R
import com.example.recipestorepro.databinding.FragmentCreateNewRecipeBinding
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.recipes.CreateNewRecipeViewModel
import com.example.recipestorepro.presentation.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CreateNewRecipeFragment :
    BaseFragment<FragmentCreateNewRecipeBinding>(FragmentCreateNewRecipeBinding::inflate) {

    private val createNewRecipeViewModel: CreateNewRecipeViewModel by activityViewModels()
    private val args: CreateNewRecipeFragmentArgs by navArgs()
    private var curStat: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNewRecipeViewModel.oldRecipe = args.recipe
        curStat = args.recipe?.isFavorite
        curStat?.let { createNewRecipeViewModel.setInitialValue(it) }

        setupActionBar()
        setRecipeText()
        setOnClickListeners()
        subscribeToCreateRecipeEvents()
        subscribeToUpdateRecipeEvents()
        subscribeToFavoriteStatusEvents()
    }

    override fun onDestroy() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(null)
        binding.saveButton.setOnClickListener(null)
        super.onDestroy()
    }

    private fun setRecipeText() {
        createNewRecipeViewModel.oldRecipe.let { recipe ->
            binding.title.setText(recipe?.title)
            binding.ingredients.setText(recipe?.ingredients)
            binding.instructions.setText(recipe?.instructions)
            binding.date.isVisible = recipe != null
            recipe?.date?.let { binding.date.text = milliToDate(it) }
        }
    }

    private fun setOnClickListeners() {
        val recipe = createNewRecipeViewModel.oldRecipe
        binding.saveButton.setOnClickListener {
            if (recipe == null) {
                createRecipe()
            } else {
                updateRecipe()
            }
        }

        binding.favoriteButton.setOnClickListener {
            curStat?.let { it1 ->
                createNewRecipeViewModel.updateFavoriteStatus(
                    recipe?.id.orEmpty(), !it1
                )
            }
        }
    }


    private fun createRecipe() {
        val title = binding.title.text.toString().trim()
        val ingredients = binding.ingredients.text.toString().trim()
        val instructions = binding.instructions.text.toString().trim()

        if (title.isEmpty() && ingredients.isEmpty() && instructions.isEmpty()) {
            showResultMessage(resources.getString(R.string.fields_empty))
            return
        }
        createNewRecipeViewModel.createRecipe(title, ingredients, instructions)
    }

    private fun updateRecipe() {
        val title = binding.title.text.toString().trim()
        val ingredients = binding.ingredients.text.toString().trim()
        val instructions = binding.instructions.text.toString().trim()
        val oldRecipe = createNewRecipeViewModel.oldRecipe

        if (title.isEmpty() && ingredients.isEmpty() && instructions.isEmpty()) {
            createNewRecipeViewModel.deleteRecipe(oldRecipe?.id.orEmpty())
            return
        }
        createNewRecipeViewModel.updateRecipe(title, ingredients, instructions)
    }

    private fun subscribeToCreateRecipeEvents() = lifecycleScope.launch {
        createNewRecipeViewModel.createRecipeState.collect { result ->
            when (result) {
                is Result.Success -> {
                    showResultMessage(result.resultMessage)
                    findNavController().popBackStack(R.id.homePageFragment, false)
                }

                else -> {
                    showResultMessage(result.resultMessage)
                }
            }
        }
    }

    private fun subscribeToUpdateRecipeEvents() = lifecycleScope.launch {
        createNewRecipeViewModel.updateRecipeState.collect { result ->
            when (result) {
                is Result.Success -> {
                    showResultMessage(result.resultMessage)
                    findNavController().popBackStack(R.id.homePageFragment, false)
                }

                else -> {
                    showResultMessage(result.resultMessage)
                }
            }
        }
    }

    private fun subscribeToFavoriteStatusEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            createNewRecipeViewModel.favoriteStatusState.collect { isFavorite ->
                curStat = isFavorite
                binding.favoriteButton.setImageResource(
                    if (isFavorite) R.drawable.ic_star_filled_bigger
                    else R.drawable.ic_star_empty
                )
            }
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.customToolBar)
        binding.customToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.customToolBar.title = null
    }

    private fun milliToDate(time: Long): String {
        val date = Date(time)
        val simpleDateFormat = SimpleDateFormat("hh:mm a | MMM d, yyyy", Locale.getDefault())
        return simpleDateFormat.format(date)
    }
}