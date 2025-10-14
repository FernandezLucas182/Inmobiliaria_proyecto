package com.example.inmob;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;public class InmobApp extends Application {

    private static InmobApp instance;
    private static final String PREFS_NAME = "auth_prefs";
    private static final String TOKEN_KEY = "auth_token";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static InmobApp getInstance() {
        return instance;
    }

    public void guardarToken(String token) {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public String obtenerToken() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getString(TOKEN_KEY, null); // Devuelve null si no hay token
    }

    public void cerrarSesion() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }
}
