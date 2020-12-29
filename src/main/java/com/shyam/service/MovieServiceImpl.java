package com.shyam.service;

import java.util.List;
import java.util.Optional;

import com.shyam.exception.MovieNotFoundException;
import com.shyam.model.Movie;
import com.shyam.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

	MovieRepository movieRepository;

	@Autowired
	public MovieServiceImpl(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public List<Movie> getAllMovies() {
		List<Movie> movies = movieRepository.findAll();;
		return movies;
	}

	@Override
	public Movie insertMovie(Movie movie) {
		Movie newMovie = movieRepository.save(movie);
		return newMovie;
	}

	@Override
	public Movie findMovieById(long id) throws MovieNotFoundException {
		Optional<Movie> movie = movieRepository.findById(id);

		if (!movie.isPresent()) throw new MovieNotFoundException("Movie Not Found");

		return movie.get();
	}

	@Override
	public Integer update(Movie movie) throws MovieNotFoundException {

		Optional<Movie> existingMovie = movieRepository.findById(movie.getMovieId());
		if (!existingMovie.isPresent()) throw new MovieNotFoundException("Movie Not Found, so cannot be updated");

		Integer numRows = movieRepository.updateMovie(movie.getMovieId(),movie.getTitle(),movie.getCategory(),movie.getRating());
		return numRows;
	}

	@Override
	public void deleteMovie(Long id) throws MovieNotFoundException{
		Optional<Movie> existingMovie = movieRepository.findById(id);
		if (!existingMovie.isPresent()) throw new MovieNotFoundException("Movie Not Found, so cannot be deleted");


		movieRepository.deleteById(id);
	}

}