package com.example.redtecnica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.redtecnica.databinding.ActivityHistorialBinding;

public class HistorialActivity extends AppCompatActivity {

    private ActivityHistorialBinding binding;

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
        EdgeToEdge.enable(this);

        // Registrar el receiver para escuchar cambios de tema
        registerReceiver(themeChangeReceiver, new IntentFilter("THEME_CHANGED"), Context.RECEIVER_NOT_EXPORTED);

        // Inicialización de ViewBinding para conectar con el XML
        binding = ActivityHistorialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuración de márgenes para evitar que la UI se oculte bajo las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Lógica de Navegación de la Barra Inferior
        binding.navInicio.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        binding.navActividad.setOnClickListener(v -> {
            Toast.makeText(this, "Ya estás en tu Actividad", Toast.LENGTH_SHORT).show();
        });

        binding.navPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfilActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        binding.navAjustes.setOnClickListener(v -> {
            Intent intent = new Intent(this, AjustesActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        // 2. Función para abrir el Mapa (ID: btnVerMapa)
        binding.btnVerMapa.setOnClickListener(v -> {
            // Coordenadas simuladas en Santiago de Surco
            String coordenadas = "-12.1323,-76.9984";
            String nombreTecnico = "Carlos Mendoza (En camino)";

            // Creación del Intent para abrir Google Maps
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + coordenadas + "(" + Uri.encode(nombreTecnico) + ")");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(this, "No tienes Google Maps instalado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(themeChangeReceiver);
    }
}