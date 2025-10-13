package com.example.inmob.request;

import com.example.inmob.model.Propietario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/Propietarios/login")
    Call<String> login(
            @Field("Usuario") String email,
            @Field("Clave") String password
    );


    @GET("api/Propietarios")
    Call<Propietario> obtenerPerfil(@Header("Authorization") String token);

}
