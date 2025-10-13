// En el paquete 'ui.login'
package com.example.inmob.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmob.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> token;
    private MutableLiveData<String> error;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.token = new MutableLiveData<>();
        this.error = new MutableLiveData<>();
    }

    public LiveData<String> getToken() { return token; }
    public LiveData<String> getError() { return error; }

    public void iniciarSesion(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            error.postValue("Correo y contraseña son requeridos.");
            return;
        }

        Call<String> call = ApiClient.getMyApiClient().login(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String authToken = "Bearer " + response.body();
                    guardarToken(authToken);
                    token.postValue(authToken);
                } else {
                    error.postValue("Usuario o contraseña incorrectos.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                error.postValue("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void guardarToken(String token) {
        SharedPreferences sp = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
