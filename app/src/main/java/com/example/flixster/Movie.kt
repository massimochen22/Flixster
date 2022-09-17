package com.example.flixster

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import android.view.Display
import android.view.WindowManager
import org.json.JSONArray


data class Movie(
    val movieId: Int,
    private val posterPath: String,
    val title: String,
    val overview: String,
){

    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"

    companion object{
        fun fromJsonArray(movieJSONArray: JSONArray): List<Movie>{
            val movies = mutableListOf<Movie>()
            for ( i in 0 until movieJSONArray.length()){
                val movieJson = movieJSONArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")

                    )
                )


            }
            return movies
        }
    }
}