package com.culturarte.ws;

import jakarta.xml.ws.Endpoint;
import com.culturarte.logica.fabrica.Fabrica;

import java.io.FileReader;
import java.util.Properties;

public class Publicador {

    /**
     Carga las propiedades desde el archivo de config
     */
    private static Properties getConfig() throws Exception {
        String configDir = System.getProperty("user.home") + System.getProperty("file.separator") + ".Culturarte";
        Properties props = new Properties();
        props.load(new FileReader(configDir + System.getProperty("file.separator") + "config.properties"));
        return props;
    }

    public static void main(String[] args) {
        try {
            // Cargar la URL base desde el archivo de config
            Properties config = getConfig();
            String urlBase = config.getProperty("ws.urlPublicacion"); // Ej: "http://localhost:9128"

            if (urlBase == null) {
                System.err.println("Error");
                return;
            }

            Fabrica fabrica = Fabrica.getInstancia();


            String urlPropuesta = urlBase + "/propuesta";
            Endpoint.publish(urlPropuesta, fabrica.getPropuestaController());
            System.out.println("Servicio de Propuesta publicado en: " + urlPropuesta + "?wsdl");


            String urlColaborador = urlBase + "/colaborador";
            Endpoint.publish(urlColaborador, fabrica.getColaboradorController());
            System.out.println("Servicio de Colaborador publicado en: " + urlColaborador + "?wsdl");


            String urlProponente = urlBase + "/proponente";
            Endpoint.publish(urlProponente, fabrica.getProponenteController());
            System.out.println("Servicio de Proponente publicado en: " + urlProponente + "?wsdl");


            String urlColaboracion = urlBase + "/colaboracion";
            Endpoint.publish(urlColaboracion, fabrica.getColaboracionController());
            System.out.println("Servicio de Colaboracion publicado en: " + urlColaboracion + "?wsdl");


            String urlCategoria = urlBase + "/categoria";
            Endpoint.publish(urlCategoria, fabrica.getCategoriaController());
            System.out.println("Servicio de Categoria publicado en: " + urlCategoria + "?wsdl");


            String urlSeguimiento = urlBase + "/seguimiento";
            Endpoint.publish(urlSeguimiento, fabrica.getSeguimientoController());
            System.out.println("Servicio de Seguimiento publicado en: " + urlSeguimiento + "?wsdl");


        } catch (Exception e) {
            System.err.println("Error al publicar servicios:");
            e.printStackTrace();
        }
    }
}
