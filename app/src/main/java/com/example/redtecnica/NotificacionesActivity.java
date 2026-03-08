package com.example.redtecnica;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityNotificacionesBinding;
import com.google.android.material.card.MaterialCardView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificacionesActivity extends AppCompatActivity {

    private ActivityNotificacionesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificacionesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarUI();
        cargarNotificaciones();
    }

    private void configurarUI() {
        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnMarcarLeidas.setOnClickListener(v -> {
            marcarTodasLeidas();
        });
    }

    private void cargarNotificaciones() {
        // Notificaciones de ejemplo
        agregarNotificacion(
                "Técnico disponible",
                "Carlos Mendoza está disponible ahora. ¡Solicítalo!",
                "Hace 5 min",
                false
        );

        agregarNotificacion(
                "Nuevo técnico destacado",
                "Miguel Torres se unió con excelentes referencias en Drywall",
                "Hace 1 hora",
                false
        );

        agregarNotificacion(
                "Servicio completado",
                "Tu servicio con Roberto Sánchez ha sido completado",
                "Hace 2 horas",
                true
        );

        agregarNotificacion(
                "Promoción especial",
                "¡20% de descuento en tu próximo servicio de electricidad!",
                "Ayer",
                true
        );

        agregarNotificacion(
                "Recordatorio",
                "Luis Alva te recuerda tu cita programada para mañana",
                "Hace 2 días",
                true
        );
    }

    private void agregarNotificacion(String titulo, String mensaje, String tiempo, boolean leida) {
        View notifView = LayoutInflater.from(this).inflate(R.layout.item_notificacion, binding.containerNotificaciones, false);

        MaterialCardView card = notifView.findViewById(R.id.cardNotificacion);
        TextView tvTitulo = notifView.findViewById(R.id.tvNotifTitulo);
        TextView tvMensaje = notifView.findViewById(R.id.tvNotifMensaje);
        TextView tvTiempo = notifView.findViewById(R.id.tvNotifTiempo);
        View indicadorNoLeida = notifView.findViewById(R.id.indicadorNoLeida);

        tvTitulo.setText(titulo);
        tvMensaje.setText(mensaje);
        tvTiempo.setText(tiempo);
        indicadorNoLeida.setVisibility(leida ? View.GONE : View.VISIBLE);

        if (!leida) {
            card.setCardBackgroundColor(getResources().getColor(R.color.notif_no_leida, null));
        }

        card.setOnClickListener(v -> {
            indicadorNoLeida.setVisibility(View.GONE);
            card.setCardBackgroundColor(getResources().getColor(R.color.white, null));
        });

        binding.containerNotificaciones.addView(notifView);
    }

    private void marcarTodasLeidas() {
        int count = binding.containerNotificaciones.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = binding.containerNotificaciones.getChildAt(i);
            MaterialCardView card = child.findViewById(R.id.cardNotificacion);
            View indicador = child.findViewById(R.id.indicadorNoLeida);

            indicador.setVisibility(View.GONE);
            card.setCardBackgroundColor(getResources().getColor(R.color.white, null));
        }
    }
}
