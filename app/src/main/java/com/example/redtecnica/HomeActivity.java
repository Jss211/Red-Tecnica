package com.example.redtecnica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    private BroadcastReceiver themeChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            recreate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // APLICAR TEMA ANTES DE SUPER.ONCREATE
        android.content.SharedPreferences prefs = getSharedPreferences("FixZonePrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);

        if (isDarkMode) {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);

        // Registrar el receiver para escuchar cambios de tema
        registerReceiver(themeChangeReceiver, new IntentFilter("THEME_CHANGED"), Context.RECEIVER_NOT_EXPORTED);

        // 1. INICIALIZAR EL BINDING PRIMERO (Esto era lo que faltaba arriba)
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 2. AHORA SÍ CONFIGURAR LOS ELEMENTOS
        configurarBuscador();
        configurarNavegacion();
        configurarCategorias();
        configurarContactos();

        // Botón de notificaciones
        binding.btnNotificaciones.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificacionesActivity.class));
        });

        // Otros botones
        binding.searchCard.setOnClickListener(v -> {
            Toast.makeText(this, "Buscando técnicos verificados...", Toast.LENGTH_SHORT).show();
        });
    }

    private void configurarBuscador() {
        binding.etBuscador.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH ||
                    actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {

                String busqueda = binding.etBuscador.getText().toString().trim();
                if (!busqueda.isEmpty()) {
                    abrirCatalogo(busqueda, "electricista");
                    binding.etBuscador.setText("");
                }
                return true;
            }
            return false;
        });
    }

    private void configurarNavegacion() {
        binding.navInicio.setOnClickListener(v -> {
            Toast.makeText(this, "Ya estás en el Inicio", Toast.LENGTH_SHORT).show();
        });

        binding.navActividad.setOnClickListener(v -> {
            startActivity(new Intent(this, HistorialActivity.class));
            overridePendingTransition(0, 0);
        });

        binding.navPerfil.setOnClickListener(v -> {
            startActivity(new Intent(this, PerfilActivity.class));
            overridePendingTransition(0, 0);
        });

        binding.navAjustes.setOnClickListener(v -> {
            startActivity(new Intent(this, AjustesActivity.class));
            overridePendingTransition(0, 0);
        });
    }

    private void configurarCategorias() {
        binding.btnGasfitero.setOnClickListener(v -> abrirCatalogo("Gasfitería", "gasfitero"));
        binding.btnElectricidad.setOnClickListener(v -> abrirCatalogo("Electricidad", "electricista"));
        binding.btnCerrajeria.setOnClickListener(v -> abrirCatalogo("Cerrajería", "cerrajeria"));
        binding.btnPintura.setOnClickListener(v -> abrirCatalogo("Instalador Drywall", "pintura"));
    }

    private void configurarContactos() {
        binding.btnContact1.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleServicioActivity.class);
            intent.putExtra("TECH_NAME", "Carlos Mendoza");
            intent.putExtra("TECH_IMAGE", "electricista");
            startActivity(intent);
        });

        binding.btnContact2.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleServicioActivity.class);
            intent.putExtra("TECH_NAME", "Roberto Sánchez");
            intent.putExtra("TECH_IMAGE", "gasfitero");
            startActivity(intent);
        });
    }

    private void abrirCatalogo(String categoria, String imagen) {
        Intent intent = new Intent(this, CatalogoActivity.class);
        intent.putExtra("CATEGORIA", categoria);
        intent.putExtra("IMAGEN", imagen);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(themeChangeReceiver);
    }
}
