package com.example.inmob;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.inmob.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel vm;
    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FLUJO_APP", "MainActivity: onCreate - INICIADO.");

        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);


        vm.getSetupNavigationEvent().observe(this, aVoid -> {
            Log.d("FLUJO_APP", "Evento recibido: SetupNavigation. Configurando UI...");

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            setupNavigation();
        });


        vm.getRedirectToLoginEvent().observe(this, intent -> {
            Log.d("FLUJO_APP", "Evento recibido: RedirectToLogin. Navegando a Login...");
            startActivity(intent);
            finish();
        });


        vm.iniciarVerificacion();
    }

    private void setupNavigation() {
        // Este método ahora se llama después de setContentView, es más seguro.
        try {
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_inicio, R.id.nav_perfil, R.id.nav_inmuebles,
                    R.id.nav_inquilinos, R.id.nav_contratos)
                    .setOpenableLayout(binding.drawerLayout)
                    .build();


            NavController navController = Navigation.findNavController(binding.getRoot().findViewById(R.id.nav_host_fragment_content_main));

            setSupportActionBar(binding.appBarMain.toolbar);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);

            binding.navView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
                mostrarDialogoLogout();
                return true;
            });

            Log.d("FLUJO_APP", "setupNavigation() completado con éxito.");

        } catch (Exception e) {
            Log.e("FLUJO_APP", "CRASH DENTRO de setupNavigation()", e);
            Toast.makeText(this, "Error de Navegación: " + e.getMessage(), Toast.LENGTH_LONG).show();

            vm.logout();
        }
    }

    private void mostrarDialogoLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que deseas salir?")
                .setPositiveButton("Sí", (dialog, which) -> vm.logout())
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
