// El nombre del paquete ya coincide con tu estructura, ¡perfecto!
package com.example.inmob.request;

// Estas son las clases de Retrofit que se necesitan para construir el cliente.
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Esta es la clase que se encarga de CONSTRUIR y CONFIGURAR Retrofit.
public class ApiClient {

    // 1. La URL base de la API. Todas las peticiones partirán de aquí.
    private static final String BASE_URL = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    // 2. Una variable para guardar la instancia de ApiService una vez creada.
    private static ApiService apiService;

    // 3. El método PÚBLICO que el resto de tu app (los ViewModels) usará para hablar con la API.
    public static ApiService getMyApiClient() {

        // Este 'if' es muy importante. Se asegura de que solo se cree UN objeto Retrofit
        // para toda la aplicación (esto es más eficiente).
        if (apiService == null) {

            // Aquí se construye Retrofit:
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Le dice cuál es la URL base.
                    .addConverterFactory(GsonConverterFactory.create()) // Le dice que use GSON para convertir JSON a objetos Java.
                    .build(); // Crea el objeto Retrofit.

            // Y aquí le pedimos a Retrofit que implemente nuestra interfaz ApiService.
            // Retrofit lee tu ApiService.java y crea todo el código necesario por detrás.
            apiService = retrofit.create(ApiService.class);
        }

        // Devuelve la instancia lista para usar.
        return apiService;
    }
}
