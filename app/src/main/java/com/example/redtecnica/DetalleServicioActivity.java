package com.example.redtecnica;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityDetalleServicioBinding;

public class DetalleServicioActivity extends AppCompatActivity {

    private ActivityDetalleServicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetalleServicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. RECUPERAR DATOS DEL TÉCNICO
        String nombre = getIntent().getStringExtra("TECH_NAME");
        String imagenKey = getIntent().getStringExtra("TECH_IMAGE");

        // 2. CONFIGURAR VISTA
        binding.tvDetailName.setText(nombre != null ? nombre : "Especialista");

        // Ajustar imagen y texto de especialidad según quién sea
        if ("gasfitero".equals(imagenKey)) {
            binding.ivServiceDetail.setImageResource(R.drawable.gasfitero);
            binding.tvDetailSpec.setText("Gasfitería Profesional");
        } else if ("cerrajeria".equals(imagenKey)) {
            binding.ivServiceDetail.setImageResource(R.drawable.cerrajeria);
            binding.tvDetailSpec.setText("Cerrajería Profesional");
        } else if ("pintura".equals(imagenKey)) {
            binding.ivServiceDetail.setImageResource(R.drawable.pintura);
            binding.tvDetailSpec.setText("Instalador de Drywall");
        } else {
            binding.ivServiceDetail.setImageResource(R.drawable.electricista);
            binding.tvDetailSpec.setText("Electricidad Certificada");
        }

        // 3. BOTÓN DE ACCIÓN (ABRIR CHAT INTERNO)
        binding.btnConfirmarSolicitud.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            // Pasamos los datos al chat para que sepa con quién hablamos
            intent.putExtra("TECH_NAME", nombre);
            intent.putExtra("TECH_IMAGE", imagenKey);
            startActivity(intent);
        });

        // Botón atrás
        binding.toolbarDetail.setNavigationOnClickListener(v -> finish());
    }
}