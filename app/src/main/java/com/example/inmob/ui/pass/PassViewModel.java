package com.example.inmob.ui.pass;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmob.InmobApp;
import com.example.inmob.request.ApiClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<String> mExito = new MutableLiveData<>();
    private final Context context;

    public PassViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<String> getError() {
        return mError;
    }

    public LiveData<String> getExito() {
        return mExito;
    }


    public void cambiarPassword(String passActual, String passNueva) {
        String token = InmobApp.obtenerToken();
        if (token == null || token.isEmpty()) {
            mError.postValue("Token no válido. Inicie sesión de nuevo.");
            return;
        }


        Call<Void> call = ApiClient.getMyApiClient().cambiarClave(
                "Bearer " + token,
                passActual,
                passNueva
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    mExito.postValue("Contraseña cambiada con éxito.");
                } else {
                    Log.d("PassVM", "Error al cambiar contraseña. Código: " + response.code());
                    mError.postValue("No se pudo cambiar la contraseña. Verifique los datos.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("PassVM", "Fallo en la llamada: " + t.getMessage());
                mError.postValue("Error de conexión al cambiar la contraseña.");
            }
        });
    }

}
