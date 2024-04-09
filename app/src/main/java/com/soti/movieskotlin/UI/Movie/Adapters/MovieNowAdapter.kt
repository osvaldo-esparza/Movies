package com.soti.movieskotlin.UI.Movie.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soti.movieskotlin.Core.BaseViewHolder
import com.soti.movieskotlin.Data.Model.Movie
import com.soti.movieskotlin.Utils.AppConstants
import com.soti.movieskotlin.databinding.NowItemBinding

class MovieNowAdapter (private var movieList: List<Movie>?,
private val itemClickListener: onMovieNowClickListener) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface onMovieNowClickListener{
        fun onMovieClicks(movie: Movie)
    }

    fun updateMovies(newMovies: List<Movie>) {
        movieList = newMovies
        notifyDataSetChanged() // Notificar al RecyclerView sobre el cambio en los datos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = NowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val holder = MoviesViewHolder(itemBinding,parent.context)

        itemBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it!= DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onMovieClicks(movieList?.get(position) ?: Movie())
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MoviesViewHolder->holder.bind(movieList?.get(position) ?: Movie())
        }
    }

    override fun getItemCount(): Int = movieList!!.size

    private inner class MoviesViewHolder(val binding: NowItemBinding, val context: Context): BaseViewHolder<Movie>(binding.root){
        override fun bind(item: Movie) {
            Glide.with(context)
                .load("${AppConstants.IMG_URL_BASE}${item.poster_path}")
                .centerCrop()
                .into(binding.imgMovie)
        }

    }
}