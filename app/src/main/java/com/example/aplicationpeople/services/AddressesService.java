package com.example.aplicationpeople.services;

import com.example.aplicationpeople.model.AddressInputDTO;
import com.example.aplicationpeople.model.AddressOutDTO;
import com.example.aplicationpeople.model.PersonInputDTO;
import com.example.aplicationpeople.model.PersonOutDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddressesService {

    @POST("/addresses")
    Call<AddressOutDTO> postAddress(@Body AddressInputDTO address);

}
