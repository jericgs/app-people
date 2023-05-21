package com.example.aplicationpeople.model;

import com.google.gson.annotations.SerializedName;

public class AddressOutDTO extends AddressBaseDTO{

    @SerializedName("street")
    private String street;

    @SerializedName("zipCode")
    private String zipCode;

    @SerializedName("number")
    private String number;

    @SerializedName("city")
    private String city;

    @SerializedName("isPrimary")
    private Boolean isPrimary;

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }
}
