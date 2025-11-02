package com.example.inmob.ui.inmuebles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmob.databinding.FragmentAgregarInmuebleBinding;

public class AgregarInmuebleFragment extends Fragment {

    // Cambiamos el nombre de "mViewModel" a "viewModel" por convención.
    private AgregarInmuebleViewModel viewModel;
    private FragmentAgregarInmuebleBinding binding;

    // Cambiamos "arl" a un nombre más descriptivo.
    private ActivityResultLauncher<Intent> galeriaLauncher;

    // El método newInstance() no es necesario si no pasas argumentos al fragment.

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. INICIALIZACIÓN SEGURA EN onCreate
        // Se inicializa el ViewModel una sola vez durante la creación del fragment.
        viewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);

        // Se registra el "launcher" para la galería. Esto debe hacerse en onCreate o onAttach.
        // Esto corrige las advertencias de "deprecated API" porque usamos la forma moderna.
        galeriaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // El ViewModel es el único responsable de procesar el resultado.
                    viewModel.recibirFoto(result);
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // La inicialización del binding es correcta, pero la hacemos más estándar.
        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 2. CONFIGURACIÓN DE LISTENERS Y OBSERVERS EN onViewCreated
        // Este es el lugar correcto para interactuar con las vistas (binding).

        // Listener para el botón de la foto
        binding.btnAgregarFoto.setOnClickListener(v -> {
            // Se crea el Intent justo antes de usarlo. Es más seguro.
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galeriaLauncher.launch(intent);
        });

        // Listener para el botón de guardar
        binding.btnAgregarInmueble.setOnClickListener(v -> {
            // Deshabilitamos el botón para prevenir múltiples clics mientras se suben los datos.
            binding.btnAgregarInmueble.setEnabled(false);

            // 3. LLAMADA CORRECTA AL VIEWMODEL
            // Se pasan los 6 argumentos String en el orden que el ViewModel espera.
            // El booleano 'disponible' ya no se envía.
            viewModel.agregarInmueble(
                    binding.etDireccion.getText().toString(),
                    binding.etPrecio.getText().toString(),
                    binding.etUso.getText().toString(),
                    binding.etTipo.getText().toString(),
                    binding.etAmbientes.getText().toString(),
                    binding.etSuperficie.getText().toString(),
                    binding.cbDisponible.isChecked()
            );
        });


        // 4. OBSERVADORES DE LIVEDATA ACTUALIZADOS
        // Observador para la imagen seleccionada. Usamos el método correcto del ViewModel.
        viewModel.getMUri().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                binding.ivFotoInmueble.setImageURI(uri);
            }
        });

        // Observador para el resultado de la operación. ¡Este es el más importante!
        viewModel.getOperacionExitosaLiveData().observe(getViewLifecycleOwner(), exitoso -> {
            if (exitoso != null && exitoso) {
                // Si la API guardó el inmueble, mostramos un mensaje y volvemos a la lista.
                Toast.makeText(getContext(), "Inmueble agregado con éxito", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).popBackStack();
            } else if (exitoso != null) {
                // Si la API devolvió un error (ej: "error en el servidor"),
                // reactivamos el botón para que el usuario pueda corregir y reintentar.
                Toast.makeText(getContext(), "No se pudo guardar el inmueble", Toast.LENGTH_SHORT).show();
                binding.btnAgregarInmueble.setEnabled(true);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Es una buena práctica limpiar la referencia al binding para evitar fugas de memoria.
        binding = null;
    }
}
