package com.example.inmob;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inmob.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirige inmediatamente a LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        // Cierra esta actividad para que no se pueda volver a ella
        finish();
    }
}
