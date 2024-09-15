package com.example.recipestorepro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalRecipe::class],
    version = 3,
    exportSchema = false
)
abstract class RecipeDB : RoomDatabase() {
    abstract fun getRecipeDao(): RecipeDao
}