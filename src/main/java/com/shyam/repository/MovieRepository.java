package com.shyam.repository;

import com.shyam.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Movie m SET m.title=:title, m.category=:category, m.rating=:rating WHERE m.movieId = :id")
    Integer updateMovie(@Param("id") Long movieId, @Param("title") String title,
                    @Param("category") String category, @Param("rating") double rating);
}
