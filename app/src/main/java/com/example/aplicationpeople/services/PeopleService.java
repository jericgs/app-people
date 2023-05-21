package com.example.aplicationpeople.services;

import com.example.aplicationpeople.model.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface PeopleService {

    @GET("/people")
    Call<List<Person>> getPeople();

    @POST("/people")
    Call<Person> postPerson(@Body Person person);

}
