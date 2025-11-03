package com.example.inmob.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit; // <<== Importa esta clase para los timeouts

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";
    private static ApiService myApiInterface;

    public static ApiService getMyApiClient() {
        if (myApiInterface == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);


            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para conectar
                    .readTimeout(30, TimeUnit.SECONDS)    // Tiempo de espera para leer la respuesta
                    .writeTimeout(30, TimeUnit.SECONDS)   // Tiempo de espera para escribir la petición
                    .build();

            // CONSTRUCCIÓN DE RETROFIT
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create()) // Para respuestas de texto plano
                    .client(client) // Usamos el cliente OkHttp mejorado
                    .build();



            myApiInterface = retrofit.create(ApiService.class);
        }
        return myApiInterface;
    }
}
