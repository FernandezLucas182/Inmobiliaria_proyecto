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
        Inmueble i = (Inmueble) bundle.getSerializable("inmueble");
        if (i != null) {
            this.inmueble.setValue(i);
        }
    }


    public void cambiarEstadoDisponibilidad() {
        Inmueble inmuebleOriginal = inmueble.getValue();
        if (inmuebleOriginal == null) {
            error.postValue("Error: No se pudo obtener el inmueble para actualizar.");
            return;
        }


        Inmueble inmuebleActualizado = inmuebleOriginal;

        inmuebleActualizado.setDisponible(!inmuebleActualizado.getDisponible());


        String token = InmobApp.obtenerToken();
        ApiService api = ApiClient.getMyApiClient();


        Call<Inmueble> llamada = api.actualizarInmueble(token, inmuebleActualizado);


        llamada.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful() && response.body() != null) {
                    inmueble.postValue(response.body());
                    Toast.makeText(getApplication(), "Estado actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("API_ERROR", "Respuesta no exitosa: " + response.code() + " - " + response.message());


                    inmuebleActualizado.setDisponible(!inmuebleActualizado.getDisponible());
                    inmueble.postValue(inmuebleActualizado);

                    error.postValue("Error al actualizar: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.e("API_FAILURE", "Fallo en la llamada a la API", t);

                inmuebleActualizado.setDisponible(!inmuebleActualizado.getDisponible());
                inmueble.postValue(inmuebleActualizado); // Notificar a la UI

                error.postValue("Fallo de conexión: " + t.getMessage());
            }
        });
    }

}
