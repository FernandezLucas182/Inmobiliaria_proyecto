package com.example.inmob.request;

import com.example.inmob.model.Propietario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT; // Asegúrate de importar PUT

public interface ApiService {

    @FormUrlEncoded
    @POST("api/propietarios/login") // Corregido a minúsculas y plural
    Call<String> login(
            @Field("Usuario") String email,
            @Field("Clave") String password
    );

    @GET("api/propietarios") // Corregido a minúsculas y plural
    Call<Propietario> obtenerPerfil(@Header("Authorization") String token);



    @PUT("api/propietarios/actualizar") // Corregido a minúsculas y plural
    Call<Propietario> actualizarPerfil(
            @Header("Authorization") String token,
            @Body Propietario propietario
    );


    @FormUrlEncoded
    @PUT("api/Propietarios/changePassword")
    Call<Void> cambiarClave(
            @Header("Authorization") String token,
            @Field("currentPassword") String actual,
            @Field("newPassword") String nueva
    );
    // ======================================================

}
