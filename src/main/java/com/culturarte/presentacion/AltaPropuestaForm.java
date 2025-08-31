package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.filechooser.FileNameExtensionFilter;


import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class AltaPropuestaForm {

    private JPanel panel1;
    private JComboBox<String> comboProponente;
    private JComboBox<String> comboCategoria;
    private JTextField titulo;
    private JTextField descripcion;
    private JTextField lugar;
    private JTextField fecha;
    private JTextField imagen;
    private JButton imgButton;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JSpinner spinEntrada;
    private JSpinner spinMonto;
    private JList<ETipoRetorno> listaRetornos;

    private final IPropuestaController controller = Fabrica.getInstancia().getPropuestaController();

    public AltaPropuestaForm() {
        // Llena combo de categorías
        List<String> categorias = Fabrica.getInstancia().getCategoriaController().listarCategorias();
        for (String cat : categorias) comboCategoria.addItem(cat);

        // Llena combo de proponentes
        List<String> proponentes = Fabrica.getInstancia().getProponenteController().listarProponentes();
        for (String prop : proponentes) comboProponente.addItem(prop);

        // Llena lista de retornos
        listaRetornos.setListData(ETipoRetorno.values());
        listaRetornos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        aceptarButton.addActionListener(e -> registrarPropuesta());

        imgButton.addActionListener(e -> seleccionarImagen());

        cancelarButton.addActionListener(e -> SwingUtilities.getWindowAncestor(panel1).dispose());
    }

    private void registrarPropuesta() {
        try {
            DTOPropuesta dto = new DTOPropuesta();
            dto.titulo = titulo.getText();
            dto.descripcion = descripcion.getText();
            dto.lugar = lugar.getText();
            dto.fecha = LocalDate.parse(fecha.getText()); // el formato es yyyy-mm-dd
            dto.precioEntrada = (Integer) spinEntrada.getValue();
            dto.montoAReunir = (Integer) spinMonto.getValue();
            dto.imagen = imagen.getText();
            dto.categoriaNombre = (String) comboCategoria.getSelectedItem();
            dto.proponenteNick = (String) comboProponente.getSelectedItem();
            dto.retornos = listaRetornos.getSelectedValuesList();

            controller.altaPropuesta(dto);
            JOptionPane.showMessageDialog(panel1, "Propuesta creada");
            limpiarCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel1, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes JPG y PNG", "jpg", "jpeg", "png"));

        //agregue esto para q guarde en la carpeta imagenes
        int resultado = chooser.showOpenDialog(panel1);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivoOriginal = chooser.getSelectedFile();

            java.io.File carpetaDestino = new java.io.File("imagenes");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs();
            }

            // Crear archivo destino
            java.io.File archivoDestino = new java.io.File(carpetaDestino, archivoOriginal.getName());

            try {
                java.nio.file.Files.copy(
                        archivoOriginal.toPath(),
                        archivoDestino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );

                // Guardar ruta en el campo imagen
                imagen.setText(archivoDestino.getPath());

            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(panel1, "Error al copiar la imagen " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        titulo.setText("");
        descripcion.setText("");
        lugar.setText("");
        fecha.setText("");
        spinEntrada.setValue(0);
        spinMonto.setValue(0);
        imagen.setText("");
        listaRetornos.clearSelection();
        comboCategoria.setSelectedIndex(0);
        comboProponente.setSelectedIndex(0);
    }

    public JPanel traerPanel() {
        return panel1;
    }

}
