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
import com.example.inmob.model.Pago;
import com.example.inmob.request.ApiClient;
import com.example.inmob.request.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Pago>> pagosMutableLiveData = new MutableLiveData<>();

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getPagos() {
        return pagosMutableLiveData;
    }

    public void obtenerPagosPorContrato(int idContrato){
        String token = InmobApp.obtenerToken();
        ApiService api = ApiClient.getMyApiClient();
        Call<List<Pago>> llamada = api.obtenerPagosPorContrato(token, idContrato);
        Log.d("TOKEN", "Token leído: " + token);

        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()){
                    pagosMutableLiveData.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No hay Pagos disponibles: "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PagoViewModel", "Error en la llamada a la API", t);
            }

        });
    }
}