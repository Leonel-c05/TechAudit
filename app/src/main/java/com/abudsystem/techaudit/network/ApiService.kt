package com.abudsystem.techaudit.network
/*
inicio de la conexion con una API como se pidio en el documento
 */
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun obtenerPosts(): List<Post>

}
