package com.example.inmob.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmob.R;
import com.example.inmob.databinding.FragmentInquilinosBinding;
import com.example.inmob.model.Inquilino;

public class InquilinosFragment extends Fragment {
    private FragmentInquilinosBinding binding;



    private InquilinosViewModel vm;

    public static InquilinosFragment newInstance() {
        return new InquilinosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);


        Bundle bundle = getArguments();
        if (bundle != null) {
            int idInmueble = bundle.getInt("idInmueble", -1);
            if (idInmueble != -1) {
                vm.cargarInquilino(idInmueble);
            }
        } else {
            Toast.makeText(getContext(), "No se recibió el ID del inmueble", Toast.LENGTH_SHORT).show();
        }

        vm.getInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                if (inquilino != null) {
                    binding.tvNombreInqDet.setText(inquilino.getNombre());
                    binding.tvApellidoInqDet.setText(inquilino.getApellido());
                    binding.tvEmailInqDet.setText(inquilino.getEmail());
                    binding.tvTelefonoInqDet.setText(inquilino.getTelefono());
                }
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






