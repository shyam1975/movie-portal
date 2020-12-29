package com.shyam;

import com.shyam.model.HttpResponse;
import com.shyam.model.Movie;
//import org.junit.Assert;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoviePortalTest
{
    @LocalServerPort
    int randomServerPort;

    private static Long insertedId;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Test
    @Order(1)
    public void testAddMovie() throws URISyntaxException
    {
        LOGGER.info("testAddMovie executing");

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+randomServerPort+"/movie/insert";
        URI uri = new URI(baseUrl);
        Movie movie = new Movie(null, "title2", "category2", 2);

        HttpEntity<Movie> request = new HttpEntity<>(movie);

        ResponseEntity<Movie> result = restTemplate.postForEntity(uri, request, Movie.class);
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().getTitle().equals("title2"));

        insertedId = result.getBody().getMovieId();

    }

    @Test
    @Order(2)
    public void testUpdateMovie() throws URISyntaxException
    {
        LOGGER.info("testUpdateMovie executing");

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+randomServerPort+"/movie/update";
        URI uri = new URI(baseUrl);
        Movie movie = new Movie(insertedId, "title2Updated", "category2Updated", 2.222);

        HttpEntity<Movie> request = new HttpEntity<>(movie);

        //ResponseEntity<Integer> result = restTemplate.postForEntity(uri, request, Integer.class);

        ResponseEntity<Integer> result = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Integer.class, insertedId);

        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().equals(new Integer(1)));

    }

    @Test
    @Order(3)
    public void testFindMovie() throws URISyntaxException
    {
        LOGGER.info("testFindMovie executing");

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+randomServerPort+"/movie/find/" + insertedId;
        URI uri = new URI(baseUrl);

        ResponseEntity<Movie> result = restTemplate.getForEntity(uri, Movie.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().getTitle().equals("title2Updated"));

    }

    @Test
    @Order(4)
    public void testGetMovieList() throws URISyntaxException
    {
        LOGGER.info("testGetMovieList executing");

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/movie/list";
        URI uri = new URI(baseUrl);

        ResponseEntity<List> result = restTemplate.getForEntity(uri, List.class);

        List<Movie> movieList = result.getBody();

        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
    }



    @Test
    @Order(5)
    public void testDeleteMovie() throws URISyntaxException
    {
        LOGGER.info("testDeleteMovie executing");

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+randomServerPort+"/movie/delete/" + insertedId;
        URI uri = new URI(baseUrl);

        //restTemplate.delete(uri);

        ResponseEntity<HttpResponse> response =
                restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, HttpResponse.class, insertedId);

        assertEquals(200, response.getStatusCodeValue());
    }




}