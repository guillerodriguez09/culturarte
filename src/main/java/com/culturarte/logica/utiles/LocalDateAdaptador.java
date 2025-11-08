package com.culturarte.logica.utiles;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdaptador extends XmlAdapter<String, LocalDate> {

    // Convierte de LocalDate (objeto) a String (para el XML)
    public String marshal(LocalDate v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.toString();
    }

    // Convierte de String (del XML) a LocalDate (objeto)
    public LocalDate unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return LocalDate.parse(v);
    }
}
