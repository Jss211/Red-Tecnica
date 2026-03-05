package com.example.redtecnica;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityHistorialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            if (insets != null) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        });

        // --- NAVEGACIÓN DE LA BARRA INFERIOR ---
        binding.navInicio.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Evita abrir múltiples pantallas apiladas
            startActivity(intent);
            overridePendingTransition(0, 0); // Transición sin parpadeo
            finish(); // Cierra el historial al salir
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

        // --- ABRIR GOOGLE MAPS ---
        // Asegúrate de que en activity_historial.xml el TextView "Ver mapa >" tenga el id: android:id="@+id/btnVerMapa"
        binding.btnVerMapa.setOnClickListener(v -> {
            // Coordenadas simuladas del técnico en camino por Surco
            String coordenadas = "-12.1323,-76.9984";
            String nombreTecnico = "Carlos Mendoza (En camino)";

            // Uri especial que abre la app de Google Maps con un marcador rojo
            android.net.Uri gmmIntentUri = android.net.Uri.parse("geo:0,0?q=" + coordenadas + "(" + android.net.Uri.encode(nombreTecnico) + ")");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(this, "No tienes Google Maps instalado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}