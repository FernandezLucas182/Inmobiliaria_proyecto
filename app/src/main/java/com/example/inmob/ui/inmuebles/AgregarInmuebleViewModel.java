package com.example.inmob.ui.inmuebles;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmob.InmobApp;
import com.example.inmob.model.Inmueble;
import com.example.inmob.request.ApiClient;
import com.example.inmob.request.ApiService;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Uri> mUri = new MutableLiveData<>();

    private MutableLiveData<Boolean> operacionExitosaLiveData = new MutableLiveData<>();




    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Uri> getMUri(){
        return mUri;
    }

    public LiveData<Boolean> getOperacionExitosaLiveData() {
        return operacionExitosaLiveData;}

    public void recibirFoto(ActivityResult result){
        if(result.getResultCode() == Activity.RESULT_OK){
            Uri uri = result.getData().getData();
            Log.d(
                    "uri",
                    uri.toString()
            );
            mUri.postValue(uri);
        }
    }

    public void agregarInmueble(String direccion, String precio, String uso, String tipo, String superficie, String ambientes, boolean disponible) {

        try {
            int superficieInt = Integer.parseInt(superficie);

            int ambientesInt = Integer.parseInt(ambientes);
            double precioDouble = Double.parseDouble(precio);

            if (direccion.isEmpty() || precio.isEmpty() || uso.isEmpty() || tipo.isEmpty() || superficie.isEmpty() || ambientes.isEmpty()) {

                Toast.makeText(getApplication(), "Error en los campos", Toast.LENGTH_SHORT).show();

            }
            if (mUri.getValue() == null) {
                Toast.makeText(getApplication(), "Debe seleccionar una imagen", Toast.LENGTH_SHORT).show();

            }
            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setValor(precioDouble);
            inmueble.setUso(uso);
            inmueble.setTipo(tipo);
            inmueble.setSuperficie(superficieInt);
            inmueble.setAmbientes(ambientesInt);
            inmueble.setDisponible(disponible);

            byte[] imagen = transformarImagen();
            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);

            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);


            ApiService api = ApiClient.getMyApiClient();
            String token = InmobApp.obtenerToken();

            Call<Inmueble> llamada = api.cargarInmueble(token, imagenPart, inmuebleBody);
            llamada.enqueue(new Callback<Inmueble>() {

                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Inmueble agregado", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplication(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                        Log.d("Error en el servidor", response.errorBody().toString());

                    }

                }

                public void onFailure(Call<Inmueble> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error en el (Failure) servidor", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (NumberFormatException ex){
            Toast.makeText(getApplication(), "Error en los campos", Toast.LENGTH_SHORT).show();
        }
    }
    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (
                FileNotFoundException er) {
            Toast.makeText(getApplication(), "No ha seleccinado una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }


}