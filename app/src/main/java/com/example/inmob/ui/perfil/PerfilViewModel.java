package com.example.inmob.ui.perfil;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmob.InmobApp; // Importamos nuestro SessionManager
import com.example.inmob.model.Propietario;
import com.example.inmob.request.ApiClient;
import com.example.inmob.request.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    // LiveData para los datos del propietario
    private final MutableLiveData<Propietario> mPropietario;
    // LiveData para mensajes de error
    private final MutableLiveData<String> mError;
    // LiveData para el estado de los botones (edición/guardado)
    private final MutableLiveData<Boolean> mEditMode;
    // LiveData para notificar a la vista sobre el éxito de una operación
    private final MutableLiveData<String> mExito;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.mPropietario = new MutableLiveData<>();
        this.mError = new MutableLiveData<>();
        this.mEditMode = new MutableLiveData<>(false); // Por defecto, no está en modo edición
        this.mExito = new MutableLiveData<>();
    }

    public LiveData<Propietario> getPropietario() {
        return mPropietario;
    }

    public LiveData<String> getError() {
        return mError;
    }

    public LiveData<Boolean> getEditMode() {
        return mEditMode;
    }

    public LiveData<String> getExito() {
        return mExito;
    }

    // Carga los datos del perfil del propietario
    public void cargarPropietario() {
        // Obtenemos el token desde nuestro SessionManager centralizado
        String token = InmobApp.obtenerToken();

        if (token == null) {
            mError.setValue("Sesión expirada. Por favor, inicie sesión de nuevo.");
            return;
        }

        ApiService api = ApiClient.getMyApiClient();
        Call<Propietario> call = api.obtenerPerfil(token); // Le pasamos el token directamente

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mPropietario.postValue(response.body());
                    Log.d("PerfilVM", "Datos del perfil cargados correctamente.");
                } else {
                    Log.d("PerfilVM", "Error al cargar perfil: " + response.code());
                    mError.postValue("No se pudieron cargar los datos del perfil.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
                Log.e("PerfilVM", "Fallo en la llamada a la API de perfil", t);
                mError.postValue("Error de conexión al cargar el perfil.");
            }
        });
    }

    // Actualiza los datos del propietario en el servidor
    public void actualizarPerfil(Propietario propietario) {
        String token = InmobApp.obtenerToken();
        if (token == null) {
            mError.setValue("Sesión expirada. No se pueden guardar los cambios.");
            return;
        }

        ApiService api = ApiClient.getMyApiClient();
        // NOTA: Asegúrate que tu ApiService tenga el método "actualizarPerfil".
        // Lo añadiremos en el siguiente paso si no lo tienes.
        Call<Propietario> call = api.actualizarPerfil(token, propietario);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(@NonNull Call<Propietario> call, @NonNull Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si el servidor confirma la actualización, actualizamos los datos locales
                    mPropietario.postValue(response.body());
                    // Cambiamos al modo "ver"
                    mEditMode.postValue(false);
                    // Enviamos un mensaje de éxito a la vista
                    mExito.postValue("Perfil actualizado con éxito.");
                    Log.d("PerfilVM", "Perfil actualizado con éxito.");
                } else {
                    Log.d("PerfilVM", "Error al actualizar. Código: " + response.code());
                    mError.postValue("No se pudieron guardar los cambios.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Propietario> call, @NonNull Throwable t) {
                Log.e("PerfilVM", "Fallo en la llamada de actualización", t);
                mError.postValue("Error de conexión al guardar los cambios.");
            }
        });
    }

    // Cambia el estado del modo edición
    public void cambiarModoEdicion() {
        boolean isEditing = mEditMode.getValue() != null && mEditMode.getValue();
        mEditMode.setValue(!isEditing);
    }
}
