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
import retrofit2.http.PUT; // Asegúrate de importar PUT
import retrofit2.http.Part;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/propietarios/login") // Corregido a minúsculas y plural
    Call<String> login(
            @Field("Usuario") String email,
            @Field("Clave") String password
    );




//    public static void guardarToken(Context context, String token){
//        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("token", token);
//        editor.apply();
//    }
//
//    public static String leerToken(Context context) {
//        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
//        return sp.getString("token", null);
//    }

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

    @GET("api/Inmuebles")
    Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);


    // =================================================================
    //  NUEVO ENDPOINT PARA ACTUALIZAR EL ESTADO DE UN INMUEBLE
    // =================================================================
    /**
     * Actualiza la información de un inmueble existente.
     * Sigue la documentación oficial de la API.
     * Método: PUT
     * Ruta: /api/Inmuebles/actualizar
     */
    @PUT("api/Inmuebles/actualizar")
    Call<Inmueble> actualizarInmueble(
            @Header("Authorization") String token,
            @Body Inmueble inmueble // La API espera el objeto completo en el cuerpo
    );

    @Multipart
    @POST("api/Inmuebles/cargar") // Asegúrate que la ruta es correcta según tu API
    Call<Inmueble> cargarInmueble(
            @Header("Authorization") String token,
            @Part MultipartBody.Part ImagenFile,
            @Part("Inmueble") RequestBody inmueble

    );






}
