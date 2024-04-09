package com.soti.movieskotlin.Domain

import com.soti.movieskotlin.Data.Model.MovieList
import com.soti.movieskotlin.Data.Network.MovieService
import javax.sql.CommonDataSource

class MovieImplemModel(private val dataSource: MovieService): MovieModel {

    override suspend fun getUpcomingMovies(page: Int,languaje:String): MovieList = dataSource.getUpcomingMovies(page,languaje)

    override suspend fun getTopRatedMovies(page: Int,languaje:String): MovieList = dataSource.getTopRatedMovies(page,languaje)

    override suspend fun getPopularMovies(page: Int,languaje:String): MovieList = dataSource.getPopularMovies(page,languaje)

    override suspend fun getNowPlayingMovies(page: Int,languaje:String):MovieList = dataSource.getNowPlayingMovies(page,languaje    )

}