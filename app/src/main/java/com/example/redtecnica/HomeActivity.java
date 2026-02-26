package com.example.redtecnica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityHomeBinding; // Import del Binding generado

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding; // 1. Declaración del Binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2. Inicialización de ViewBinding (Esto quita los errores de 'binding')
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // --- NAVEGACIÓN DE LA BARRA INFERIOR ---

        // Botón "Inicio" (Ya estamos aquí, solo refrescamos o mostramos mensaje)
        binding.navInicio.setOnClickListener(v -> {
            Toast.makeText(this, "Ya estás en el Inicio", Toast.LENGTH_SHORT).show();
        });

        // Botón "Actividad" (Historial de solicitudes)
        binding.navActividad.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistorialActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0); // Navegación fluida sin parpadeo
        });

        // Botón "Perfil" (Ajustes de usuario)
        binding.navPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfilActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        // --- ACCIONES DE TÉCNICOS (Dinámicas) ---

        // Botón del Electricista (Carlos Mendoza)
        binding.btnContact1.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleServicioActivity.class);
            // Pasamos los datos para que la siguiente pantalla sepa a quién mostrar
            intent.putExtra("TECH_NAME", "Carlos Mendoza");
            intent.putExtra("TECH_IMAGE", "electricista");
            startActivity(intent);
        });

        // Botón del Gasfitero (Roberto Sánchez)
        binding.btnContact2.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleServicioActivity.class);
            intent.putExtra("TECH_NAME", "Roberto Sánchez");
            intent.putExtra("TECH_IMAGE", "gasfitero");
            startActivity(intent);
        });

        // --- EXTRAS DE SIMULACIÓN PROFESIONAL ---

        // Icono de Ajustes (Engranaje)
        binding.btnSettings.setOnClickListener(v -> {
            Toast.makeText(this, "Ajustes: Configuración disponible en la v2.0", Toast.LENGTH_SHORT).show();
        });

        // Card de búsqueda
        binding.searchCard.setOnClickListener(v -> {
            Toast.makeText(this, "Buscando técnicos verificados cerca de Surco...", Toast.LENGTH_SHORT).show();
        });
    }
}