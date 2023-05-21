package com.example.aplicationpeople.services;

import com.example.aplicationpeople.model.AddressOutDTO;
import com.example.aplicationpeople.model.PersonInputDTO;
import com.example.aplicationpeople.model.PersonOutDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface PeopleService {

    @GET("/people")
    Call<List<PersonOutDTO>> getPeople();

    @POST("/people")
    Call<PersonOutDTO> postPerson(@Body PersonInputDTO person);

    @PUT("/people/{personId}")
    Call<PersonOutDTO> putPerson(@Path("personId") Long personId, @Body PersonInputDTO person);

}
