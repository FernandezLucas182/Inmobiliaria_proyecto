package com.example.inmob;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.inmob.databinding.ActivityMainBinding;
import com.example.inmob.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // --- Observadores: La Vista solo REACCIONA a las órdenes del ViewModel ---

        // Observador para la orden de "navegar al login"
        vm.getNavegarALogin().observe(this, aVoid -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // NUEVO: Observador para la orden de "cerrar el menú"
        vm.getCerrarDrawer().observe(this, cerrar -> {
            binding.drawerLayout.closeDrawers();
        });

        // --- Configuración: La Vista solo CONFIGURA los componentes ---
        setupNavigation();
    }

    private void setupNavigation() {
        setSupportActionBar(binding.appBarMain.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        navController = navHostFragment.getNavController();

        // El listener es un "pasamanos" puro.
        binding.navView.setNavigationItemSelectedListener(item -> {
            // Pasa el evento al ViewModel. El VM dirá si lo manejó.
            boolean handledByViewModel = vm.procesarOpcionMenu(item.getItemId());

            // Si el VM no lo manejó, se lo pasamos al Navigation Component.
            // La negación '!' se ejecuta sin un 'if'.
            boolean handledByNavUI = NavigationUI.onNavDestinationSelected(item, navController);

            // Informa al ViewModel si la navegación de UI se realizó con éxito.
            // Esto se hace para que el VM pueda decidir si cerrar el drawer.
            vm.navegacionExitosa();

            // Devuelve true si CUALQUIERA de los dos manejó el evento.
            return handledByViewModel || handledByNavUI;
        });

        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // La configuración de la flecha "atrás" y el botón de hamburguesa. CERO if.
        return NavigationUI.navigateUp(navController, binding.drawerLayout) || super.onSupportNavigateUp();
    }
}
