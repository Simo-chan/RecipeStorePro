package com.example.recipestorepro.data.remote

import com.example.recipestorepro.domain.models.SimpleResponse
import com.example.recipestorepro.domain.models.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RecipeApi {

    @POST("$API_VERSION/users/register")
    suspend fun registerAccount(@Body user: User): SimpleResponse

    @POST("$API_VERSION/users/login")
    suspend fun login(@Body user: User): SimpleResponse


    //========= Recipes =========


    @POST("$API_VERSION/recipes/create")
    suspend fun createRecipe(
        @Header("Authorization") token: String,
        @Body recipe: RemoteRecipeDTO
    ): SimpleResponse

    @GET("$API_VERSION/recipes")
    suspend fun getAllRecipes(
        @Header("Authorization") token: String,
    ): List<RemoteRecipeDTO>

    @POST("$API_VERSION/recipes/update")
    suspend fun updateRecipe(
        @Header("Authorization") token: String,
        @Body recipe: RemoteRecipeDTO
    ): SimpleResponse

    @POST("$API_VERSION/recipes/updateFavoriteStatus")
    suspend fun updateFavoriteStatus(
        @Header("Authorization") token: String,
        @Query("id") id: String,
        @Body isFavorite: Boolean
    ): SimpleResponse

    @DELETE("$API_VERSION/recipes/delete")
    suspend fun deleteRecipe(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): SimpleResponse

    private companion object {
        const val API_VERSION = "/v1"
    }
}
