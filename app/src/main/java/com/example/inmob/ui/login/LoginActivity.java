package com.example.inmob.ui.login;

import android.content.Intent;
import android.os.Bundle;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmob.MainActivity;
import com.example.inmob.databinding.FragmentLoginBinding; // Importa la clase de binding

public class LoginActivity extends Fragment {

    // 1. Cambia el nombre de la variable del ViewModel (por convención)
    private LoginViewModel loginViewModel;

    // 2. Declara la variable para el ViewBinding
    private FragmentLoginBinding binding;

    // El método newInstance() no es necesario para este caso, se puede borrar.

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // 3. Infla la vista usando ViewBinding en lugar de R.layout
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 4. Inicializa el ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // 5. Configura el Listener del botón de Ingresar
        binding.btnIngresar.setOnClickListener(v -> {
            String email = binding.etUser.getText().toString();
            String password = binding.etPass.getText().toString();

            // Llama al método del ViewModel para que haga el trabajo
            loginViewModel.iniciarSesion(email, password);
        });

        // 6. Crea los observadores para reaccionar a los resultados del ViewModel

        // Observador para un login exitoso (cuando el token llega)
        loginViewModel.getToken().observe(getViewLifecycleOwner(), token -> {
            // Si el token no es nulo, el login fue exitoso.
            // Navegamos a la pantalla principal.
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Observador para un error en el login
        loginViewModel.getError().observe(getViewLifecycleOwner(), errorMessage -> {
            // Si llega un mensaje de error, lo mostramos en un Toast.
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
        });
    }

    // 7. Es buena práctica limpiar el binding cuando la vista se destruye
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
