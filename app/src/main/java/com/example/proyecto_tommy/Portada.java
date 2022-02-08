package com.example.proyecto_tommy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_tommy.databinding.ActivityPortadaBinding;
import com.google.android.material.snackbar.Snackbar;

public class Portada extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Intent intent;
    private ActivityPortadaBinding binding;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPortadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPortada.toolbar);
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.addPc, R.id.mostrarPcs, R.id.editarUsuario, R.id.cerrarSesion, R.id.cerrarApp)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_portada);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburguesa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.mostrarPcs:
                                intent = new Intent(Portada.this, ListaOrdenadores.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.addPc:
                                intent = new Intent(Portada.this, Cpu.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.editarUsuario:
                                intent = new Intent(Portada.this, Perfil.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.cerrarSesion:
                                intent = new Intent(Portada.this, Login.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.cerrarApp:
                                Toast.makeText(Portada.this, "Se ha cerrado la aplicación satisfactoriamente", Toast.LENGTH_SHORT).show();
                                Portada.this.finishAffinity();
                                return true;
                        }

                        drawer.closeDrawers();

                        return true;
                    }
                });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_portada);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}