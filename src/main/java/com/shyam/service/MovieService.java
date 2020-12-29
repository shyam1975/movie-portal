package com.shyam.service;

import java.util.List;

import com.shyam.exception.MovieNotFoundException;
import com.shyam.model.Movie;

public interface MovieService {
	List<Movie> getAllMovies();
	Movie insertMovie(Movie movie);
	Movie findMovieById(long id) throws MovieNotFoundException;

	Integer update(Movie movie) throws MovieNotFoundException;

	void deleteMovie(Long id) throws MovieNotFoundException;
}