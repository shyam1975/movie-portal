package com.shyam.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Movie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long movieId;

	private String title;
	private String category;
	private double rating;

	public Movie() {
	}

	public Movie(Long movieId, String title, String category, double rating) {
		this.movieId = movieId;
		this.title = title;
		this.category = category;
		this.rating = rating;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Movie movie = (Movie) o;
		return Double.compare(movie.rating, rating) == 0 && Objects.equals(movieId, movie.movieId) && Objects.equals(title, movie.title) && Objects.equals(category, movie.category);
	}

	@Override
	public int hashCode() {
		return Objects.hash(movieId, title, category, rating);
	}

	@Override
	public String toString() {
		return "Movie{" +
				"movieId=" + movieId +
				", title='" + title + '\'' +
				", category='" + category + '\'' +
				", rating=" + rating +
				'}';
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
}