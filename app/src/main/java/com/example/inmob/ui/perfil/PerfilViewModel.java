package com.example.inmob.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmob.model.Propietario;
import com.example.inmob.request.ApiClient;
import com.example.inmob.request.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private final Context context;
    private final MutableLiveData<Propietario> mPropietario;
    private final MutableLiveData<String> mError;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.mPropietario = new MutableLiveData<>();
        this.mError = new MutableLiveData<>();
    }

    public LiveData<Propietario> getPropietario() {
        return mPropietario;
    }

    public LiveData<String> getError() {
        return mError;
    }

    // Este es el método que el Fragment llamará
    public void cargarPropietario() {

        final String PREFS_NAME = "auth_prefs";
        final String TOKEN_KEY = "auth_token";
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = sp.getString(TOKEN_KEY, null);

        if (token == null) {
            mError.setValue("No se pudo recuperar el token de autenticación. Por favor, inicie sesión de nuevo.");
            return;
        }


        ApiService api = ApiClient.getMyApiClient();
        Call<Propietario> call = api.obtenerPerfil(token);


        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {

                    mPropietario.postValue(response.body());
                } else {

                    Log.d("PerfilError", "Respuesta no exitosa: " + response.code());
                    mError.postValue("No se pudieron cargar los datos del perfil.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {

                Log.e("PerfilFailure", "Fallo en la llamada a la API de perfil", t);
                mError.postValue("Error de conexión al cargar el perfil. Intente más tarde.");
            }
        });
    }
}
