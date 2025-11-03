package com.example.inmob;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.inmob.ui.login.LoginActivity;

public class InmobApp extends Application {

    private static InmobApp instance;
    private static final String PREFS_NAME = "auth_prefs";
    private static final String TOKEN_KEY = "auth_token";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.d("InmobApp", "Application instance creada y guardada.");
    }


    public static Context getContext() {
        if (instance == null) {
            Log.e("InmobApp", "La instancia de la aplicación es nula. Asegúrate de que está declarada en el Manifest.");
            return null;
        }
        return instance.getApplicationContext();
    }

    public static void guardarToken(String token) {
        SharedPreferences sp = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String bearerToken = "Bearer " + token;
        sp.edit().putString(TOKEN_KEY, bearerToken).apply();
        Log.d("InmobApp", "Token guardado: " + bearerToken);
    }

    public static String obtenerToken() {
        SharedPreferences sp = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = sp.getString(TOKEN_KEY, null);
        Log.d("InmobApp", "Token obtenido desde SharedPreferences: " + token);
        return token;
    }

    public static void cerrarSesion() {
        SharedPreferences sp = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(TOKEN_KEY).apply();
        Log.d("InmobApp", "Token eliminado. Sesión cerrada.");

        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getContext().startActivity(intent);
    }
}
