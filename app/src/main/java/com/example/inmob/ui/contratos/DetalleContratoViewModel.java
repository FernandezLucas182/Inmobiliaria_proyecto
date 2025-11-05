package com.example.inmob.ui.contratos;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmob.InmobApp;
import com.example.inmob.model.Contrato;
import com.example.inmob.model.Inmueble;
import com.example.inmob.request.ApiClient;
import com.example.inmob.request.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleContratoViewModel extends AndroidViewModel {
    MutableLiveData <Contrato> contrato = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData <Contrato> getContrato(){
        return contrato;
    }

    public void obtenerContratoPorInmueble(int idInmueble){
        String token = InmobApp.obtenerToken();
        ApiService api = ApiClient.getMyApiClient();
        Call<Contrato> llamada = api.obtenerContratoPorInmueble(token, idInmueble);
        Log.d("TOKEN", "Token leído: " + token);

        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()){
                    contrato.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No hay contratos disponibles: "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ContratoViewModel", "Error en la llamada a la API", t);
            }

        });
    }
}