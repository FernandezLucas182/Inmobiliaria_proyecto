package com.example.inmob.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

// Importamos la clase principal de la API
import com.example.inmob.request.ApiClient;
// IMPORTANTE: Importamos la interfaz ApiService que descubrimos gracias al error
import com.example.inmob.request.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private final Context context;
    private final MutableLiveData<String> mToken;
    private final MutableLiveData<String> mError;


    private ApiService apiService;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.mToken = new MutableLiveData<>();
        this.mError = new MutableLiveData<>();


        this.apiService = ApiClient.getMyApiClient();
    }

    public LiveData<String> getToken() { return mToken; }
    public LiveData<String> getError() { return mError; }

    public void iniciarSesion(String email, String password) {
        if (email == null || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mError.setValue("Por favor, ingrese un correo electrónico válido.");
            return;
        }
        if (password == null || password.isEmpty()) {
            mError.setValue("La contraseña no puede estar vacía.");
            return;
        }


        Call<String> call = apiService.login(email, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String tokenRespuesta = response.body();
                    String authToken = "Bearer " + tokenRespuesta;
                    guardarToken(authToken);
                    mToken.postValue(authToken);
                } else {
                    Log.d("LoginError", "Respuesta no exitosa: " + response.code());
                    mError.postValue("Usuario o contraseña incorrectos.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("LoginFailure", "Fallo en la llamada a la API", t);
                mError.postValue("Error de conexión con el servidor. Intente más tarde.");
            }
        });
    }

    private void guardarToken(String token) {
        final String PREFS_NAME = "auth_prefs";
        final String TOKEN_KEY = "auth_token";

        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }
}
