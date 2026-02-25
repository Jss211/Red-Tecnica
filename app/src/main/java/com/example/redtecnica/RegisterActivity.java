package com.example.redtecnica;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Uso de ViewBinding para conectar con el XML
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Simulación del Botón Registrar
        binding.btnDoRegister.setOnClickListener(v -> {
            String name = binding.etRegisterName.getText().toString();
            String email = binding.etRegisterEmail.getText().toString();
            String pass = binding.etRegisterPassword.getText().toString();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "¡Cuenta creada con éxito! (Simulado)", Toast.LENGTH_SHORT).show();
                finish(); // Cerramos y volvemos al Login
            }
        });

        // 2. Simulación de "¿Ya tienes cuenta? Iniciar Sesión"
        binding.tvGoToLogin.setOnClickListener(v -> {
            finish(); // Cerramos esta pantalla para volver a la anterior (Login)
        });
    }
}