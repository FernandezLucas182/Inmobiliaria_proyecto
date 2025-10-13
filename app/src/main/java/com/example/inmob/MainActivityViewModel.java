package com.example.inmob;

import android.app.Application;import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Void> navegarALogin;
    private MutableLiveData<Boolean> cerrarDrawer; // <-- NUEVO: Para controlar el menú

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.navegarALogin = new MutableLiveData<>();
        this.cerrarDrawer = new MutableLiveData<>(); // <-- NUEVO
    }

    // --- LiveDatas para que la Vista observe ---
    public LiveData<Void> getNavegarALogin() {
        return navegarALogin;
    }
    public LiveData<Boolean> getCerrarDrawer() { return cerrarDrawer; } // <-- NUEVO

    /**
     * Procesa el clic en un ítem del menú.
     * Retorna 'true' si el evento fue manejado por el ViewModel (logout),
     * para que la MainActivity sepa si debe dejar que NavigationUI actúe o no.
     */
    public boolean procesarOpcionMenu(int itemId) {
        if (itemId == R.id.nav_logout) {
            SharedPreferences sp = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
            sp.edit().remove("token").apply();
            navegarALogin.postValue(null);
            return true; // El ViewModel manejó este evento.
        }
        return false; // El ViewModel no manejó este evento, deja que otro lo haga.
    }

    // --- Nuevos métodos para controlar la UI ---

    /**
     * Le dice al ViewModel que la navegación (por NavigationUI) fue exitosa.
     */
    public void navegacionExitosa() {
        cerrarDrawer.postValue(true); // Emite la señal para cerrar el menú.
    }
}
