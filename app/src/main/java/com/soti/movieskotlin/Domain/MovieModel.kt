package com.soti.movieskotlin.Domain

import com.soti.movieskotlin.Data.Model.MovieList

interface MovieModel {
    suspend fun getUpcomingMovies(page: Int): MovieList
    suspend fun getTopRatedMovies(page: Int): MovieList
    suspend fun getPopularMovies(page: Int): MovieList
}