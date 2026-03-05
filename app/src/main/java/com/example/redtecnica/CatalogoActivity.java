package com.example.redtecnica;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityCatalogoBinding;

public class CatalogoActivity extends AppCompatActivity {

    private ActivityCatalogoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String categoria = getIntent().getStringExtra("CATEGORIA");
        String imagenKey = getIntent().getStringExtra("IMAGEN");

        binding.tvTituloCatalogo.setText("Catálogo: " + categoria);

        // Nombres por defecto según categoría
        String tech1Name = "Carlos Mendoza";
        String tech2Name = "Roberto Sánchez";
        int imageRes = R.drawable.electricista;

        if ("Gasfitería".equals(categoria)) {
            tech1Name = "Juan Pérez";
            tech2Name = "Roberto Sánchez";
            imageRes = R.drawable.gasfitero;
        } else if ("Electricidad".equals(categoria)) {
            tech1Name = "Carlos Mendoza";
            tech2Name = "Luis Alva";
            imageRes = R.drawable.electricista;
        } else if ("Cerrajería".equals(categoria)) {
            tech1Name = "Mario Vargas";
            tech2Name = "Pedro Suárez";
            imageRes = R.drawable.cerrajeria;
        } else if ("Instalador Drywall".equals(categoria)) {
            tech1Name = "Miguel Torres";
            tech2Name = "Julio Iglesias";
            imageRes = R.drawable.pintura; // Mantienes tu imagen pintura.png
        }

        // Asignar datos de la Interfaz
        binding.tvName1.setText(tech1Name);
        binding.ivTech1.setImageResource(imageRes);
        binding.tvName2.setText(tech2Name);
        binding.ivTech2.setImageResource(imageRes);

        // Eventos de botones para ir al Detalle con WhatsApp
        final String finalTech1Name = tech1Name;
        final String finalImagenKey = imagenKey;
        binding.btnContact1.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleServicioActivity.class);
            intent.putExtra("TECH_NAME", finalTech1Name);
            intent.putExtra("TECH_IMAGE", finalImagenKey);
            startActivity(intent);
        });

        final String finalTech2Name = tech2Name;
        binding.btnContact2.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleServicioActivity.class);
            intent.putExtra("TECH_NAME", finalTech2Name);
            intent.putExtra("TECH_IMAGE", finalImagenKey);
            startActivity(intent);
        });

        // Botón atrás
        binding.btnBack.setOnClickListener(v -> finish());
    }
}