package com.example.recipestorepro.presentation.viewmodels.recipes

import androidx.lifecycle.viewModelScope
import com.example.recipestorepro.data.local.LocalRecipe
import com.example.recipestorepro.domain.models.RecipeItem
import com.example.recipestorepro.domain.use_case.recipes.CreateRecipeUseCase
import com.example.recipestorepro.domain.use_case.recipes.DeleteRecipeUseCase
import com.example.recipestorepro.domain.use_case.recipes.UpdateFavoriteStatusUseCase
import com.example.recipestorepro.domain.use_case.recipes.UpdateRecipeUseCase
import com.example.recipestorepro.domain.utils.Result
import com.example.recipestorepro.presentation.viewmodels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewRecipeViewModel @Inject constructor(
    private val createRecipeUseCase: CreateRecipeUseCase,
    private val updateRecipeUseCase: UpdateRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : BaseViewModel() {

    private val _createRecipeState = MutableSharedFlow<Result<String>>()
    val createRecipeState: SharedFlow<Result<String>> = _createRecipeState

    private val _updateRecipeState = MutableSharedFlow<Result<String>>()
    val updateRecipeState: SharedFlow<Result<String>> = _updateRecipeState

    private val _favoriteStatusState = MutableStateFlow(false)
    val favoriteStatusState: SharedFlow<Boolean> = _favoriteStatusState

    var oldRecipe: RecipeItem? = null

    fun createRecipe(
        title: String,
        ingredients: String,
        instructions: String,
        isFavorite: Boolean
    ) = viewModelScope.launch(exceptionHandler) {
        val result = createRecipeUseCase.createRecipe(
            LocalRecipe(
                recipeTitle = title,
                recipeIngredients = ingredients,
                recipeInstructions = instructions,
                isFavorite = isFavorite
            )
        )
        _createRecipeState.emit(result)
    }

    fun updateRecipe(
        title: String,
        ingredients: String,
        instructions: String,
        isFavorite: Boolean
    ) = viewModelScope.launch(exceptionHandler) {
        if (title == oldRecipe?.title
            && ingredients == oldRecipe?.ingredients
            && instructions == oldRecipe?.instructions
            && oldRecipe?.remotelyConnected == true
        ) {
            return@launch
        }

        val result = updateRecipeUseCase.updateRecipe(
            LocalRecipe(
                recipeTitle = title,
                recipeIngredients = ingredients,
                recipeInstructions = instructions,
                isFavorite = isFavorite,
                recipeId = oldRecipe?.id.orEmpty()
            )
        )
        _updateRecipeState.emit(result)
    }

    fun updateFavoriteStatus(id: String, isFavorite: Boolean) =
        viewModelScope.launch(exceptionHandler) {
            updateFavoriteStatusUseCase.updateFavoriteStatus(id, isFavorite)
            _favoriteStatusState.value = isFavorite
        }

    fun setInitialValue(isFavorite: Boolean) {
        _favoriteStatusState.value = isFavorite
    }

    fun resetFavoriteValue() {
        _favoriteStatusState.value = false
    }

    fun deleteRecipe(
        recipeId: String
    ) = viewModelScope.launch(exceptionHandler) {
        deleteRecipeUseCase.deleteRecipe(recipeId)
    }
}