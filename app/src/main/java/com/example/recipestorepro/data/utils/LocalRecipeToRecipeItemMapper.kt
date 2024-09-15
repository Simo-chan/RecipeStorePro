package com.example.recipestorepro.data.utils

import com.example.recipestorepro.data.local.LocalRecipe
import com.example.recipestorepro.domain.models.RecipeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Collections

object LocalRecipeToRecipeItemMapper {

    fun map(locals: Flow<List<LocalRecipe>>): Flow<List<RecipeItem>> = flow {
        locals.collect { locals ->
            val items = ArrayList<RecipeItem>()
            for (local in locals) {
                val recipeItem = mapLocalRecipeToRecipeItem(local)
                items.add(recipeItem)
            }
            emit(Collections.unmodifiableList(items))
        }
    }

    private fun mapLocalRecipeToRecipeItem(local: LocalRecipe): RecipeItem {
        return RecipeItem(
            id = local.recipeId,
            title = local.recipeTitle,
            ingredients = local.recipeIngredients,
            instructions = local.recipeInstructions,
            date = local.date,
            remotelyConnected = local.remotelyConnected,
            locallyDeleted = local.locallyDeleted,
            isFavorite = local.isFavorite
        )
    }
}