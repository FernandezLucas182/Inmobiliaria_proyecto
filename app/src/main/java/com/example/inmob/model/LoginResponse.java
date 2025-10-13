package com.example.inmob.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token") // Asegúrate que "token" sea el nombre exacto del campo en el JSON de respuesta.
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
        