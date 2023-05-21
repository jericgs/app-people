package com.example.aplicationpeople.model;

import com.google.gson.annotations.SerializedName;

public class AddressInputDTO {

    @SerializedName("person")
    private PersonBaseDTO person;

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

    public AddressInputDTO(PersonBaseDTO person, String street, String zipCode, String number, String city, Boolean isPrimary) {
        this.person = person;
        this.street = street;
        this.zipCode = zipCode;
        this.number = number;
        this.city = city;
        this.isPrimary = isPrimary;
    }

    public PersonBaseDTO getPerson() {
        return person;
    }

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

    public void setPerson(PersonBaseDTO person) {
        this.person = person;
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
