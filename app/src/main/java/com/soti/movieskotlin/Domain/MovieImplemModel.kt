package com.soti.movieskotlin.Domain

import com.soti.movieskotlin.Data.Model.MovieList
import com.soti.movieskotlin.Data.Network.MovieService
import javax.sql.CommonDataSource

class MovieImplemModel(private val dataSource: MovieService): MovieModel {

    override suspend fun getUpcomingMovies(page: Int): MovieList = dataSource.getUpcomingMovies(page)

    override suspend fun getTopRatedMovies(page: Int): MovieList = dataSource.getTopRatedMovies(page)

    override suspend fun getPopularMovies(page: Int): MovieList = dataSource.getPopularMovies(page)

}