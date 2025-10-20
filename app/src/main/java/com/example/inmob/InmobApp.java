package com.example.inmob;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.inmob.ui.login.LoginActivity;

public class InmobApp extends Application {

    private static Context context;
    private static final String PREFS_NAME = "auth_prefs";
    private static final String TOKEN_KEY = "auth_token";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.d("InmobApp", "SessionManager inicializado.");
    }

    // Guarda el token recibido del login, añadiéndole el prefijo "Bearer ".
    public static void guardarToken(String token) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String bearerToken = "Bearer " + token;
        sp.edit().putString(TOKEN_KEY, bearerToken).apply();
        Log.d("InmobApp", "Token guardado exitosamente.");
    }

    // Obtiene el token completo ("Bearer ...") para usar en las llamadas a la API.
    public static String obtenerToken() {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getString(TOKEN_KEY, null);
    }

    // Elimina el token y redirige forzosamente a la pantalla de Login.
    public static void cerrarSesion() {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(TOKEN_KEY).apply();
        Log.d("InmobApp", "Token eliminado. Sesión cerrada.");

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
