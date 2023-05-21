package com.example.aplicationpeople.services;

import com.example.aplicationpeople.model.AddressInputDTO;
import com.example.aplicationpeople.model.AddressOutDTO;
import com.example.aplicationpeople.model.PersonInputDTO;
import com.example.aplicationpeople.model.PersonOutDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AddressesService {

    @GET("/addresses/{personId}")
    Call<List<AddressOutDTO>> getAddressMain(@Path("personId") Long personId);

    @POST("/addresses")
    Call<AddressOutDTO> postAddress(@Body AddressInputDTO address);

    @DELETE("/addresses/{addressId}")
    Call<Void> deleteAddress(@Path("addressId") Long addressId);

}
