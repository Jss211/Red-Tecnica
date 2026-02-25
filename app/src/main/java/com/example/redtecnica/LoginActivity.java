package com.example.redtecnica;

import android.content.Intent; // Importante para cambiar de pantalla
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Botón Iniciar Sesión (Simulado)
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String pass = binding.etPassword.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Salto a la HomeActivity que ya tienes en tu proyecto
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish(); // Cerramos el login para no poder volver atrás con el botón físico
            }
        });

        // 2. Botón Crear Cuenta (Nos lleva al registro)
        binding.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}