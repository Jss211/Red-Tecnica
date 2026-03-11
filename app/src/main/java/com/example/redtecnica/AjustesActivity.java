package com.example.redtecnica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.redtecnica.databinding.ActivityAjustesBinding;

public class AjustesActivity extends AppCompatActivity {

    private ActivityAjustesBinding binding;
    private SharedPreferences prefs;
    private boolean isDarkMode = false;

    private BroadcastReceiver themeChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            recreate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // APLICAR TEMA ANTES DE SUPER.ONCREATE
        prefs = getSharedPreferences("FixZonePrefs", MODE_PRIVATE);
        isDarkMode = prefs.getBoolean("dark_mode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        binding = ActivityAjustesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Registrar el receiver para escuchar cambios de tema
        registerReceiver(themeChangeReceiver, new IntentFilter("THEME_CHANGED"), Context.RECEIVER_NOT_EXPORTED);

        configurarUI();
        configurarNavegacion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(themeChangeReceiver);
    }

    private void configurarUI() {
        // Actualizar estado del toggle
        binding.switchDarkMode.setChecked(isDarkMode);
        actualizarTextoModo();

        // Toggle de modo oscuro/claro
        binding.cardDarkMode.setOnClickListener(v -> toggleDarkMode());
        binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                toggleDarkMode();
            }
        });

        // Reportar técnico
        binding.cardReportarTecnico.setOnClickListener(v -> {
            startActivity(new Intent(this, ReportarTecnicoActivity.class));
        });

        // Términos y condiciones
        binding.cardTerminos.setOnClickListener(v -> {
            startActivity(new Intent(this, TerminosActivity.class));
        });

        // Política de privacidad
        binding.cardPrivacidad.setOnClickListener(v -> {
            startActivity(new Intent(this, PrivacidadActivity.class));
        });

        // Botón atrás
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        binding.switchDarkMode.setChecked(isDarkMode);

        // Guardar preferencia
        prefs.edit().putBoolean("dark_mode", isDarkMode).apply();

        // Aplicar tema inmediatamente
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        actualizarTextoModo();

        // Enviar broadcast para que todas las actividades se recreen
        Intent intent = new Intent("THEME_CHANGED");
        sendBroadcast(intent);

        // Recrear esta actividad para aplicar el cambio inmediatamente
        recreate();
    }

    private void actualizarTextoModo() {
        if (isDarkMode) {
            binding.tvDarkModeStatus.setText("Activado");
        } else {
            binding.tvDarkModeStatus.setText("Desactivado");
        }
    }

    private void configurarNavegacion() {
        binding.navInicio.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        binding.navActividad.setOnClickListener(v -> {
            startActivity(new Intent(this, HistorialActivity.class));
            finish();
        });

        binding.navPerfil.setOnClickListener(v -> {
            startActivity(new Intent(this, PerfilActivity.class));
            finish();
        });

        binding.navAjustes.setOnClickListener(v -> {
            Toast.makeText(this, "Ya estás en Ajustes", Toast.LENGTH_SHORT).show();
        });
    }
}
