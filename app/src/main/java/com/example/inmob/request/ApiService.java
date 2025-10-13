// El nombre del paquete ya coincide con el tuyo, lo cual es perfecto.
package com.example.inmob.request;

// Estas líneas importan las clases necesarias para que el código funcione.
// Retrofit sabe qué hacer gracias a ellas.
import com.example.inmob.model.Propietario; // Necesita saber qué es un Propietario.
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

// "public interface ApiService" en lugar de "public class" es crucial.
// Una interfaz solo define QUÉ se puede hacer, no CÓMO se hace.
public interface ApiService {

    // Este es el "contrato" para hacer el login.
    // Le dice a Retrofit: "Cuando llame al método 'login', tienes que hacer
    // una petición POST a 'api/Propietarios/login' con estos campos".
    @FormUrlEncoded
    @POST("api/Propietarios/login")
    Call<String> login(
            @Field("Usuario") String usuario,
            @Field("Clave") String clave
    );

    // MÁS ADELANTE, AÑADIRÁS AQUÍ LOS OTROS ENDPOINTS
    // Por ejemplo:
    // @GET("api/Propietarios")
    // Call<Propietario> obtenerPerfil(@Header("Authorization") String token);
}
