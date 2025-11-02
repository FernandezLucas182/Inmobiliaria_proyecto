package com.example.inmob.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inmob.databinding.FragmentInmuebleBinding;
import java.util.ArrayList; // Importante para la lista vacía

public class InmueblesFragment extends Fragment {

    private FragmentInmuebleBinding binding;
    private InmueblesViewModel vm;
    private InmuebleAdapter adapter; // <<== 1. DECLARAMOS EL ADAPTER COMO VARIABLE GLOBAL DEL FRAGMENT

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar ViewModel
        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);

        // ==========================================================
        // ==  CORRECCIÓN 1: CONFIGURAR EL RECYCLERVIEW UNA SOLA VEZ ==
        // ==========================================================
        RecyclerView rv = binding.recyclerView;
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Creamos el adapter con una lista vacía. Se hace solo una vez.
        adapter = new InmuebleAdapter(new ArrayList<>(), getContext());
        rv.setAdapter(adapter);
        // ==========================================================


        // El observador ahora es mucho más simple y eficiente.
        // Solo le pasa la nueva lista al adapter ya existente.
        vm.getmInmueble().observe(getViewLifecycleOwner(), inmuebles -> {
            if (inmuebles != null) {
                adapter.setInmuebles(inmuebles); // El adapter se actualiza, no se crea de nuevo
            }
        });

        // La llamada a leerInmuebles() se elimina de aquí para moverla a onResume().
        // vm.leerInmuebles();

        return root;
    }

    // ==========================================================
    // ==   CORRECCIÓN 2: ACTUALIZAR DATOS AL VOLVER A LA VISTA  ==
    // ==========================================================
    @Override
    public void onResume() {
        super.onResume();
        // Este método se ejecuta cada vez que el fragmento se vuelve visible
        // para el usuario. Es el lugar ideal para refrescar los datos.
        vm.leerInmuebles();
    }
    // ==========================================================


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
