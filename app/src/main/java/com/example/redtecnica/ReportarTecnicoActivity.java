package com.example.redtecnica;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityReportarTecnicoBinding;
import java.util.ArrayList;
import java.util.List;

public class ReportarTecnicoActivity extends AppCompatActivity {

    private ActivityReportarTecnicoBinding binding;
    private String tecnicoSeleccionado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportarTecnicoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarUI();
        cargarTecnicos();
    }

    private void configurarUI() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnEnviarReporte.setOnClickListener(v -> enviarReporte());
    }

    private void cargarTecnicos() {
        List<String> tecnicos = new ArrayList<>();
        tecnicos.add("Selecciona un técnico");
        tecnicos.add("Carlos Mendoza - Electricista");
        tecnicos.add("Roberto Sánchez - Gasfitero");
        tecnicos.add("Juan Pérez - Gasfitero");
        tecnicos.add("Luis Alva - Electricista");
        tecnicos.add("Mario Vargas - Cerrajero");
        tecnicos.add("Pedro Suárez - Cerrajero");
        tecnicos.add("Miguel Torres - Instalador Drywall");
        tecnicos.add("Julio Iglesias - Instalador Drywall");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tecnicos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTecnicos.setAdapter(adapter);

        binding.spinnerTecnicos.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    tecnicoSeleccionado = tecnicos.get(position);
                } else {
                    tecnicoSeleccionado = "";
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                tecnicoSeleccionado = "";
            }
        });
    }

    private void enviarReporte() {
        String motivo = binding.etMotivo.getText().toString().trim();

        if (tecnicoSeleccionado.isEmpty() || tecnicoSeleccionado.equals("Selecciona un técnico")) {
            Toast.makeText(this, "Por favor selecciona un técnico", Toast.LENGTH_SHORT).show();
            return;
        }

        if (motivo.isEmpty()) {
            Toast.makeText(this, "Por favor describe el motivo del reporte", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí enviarías el reporte al servidor
        Toast.makeText(this, "Reporte enviado correctamente. Gracias por tu feedback.", Toast.LENGTH_LONG).show();

        // Limpiar formulario
        binding.spinnerTecnicos.setSelection(0);
        binding.etMotivo.setText("");

        // Volver atrás después de 1 segundo
        binding.getRoot().postDelayed(() -> finish(), 1000);
    }
}