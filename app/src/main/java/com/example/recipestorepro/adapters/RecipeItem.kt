package com.example.recipestorepro.adapters

import java.io.Serializable

data class RecipeItem(
    val id: String,
    val title: String?,
    val date: Long
) : Serializable

