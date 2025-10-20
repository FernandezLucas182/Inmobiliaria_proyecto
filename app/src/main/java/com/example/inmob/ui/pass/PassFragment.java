package com.example.inmob.ui.pass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmob.databinding.FragmentPassBinding;

public class PassFragment extends Fragment {

    private PassViewModel passViewModel;
    private FragmentPassBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPassBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        passViewModel = new ViewModelProvider(this).get(PassViewModel.class);

        setupObservers();

        binding.btnGuardarPassword.setOnClickListener(v -> {
            String passActual = binding.etPassActual.getText().toString();
            String passNueva = binding.etPassNueva.getText().toString();
            String passRepetir = binding.etPassRepetir.getText().toString();

            // Validaciones básicas
            if (passActual.isEmpty() || passNueva.isEmpty() || passRepetir.isEmpty()) {
                Toast.makeText(getContext(), "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!passNueva.equals(passRepetir)) {
                binding.tilPassRepetir.setError("Las contraseñas no coinciden.");
                return;
            } else {
                binding.tilPassRepetir.setError(null); // Limpiar error si coinciden
            }

            // Si las validaciones pasan, llamamos al ViewModel
            passViewModel.cambiarPassword(passActual, passNueva);
        });
    }

    private void setupObservers() {
        passViewModel.getExito().observe(getViewLifecycleOwner(), exito -> {
            Toast.makeText(getContext(), exito, Toast.LENGTH_LONG).show();
            // Navegar de vuelta al perfil después del éxito
            Navigation.findNavController(requireView()).popBackStack();
        });

        passViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
