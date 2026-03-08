package com.example.redtecnica;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjustesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("FixZonePrefs", MODE_PRIVATE);
        isDarkMode = prefs.getBoolean("dark_mode", false);

        configurarUI();
        configurarNavegacion();
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

        prefs.edit().putBoolean("dark_mode", isDarkMode).apply();

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        actualizarTextoModo();
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
