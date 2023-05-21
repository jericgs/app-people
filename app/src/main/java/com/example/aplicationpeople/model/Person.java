package com.example.aplicationpeople.model;

import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("dateBirth")
    private String dateBirth;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }
}
