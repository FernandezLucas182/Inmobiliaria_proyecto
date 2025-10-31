package com.example.inmob.ui.inmuebles;

import android.os.Bundle;
import android.util.Log; // ¡Importante para depurar!
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast; // Para dar feedback al usuario

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmob.databinding.FragmentInmuebleBinding;
import com.example.inmob.model.Inmueble;

import java.util.List;

public class InmueblesFragment extends Fragment {

    private FragmentInmuebleBinding binding;
    private InmueblesViewModel vm;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);

        binding = FragmentInmuebleBinding.inflate(inflater, container, false);


        vm.getmInmueble().observe(getViewLifecycleOwner(), inmuebles -> {
            InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getContext());
            GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
            RecyclerView rv = binding.recyclerView;

            rv.setAdapter(adapter);
            rv.setLayoutManager(glm);

        });


        vm.leerInmuebles();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
