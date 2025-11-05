package com.example.inmob.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmob.R;
import com.example.inmob.databinding.FragmentPagosBinding;

public class PagosFragment extends Fragment {

    private FragmentPagosBinding binding;
    private PagosViewModel mViewModel;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);


        int idContrato = getArguments() != null ? getArguments().getInt("idContrato", -1) : -1;
        if (idContrato != -1) {
            mViewModel.obtenerPagosPorContrato(idContrato);
        }

        mViewModel.getPagos().observe(getViewLifecycleOwner(), pagos -> {
            if (pagos != null && !pagos.isEmpty()) {
                PagosAdapter adapter = new PagosAdapter(pagos, getContext());
                binding.rvPagos.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvPagos.setAdapter(adapter);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}