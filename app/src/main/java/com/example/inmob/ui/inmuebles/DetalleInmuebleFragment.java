package com.example.inmob.ui.inmuebles;

import static com.example.inmob.request.ApiClient.BASE_URL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast; // <<== IMPORT NECESARIO PARA MOSTRAR MENSAJES

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

        // =========================================================================
        // TU CÓDIGO EXISTENTE (SE MANTIENE IGUAL, ESTÁ PERFECTO)
        // Este observador se asegura de que la UI se actualice cuando los datos cambian.
        // =========================================================================
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

                // Esta es la línea clave que actualiza el checkbox cuando el ViewModel notifica un cambio.
                binding.cbDisponible.setChecked(inmueble.getDisponible());
            }
        });
        // =========================================================================

        // ==========================================================
        // ==  NUEVO BLOQUE 1: REACCIONAR AL CLIC DEL USUARIO      ==
        // ==========================================================
        // Este código escucha los toques en el CheckBox y le dice al ViewModel que inicie la actualización.
        binding.cbDisponible.setOnClickListener(v -> {
            vm.cambiarEstadoDisponibilidad();
        });
        // ==========================================================

        // ==========================================================
        // ==  NUEVO BLOQUE 2: OBSERVAR Y MOSTRAR ERRORES          ==
        // ==========================================================
        // Esto te dará feedback visual si algo sale mal con la API.
        vm.getError().observe(getViewLifecycleOwner(), mensaje -> {
            if (mensaje != null && !mensaje.isEmpty()) {
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
            }
        });
        // ==========================================================


        // TU CÓDIGO PARA CARGAR DATOS INICIALES (SE MANTIENE IGUAL, ESTÁ PERFECTO)
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
