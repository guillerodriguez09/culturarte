package com.culturarte.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//es el encargado de dar la entity manager a los dao
public class JpaUtil {

    private static final EntityManagerFactory emf;

    /**
     * Carga las propiedades desde el archivo de configuración como pide entrega 3
     */
    private static Properties getConfig() throws Exception {
        String configDir = System.getProperty("user.home") + System.getProperty("file.separator") + ".Culturarte";
        Properties props = new Properties();
        props.load(new FileReader(configDir + System.getProperty("file.separator") + "config.properties"));
        return props;
    }

    static {
        try {
            // Cargar propiedades desde el archivo config.properties
            Properties config = getConfig();

            // Crear un mapa de propiedades para pasar a JPA
            Map<String, String> jpaProperties = new HashMap<>();

            // Ponemos el driver
            jpaProperties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");

            // Leemos las propiedades de la base de datos desde el archivo de config
            jpaProperties.put("jakarta.persistence.jdbc.url", config.getProperty("db.url"));
            jpaProperties.put("jakarta.persistence.jdbc.user", config.getProperty("db.user"));
            jpaProperties.put("jakarta.persistence.jdbc.password", config.getProperty("db.password"));

            emf = Persistence.createEntityManagerFactory("CulturartePU", jpaProperties);

        } catch (Exception e) {
            System.err.println("Error al iniciar EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}


