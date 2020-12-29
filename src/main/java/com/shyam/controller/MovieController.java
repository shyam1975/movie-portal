package com.shyam.controller;

import java.io.IOException;
import java.util.List;

import com.shyam.exception.MovieNotFoundException;
import com.shyam.model.HttpResponse;
import com.shyam.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shyam.model.Movie;

import static com.shyam.controller.MovieController.ROOT_PATH;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Shyam Jeganathan
 */

@RestController
//@RequestMapping(path = { "/", "/movie"})
@RequestMapping(path = {ROOT_PATH})
@Api(value = "MovieController", description = "REST APIs related to Movie Entity")
public class MovieController {
	public static final String MOVIE_DELETED_SUCCESSFULLY = "Movie deleted successfully";
	public static final String ROOT_PATH = "/movie";

	/**
	 * The service class used by the RestController
	 */
	@Autowired
	MovieService movieService;

	@ApiOperation(value = "Get list of Movies in the System ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK")
	})
	@GetMapping("/list")
	/**
	 * call the service class to get a list of all available movies in the system
	 * generate and return a ResponseEntity containing list of Movies
	 */
	public ResponseEntity<List<Movie>> getMovies() {


		List<Movie> movies = movieService.getAllMovies();

		ResponseEntity<List<Movie>> responseEntity = new ResponseEntity<>(movies, OK);
		return responseEntity;
	}

	@ApiOperation(value = "Find movie in system by Id ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "Not Found"),
	})
	@GetMapping("/find/{id}")
	/**
	 * call service class to get the movie for corresponding id
	 * propagate exception thrown by service class
	 */
	public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id) throws MovieNotFoundException {

		Movie movie = movieService.findMovieById(id);
		return new ResponseEntity<>(movie, OK);
	}

	@ApiOperation(value = "Update movie in system by Id ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "Not Found"),
	})
	@PutMapping("/update")
	/**
	 * call service layer to update movie
	 * propagate exception if movie not found
	 */
	public ResponseEntity<Integer> updateMovie(@RequestBody Movie movie) throws MovieNotFoundException {
		Integer numUpdates = movieService.update(movie);
		return new ResponseEntity<>(numUpdates, OK);
	}

	@ApiOperation(value = "Insert a movie into system ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
	})
	@PostMapping("/insert")
	/**
	 * call service layer to insert movie, system does NOT check for duplicate movies
	 * duplicates with same title could exist in the system
	 */
	public ResponseEntity<Movie> insertMovie(@RequestBody Movie movie) {
		Movie newMovie = movieService.insertMovie(movie);  //call to service class
		return new ResponseEntity<>(newMovie, OK);
	}

	@ApiOperation(value = "Delete movie in system by Id ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "Not Found"),
	})
	@DeleteMapping("/delete/{id}")
	/**
	 * call service layer to delete movie with specified id
	 * propagate exception if movie is not found in system
	 */
	public ResponseEntity<HttpResponse> deleteMovie(@PathVariable("id") Long id) throws MovieNotFoundException {
		movieService.deleteMovie(id);  //call to service class
		return response(OK, MOVIE_DELETED_SUCCESSFULLY );
	}

	/**
	 * create a wrapper with HttpResponse object taking in the HttpStatus and a message
	 */
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {

		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
				message), httpStatus);
	}


}
