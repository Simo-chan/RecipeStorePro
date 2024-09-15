package com.example.recipestorepro.presentation.viewmodels.recipes

import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.data.local.LocalRecipe
import com.example.recipestorepro.data.utils.LocalRecipeToRecipeItemMapper
import com.example.recipestorepro.domain.models.RecipeItem
import com.example.recipestorepro.domain.use_case.recipes.CreateRecipeUseCase
import com.example.recipestorepro.domain.use_case.recipes.DeleteRecipeUseCase
import com.example.recipestorepro.domain.use_case.recipes.GetAllRecipesBySearchQueryUseCase
import com.example.recipestorepro.domain.use_case.recipes.GetAllRecipesUseCase
import com.example.recipestorepro.domain.use_case.recipes.GetFavoriteRecipesUseCase
import com.example.recipestorepro.domain.use_case.recipes.SyncRecipesUseCase
import com.example.recipestorepro.presentation.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val getAllRecipesUseCase: GetAllRecipesUseCase,
    private val getFavoriteRecipes: GetFavoriteRecipesUseCase,
    private val getAllRecipesByQueryUseCase: GetAllRecipesBySearchQueryUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val createRecipeUseCase: CreateRecipeUseCase,
    private val syncRecipesUseCase: SyncRecipesUseCase
) : BaseViewModel() {
    private val mapper = LocalRecipeToRecipeItemMapper

    private val _recipeData = MutableStateFlow<List<RecipeItem>>(emptyList())
    val recipeData: StateFlow<List<RecipeItem>> = _recipeData

    fun getAllRecipes() = viewModelScope.launch(exceptionHandler) {
        val data = mapper.map(getAllRecipesUseCase.getAllRecipes())
        data.collect { recipes ->
            _recipeData.value = recipes
        }
    }

    fun getFavoriteRecipes() = viewModelScope.launch(exceptionHandler) {
        val data = mapper.map(getFavoriteRecipes.getFavoriteRecipes())
        data.collect { recipes ->
            _recipeData.value = recipes
        }
    }

    fun getAllRecipesByQuery(query: String) = viewModelScope.launch(exceptionHandler) {
        val data = mapper.map(getAllRecipesByQueryUseCase.getRecipesBySearchQuery(query))
        data.collect { recipes ->
            _recipeData.value = recipes
        }
    }

    fun deleteRecipe(
        recipeId: String
    ) = viewModelScope.launch(exceptionHandler) {
        deleteRecipeUseCase.deleteRecipe(recipeId)
    }

    fun undoDeletion(
        recipe: RecipeItem
    ) = viewModelScope.launch(exceptionHandler) {
        createRecipeUseCase.createRecipe(
            LocalRecipe(
                recipe.title,
                recipe.ingredients,
                recipe.instructions,
                recipe.date,
                recipe.remotelyConnected,
                recipe.locallyDeleted,
                recipe.isFavorite,
                recipe.id
            )
        )
    }

    fun syncRecipes(onDone: (() -> Unit)? = null) = viewModelScope.launch {
        syncRecipesUseCase.syncRecipes()
        onDone?.invoke()
    }
}
