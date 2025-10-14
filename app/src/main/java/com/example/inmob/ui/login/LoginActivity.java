package com.example.inmob.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmob.MainActivity;
import com.example.inmob.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // --- BLOQUE CORREGIDO ---
        // Observador que reacciona cuando el ViewModel obtiene el token
        loginViewModel.getToken().observe(this, token -> {
            if (token != null && !token.isEmpty()) {
                // Log para confirmar que el flujo es correcto
                Log.d("FLUJO_APP", "Login Exitoso. Token recibido. Navegando a MainActivity...");

                // 1. Creamos la intención para abrir MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                // 2. Flags para limpiar el historial y que el usuario no pueda "volver atrás" a esta pantalla de login
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // 3. Iniciamos la MainActivity
                startActivity(intent);

                // 4. Cerramos esta LoginActivity (aunque el flag CLEAR_TASK ya ayuda a esto, es una buena práctica)
                finish();
            }
        });

        // Observador que reacciona a los mensajes de error del ViewModel
        loginViewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        // Listener para el botón de ingresar
        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etUser.getText().toString();
            String password = binding.etPass.getText().toString();
            loginViewModel.iniciarSesion(email, password);
        });
    }
}
