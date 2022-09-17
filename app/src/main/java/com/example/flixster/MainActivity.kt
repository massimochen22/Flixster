package com.example.flixster

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException


private const val TAG = "MainActivity"
    private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=7e2102c9ed0ef3ba569e5b1ee5648608"
class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rvMovies = findViewById(R.id.rvMovies)
        val movieAdapter = MovieAdapter(this,movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.apply{
            var itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            getDrawable(R.drawable.divider)?.let { itemDecoration.setDrawable(it) }
            addItemDecoration(itemDecoration)

        }
        val client = AsyncHttpClient()

        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG,"onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                try {
                    Log.i(TAG, "onSuccess $json")
                    Log.i("orient3",resources.configuration.orientation.toString())
                    val movieJsonArray = json!!.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))

                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list $movies")
                }
                catch (e:JSONException){
                    Log.e(TAG,"encontered exception $e")
                }

            }

        })
    }
}