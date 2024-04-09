package com.soti.movieskotlin.Domain

import com.google.gson.GsonBuilder
import com.soti.movieskotlin.Data.Model.MovieList
import com.soti.movieskotlin.Utils.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey:String,@Query("language")languaje:String, @Query("page") page:Int): MovieList

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey:String,@Query("language")languaje:String, @Query("page") page:Int): MovieList

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey:String,@Query("language")languaje:String, @Query("page") page:Int): MovieList

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key")apiKey: String,@Query("language")languaje:String,@Query("page") page: Int): MovieList
}

object RetrofitClient{
    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}