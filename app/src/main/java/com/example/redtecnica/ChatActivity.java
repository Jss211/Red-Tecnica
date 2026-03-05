package com.example.redtecnica;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Obtener datos del técnico
        String nombre = getIntent().getStringExtra("TECH_NAME");
        String imagenKey = getIntent().getStringExtra("TECH_IMAGE");

        binding.tvChatName.setText(nombre != null ? nombre : "Especialista");

        // Configurar foto del técnico
        if ("gasfitero".equals(imagenKey)) {
            binding.ivChatProfile.setImageResource(R.drawable.gasfitero);
        } else if ("cerrajeria".equals(imagenKey)) {
            binding.ivChatProfile.setImageResource(R.drawable.cerrajeria);
        } else if ("pintura".equals(imagenKey)) {
            binding.ivChatProfile.setImageResource(R.drawable.pintura);
        } else {
            binding.ivChatProfile.setImageResource(R.drawable.electricista);
        }

        binding.btnBack.setOnClickListener(v -> finish());

        // 2. Mandar el mensaje inicial automáticamente
        String mensajeInicial = "Hola " + (nombre != null ? nombre : "especialista") + ", vi tu perfil en FixZone y me gustaría solicitar tus servicios.";
        agregarMensaje(mensajeInicial, true);

        // 3. Simular que el técnico responde después de 2 segundos
        binding.tvTyping.setVisibility(View.VISIBLE); // Mostramos "escribiendo..."
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.tvTyping.setVisibility(View.GONE);
            agregarMensaje("¡Hola! Claro que sí. ¿En qué te puedo ayudar hoy? ¿Podrías darme más detalles de lo que necesitas?", false);
        }, 2500);

        // 4. Configurar el botón de enviar para chatear
        binding.btnSend.setOnClickListener(v -> {
            String nuevoMensaje = binding.etMessage.getText().toString().trim();
            if (!nuevoMensaje.isEmpty()) {
                agregarMensaje(nuevoMensaje, true); // Tu mensaje
                binding.etMessage.setText(""); // Limpiar caja de texto

                // El técnico vuelve a responder a lo que digas
                binding.tvTyping.setVisibility(View.VISIBLE);
                bajarScroll();

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    binding.tvTyping.setVisibility(View.GONE);
                    agregarMensaje("Entiendo el problema. En un momento te confirmo la disponibilidad para acercarme a revisarlo.", false);
                }, 3000);
            }
        });
    }

    // Método para crear y dibujar las burbujas de texto
    private void agregarMensaje(String texto, boolean esMio) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextSize(15f);
        tv.setPadding(40, 30, 40, 30);
        tv.setTextColor(getResources().getColor(android.R.color.black));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 16);

        if (esMio) {
            // Burbuja derecha (Tú)
            tv.setBackgroundResource(R.drawable.bg_chat_mio);
            params.gravity = android.view.Gravity.END;
            params.leftMargin = 150;
        } else {
            // Burbuja izquierda (Técnico)
            tv.setBackgroundResource(R.drawable.bg_chat_tecnico);
            params.gravity = android.view.Gravity.START;
            params.rightMargin = 150;
        }

        tv.setLayoutParams(params);
        binding.chatContainer.addView(tv);
        bajarScroll();
    }

    private void bajarScroll() {
        binding.scrollViewChat.post(() -> binding.scrollViewChat.fullScroll(View.FOCUS_DOWN));
    }
}