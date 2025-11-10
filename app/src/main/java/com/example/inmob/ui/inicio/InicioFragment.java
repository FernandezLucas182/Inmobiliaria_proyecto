// InicioFragment.java
package com.example.inmob.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmob.R;
import com.example.inmob.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private InicioViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        // Inicia el ViewModel
        viewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observa el LiveData del ViewModel
        viewModel.getMapaConfig().observe(getViewLifecycleOwner(), mapaConfig -> {
            if (mapaConfig != null) {
                // Cuando la configuración está lista, inicializa el mapa.
                setupMap(mapaConfig);
            }
        });
    }

    private void setupMap(InicioViewModel.MapaConfig mapaConfig) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    // La lógica aplica la configuración que viene del ViewModel.
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(mapaConfig.getCameraPosition()));
                    googleMap.addMarker(mapaConfig.getMarkerOptions());
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
