package com.example.inmob.ui.inmuebles;

import static com.example.inmob.request.ApiClient.BASE_URL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.inmob.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmob.request.ApiClient;

import java.text.NumberFormat;
import java.util.Locale;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel vm;
    private FragmentDetalleInmuebleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);


        vm.getInmueble().observe(getViewLifecycleOwner(), inmueble -> {
            if (inmueble != null) {
                binding.tvDireccionDetalle.setText(inmueble.getDireccion());
                binding.tvUsoDetalle.setText("Uso: " + inmueble.getUso());
                binding.tvTipoDetalle.setText("Tipo: " + inmueble.getTipo());
                binding.tvAmbientesDetalle.setText("Ambientes: " + inmueble.getAmbientes());
                binding.tvSuperficieDetalle.setText("Superficie: " + inmueble.getSuperficie() + " m²");

                NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));
                binding.tvPrecioDetalle.setText(formatoMoneda.format(inmueble.getValor()));

                Glide.with(getContext())
                        .load(BASE_URL + inmueble.getImagen())
                        .into(binding.ivFotoInmueble);

                binding.cbDisponible.setChecked(inmueble.getDisponible());
            }
        });


        binding.cbDisponible.setOnClickListener(v -> {
            vm.cambiarEstadoDisponibilidad();
        });


        vm.getError().observe(getViewLifecycleOwner(), mensaje -> {
            if (mensaje != null && !mensaje.isEmpty()) {
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
            }
        });




        if (getArguments() != null) {
            vm.cargarInmueble(getArguments());
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
