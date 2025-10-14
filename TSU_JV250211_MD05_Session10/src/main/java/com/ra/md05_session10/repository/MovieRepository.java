package com.ra.md05_session10.repository;

import com.ra.md05_session10.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}