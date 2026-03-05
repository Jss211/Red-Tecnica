package com.example.redtecnica;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityEditarPerfilBinding;

public class EditarPerfilActivity extends AppCompatActivity {

    private ActivityEditarPerfilBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditarPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Cargar datos guardados previamente
        SharedPreferences prefs = getSharedPreferences("MisDatosPerfil", MODE_PRIVATE);
        String nombreGuardado = prefs.getString("nombre", "");
        String telefonoGuardado = prefs.getString("telefono", "");
        String direccionGuardada = prefs.getString("direccion", "");

        binding.etNombre.setText(nombreGuardado);
        binding.etTelefono.setText(telefonoGuardado);
        binding.etDireccion.setText(direccionGuardada);

        // 2. Botón atrás
        binding.btnBack.setOnClickListener(v -> finish());

        // 3. Botón Guardar Cambios
        binding.btnGuardarPerfil.setOnClickListener(v -> {
            String nuevoNombre = binding.etNombre.getText().toString().trim();
            String nuevoTelefono = binding.etTelefono.getText().toString().trim();
            String nuevaDireccion = binding.etDireccion.getText().toString().trim();

            if (nuevoNombre.isEmpty() || nuevoTelefono.isEmpty()) {
                Toast.makeText(this, "Por favor, llena al menos tu nombre y teléfono", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar en la memoria del celular
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nombre", nuevoNombre);
            editor.putString("telefono", nuevoTelefono);
            editor.putString("direccion", nuevaDireccion);
            editor.apply();

            Toast.makeText(this, "¡Perfil actualizado correctamente!", Toast.LENGTH_SHORT).show();
            finish(); // Cierra esta pantalla y vuelve al Perfil
        });
    }
}