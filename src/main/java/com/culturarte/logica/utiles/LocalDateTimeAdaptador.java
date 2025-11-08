package com.culturarte.logica.utiles; // O el paquete que prefieras

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

public class LocalDateTimeAdaptador extends XmlAdapter<String, LocalDateTime> {

    // Convierte de LocalDateTime (objeto) a String (para el XML)
    @Override
    public String marshal(LocalDateTime v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.toString();
    }

    // Convierte de String (del XML) a LocalDateTime (objeto)
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return LocalDateTime.parse(v);
    }
}
