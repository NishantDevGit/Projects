package com.clickbook.bookmymovie.controller;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class Response {

    private Integer  total;
    List<Movie> movies;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
