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

    private AgregarInmuebleViewModel viewModel;
    private FragmentAgregarInmuebleBinding binding;


    private ActivityResultLauncher<Intent> galeriaLauncher;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);

        galeriaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    viewModel.recibirFoto(result);
                }
        );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.btnAgregarFoto.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galeriaLauncher.launch(intent);
        });


        binding.btnAgregarInmueble.setOnClickListener(v -> {

            binding.btnAgregarInmueble.setEnabled(false);

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



        viewModel.getMUri().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                binding.ivFotoInmueble.setImageURI(uri);
            }
        });


        viewModel.getOperacionExitosaLiveData().observe(getViewLifecycleOwner(), exitoso -> {
            if (exitoso != null && exitoso) {
                Toast.makeText(getContext(), "Inmueble agregado con éxito", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).popBackStack();
            } else if (exitoso != null) {
                Toast.makeText(getContext(), "No se pudo guardar el inmueble", Toast.LENGTH_SHORT).show();
                binding.btnAgregarInmueble.setEnabled(true);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
