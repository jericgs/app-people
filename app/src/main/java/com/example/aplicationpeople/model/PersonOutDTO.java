package com.example.aplicationpeople.model;

import com.google.gson.annotations.SerializedName;

public class PersonOutDTO extends PersonBaseDTO{

    @SerializedName("name")
    private String name;

    @SerializedName("dateBirth")
    private String dateBirth;

    public String getName() {
        return name;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }
}
