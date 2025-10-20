package com.example.inmob.ui.perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.inmob.R;
import com.example.inmob.databinding.FragmentPerfilBinding;
import com.example.inmob.model.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        setupObservers();
        setupButtonClickListeners();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        perfilViewModel.cargarPropietario();
    }

    private void setupObservers() {

        perfilViewModel.getPropietario().observe(getViewLifecycleOwner(), propietario -> {
            if (propietario != null) {
                Log.d("PerfilFragment", "Datos del propietario recibidos. Actualizando UI.");


                binding.etCodigo.setText(String.valueOf(propietario.getId()));

                binding.etDni.setText(propietario.getDni());
                binding.etApellido.setText(propietario.getApellido());
                binding.etNombre.setText(propietario.getNombre());
                binding.etEmail.setText(propietario.getEmail());
                binding.etPassword.setText(""); // La contraseña nunca se muestra
                binding.etTelefono.setText(propietario.getTelefono());
            }
        });

        perfilViewModel.getNavegar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean navegarCambiarClave) {
                if (Boolean.TRUE.equals(navegarCambiarClave)) {
                    perfilViewModel.resetNavegar();
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.action_perfilFragment_to_PassFragment);
                }
            }
        });

        // Observador para el modo de edición
        perfilViewModel.getEditMode().observe(getViewLifecycleOwner(), isEditing -> {
            setEditMode(isEditing);
        });

        // Observador para mensajes de error
        perfilViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        // Observador para mensajes de éxito
        perfilViewModel.getExito().observe(getViewLifecycleOwner(), exito -> {
            if (exito != null && !exito.isEmpty()) {
                Toast.makeText(getContext(), exito, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupButtonClickListeners() {
        // El botón "EDITAR" ahora solo le dice al ViewModel que cambie de modo.
        binding.btnEditar.setOnClickListener(v -> {
            perfilViewModel.cambiarModoEdicion();
        });
        //btn cambiar clave
        binding.btnCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfilViewModel.onNavegarClicked();
            }
        });
        // El botón "GUARDAR" recoge los datos y los envía al ViewModel.
        binding.btnGuardar.setOnClickListener(v -> {


            // Obtenemos el objeto Propietario actual que ya tiene el ViewModel.

            Propietario propietarioActual = perfilViewModel.getPropietario().getValue();


            if (propietarioActual == null) {
                Toast.makeText(getContext(), "Error: No se pueden obtener los datos actuales del perfil.", Toast.LENGTH_SHORT).show();
                return;
            }


            propietarioActual.setDni(binding.etDni.getText().toString());
            propietarioActual.setApellido(binding.etApellido.getText().toString());
            propietarioActual.setNombre(binding.etNombre.getText().toString());
            propietarioActual.setTelefono(binding.etTelefono.getText().toString());




            Log.d("PerfilFragment", "Enviando actualización a la API...");
            perfilViewModel.actualizarPerfil(propietarioActual);

        });
    }


    private void setEditMode(boolean enabled) {
        binding.etDni.setEnabled(enabled);
        binding.etApellido.setEnabled(enabled);
        binding.etNombre.setEnabled(enabled);
        binding.etEmail.setEnabled(enabled);
        binding.etTelefono.setEnabled(enabled);

        // Campos que nunca se editan
        binding.etCodigo.setEnabled(false);
        binding.etPassword.setEnabled(false);
        binding.etEmail.setEnabled(false);


        // Muestra y oculta los botones correctos según el modo
        binding.btnGuardar.setVisibility(enabled ? View.VISIBLE : View.GONE);
        binding.btnEditar.setVisibility(enabled ? View.GONE : View.VISIBLE);
        binding.btnCambiarPassword.setVisibility(enabled ? View.GONE : View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
