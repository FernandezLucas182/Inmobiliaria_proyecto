package com.example.inmob.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.inmob.R;
import com.example.inmob.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class InicioFragment extends Fragment implements OnMapReadyCallback {

    private FragmentInicioBinding binding;
    private GoogleMap googleMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.googleMap = map;


        LatLng sanLuis = new LatLng(-33.29501, -66.33563);


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sanLuis)      // Centra el mapa
                .zoom(14)             // Nivel de zoom
                .bearing(0)           // Orientación (0 = Norte)
                .tilt(30)             // Inclinación
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        googleMap.addMarker(new MarkerOptions()
                .position(sanLuis)
                .title("San Luis"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
