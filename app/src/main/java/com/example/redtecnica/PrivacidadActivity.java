package com.example.redtecnica;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityPrivacidadBinding;

public class PrivacidadActivity extends AppCompatActivity {

    private ActivityPrivacidadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacidadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());

        cargarPolitica();
    }

    private void cargarPolitica() {
        String politica = "POLÍTICA DE PRIVACIDAD\n\n" +
                "Última actualización: Marzo 2026\n\n" +
                "En FixZone, nos comprometemos a proteger tu privacidad y datos personales. " +
                "Esta política explica cómo recopilamos, usamos y protegemos tu información.\n\n" +
                "1. INFORMACIÓN QUE RECOPILAMOS\n\n" +
                "Recopilamos la siguiente información:\n\n" +
                "• Información de registro: nombre, email, teléfono\n" +
                "• Información de perfil: foto, dirección, preferencias\n" +
                "• Información de uso: servicios solicitados, historial\n" +
                "• Información de ubicación: para encontrar técnicos cercanos\n" +
                "• Información de pago: datos de tarjetas (encriptados)\n\n" +
                "2. CÓMO USAMOS TU INFORMACIÓN\n\n" +
                "Utilizamos tu información para:\n\n" +
                "• Conectarte con técnicos profesionales\n" +
                "• Procesar pagos y transacciones\n" +
                "• Mejorar nuestros servicios\n" +
                "• Enviarte notificaciones relevantes\n" +
                "• Prevenir fraudes y garantizar seguridad\n" +
                "• Cumplir con obligaciones legales\n\n" +
                "3. COMPARTIR INFORMACIÓN\n\n" +
                "Compartimos tu información solo con:\n\n" +
                "• Técnicos: nombre, ubicación, teléfono (para el servicio)\n" +
                "• Procesadores de pago: información necesaria para transacciones\n" +
                "• Autoridades: cuando sea requerido por ley\n\n" +
                "NO vendemos tu información a terceros.\n\n" +
                "4. SEGURIDAD DE DATOS\n\n" +
                "Implementamos medidas de seguridad:\n\n" +
                "• Encriptación de datos sensibles\n" +
                "• Servidores seguros con certificados SSL\n" +
                "• Acceso restringido a información personal\n" +
                "• Monitoreo constante de seguridad\n\n" +
                "5. TUS DERECHOS\n\n" +
                "Tienes derecho a:\n\n" +
                "• Acceder a tu información personal\n" +
                "• Corregir datos incorrectos\n" +
                "• Solicitar eliminación de tu cuenta\n" +
                "• Oponerte al procesamiento de datos\n" +
                "• Exportar tus datos\n\n" +
                "6. COOKIES Y TECNOLOGÍAS SIMILARES\n\n" +
                "Usamos cookies para:\n\n" +
                "• Mantener tu sesión activa\n" +
                "• Recordar tus preferencias\n" +
                "• Analizar el uso de la aplicación\n" +
                "• Mejorar la experiencia del usuario\n\n" +
                "7. RETENCIÓN DE DATOS\n\n" +
                "Conservamos tu información mientras:\n\n" +
                "• Tu cuenta esté activa\n" +
                "• Sea necesario para proporcionar servicios\n" +
                "• Sea requerido por ley\n\n" +
                "8. MENORES DE EDAD\n\n" +
                "FixZone no está dirigido a menores de 18 años. " +
                "No recopilamos intencionalmente información de menores.\n\n" +
                "9. CAMBIOS A ESTA POLÍTICA\n\n" +
                "Podemos actualizar esta política periódicamente. " +
                "Te notificaremos sobre cambios significativos.\n\n" +
                "10. CONTACTO\n\n" +
                "Para preguntas sobre privacidad:\n\n" +
                "Email: privacidad@fixzone.com\n" +
                "Teléfono: +51 999 888 777\n" +
                "Dirección: Av. Principal 123, Lima, Perú\n\n" +
                "Al usar FixZone, aceptas esta Política de Privacidad.";

        binding.tvPrivacidadContenido.setText(politica);
    }
}
