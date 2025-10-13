package com.example.inmob.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmob.MainActivity;
import com.example.inmob.databinding.ActivityLoginBinding;

// CORRECCIÓN 1: La clase es una AppCompatActivity, usaremos sus métodos.
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    // CORRECCIÓN 2: Se usa el método onCreate(), que es el punto de entrada de una Activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // CORRECCIÓN 3: Se infla la vista de la Activity usando ViewBinding.
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // Se establece la vista inflada como el contenido de la Activity.
        setContentView(binding.getRoot());

        // CORRECCIÓN 4: Se inicializa el ViewModel. El 'owner' es la propia Activity (this).
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Se configuran los observadores para reaccionar a los cambios.
        configurarObservadores();

        // Se configura el listener del botón.
        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etUser.toString();
            String password = binding.etPass.getText().toString();

            // Validaciones básicas antes de llamar al ViewModel.
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llama al método del ViewModel para que inicie sesión.
            loginViewModel.iniciarSesion(email, password);
        });
    }

    // Método privado para organizar el código de los observadores.
    private void configurarObservadores() {

        // CORRECCIÓN: Usar 'getToken()' en lugar de 'getUsuario()'
        // El LiveData devuelve un String (el token), no un objeto Propietario.
        loginViewModel.getToken().observe(this, token -> {
            // Verificamos si el token que llega no es nulo ni está vacío.
            if (token != null && !token.isEmpty()) {
                // Si hay token, el login fue exitoso. Navegamos a la pantalla principal.
                Intent intent = new Intent(this, MainActivity.class);
                // Estas flags evitan que el usuario pueda volver a la pantalla de Login con el botón "atrás".
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        // CORRECCIÓN: Usar 'getError()' en lugar de 'getMensajeError()'
        loginViewModel.getError().observe(this, mensaje -> {
            if (mensaje != null && !mensaje.isEmpty()) {
                // Si llega un mensaje de error, lo mostramos en un Toast.
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
            }
        });
    }

    // CORRECCIÓN 7: Las Activities no usan onDestroyView().
    // El 'binding' se gestiona automáticamente con el ciclo de vida de la Activity.
}
