package com.example.recipestorepro.di

import android.content.Context
import androidx.room.Room
import com.example.recipestorepro.data.local.RecipeDB
import com.example.recipestorepro.data.local.dao.RecipeDao
import com.example.recipestorepro.data.remote.RecipeApi
import com.example.recipestorepro.repository.RecipeRepo
import com.example.recipestorepro.repository.RecipeRepoImpl
import com.example.recipestorepro.utils.Constants.BASE_URL
import com.example.recipestorepro.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context) = SessionManager(context)


    @Provides
    @Singleton
    fun provideRecipeDB(
        @ApplicationContext context: Context
    ): RecipeDB = Room.databaseBuilder(
        context, RecipeDB::class.java, "recipe_db"
    ).build()


    @Provides
    @Singleton
    fun provideRecipeDao(recipeDb: RecipeDB) = recipeDb.getRecipeDao()

    @Provides
    @Singleton
    fun provideRecipeApi(): RecipeApi {

        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepo(
        recipeApi: RecipeApi,
        recipeDao: RecipeDao,
        sessionManager: SessionManager
    ): RecipeRepo {
        return RecipeRepoImpl(
            recipeApi,
            recipeDao,
            sessionManager
        )
    }
}