package com.soti.movieskotlin.Domain

import com.soti.movieskotlin.Data.Model.MovieList

interface MovieModel {
    suspend fun getNowPlayingMovies(page: Int,languaje:String): MovieList
    suspend fun getUpcomingMovies(page: Int,languaje:String): MovieList
    suspend fun getTopRatedMovies(page: Int,languaje:String): MovieList
    suspend fun getPopularMovies(page: Int,languaje:String): MovieList


}