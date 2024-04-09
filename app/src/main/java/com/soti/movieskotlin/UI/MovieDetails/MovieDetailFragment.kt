package com.soti.movieskotlin.UI.MovieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.soti.movieskotlin.R
import com.soti.movieskotlin.Utils.AppConstants
import com.soti.movieskotlin.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)

        //lenamos los campos con los safeargs
        Glide.with(requireContext())
            .load("${AppConstants.IMG_URL_BASE}${args.posterImageUrl}")
            .centerCrop()
            .into(binding.imageMovie)

        Glide.with(requireContext())
            .load("${AppConstants.IMG_URL_BASE}${args.backgroundImageUrl}")
            .centerCrop()
            .into(binding.imgBackground)

        binding.txtDescrition.text = args.overview
        binding.txtTitle.text = args.title
        binding.txtLanguaje.text = args.languaje
        binding.txtLanguaje.text = "Language ${args.languaje}"
        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
        binding.txtRelease.text = "Released ${args.realeaseDate}"

    }

}