package com.example.aplicationpeople.services;

import com.example.aplicationpeople.model.PersonInputDTO;
import com.example.aplicationpeople.model.PersonOutDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface PeopleService {

    @GET("/people")
    Call<List<PersonOutDTO>> getPeople();

    @POST("/people")
    Call<PersonOutDTO> postPerson(@Body PersonInputDTO person);

}
