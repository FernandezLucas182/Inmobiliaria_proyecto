package com.example.inmob.ui.login;

import android.content.Intent;
import android.os.Bundle;
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

        // Observador para el evento de login exitoso
        loginViewModel.getLoginExitosoEvent().observe(this, exitoso -> {
            if (exitoso) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cierra LoginActivity para que no se pueda volver atrás.
            }
        });

        // Observador para los mensajes de error
        loginViewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        // Asegúrate que los IDs en tu activity_login.xml sean "etEmail" y "etPassword"
        // Si usas otros IDs como "etUser" o "etPass", cámbialos aquí.
        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etUser.getText().toString();
            String password = binding.etPass.getText().toString();
            loginViewModel.iniciarSesion(email, password);
        });
    }
}
