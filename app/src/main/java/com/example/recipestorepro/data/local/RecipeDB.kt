package com.example.recipestorepro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipestorepro.domain.models.LocalRecipe

@Database(
    entities = [LocalRecipe::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDB : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao
}