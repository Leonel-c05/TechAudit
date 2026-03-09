package com.abudsystem.techaudit.network
/*
inicio de la conexion con una API
 */
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun obtenerPosts(): List<Post>

}
