package com.example.flixster

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class Movie(
    val movieId: Int,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String,
    val title: String,
    val overview: String,
): Parcelable{



    companion object{
        fun fromJsonArray(movieJSONArray: JSONArray): List<Movie>{
            val movies = mutableListOf<Movie>()
            for ( i in 0 until movieJSONArray.length()){
                val movieJson = movieJSONArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getDouble("vote_average"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("backdrop_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")

                    )
                )


            }
            return movies
        }
    }
}