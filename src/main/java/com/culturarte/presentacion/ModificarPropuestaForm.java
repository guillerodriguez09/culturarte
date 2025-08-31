package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOConsultaPropuesta;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


import java.time.LocalDate;
import java.util.List;

public class ModificarPropuestaForm {
    private JPanel mainPanel;
    private JPanel pan1;
    private JComboBox <String> comboPropuestas;
    private JPanel pan2;
    private JTextField descripcion;
    private JTextField lugar;
    private JTextField fecha;
    private JTextField estadoActual;
    private JButton cargarButton;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JComboBox <String> comboCategoria;
    private JComboBox <String> comboProponente;
    private JSpinner montoE;
    private JSpinner montoR;
    private JTextField imagendir;
    private JButton seleccionarButton;
    private JList <ETipoRetorno> listaRet;
    private JTextField titulo;

    private final IPropuestaController controller = Fabrica.getInstancia().getPropuestaController();


    public ModificarPropuestaForm() {
    // Carga propuestas a selecc
        comboPropuestas.addItem("-- Seleccione una propuesta --");
        List<String> propuestas = controller.listarPropuestas();
        for (String p : propuestas) comboPropuestas.addItem(p);
        comboPropuestas.setSelectedIndex(0);


    // Carga combos de cat y prop
        comboCategoria.addItem("-- Seleccione una categoria --");
        List<String> categorias = Fabrica.getInstancia().getCategoriaController().listarCategorias();
        for (String cat : categorias) comboCategoria.addItem(cat);
        comboCategoria.setSelectedIndex(0);


        comboProponente.addItem("-- Seleccione una proponente --");
        List<String> proponentes = Fabrica.getInstancia().getProponenteController().listarProponentes();
        for (String prop : proponentes) comboProponente.addItem(prop);
        comboProponente.setSelectedIndex(0);


        //para arreglar
        listaRet.setListData(ETipoRetorno.values());
        listaRet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


        cargarButton.addActionListener(e -> cargarDatos());
        aceptarButton.addActionListener(e -> guardarCambios());

        //para arreglar
        cancelarButton.addActionListener(e -> {
            JInternalFrame internal = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, mainPanel);
            if (internal != null) {
                internal.dispose();
            } else {
                // Por si se ejecuta fuera de un InternalFrame, solo oculta la ventana padre
                Window ventana = SwingUtilities.getWindowAncestor(mainPanel);
                if (ventana != null) {
                    ventana.setVisible(false);
                }
            }
        });

        seleccionarButton.addActionListener(e -> seleccionarImagen());

    }

    private void cargarDatos() {
        try {
            String tituloSeleccionado = (String) comboPropuestas.getSelectedItem();
            DTOConsultaPropuesta dto = controller.consultarPropuesta(tituloSeleccionado);


            titulo.setText(dto.titulo);
            titulo.setEditable(false);
            descripcion.setText(dto.descripcion);
            lugar.setText(dto.lugar);
            fecha.setText(dto.fecha.toString());
            montoE.setValue(dto.precioEntrada);
            montoR.setValue(dto.montoAReunir);
            imagendir.setText(dto.imagen);
            estadoActual.setText(dto.estado);
            comboCategoria.setSelectedItem(dto.categoriaNombre);
            comboProponente.setSelectedItem(dto.proponenteNick);
            //ver tema de retornos como puedo dejar marcado
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, "Error al cargar datos: " + ex.getMessage());
        }
    }

    private void guardarCambios() {
        try {
            DTOPropuesta dto = new DTOPropuesta();
            dto.titulo = titulo.getText();
            dto.descripcion = descripcion.getText();
            dto.lugar = lugar.getText();
            dto.fecha = LocalDate.parse(fecha.getText());
            dto.precioEntrada = (Integer) montoE.getValue();
            dto.montoAReunir = (Integer) montoR.getValue();
            dto.imagen = imagendir.getText();
            dto.categoriaNombre = (String) comboCategoria.getSelectedItem();
            dto.proponenteNick = (String) comboProponente.getSelectedItem();
            dto.retornos = listaRet.getSelectedValuesList();


            controller.modificarPropuesta(titulo.getText(), dto);
            JOptionPane.showMessageDialog(mainPanel, "Propuesta modificada correctamente.");


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
// esto podria cambiarlo porque se repite en otros formularios
    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes JPG y PNG", "jpg", "jpeg", "png"));

        int resultado = chooser.showOpenDialog(mainPanel);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoOriginal = chooser.getSelectedFile();

            // Crea carpeta si no existe
            File carpetaDestino = new File("imagenes");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs();
            }

            // Crea ruta destino dentro de /imagenes
            File archivoDestino = new File(carpetaDestino, archivoOriginal.getName());

            try {
                Files.copy(
                        archivoOriginal.toPath(),
                        archivoDestino.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
                // Setea el campo con la ruta
                imagendir.setText(archivoDestino.getPath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error al copiar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    public JPanel traerPanel() {
        return mainPanel;
    }


}



