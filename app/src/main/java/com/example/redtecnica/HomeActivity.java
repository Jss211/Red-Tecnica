package com.example.redtecnica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- BUSCADOR INTELIGENTE ---
        binding.etBuscador.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH ||
                    actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {

                String busqueda = binding.etBuscador.getText().toString().trim();
                if (!busqueda.isEmpty()) {
                    // Reutilizamos el catálogo, enviando lo que el usuario escribió
                    abrirCatalogo(busqueda, "electricista");
                    binding.etBuscador.setText(""); // Limpiar la barra
                }
                return true;
            }
            return false;
        });

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- NAVEGACIÓN DE LA BARRA INFERIOR ---
        binding.navInicio.setOnClickListener(v -> {
            Toast.makeText(this, "Ya estás en el Inicio", Toast.LENGTH_SHORT).show();
        });

        binding.navActividad.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistorialActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        binding.navPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfilActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        // --- CATEGORÍAS (LLEVAN AL CATÁLOGO) ---
        binding.btnGasfitero.setOnClickListener(v -> abrirCatalogo("Gasfitería", "gasfitero"));
        binding.btnElectricidad.setOnClickListener(v -> abrirCatalogo("Electricidad", "electricista"));
        binding.btnCerrajeria.setOnClickListener(v -> abrirCatalogo("Cerrajería", "cerrajeria"));
        binding.btnPintura.setOnClickListener(v -> abrirCatalogo("Instalador Drywall", "pintura"));

        // --- TÉCNICOS RECOMENDADOS EN INICIO ---
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

        // --- EXTRAS ---
        binding.btnSettings.setOnClickListener(v -> {
            Toast.makeText(this, "Ajustes: Configuración disponible en la v2.0", Toast.LENGTH_SHORT).show();
        });

        binding.searchCard.setOnClickListener(v -> {
            Toast.makeText(this, "Buscando técnicos verificados...", Toast.LENGTH_SHORT).show();


        });
    }
    private void abrirCatalogo(String categoria, String imagen) {
        Intent intent = new Intent(this, CatalogoActivity.class);
        intent.putExtra("CATEGORIA", categoria);
        intent.putExtra("IMAGEN", imagen);
        startActivity(intent);
    }
}