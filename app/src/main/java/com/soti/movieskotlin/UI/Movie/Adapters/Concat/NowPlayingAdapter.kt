package com.soti.movieskotlin.UI.Movie.Adapters.Concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soti.movieskotlin.Core.BaseConcatHolder
import com.soti.movieskotlin.Data.Model.Movie
import com.soti.movieskotlin.UI.Movie.Adapters.MovieAdapter
import com.soti.movieskotlin.UI.Movie.Adapters.MovieNowAdapter
import com.soti.movieskotlin.databinding.NowPlayingMoviesBinding

class NowPlayingAdapter ( private val moviesAdapter: MovieNowAdapter,
private val endReachedListener: OnNowPlayingListEndReachedListener) :
RecyclerView.Adapter<BaseConcatHolder<*>>() {

    interface OnNowPlayingListEndReachedListener {
        fun onNowPlayingListEndReached()
    }

    fun updatePopularMovies(newMovies: List<Movie>) {
        // Actualizar el conjunto de datos de películas populares en el adaptador
        moviesAdapter.updateMovies(newMovies)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding = NowPlayingMoviesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ConcatViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is NowPlayingAdapter.ConcatViewHolder -> moviesAdapter.let { adapter ->
                holder.bind(adapter)
            }
        }
    }

    private inner class ConcatViewHolder(
        val binding: NowPlayingMoviesBinding
    ) : BaseConcatHolder<MovieNowAdapter>(binding.root) {
        override fun bind(adapter: MovieNowAdapter) {
            binding.rvNow.adapter = adapter
            setupScrollListener()
        }


        private fun setupScrollListener() {
            binding.rvNow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition =
                        layoutManager.findLastCompletelyVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    // Verificar si se ha llegado al final del RecyclerView
                    if (lastVisibleItemPosition == totalItemCount - 1) {
                        // Notificar a la capa de la UI que se llegó al final del RecyclerView
                        endReachedListener.onNowPlayingListEndReached()

                    }
                }
            })
        }

    }
}