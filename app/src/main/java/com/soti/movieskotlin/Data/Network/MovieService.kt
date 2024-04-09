package com.soti.movieskotlin.Data.Network

import com.soti.movieskotlin.Data.Model.MovieList
import com.soti.movieskotlin.Domain.WebService
import com.soti.movieskotlin.R
import com.soti.movieskotlin.Utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieService(private val webService: WebService) {



    suspend fun getUpcomingMovies(page: Int,languaje:String): MovieList{
        val ws: MovieList
        withContext(Dispatchers.IO){
           ws = webService.getUpcomingMovies(AppConstants.API_KEY,languaje,page)
        }
        return ws
    }

    suspend fun getTopRatedMovies(page: Int,languaje:String): MovieList{
        val ws: MovieList
        withContext(Dispatchers.IO){
            ws =  webService.getTopRatedMovies(AppConstants.API_KEY,
                languaje,page)
        }
        return ws

    }

    suspend fun getPopularMovies(page: Int,languaje:String): MovieList{
        val ws: MovieList
        withContext(Dispatchers.IO){
            ws =  webService.getPopularMovies(AppConstants.API_KEY,languaje,page)
        }
        return ws

    }

    suspend fun getNowPlayingMovies(page: Int,languaje:String):MovieList{
        val ws: MovieList
        withContext(Dispatchers.IO){
            ws = webService.getNowPlayingMovies(AppConstants.API_KEY,languaje,page)
        }
        return ws
    }
}