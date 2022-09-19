package com.example.flixster


import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class MovieAdapter(private val context: Context, private val movies: List<Movie>):
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() =  movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        var posterImageUrl = ""
        fun bind(movie: Movie){
            if (context.resources.configuration.orientation === Configuration.ORIENTATION_LANDSCAPE){
                posterImageUrl = "https://image.tmdb.org/t/p/w342/${movie.backdropPath}"
            }
            else{
                posterImageUrl = "https://image.tmdb.org/t/p/w342/${movie.posterPath}"
            }
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            Glide.with(context).load(posterImageUrl).placeholder(R.drawable.placeholder).error(R.drawable.error).into(ivPoster)
            Log.i("orient2", context.resources.configuration.orientation.toString())

        }
    }
}
