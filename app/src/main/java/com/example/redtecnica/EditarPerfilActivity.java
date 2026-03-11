package com.example.redtecnica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

public class EditarPerfilActivity extends AppCompatActivity {

    private ShapeableImageView ivEditProfilePic;
    private MaterialCardView btnCargarFoto;
    private EditText etNombre, etTelefono, etDireccion;
    private com.google.android.material.button.MaterialButton btnGuardarPerfil;
    private ImageView btnBack;

    private Uri imagenSeleccionadaUri = null;

    // Aquí le pedimos permiso permanente a Android
    private final ActivityResultLauncher<Intent> abrirGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imagenSeleccionadaUri = result.getData().getData();
                    try {
                        // ¡MAGIA! Guardamos el permiso para siempre
                        getContentResolver().takePersistableUriPermission(
                                imagenSeleccionadaUri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                    } catch (Exception e) {}

                    ivEditProfilePic.setImageURI(imagenSeleccionadaUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        ivEditProfilePic = findViewById(R.id.ivEditProfilePic);
        btnCargarFoto = findViewById(R.id.btnCargarFoto);
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);
        btnGuardarPerfil = findViewById(R.id.btnGuardarPerfil);
        btnBack = findViewById(R.id.btnBack);

        cargarDatosGuardados();

        btnCargarFoto.setOnClickListener(v -> {
            // Usamos OPEN_DOCUMENT para tener permisos reales
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            abrirGaleria.launch(intent);
        });

        btnGuardarPerfil.setOnClickListener(v -> guardarDatos());
        btnBack.setOnClickListener(v -> finish());
    }

    private void cargarDatosGuardados() {
        SharedPreferences prefs = getSharedPreferences("MisDatosPerfil", MODE_PRIVATE);
        etNombre.setText(prefs.getString("nombre", ""));
        etTelefono.setText(prefs.getString("telefono", ""));
        etDireccion.setText(prefs.getString("direccion", ""));

        String uriImagenGuardada = prefs.getString("imagen_perfil", "");
        if (!uriImagenGuardada.isEmpty()) {
            try {
                imagenSeleccionadaUri = Uri.parse(uriImagenGuardada);
                ivEditProfilePic.setImageURI(imagenSeleccionadaUri);
            } catch (Exception e) {
                ivEditProfilePic.setImageResource(R.drawable.avatar);
            }
        }
    }

    private void guardarDatos() {
        SharedPreferences prefs = getSharedPreferences("MisDatosPerfil", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("nombre", etNombre.getText().toString());
        editor.putString("telefono", etTelefono.getText().toString());
        editor.putString("direccion", etDireccion.getText().toString());

        if (imagenSeleccionadaUri != null) {
            editor.putString("imagen_perfil", imagenSeleccionadaUri.toString());
        }

        editor.apply();
        Toast.makeText(this, "¡Perfil guardado correctamente!", Toast.LENGTH_SHORT).show();
        finish();
    }
}