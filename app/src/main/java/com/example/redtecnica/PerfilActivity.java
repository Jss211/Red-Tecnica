package com.example.redtecnica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.redtecnica.databinding.ActivityPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {

    private ActivityPerfilBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            binding.tvEmail.setText(currentUser.getEmail());
        }

        // --- ABRIR PANTALLA DE EDITAR PERFIL ---
        binding.btnEditarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarPerfilActivity.class);
            startActivity(intent);
        });

        binding.btnDirecciones.setOnClickListener(v -> {
            Toast.makeText(this, "Las direcciones se configuran en Editar Perfil", Toast.LENGTH_SHORT).show();
        });

        binding.btnSoporte.setOnClickListener(v -> {
            Toast.makeText(this, "Contactando a soporte...", Toast.LENGTH_SHORT).show();
        });

        // --- BOTÓN CERRAR SESIÓN ---
        binding.btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // --- NAVEGACIÓN ---
        binding.navInicio.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        binding.navActividad.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistorialActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        binding.navPerfil.setOnClickListener(v -> {
            Toast.makeText(this, "Ya estás en tu Perfil", Toast.LENGTH_SHORT).show();
        });

        binding.navAjustes.setOnClickListener(v -> {
            Intent intent = new Intent(this, AjustesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    // Usamos onResume para que los datos se actualicen apenas regresamos de EditarPerfilActivity
    @Override
    protected void onResume() {
        super.onResume();
        // Cargar el nombre que el usuario guardó en EditarPerfilActivity
        SharedPreferences prefs = getSharedPreferences("MisDatosPerfil", MODE_PRIVATE);
        String nombreGuardado = prefs.getString("nombre", "");

        if (!nombreGuardado.isEmpty()) {
            binding.tvName.setText(nombreGuardado);
        } else {
            binding.tvName.setText("Usuario FixZone");
        }
    }
}