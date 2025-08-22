package com.culturarte.logica.fabrica;

import com.culturarte.logica.controllers.*;

public class Fabrica {

    // Singleton
    private static Fabrica instancia;

    // Controllers
    private final IPropuestaController propuestaController;

    private Fabrica() {
        // Instanciar controllers aca
        this.propuestaController = new PropuestaController();
    }

    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }

    // Getters de controllers
    public IPropuestaController getPropuestaController() {
        return propuestaController;
    }
}

