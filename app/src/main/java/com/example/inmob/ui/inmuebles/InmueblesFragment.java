package com.example.inmob.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inmob.R;
import com.example.inmob.databinding.FragmentInmuebleBinding;
import java.util.ArrayList;

public class InmueblesFragment extends Fragment {

    private FragmentInmuebleBinding binding;
    private InmueblesViewModel vm;
    private InmuebleAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);

        RecyclerView rv = binding.recyclerView;
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new InmuebleAdapter(new ArrayList<>(), getContext());
        rv.setAdapter(adapter);

        vm.getmInmueble().observe(getViewLifecycleOwner(), inmuebles -> {
            if (inmuebles != null) {
                adapter.setInmuebles(inmuebles);
            }
        });

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fabAgregarInmueble.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_nav_inmuebles_to_agregarInmuebleFragment);
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        vm.leerInmuebles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
