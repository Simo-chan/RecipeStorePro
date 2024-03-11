package com.example.recipestorepro.di

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.example.recipestorepro.data.local.RecipeDB
import com.example.recipestorepro.data.local.RecipeDao
import com.example.recipestorepro.data.remote.ContentTypeInterceptor
import com.example.recipestorepro.data.remote.RecipeApi
import com.example.recipestorepro.data.repository.RecipeRepoImpl
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.use_case.CreateUserUseCase
import com.example.recipestorepro.domain.use_case.GetUserUseCase
import com.example.recipestorepro.domain.use_case.LogOutUseCase
import com.example.recipestorepro.domain.use_case.LoginUseCase
import com.example.recipestorepro.data.utils.SessionManager
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
    private const val BASE_URL = "https://recipe-store-pro-api-32d5feef64b4.herokuapp.com"

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
        val contentTypeInterceptor = ContentTypeInterceptor()

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(contentTypeInterceptor)
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
        sessionManager: SessionManager,
        resources: Resources
    ): RecipeRepo {
        return RecipeRepoImpl(
            recipeApi,
            recipeDao,
            sessionManager,
            resources
        )
    }

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun provideCreateUserUseCase(
        repo: RecipeRepo,
        resources: Resources
    ): CreateUserUseCase {
        return CreateUserUseCase(repo, resources)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        repo: RecipeRepo
    ): GetUserUseCase {
        return GetUserUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repo: RecipeRepo,
        resources: Resources
    ): LoginUseCase {
        return LoginUseCase(repo, resources)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        repo: RecipeRepo
    ): LogOutUseCase {
        return LogOutUseCase(repo)
    }
}