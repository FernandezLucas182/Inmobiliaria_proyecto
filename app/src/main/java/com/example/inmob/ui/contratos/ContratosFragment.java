package com.example.inmob.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

// 1. CAMBIO: Importar la clase de Binding correcta
import com.example.inmob.databinding.FragmentContratosBinding;

public class ContratosFragment extends Fragment {

    // 2. CAMBIO: Declarar la variable del tipo correcto
    private FragmentContratosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContratosViewModel contratosViewModel =
                new ViewModelProvider(this).get(ContratosViewModel.class);

        // 3. CAMBIO: Usar la clase correcta para inflar el layout
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 4. CAMBIO: Acceder a las vistas a través del 'binding' correcto
        // Asegúrate de que tu 'fragment_contratos.xml' tiene un TextView con el id 'textContratos'
        final TextView textView = binding.textContratos;
        contratosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
