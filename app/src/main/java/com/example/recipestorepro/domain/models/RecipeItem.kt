package com.example.recipestorepro.domain.models

import java.io.Serializable

data class RecipeItem(
    val id: String,
    val title: String?,
    val instructions: String?,
    val ingredients: String?,
    val date: Long,
    var remotelyConnected: Boolean = false,
    var locallyDeleted: Boolean = false,
    var isFavorite: Boolean = false
) : Serializable

