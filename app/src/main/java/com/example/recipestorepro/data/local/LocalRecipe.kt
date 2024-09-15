package com.example.recipestorepro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "recipes")
data class LocalRecipe(
    val recipeTitle: String? = null,
    val recipeIngredients: String? = null,
    val recipeInstructions: String? = null,
    val date: Long = System.currentTimeMillis(),
    var remotelyConnected: Boolean = false,
    var locallyDeleted: Boolean = false,
    var isFavorite: Boolean = false,

    @PrimaryKey(autoGenerate = false)
    val recipeId: String = UUID.randomUUID().toString()
) : Serializable