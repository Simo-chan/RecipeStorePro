package com.example.recipestorepro.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipestorepro.data.local.RecipeDB
import com.example.recipestorepro.data.local.dao.RecipeDao
import com.example.recipestorepro.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context) =
        SessionManager(context)


    @Provides
    @Singleton
    fun provideRecipeDB(
        @ApplicationContext context: Context
    ): RecipeDB = Room.databaseBuilder(
        context,
        RecipeDB::class.java,
        "recipe_db"
    ).build()

    @Provides
    @Singleton
    fun provideRecipeDao(recipeDb: RecipeDB) = recipeDb.getRecipeDao()
}