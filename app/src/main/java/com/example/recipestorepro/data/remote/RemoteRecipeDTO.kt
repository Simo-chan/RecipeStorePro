package com.example.recipestorepro.data.remote

import java.io.Serializable

data class RemoteRecipeDTO(
    val id: String,
    val title: String?,
    val ingredients: String?,
    val instructions: String?,
    val date: Long,
    val isFavorite: Boolean
) : Serializable