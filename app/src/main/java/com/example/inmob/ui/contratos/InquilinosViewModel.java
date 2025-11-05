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
import com.example.inmob.model.Inquilino;
import com.example.inmob.request.ApiClient;
import com.example.inmob.request.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> inquilinos = new MutableLiveData<>();


    public InquilinosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData <Inquilino> getInquilino(){
        return inquilinos;

    }

    public void cargarInquilino(int idInmueble){
        String token = InmobApp.obtenerToken();
        ApiService api = ApiClient.getMyApiClient();
        Call<Contrato> llamada = api.obtenerContratoPorInmueble(token, idInmueble);
        Log.d("TOKEN", "Token leído: " + token);

        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()){
                    inquilinos.postValue(response.body().getInquilino());
                } else {
                    Toast.makeText(getApplication(), "No hay inmuebles disponibles: "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("InmueblesViewModel", "Error en la llamada a la API", t);
            }

        });
    }
}