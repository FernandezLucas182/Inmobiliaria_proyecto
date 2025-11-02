package com.example.inmob.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmob.InmobApp;
import com.example.inmob.model.Inmueble;
import com.example.inmob.request.ApiClient;
import com.example.inmob.request.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private final MutableLiveData<Inmueble> inmueble = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getInmueble() {
        return inmueble;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void cargarInmueble(Bundle bundle) {
        // Recuperamos el inmueble que nos pasaron al crear el fragmento
        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
        if (i != null) {
            this.inmueble.setValue(i);
        }
    }

    /**
     * Este método se encarga de cambiar el estado de disponibilidad del inmueble.
     * Invierte el estado actual y llama a la API para persistir el cambio.
     * Maneja las respuestas de éxito y error.
     */
    public void cambiarEstadoDisponibilidad() {
        // 1. Obtenemos el inmueble actual desde el LiveData
        Inmueble inmuebleOriginal = inmueble.getValue();
        if (inmuebleOriginal == null) {
            error.postValue("Error: No se pudo obtener el inmueble para actualizar.");
            return;
        }

        // 2. Creamos una copia del objeto para no modificar el original hasta tener la confirmación.
        Inmueble inmuebleActualizado = inmuebleOriginal; // En Java, esto es una referencia, ¡así que es el mismo objeto!

        // Invertimos el estado en nuestro objeto local. Esto es lo que enviaremos a la API.
        inmuebleActualizado.setDisponible(!inmuebleActualizado.getDisponible());

        // 3. Obtenemos el token y preparamos la llamada a la API
        String token = InmobApp.obtenerToken();
        ApiService api = ApiClient.getMyApiClient();

        // Nos aseguramos que la llamada se hace con los datos actualizados.
        Call<Inmueble> llamada = api.actualizarInmueble(token, inmuebleActualizado);

        // 4. Ejecutamos la llamada de forma asíncrona
        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Éxito: El servidor confirma el cambio. Actualizamos el LiveData con la respuesta del servidor.
                    // Esto asegura que la UI refleje el estado final y confirmado.
                    inmueble.postValue(response.body());
                    Toast.makeText(getApplication(), "Estado actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Fallo del servidor: Revertimos el cambio en la UI y mostramos un error.
                    Log.d("API_ERROR", "Respuesta no exitosa: " + response.code() + " - " + response.message());

                    // Revertimos el cambio que hicimos localmente antes de la llamada.
                    inmuebleActualizado.setDisponible(!inmuebleActualizado.getDisponible());
                    inmueble.postValue(inmuebleActualizado); // Notificar a la UI para que se redibuje al estado original

                    error.postValue("Error al actualizar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                // Fallo de conexión o de Retrofit: Revertimos el cambio y mostramos un error.
                Log.e("API_FAILURE", "Fallo en la llamada a la API", t);

                // Revertimos el cambio que hicimos localmente antes de la llamada.
                inmuebleActualizado.setDisponible(!inmuebleActualizado.getDisponible());
                inmueble.postValue(inmuebleActualizado); // Notificar a la UI

                error.postValue("Fallo de conexión: " + t.getMessage());
            }
        });
    }

}
