package com.soti.movieskotlin.UI.Movie

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.soti.movieskotlin.Core.Resource
import com.soti.movieskotlin.Data.Model.Movie
import com.soti.movieskotlin.Data.Model.MovieList
import com.soti.movieskotlin.Data.Network.MovieService
import com.soti.movieskotlin.Domain.MovieImplemModel
import com.soti.movieskotlin.Domain.RetrofitClient
import com.soti.movieskotlin.R
import com.soti.movieskotlin.UI.Movie.Adapters.Concat.PopularConcatAdapter
import com.soti.movieskotlin.UI.Movie.Adapters.Concat.TopRatedConcatAdapter
import com.soti.movieskotlin.UI.Movie.Adapters.Concat.UpcomingConcatAdapter
import com.soti.movieskotlin.UI.Movie.Adapters.MovieAdapter
import com.soti.movieskotlin.ViewModel.MovieViewModel
import com.soti.movieskotlin.ViewModel.MovieViewModelFactory
import com.soti.movieskotlin.databinding.FragmentMovieBinding

class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.onMovieClickListener,
    PopularConcatAdapter.OnPopularListEndReachedListener,
    TopRatedConcatAdapter.OnTopListEndReachedListener,
    UpcomingConcatAdapter.OnTUpListEndReachedListener {

    private lateinit var binding: FragmentMovieBinding
    private var page: Int = 1
    private var pageTop: Int = 1
    private var pageUp: Int = 1

    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieImplemModel(
                MovieService(RetrofitClient.webService)
            )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()




        /*  viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer {
              when (it) {
                  is Resource.Loading -> {

                      binding.progressBar.visibility = View.VISIBLE
                  }
                  is Resource.Success -> {
                      binding.progressBar.visibility = View.GONE
                      concatAdapter.apply {
                          addAdapter(
                              0,
                              UpcomingConcatAdapter(
                                  MovieAdapter(
                                      it.data.first.results,
                                      this@MovieFragment
                                  )
                              )
                          )
                          addAdapter(
                              1,
                              TopRatedConcatAdapter(
                                  MovieAdapter(
                                      it.data.second.results,
                                      this@MovieFragment
                                  )
                              )
                          )
                          addAdapter(
                              2,
                              PopularConcatAdapter(
                                  MovieAdapter(
                                      it.data.third.value?.results ?: emptyList(),
                                      this@MovieFragment
                                  )
                              )
                          )
                      }

                      binding.rvMovies.adapter = concatAdapter
                  }
                  is Resource.Failure -> {
                      binding.progressBar.visibility = View.GONE
                      Snackbar.make(view, "${it.exception}", Snackbar.LENGTH_SHORT)
                          .setBackgroundTint(Color.RED)
                          .setTextColor(Color.WHITE)
                          .show()
                  }
              }
          })*/

        binding.rvMovies.adapter = concatAdapter

       // if (viewModel.upcomingMovies.value == null)
            viewModel.onCreate(page++)

        viewModel.upcomingMovies.observe(viewLifecycleOwner, Observer {
            concatAdapter.apply {
                // Si ya hay un adaptador para películas populares, actualizarlo
                val existingUpAdapter = findUpAdapter()
                if (existingUpAdapter != null) {
                    Toast.makeText(requireContext(),"fak",Toast.LENGTH_LONG).show()
                    existingUpAdapter.updateUpMovies(it.results)
                } else {
                    // Si no hay un adaptador para películas populares, crear uno nuevo
                    val newPopularAdapter = UpcomingConcatAdapter(
                        MovieAdapter(it.results, this@MovieFragment),
                        this@MovieFragment
                    )
                    addAdapter(0, newPopularAdapter)
                }
            }
        })

        viewModel.topRatedMovies.observe(viewLifecycleOwner, Observer {
            concatAdapter.apply {
                // Si ya hay un adaptador para películas populares, actualizarlo
                val existingTopAdapter = findTopAdapter()
                if (existingTopAdapter != null) {
                    Toast.makeText(requireContext(),"fak",Toast.LENGTH_LONG).show()
                    existingTopAdapter.updateTopMovies(it.results)
                } else {
                    // Si no hay un adaptador para películas populares, crear uno nuevo
                    val newPopularAdapter = TopRatedConcatAdapter(
                        MovieAdapter(it.results, this@MovieFragment),
                        this@MovieFragment
                    )
                    addAdapter(1, newPopularAdapter)
                }
            }
        })

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { popularMovies ->
            concatAdapter.apply {
                // Si ya hay un adaptador para películas populares, actualizarlo
                val existingPopularAdapter = findPopularAdapter()
                if (existingPopularAdapter != null) {
                    Toast.makeText(requireContext(),"fak",Toast.LENGTH_LONG).show()
                    existingPopularAdapter.updatePopularMovies(popularMovies.results)
                } else {
                    // Si no hay un adaptador para películas populares, crear uno nuevo
                    val newPopularAdapter = PopularConcatAdapter(
                        MovieAdapter(popularMovies.results, this@MovieFragment),
                        this@MovieFragment
                    )
                    addAdapter(2, newPopularAdapter)
                }
            }
        })



        viewModel.isLoading2.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })


    }

    private fun ConcatAdapter.findPopularAdapter(): PopularConcatAdapter? {
        // Buscar el adaptador de películas populares en el ConcatAdapter
        adapters.forEach { adapter ->
            if (adapter is PopularConcatAdapter) {
                return adapter
            }
        }
        return null
    }

    private fun ConcatAdapter.findTopAdapter(): TopRatedConcatAdapter? {
        // Buscar el adaptador de películas populares en el ConcatAdapter
        adapters.forEach { adapter ->
            if (adapter is TopRatedConcatAdapter) {
                return adapter
            }
        }
        return null
    }

    private fun ConcatAdapter.findUpAdapter(): UpcomingConcatAdapter? {
        // Buscar el adaptador de películas populares en el ConcatAdapter
        adapters.forEach { adapter ->
            if (adapter is UpcomingConcatAdapter) {
                return adapter
            }
        }
        return null
    }

    override fun onMovieClick(movie: Movie) {

        val action = MovieFragmentDirections
            .actionMovieFragmentToMovieDetailFragment(
                movie.poster_path,
                movie.backdrop_path,
                movie.vote_average.toFloat(),
                movie.vote_count,
                movie.overview,
                movie.title,
                movie.original_languaje,
                movie.release_date
            )
        findNavController().navigate(action)
    }

    // Implementación del método de la interfaz para manejar el evento de llegar al final del RecyclerView
    override fun onPopularListEndReached() {
        viewModel.onPopularListEndReached(page++,"POPULAR")
        //Toast.makeText(requireContext(),"LLEGO",Toast.LENGTH_LONG).show()
    }

    override fun onTopListEndReached() {
        viewModel.onPopularListEndReached(pageTop++,"TOP")
       // Toast.makeText(requireContext(),pageTop.toString(),Toast.LENGTH_LONG).show()
    }

    override fun onUpListEndReached() {
        viewModel.onPopularListEndReached(pageUp++,"UP")
        //Toast.makeText(requireContext(),"LLEGO",Toast.LENGTH_LONG).show()
    }

}