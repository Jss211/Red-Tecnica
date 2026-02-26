package com.example.redtecnica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
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

        // Ajustar imagen según quién sea
        if ("gasfitero".equals(imagenKey)) {
            binding.ivServiceDetail.setImageResource(R.drawable.gasfitero);
            binding.tvDetailSpec.setText("Gasfitería Profesional");
        } else {
            binding.ivServiceDetail.setImageResource(R.drawable.electricista);
            binding.tvDetailSpec.setText("Electricidad Certificada");
        }

        // 3. BOTÓN DE ACCIÓN
        binding.btnConfirmarSolicitud.setOnClickListener(v -> {
            Toast.makeText(this, "¡Iniciando contacto seguro!", Toast.LENGTH_SHORT).show();
            // Ir al historial para ver el servicio en curso
            startActivity(new Intent(this, HistorialActivity.class));
            finish();
        });

        // Botón atrás
        binding.toolbarDetail.setNavigationOnClickListener(v -> finish());
    }
}