package com.example.aplicationpeople.model;

import com.google.gson.annotations.SerializedName;

public class AddressBaseDTO {

    @SerializedName("id")
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
