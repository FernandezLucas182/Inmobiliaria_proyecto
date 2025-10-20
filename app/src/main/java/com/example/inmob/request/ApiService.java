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


    // ================== MÉTODO AÑADIDO ==================
    // Este endpoint es necesario para que la función de "Guardar Perfil" funcione.
    // Envía los datos del propietario en el cuerpo de la petición.
    @PUT("api/propietarios") // Corregido a minúsculas y plural
    Call<Propietario> actualizarPerfil(
            @Header("Authorization") String token,
            @Body Propietario propietario
    );
    // ======================================================

}
