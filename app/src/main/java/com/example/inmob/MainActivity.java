package com.example.inmob;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.inmob.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (InmobApp.obtenerToken() == null) {
            Log.d("MainActivity", "No hay token, cerrando MainActivity para ir a Login.");

            finish();
            return;
        }

        Log.d("MainActivity", "Token encontrado, inflando la UI.");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavigation();
    }

    private void setupNavigation() {
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_perfil, R.id.nav_inmuebles,
                R.id.nav_inquilinos, R.id.nav_contratos)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        setSupportActionBar(binding.appBarMain.toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        binding.navView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            mostrarDialogoLogout();
            return true;
        });
    }

    private void mostrarDialogoLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que deseas salir?")
                .setPositiveButton("Sí", (dialog, which) -> InmobApp.cerrarSesion())
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
