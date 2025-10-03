package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

public class AltaPropuestaForm {

    private JPanel panel1;
    private JComboBox<String> comboProponente;
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
    private JTree arbolCats;
    private JScrollPane scrollCats;

    private final IPropuestaController controller = Fabrica.getInstancia().getPropuestaController();

    public AltaPropuestaForm() {
        // Llenar árbol de categorías
        DefaultMutableTreeNode raiz = Fabrica.getInstancia()
                .getCategoriaController()
                .construirArbolCategorias();

        arbolCats.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
        scrollCats.setPreferredSize(new Dimension(200, 150));


        // Llena combo de proponentes
        List<String> proponentes = Fabrica.getInstancia().getProponenteController().listarProponentes();
        for (String prop : proponentes) comboProponente.addItem(prop);

        // Llena lista de retornos
        listaRetornos.setListData(ETipoRetorno.values());
        listaRetornos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        aceptarButton.addActionListener(e -> registrarPropuesta());

        imgButton.addActionListener(e -> seleccionarImagen());

        cancelarButton.addActionListener(e -> {
            JInternalFrame internal = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, panel1);
            if (internal != null) {
                internal.dispose();
            } else {
                // Por si se ejecuta fuera de un InternalFrame, solo oculta la ventana padre
                Window ventana = SwingUtilities.getWindowAncestor(panel1);
                if (ventana != null) {
                    ventana.setVisible(false);
                }
            }
        });
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

            Object nodoSeleccionado = arbolCats.getLastSelectedPathComponent();
            if (nodoSeleccionado == null) {
                throw new IllegalArgumentException("Debe seleccionar una categoría.");
            }
            dto.categoriaNombre = nodoSeleccionado.toString();

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
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes JPG y PNG", "jpg", "jpeg", "png"
        ));

        int resultado = chooser.showOpenDialog(panel1);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoOriginal = chooser.getSelectedFile();

            // Carpeta externa "imagenes" en el working directory
            File carpetaDestino = new File(System.getProperty("user.dir"), "imagenes");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs();
            }

            // Crear archivo destino dentro de esa carpeta
            File archivoDestino = new File(carpetaDestino, archivoOriginal.getName());

            try {
                Files.copy(
                        archivoOriginal.toPath(),
                        archivoDestino.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );

                // Guardar SOLO la ruta relativa (para BD y GUI)
                String rutaRelativa = "imagenes/" + archivoOriginal.getName();
                imagen.setText(rutaRelativa);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(panel1,
                        "Error al copiar la imagen: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
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
        arbolCats.clearSelection();
        comboProponente.setSelectedIndex(0);
    }

    public JPanel traerPanel() {
        return panel1;
    }

}
