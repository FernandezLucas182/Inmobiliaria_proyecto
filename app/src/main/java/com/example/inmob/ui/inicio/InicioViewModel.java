// InicioViewModel.java
package com.example.inmob.ui.inicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends ViewModel {

    // 1. LiveData para exponer la configuración del mapa.
    private final MutableLiveData<MapaConfig> mapaConfig;

    public InicioViewModel() {
        mapaConfig = new MutableLiveData<>();
        // 2. Llama a un método para crear la configuración inicial.
        crearConfiguracionMapa();
    }

    public LiveData<MapaConfig> getMapaConfig() {
        return mapaConfig;
    }

    private void crearConfiguracionMapa() {
        // Toda la lógica de negocio para definir la ubicación y la cámara
        LatLng sanLuis = new LatLng(-33.29501, -66.33563);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sanLuis)      // Centra el mapa
                .zoom(14)             // Nivel de zoom
                .bearing(0)           // Orientación (0 = Norte)
                .tilt(30)             // Inclinación
                .build();

        MarkerOptions markerOptions = new MarkerOptions()
                .position(sanLuis)
                .title("Inmobiliaria");

        // 3. Crea un objeto contenedor con toda la información.
        MapaConfig config = new MapaConfig(cameraPosition, markerOptions);
        mapaConfig.setValue(config);
    }


    // 4. Clase interna para agrupar los datos del mapa.
    public static class MapaConfig {
        private final CameraPosition cameraPosition;
        private final MarkerOptions markerOptions;

        public MapaConfig(CameraPosition cameraPosition, MarkerOptions markerOptions) {
            this.cameraPosition = cameraPosition;
            this.markerOptions = markerOptions;
        }

        public CameraPosition getCameraPosition() {
            return cameraPosition;
        }

        public MarkerOptions getMarkerOptions() {
            return markerOptions;
        }
    }
}
