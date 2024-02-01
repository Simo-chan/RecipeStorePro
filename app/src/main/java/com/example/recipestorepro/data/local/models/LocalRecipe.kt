package com.example.recipestorepro.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class LocalRecipe(
    var recipeTitle: String? = null,
    var recipeIngredients: String? = null,
    var recipeInstructions: String? = null,
    var date: Long? = System.currentTimeMillis(),
    var remotelyConnected: Boolean? = null,
    var locallyDeleted: Boolean? = null,

    @PrimaryKey(autoGenerate = false)
    var recipeId: String = UUID.randomUUID().toString()
)