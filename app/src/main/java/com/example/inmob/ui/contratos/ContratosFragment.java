package com.example.inmob.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;


import com.example.inmob.databinding.FragmentContratosBinding;

public class ContratosFragment extends Fragment {


    private FragmentContratosBinding binding;

    private ContratosViewModel cvm;
    private ContratosAdapter ca;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContratosBinding.inflate(inflater, container, false);


        cvm = new ViewModelProvider(this).get(ContratosViewModel.class);

        binding.rvContratos.setLayoutManager(new GridLayoutManager(getContext(), 2));

        cvm.getInmueblesAlquilados().observe(getViewLifecycleOwner(), inmuebles -> {
            if (inmuebles != null && !inmuebles.isEmpty()) {
                ca = new ContratosAdapter(inmuebles, getContext());
                binding.rvContratos.setAdapter(ca);
            } else {

                binding.rvContratos.setAdapter(null);
            }
        });
        cvm.cargarInmueblesAlquilados();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
