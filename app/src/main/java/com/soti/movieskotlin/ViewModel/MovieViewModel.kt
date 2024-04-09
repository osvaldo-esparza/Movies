package com.soti.movieskotlin.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.soti.movieskotlin.Core.Resource
import com.soti.movieskotlin.Data.Model.Movie
import com.soti.movieskotlin.Data.Model.MovieList
import com.soti.movieskotlin.Domain.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(private val repo: MovieModel) : ViewModel() {



    private val _upcomingMovies = MutableLiveData<MovieList>()
    val upcomingMovies: LiveData<MovieList> = _upcomingMovies

    private val _topRatedMovies = MutableLiveData<MovieList>()
    val topRatedMovies: LiveData<MovieList> = _topRatedMovies

    private val _popularMovies = MutableLiveData<MovieList>()
    val popularMovies: LiveData<MovieList> = _popularMovies

    private val _nowPlayingMovies = MutableLiveData<MovieList>()
    val nowPlayingMovies: LiveData<MovieList> = _nowPlayingMovies

    private var _isLoading = MutableLiveData<Boolean>()

    val isLoading2: LiveData<Boolean> = _isLoading
    var isLastPage = false

    var isLoading = false



    //metodo para comunicarse con el Model
    /*fun fetchMainScreenMovies(page: Int) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())
        try {
            val upcomingMoviesResult = repo.getUpcomingMovies()
            val topRatedMoviesResult = repo.getTopRatedMovies()
            val popularMoviesResult = repo.getPopularMovies(page)

            _upcomingMovies.postValue(upcomingMoviesResult)
            _topRatedMovies.postValue(topRatedMoviesResult)
            _popularMovies.postValue(popularMoviesResult)


            emit(
                Resource.Success(
                    Triple(
                        _upcomingMovies,
                        _topRatedMovies,
                        _popularMovies
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
*/


    override fun onCleared() {
        super.onCleared()

    }

    init {
        _isLoading.value = false
    }

    fun onCreate(page:Int,pageUp:Int,pageTop:Int,pageNow:Int,languaje:String){
        if(isLastPage || isLastPage)return

        isLoading = true
        //cargamos
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val now = repo.getNowPlayingMovies(pageNow,languaje)
                now.let {
                    _nowPlayingMovies.postValue(
                        it
                    )
                }

                val result = repo.getUpcomingMovies(pageUp,languaje)
                result.let {
                    _upcomingMovies.postValue(it)
                }

                val top = repo.getTopRatedMovies(pageTop,languaje)
                top.let {
                    _topRatedMovies.postValue(it)
                }

                val popular = repo.getPopularMovies(page,languaje)
                popular.let {
                    _popularMovies.postValue(it)
                }


            }
            catch (e: Exception)
            {
                Log.i("OSVALDO",e.message.toString())
            }
            finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun onPopularListEndReached(page: Int,list: String,languaje:String) {
        // Lógica para manejar el evento de llegar al final del RecyclerView
        // Por ejemplo, cargar más películas populares
        loadMorePopularMovies(page,list,languaje)
    }

    private fun loadMorePopularMovies(page: Int, list:String,languaje: String) {
        if(isLastPage)return

        isLoading = true
        //cargamos
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                when(list)
                {
                    "POPULAR"->{
                        val newPopularMovies: MovieList = repo.getPopularMovies(page,languaje)
                        val currentPopularMovies = popularMovies.value?.results?.toMutableList() ?: mutableListOf()
                        currentPopularMovies.addAll(newPopularMovies.results)
                        _popularMovies.postValue(MovieList(currentPopularMovies.size, currentPopularMovies))
                    }
                    "TOP"->{
                        val newTopMovies: MovieList = repo.getTopRatedMovies(page,languaje)
                        val currentTopMovies = topRatedMovies.value?.results?.toMutableList() ?: mutableListOf()
                        currentTopMovies.addAll(newTopMovies.results)
                        _topRatedMovies.postValue(MovieList(currentTopMovies.size, currentTopMovies))
                    }
                    "UP"->{
                        val newUpMovies: MovieList = repo.getUpcomingMovies(page,languaje)
                        val currentUpMovies = upcomingMovies.value?.results?.toMutableList() ?: mutableListOf()
                        currentUpMovies.addAll(newUpMovies.results)
                        _upcomingMovies.postValue(MovieList(currentUpMovies.size, currentUpMovies))
                    }
                    "NOW"->{
                        val newNowMovies: MovieList = repo.getNowPlayingMovies(page,languaje)
                        val currentNowPlaying = nowPlayingMovies.value?.results?.toMutableList() ?: mutableListOf()
                        currentNowPlaying.addAll(newNowMovies.results)
                        _nowPlayingMovies.postValue(MovieList(currentNowPlaying.size,currentNowPlaying))
                    }
                }

               // page++
            } catch (e: Exception) {
                // Manejar el error, si es necesario
            }
            finally {
                isLoading = false
                _isLoading.postValue(false)
            }
        }
    }


}

class MovieViewModelFactory(private val repo: MovieModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieModel::class.java).newInstance(repo)
    }

}

//para retornar mas de 3 en el emit del metodo fetchMainScreeMovies
data class Ntuple4<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)