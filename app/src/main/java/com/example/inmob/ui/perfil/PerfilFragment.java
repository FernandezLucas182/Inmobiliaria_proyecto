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
import androidx.lifecycle.ViewModelProvider;
import com.example.inmob.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d("FLUJO_APP", "PerfilFragment: onCreateView INICIADO (inflando vista).");


        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Log.d("FLUJO_APP", "PerfilFragment: Vista inflada. Configurando observadores...");


        perfilViewModel.getPropietario().observe(getViewLifecycleOwner(), propietario -> {
            if (propietario != null) {

                Log.d("FLUJO_APP", "PerfilFragment: Observer de Propietario activado. Actualizando UI.");

                binding.etCodigo.setText(String.valueOf(propietario.getId()));
                binding.etDni.setText(propietario.getDni());
                binding.etApellido.setText(propietario.getApellido());
                binding.etNombre.setText(propietario.getNombre());
                binding.etEmail.setText(propietario.getEmail());
                binding.etPassword.setText("");
                binding.etTelefono.setText(propietario.getTelefono());
            }
        });

        perfilViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {

                Log.e("FLUJO_APP", "PerfilFragment: Observer de Error activado: " + error);

                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        binding.btnEditar.setOnClickListener(v -> {

        });

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("FLUJO_APP", "PerfilFragment: onViewCreated INICIADO. Llamando a cargarPropietario().");

        perfilViewModel.cargarPropietario();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
