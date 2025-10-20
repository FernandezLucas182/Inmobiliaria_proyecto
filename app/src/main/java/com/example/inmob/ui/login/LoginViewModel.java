package com.example.inmob.ui.login;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmob.InmobApp;
import com.example.inmob.request.ApiClient;
import com.example.inmob.request.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mError = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loginExitosoEvent = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getError() {
        return mError;
    }

    public LiveData<Boolean> getLoginExitosoEvent() {
        return loginExitosoEvent;
    }

    public void iniciarSesion(String email, String password) {
        if (email == null || email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mError.setValue("Por favor, ingrese un correo electrónico válido.");
            return;
        }
        if (password == null || password.isEmpty()) {
            mError.setValue("La contraseña no puede estar vacía.");
            return;
        }

        ApiService apiService = ApiClient.getMyApiClient();
        Call<String> call = apiService.login(email, password);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LoginVM", "Login exitoso. Token recibido.");

                    InmobApp.guardarToken(response.body());

                    loginExitosoEvent.postValue(true);
                } else {
                    Log.d("LoginVM", "Respuesta no exitosa: " + response.code());
                    mError.setValue("Usuario o contraseña incorrectos.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("LoginVM", "Fallo en la llamada a la API", t);
                mError.setValue("Error de conexión. Verifique su red.");
            }
        });
    }
}
