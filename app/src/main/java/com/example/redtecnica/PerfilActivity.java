package com.example.redtecnica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

    private BroadcastReceiver themeChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            recreate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("FixZonePrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);

        if (isDarkMode) {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        registerReceiver(themeChangeReceiver, new IntentFilter("THEME_CHANGED"), Context.RECEIVER_NOT_EXPORTED);

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

        binding.btnEditarPerfil.setOnClickListener(v -> startActivity(new Intent(this, EditarPerfilActivity.class)));
        binding.btnDirecciones.setOnClickListener(v -> Toast.makeText(this, "Las direcciones se configuran en Editar Perfil", Toast.LENGTH_SHORT).show());
        binding.btnSoporte.setOnClickListener(v -> Toast.makeText(this, "Contactando a soporte...", Toast.LENGTH_SHORT).show());

        binding.btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

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

        binding.navPerfil.setOnClickListener(v -> Toast.makeText(this, "Ya estás en tu Perfil", Toast.LENGTH_SHORT).show());
        binding.navAjustes.setOnClickListener(v -> {
            Intent intent = new Intent(this, AjustesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("MisDatosPerfil", MODE_PRIVATE);
        String nombreGuardado = prefs.getString("nombre", "");
        String uriImagen = prefs.getString("imagen_perfil", "");

        if (!nombreGuardado.trim().isEmpty()) {
            binding.tvName.setText(nombreGuardado);
        } else {
            binding.tvName.setText("Usuario FixZone");
        }

        if (!uriImagen.isEmpty()) {
            try {
                binding.imgProfile.setImageURI(android.net.Uri.parse(uriImagen));
            } catch (Exception e) {
                // Previene el crasheo si se borró la foto
                binding.imgProfile.setImageResource(R.drawable.avatar);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(themeChangeReceiver);
    }
}