package com.soti.movieskotlin.Data.Model

import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("id")                   val id: Int = -1,
    @SerializedName("adult")                val adult: Boolean =false,
    @SerializedName("genre_ids")            val genre_ids: List<Int> = listOf(),
    @SerializedName("backdrop_path")        val backdrop_path: String = "",
    @SerializedName("original_title")       val original_title: String = "",
    @SerializedName("original_language")    val original_languaje: String = "",
    @SerializedName("overview")             val overview:String = "",
    @SerializedName("popularity")           val popularity: Double = 1.0,
    @SerializedName("poster_path")          val poster_path: String = "",
    @SerializedName("release_date")         val release_date: String = "",
    @SerializedName("title")                val title: String = "",
    @SerializedName("video")                val video: Boolean = false,
    @SerializedName("vote_average")         val vote_average: Double = -1.0,
    @SerializedName("vote_count")           val vote_count: Int = -1
    )

data class MovieList(
    @SerializedName("page")             val page : Int = -1,
    @SerializedName("results")          val results: List<Movie> = listOf(),
    @SerializedName("total_pages")      val total_pages: Int = -1,
    @SerializedName("total_results")    val total_results: Int = -1
) {


}

data class MovieList2(
    @SerializedName("results")          val results: List<Movie> = listOf()
)