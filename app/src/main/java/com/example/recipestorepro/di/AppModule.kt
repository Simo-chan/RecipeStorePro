package com.example.recipestorepro.di

import android.content.Context
import androidx.room.Room
import com.example.recipestorepro.data.local.RecipeDB
import com.example.recipestorepro.data.local.RecipeDao
import com.example.recipestorepro.data.remote.ContentTypeInterceptor
import com.example.recipestorepro.data.remote.RecipeApi
import com.example.recipestorepro.data.repository.RecipeRepoImpl
import com.example.recipestorepro.domain.repository.RecipeRepo
import com.example.recipestorepro.domain.use_case.AccountUseCases
import com.example.recipestorepro.domain.use_case.CreateUser
import com.example.recipestorepro.domain.use_case.GetUser
import com.example.recipestorepro.domain.use_case.LogOut
import com.example.recipestorepro.domain.use_case.Login
import com.example.recipestorepro.domain.utils.ResourceManager
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
        resourceManager: ResourceManager
    ): RecipeRepo {
        return RecipeRepoImpl(
            recipeApi,
            recipeDao,
            sessionManager,
            resourceManager
        )
    }

    @Provides
    @Singleton
    fun provideAccountUseCases(
        repo: RecipeRepo,
        resManager: ResourceManager
    ): AccountUseCases {
        return AccountUseCases(
            createUser = CreateUser(repo, resManager),
            getUser = GetUser(repo),
            login = Login(repo, resManager),
            logOut = LogOut(repo)
        )
    }

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context): ResourceManager {
        return object : ResourceManager {
            override fun getString(id: Int): String {
                return context.getString(id)
            }
        }
    }
}