package com.example.inmob.request;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import com.example.inmob.model.Inmueble;
import com.example.inmob.model.Propietario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/propietarios/login")
    Call<String> login(
            @Field("Usuario") String email,
            @Field("Clave") String password
    );



    @GET("api/propietarios")
    Call<Propietario> obtenerPerfil(@Header("Authorization") String token);



    @PUT("api/propietarios/actualizar")
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


    @GET("api/Inmuebles")
    Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);




    @PUT("api/Inmuebles/actualizar")
    Call<Inmueble> actualizarInmueble(
            @Header("Authorization") String token,
            @Body Inmueble inmueble
    );

    @Multipart
    @POST("api/Inmuebles/cargar")
    Call<Inmueble> cargarInmueble(
            @Header("Authorization") String token,
            @Part MultipartBody.Part ImagenFile,
            @Part("Inmueble") RequestBody inmueble

    );






}
