package com.clickbook.bookmymovie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class MovieController {


    @GetMapping("/test")
    public String sayHello(){
        return "Movies list in Progress";
    }

    @GetMapping("/movie")
   public Response getmovies(@RequestHeader("count")Integer count){
     List<Movie> movies = new ArrayList<Movie>();
        if(count == null) count = 100;
        if(count > 500000) count = 500000;
        for (int i =0 ; i < count; i++){
            Movie movie = new Movie();
            movie.setId(UUID.randomUUID().toString());
            movie.setName("Name-"+UUID.randomUUID().toString());
            movie.setDirector("Director-"+UUID.randomUUID().toString());
            movie.setLeadActor("LeadActor-" +UUID.randomUUID().toString());
            movie.setProductionName("ProductionName-"+UUID.randomUUID().toString());
            movie.setReleaseDate(new Date());
            movies.add(movie);
        }

     Response response = new Response();
        response.setMovies(movies);
        response.setTotal(count);
      return response;
   }



}