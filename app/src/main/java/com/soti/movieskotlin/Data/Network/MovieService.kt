package com.soti.movieskotlin.Data.Network

import com.soti.movieskotlin.Data.Model.MovieList
import com.soti.movieskotlin.Domain.WebService
import com.soti.movieskotlin.Utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieService(private val webService: WebService) {



    suspend fun getUpcomingMovies(page: Int): MovieList{
        val ws: MovieList
        withContext(Dispatchers.IO){
           ws = webService.getUpcomingMovies(AppConstants.API_KEY,page)
        }
        return ws
    }

    suspend fun getTopRatedMovies(page: Int): MovieList{
        val ws: MovieList
        withContext(Dispatchers.IO){
            ws =  webService.getTopRatedMovies(AppConstants.API_KEY,page)
        }
        return ws

    }

    suspend fun getPopularMovies(page: Int): MovieList{
        val ws: MovieList
        withContext(Dispatchers.IO){
            ws =  webService.getPopularMovies(AppConstants.API_KEY,page)
        }
        return ws

    }
}