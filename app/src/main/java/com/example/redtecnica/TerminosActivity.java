package com.example.redtecnica;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.redtecnica.databinding.ActivityTerminosBinding;

public class TerminosActivity extends AppCompatActivity {

    private ActivityTerminosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTerminosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());

        cargarTerminos();
    }

    private void cargarTerminos() {
        String terminos = "TÉRMINOS Y CONDICIONES DE USO\n\n" +
                "Última actualización: Marzo 2026\n\n" +
                "1. ACEPTACIÓN DE LOS TÉRMINOS\n\n" +
                "Al acceder y utilizar FixZone, aceptas estar sujeto a estos Términos y Condiciones. " +
                "Si no estás de acuerdo con alguna parte de estos términos, no debes usar nuestra aplicación.\n\n" +
                "2. DESCRIPCIÓN DEL SERVICIO\n\n" +
                "FixZone es una plataforma que conecta usuarios con técnicos profesionales verificados " +
                "en diversas especialidades como gasfitería, electricidad, cerrajería e instalación de drywall.\n\n" +
                "3. REGISTRO Y CUENTA\n\n" +
                "• Debes proporcionar información precisa y actualizada\n" +
                "• Eres responsable de mantener la confidencialidad de tu cuenta\n" +
                "• Debes notificar inmediatamente cualquier uso no autorizado\n\n" +
                "4. USO APROPIADO\n\n" +
                "Te comprometes a:\n" +
                "• Usar el servicio solo para fines legales\n" +
                "• No acosar, abusar o dañar a otros usuarios o técnicos\n" +
                "• No publicar contenido falso o engañoso\n" +
                "• Respetar los derechos de propiedad intelectual\n\n" +
                "5. SERVICIOS DE TÉCNICOS\n\n" +
                "• FixZone actúa como intermediario entre usuarios y técnicos\n" +
                "• No somos responsables de la calidad del trabajo realizado\n" +
                "• Los técnicos son contratistas independientes\n" +
                "• Recomendamos verificar credenciales antes de contratar\n\n" +
                "6. PAGOS Y TARIFAS\n\n" +
                "• Los precios son establecidos por cada técnico\n" +
                "• FixZone puede cobrar una comisión por el servicio\n" +
                "• Todos los pagos son procesados de forma segura\n\n" +
                "7. CANCELACIONES Y REEMBOLSOS\n\n" +
                "• Las políticas de cancelación varían según el técnico\n" +
                "• Los reembolsos se procesan según cada caso\n" +
                "• Contacta a soporte para resolver disputas\n\n" +
                "8. LIMITACIÓN DE RESPONSABILIDAD\n\n" +
                "FixZone no será responsable por:\n" +
                "• Daños directos o indirectos derivados del uso del servicio\n" +
                "• Pérdidas económicas o de datos\n" +
                "• Interrupciones del servicio\n\n" +
                "9. MODIFICACIONES\n\n" +
                "Nos reservamos el derecho de modificar estos términos en cualquier momento. " +
                "Los cambios serán notificados a través de la aplicación.\n\n" +
                "10. CONTACTO\n\n" +
                "Para preguntas sobre estos términos:\n" +
                "Email: soporte@fixzone.com\n" +
                "Teléfono: +51 999 888 777\n\n" +
                "Al continuar usando FixZone, aceptas estos Términos y Condiciones.";

        binding.tvTerminosContenido.setText(terminos);
    }
}
