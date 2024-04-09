package com.soti.movieskotlin.UI.Movie.Adapters.Concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soti.movieskotlin.Core.BaseConcatHolder
import com.soti.movieskotlin.Data.Model.Movie
import com.soti.movieskotlin.UI.Movie.Adapters.MovieAdapter
import com.soti.movieskotlin.databinding.TopRaitedMoviesRowBinding

class TopRatedConcatAdapter(
    private val moviesAdapter: MovieAdapter,
    private val endReachedListener: OnTopListEndReachedListener
) : RecyclerView.Adapter<BaseConcatHolder<*>>() {

    interface OnTopListEndReachedListener {
        fun onTopListEndReached()
    }

    fun updateTopMovies(newMovies: List<Movie>) {
        // Actualizar el conjunto de datos de películas populares en el adaptador
        moviesAdapter.updateMovies(newMovies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding =
            TopRaitedMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is ConcatViewHolder -> moviesAdapter.let { adapter ->
                holder.bind(adapter)
            }
        }
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(val binding: TopRaitedMoviesRowBinding) :
        BaseConcatHolder<MovieAdapter>(binding.root) {
        override fun bind(adapter: MovieAdapter) {
            binding.rvTopRaitedMovies.adapter = adapter
            setupScrollListener()
        }

        private fun setupScrollListener() {
            binding.rvTopRaitedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition =
                        layoutManager.findLastCompletelyVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    // Verificar si se ha llegado al final del RecyclerView
                    if (lastVisibleItemPosition == totalItemCount - 1) {
                        // Notificar a la capa de la UI que se llegó al final del RecyclerView
                        endReachedListener.onTopListEndReached()

                    }
                }
            })
        }

    }
}