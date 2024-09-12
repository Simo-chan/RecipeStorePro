package com.example.recipestorepro.di

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.example.recipestorepro.data.local.RecipeDB
import com.example.recipestorepro.data.local.RecipeDao
import com.example.recipestorepro.data.remote.ContentTypeInterceptor
import com.example.recipestorepro.data.remote.RecipeApi
import com.example.recipestorepro.data.repository.AuthRepoImpl
import com.example.recipestorepro.data.repository.RecipeRepoImpl
import com.example.recipestorepro.data.utils.SessionManager
import com.example.recipestorepro.domain.repository.AuthRepo
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.use_case.account.CreateUserUseCase
import com.example.recipestorepro.domain.use_case.account.GetUserUseCase
import com.example.recipestorepro.domain.use_case.account.LogOutUseCase
import com.example.recipestorepro.domain.use_case.account.LoginUseCase
import com.example.recipestorepro.domain.use_case.recipes.CreateRecipeUseCase
import com.example.recipestorepro.domain.use_case.recipes.DeleteRecipeUseCase
import com.example.recipestorepro.domain.use_case.recipes.GetAllRecipesBySearchQueryUseCase
import com.example.recipestorepro.domain.use_case.recipes.GetAllRecipesUseCase
import com.example.recipestorepro.domain.use_case.recipes.GetFavoriteRecipesUseCase
import com.example.recipestorepro.domain.use_case.recipes.SyncRecipesUseCase
import com.example.recipestorepro.domain.use_case.recipes.UpdateFavoriteStatusUseCase
import com.example.recipestorepro.domain.use_case.recipes.UpdateRecipeUseCase
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
    ).fallbackToDestructiveMigration().build()


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
    fun provideAuthRepo(
        recipeApi: RecipeApi,
        sessionManager: SessionManager,
        recipeDao: RecipeDao,
        resources: Resources
    ): AuthRepo {
        return AuthRepoImpl(
            recipeApi,
            sessionManager,
            recipeDao,
            resources
        )
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


    //============== USE CASES =============

    @Provides
    @Singleton
    fun provideCreateUserUseCase(
        repo: AuthRepo,
        resources: Resources
    ): CreateUserUseCase {
        return CreateUserUseCase(repo, resources)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        repo: AuthRepo
    ): GetUserUseCase {
        return GetUserUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        repo: AuthRepo,
        resources: Resources
    ): LoginUseCase {
        return LoginUseCase(repo, resources)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        repo: AuthRepo
    ): LogOutUseCase {
        return LogOutUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideCreateRecipeUserCase(
        repo: RecipeRepo
    ): CreateRecipeUseCase {
        return CreateRecipeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateRecipeUserCase(
        repo: RecipeRepo
    ): UpdateRecipeUseCase {
        return UpdateRecipeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetAllRecipesBySearchQueryUseCase(
        repo: RecipeRepo
    ): GetAllRecipesBySearchQueryUseCase {
        return GetAllRecipesBySearchQueryUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetAllRecipesUseCase(
        repo: RecipeRepo
    ): GetAllRecipesUseCase {
        return GetAllRecipesUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateFavoriteStatusUseCase(
        repo: RecipeRepo
    ): UpdateFavoriteStatusUseCase {
        return UpdateFavoriteStatusUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteRecipesUseCase(
        repo: RecipeRepo
    ): GetFavoriteRecipesUseCase {
        return GetFavoriteRecipesUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideDeleteRecipeUseCase(
        repo: RecipeRepo
    ): DeleteRecipeUseCase {
        return DeleteRecipeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideSyncRecipesUseCase(
        repo: RecipeRepo
    ): SyncRecipesUseCase {
        return SyncRecipesUseCase(repo)
    }
}