package com.example.flixster


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


const val MOVIE_EXTRA = "MOVIE_EXTRA"

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val playButton = itemView.findViewById<ImageView>(R.id.playButton)
        var posterImageUrl = ""
        fun bind(movie: Movie){
            if (context.resources.configuration.orientation === Configuration.ORIENTATION_LANDSCAPE){
                posterImageUrl = "https://image.tmdb.org/t/p/w342/${movie.backdropPath}"
            }
            else{
                posterImageUrl = "https://image.tmdb.org/t/p/w342/${movie.posterPath}"
            }

            if (movie.voteAverage <= 6.5){
                playButton.visibility = View.INVISIBLE
            }
            else{
                playButton.visibility = View.VISIBLE
            }

            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            Glide.with(context)
                .load(posterImageUrl)
//                .centerCrop()
//                .transform(RoundedCornersTransformation(100,0)) // <-- 1st method
//                .transform(RoundedCorners(30)) //<-- 2nd method
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(ivPoster)
            Log.i("orient2", context.resources.configuration.orientation.toString())

        }

        init{
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
//            1. Get notified of the particular movie which was clicked
            val movie = movies[adapterPosition]
//            2. Use intent system to navigate to the new activity
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA,movie)

            val context2: Activity = context as Activity
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context2,Pair(tvOverview,"tvOverview"),Pair(tvTitle,"tvTitle"))
            context.startActivity(intent,options.toBundle())
        }
    }
}
