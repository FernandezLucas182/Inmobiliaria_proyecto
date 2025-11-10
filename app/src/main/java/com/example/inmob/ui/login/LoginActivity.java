package com.example.inmob.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmob.MainActivity;
import com.example.inmob.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    private static final int REQUEST_CALL = 1;
    private static final String PHONE_NUMBER = "2665250181";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // --- INICIO DE LA LÓGICA REFECTORIZADA ---

        // 1. Observador para el evento de login exitoso
        loginViewModel.getLoginExitosoEvent().observe(this, exitoso -> {
            if (exitoso) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cierra LoginActivity para que no se pueda volver atrás.
            }
        });

        // 2. Observador para los errores
        loginViewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        // 3. Observador para el evento de sacudida (shake)
        loginViewModel.getHacerLlamadaEvent().observe(this, hacerLlamada -> {
            if (hacerLlamada != null && hacerLlamada) {
                vibrarTelefono();
                realizarLlamada();
            }
        });

        // 4. Listener para el botón de ingresar
        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etUser.getText().toString();
            String password = binding.etPass.getText().toString();
            loginViewModel.iniciarSesion(email, password);
        });

        // --- FIN DE LA LÓGICA REFECTORIZADA ---
    }

    private void vibrarTelefono() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null && v.hasVibrator()) {
            // Vibra por 500 milisegundos
            v.vibrate(500);
        }
    }

    private void realizarLlamada() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + PHONE_NUMBER));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Si no tenemos permiso, lo solicitamos.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            // Si ya tenemos permiso, iniciamos la llamada.
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si el usuario otorga el permiso, intentamos la llamada de nuevo.
                realizarLlamada();
            } else {
                // Si el usuario niega el permiso, le informamos.
                Toast.makeText(this, "Permiso de llamada denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Ya no necesitas onResume, onPause, onSensorChanged ni onAccuracyChanged aquí.
    // La clase SensorLiveData se encarga de eso automáticamente.
}
