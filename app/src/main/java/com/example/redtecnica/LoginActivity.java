package com.example.redtecnica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseFirestore db;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Verificar si ya hay sesión activa
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Ya hay sesión, ir directo al Home
            irAlInicio();
            return;
        }

        // Configurar Google Sign In con selector de cuentas
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Botón Iniciar Sesión Normal
        binding.btnLogin.setOnClickListener(v -> loginConEmail());

        // Botón Google Sign In
        binding.btnGoogleSignIn.setOnClickListener(v -> signInWithGoogle());

        // Botón para ir a Registrarse
        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void loginConEmail() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            // Ir a pantalla de carga y luego al Home
                            irAPantallaDeCarga();
                        } else {
                            Toast.makeText(this, "Por favor verifica tu correo electrónico primero", Toast.LENGTH_LONG).show();
                            mAuth.signOut();
                        }
                    } else {
                        Toast.makeText(this, "Error: Revisa tus credenciales", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signInWithGoogle() {
        // Cerrar sesión anterior para forzar selector de cuentas
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Cuenta seleccionada: " + account.getEmail(), Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Toast.makeText(this, "Error al iniciar sesión: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Verificar si el usuario está registrado en Firestore
                            verificarUsuarioEnFirestore(user);
                        }
                    } else {
                        Toast.makeText(this, "Error de autenticación: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void verificarUsuarioEnFirestore(FirebaseUser user) {
        String userId = user.getUid();

        db.collection("usuarios").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Usuario SÍ está registrado en Firestore
                        Toast.makeText(this, "Bienvenido de nuevo", Toast.LENGTH_SHORT).show();
                        irAPantallaDeCarga();
                    } else {
                        // Usuario autenticado pero sin documento en Firestore
                        // Crear documento automáticamente
                        crearDocumentoUsuario(user);
                    }
                })
                .addOnFailureListener(e -> {
                    // Si hay error de conexión, crear documento de todas formas
                    Toast.makeText(this, "Creando perfil...", Toast.LENGTH_SHORT).show();
                    crearDocumentoUsuario(user);
                });
    }

    private void crearDocumentoUsuario(FirebaseUser user) {
        String userId = user.getUid();
        String email = user.getEmail();
        String nombre = user.getDisplayName() != null ? user.getDisplayName() : "Usuario";

        db.collection("usuarios").document(userId)
                .set(new java.util.HashMap<String, Object>() {{
                    put("nombre", nombre);
                    put("email", email);
                    put("fechaRegistro", com.google.firebase.Timestamp.now());
                    put("tipoRegistro", "Google");
                }})
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Perfil creado exitosamente", Toast.LENGTH_SHORT).show();
                    irAPantallaDeCarga();
                })
                .addOnFailureListener(e -> {
                    // Aunque falle crear el documento, dejar pasar al usuario
                    Toast.makeText(this, "Continuando sin conexión...", Toast.LENGTH_SHORT).show();
                    irAPantallaDeCarga();
                });
    }

    private void irAPantallaDeCarga() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void irAlInicio() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
