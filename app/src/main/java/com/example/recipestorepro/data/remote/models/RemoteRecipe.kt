package com.example.recipestorepro.data.remote.models

import java.io.Serializable

data class RemoteRecipe(
    val id: String,
    val title: String?,
    val ingredients: String?,
    val instructions: String?,
    val date: Long
) : Serializable