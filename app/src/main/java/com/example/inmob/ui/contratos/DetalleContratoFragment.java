package com.example.inmob.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmob.R;
import com.example.inmob.databinding.FragmentDetalleContratoBinding;
import com.example.inmob.model.Contrato;
import com.example.inmob.model.Inquilino;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DetalleContratoFragment extends Fragment {
    private FragmentDetalleContratoBinding binding;
    private DetalleContratoViewModel mViewModel;
    private Contrato contratoActual;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        Bundle args = getArguments();
        if (args != null && args.containsKey("idInmueble")) {
            int idInmueble = args.getInt("idInmueble");
            mViewModel.obtenerContratoPorInmueble(idInmueble);
        }

        mViewModel.getContrato().observe(getViewLifecycleOwner(), contrato -> {
            if (contrato != null) {
                contratoActual = contrato;

                binding.tvFechaInicio.setText("Fecha de inicio: " + formatoLocal(contrato.getFechaInicio()));
                binding.tvFechaFin.setText("Fecha de finalización: " + formatoLocal( contrato.getFechaFinalizacion()));
                binding.tvMontoAlquiler.setText("Monto de alquiler: $" + contrato.getMontoAlquiler());
            }
        });

        // Ver Inquilino
        binding.btnVerInquilino.setOnClickListener(v -> {
            if (contratoActual != null && contratoActual.getInquilino() != null) {
                Inquilino inquilino = contratoActual.getInquilino();

                Bundle b = new Bundle();

                b.putInt("idInmueble", contratoActual.getIdInmueble());

                // Navegamos al detalle del inquilino
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.inquilinosFragment, b);
            }
        });

        //  Ver Pagos
        binding.btnVerPago.setOnClickListener(v -> {
            if (contratoActual != null) {
                Bundle b = new Bundle();
                b.putInt("idContrato", contratoActual.getIdContrato());

                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.pagosFragment, b);
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private String formatoLocal(String fechaIso) {
        try {
            LocalDate fecha = LocalDate.parse(fechaIso);
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "AR"));
            return fecha.format(formato);
        } catch (Exception e) {
            return fechaIso;
        }
    }


}