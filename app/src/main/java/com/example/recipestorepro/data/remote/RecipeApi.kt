package com.example.recipestorepro.data.remote

import com.example.recipestorepro.data.remote.models.RemoteRecipe
import com.example.recipestorepro.data.remote.models.SimpleResponse
import com.example.recipestorepro.data.remote.models.User
import com.example.recipestorepro.utils.Constants.API_VERSION
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RecipeApi {

    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/register")
    suspend fun registerAccount(@Body user: User): SimpleResponse


    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/login")
    suspend fun login(@Body user: User): SimpleResponse


    //========= Recipes =========


    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/recipes/create")
    suspend fun createRecipe(
        @Header("Authorization") token: String,
        @Body recipe: RemoteRecipe
    ): SimpleResponse


    @Headers("Content-Type: application/json")
    @GET("$API_VERSION/recipes")
    suspend fun getAllRecipes(
        @Header("Authorization") token: String,
    ): List<RemoteRecipe>


    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/recipes/update")
    suspend fun updateRecipe(
        @Header("Authorization") token: String,
        @Body recipe: RemoteRecipe
    ): SimpleResponse


    @Headers("Content-Type: application/json")
    @DELETE("$API_VERSION/recipes/delete")
    suspend fun deleteRecipe(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): SimpleResponse
}
