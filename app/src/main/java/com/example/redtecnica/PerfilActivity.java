package com.example.redtecnica;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.redtecnica.databinding.ActivityPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;

public class PerfilActivity extends AppCompatActivity {

    private ActivityPerfilBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // Mostrar datos del usuario actual
        if (mAuth.getCurrentUser() != null) {
            binding.tvEmail.setText(mAuth.getCurrentUser().getEmail());
            binding.tvName.setText("Usuario Activo");
        }

        // Botón Cerrar Sesión
        binding.btnLogout.setOnClickListener(v -> {

            mAuth.signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}