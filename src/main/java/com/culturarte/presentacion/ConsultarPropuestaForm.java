package com.culturarte.presentacion;

import javax.swing.*;
import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;

import java.awt.*;
import java.util.List;


public class ConsultarPropuestaForm {
    private JPanel mainPanel;
    private JPanel pan1;
    private JPanel pan2;
    private JComboBox <String> comboPropuestas;
    private JTextField titulo;
    private JTextField descripcion;
    private JTextField lugar;
    private JTextField fecha;
    private JTextField montoE;
    private JTextField montoR;
    private JTextField estadoActual;
    private JList <String> colaboradores;
    private JTextField montoRecaudado;
    private JLabel imagendir;
    private JButton cancelarButton;
    private JButton aceptarButton;
    private JTextField fPubli;
    private JTextField retornos;

    private final IPropuestaController controller = Fabrica.getInstancia().getPropuestaController();

    public ConsultarPropuestaForm() {
        //llenar combo con propuestas q existen
        List<String> propuestas = controller.listarPropuestas();
        for (String titulo : propuestas) comboPropuestas.addItem(titulo);
        // Botón Aceptar carga datos
        aceptarButton.addActionListener(e -> mostrarDatosPropuesta());
        //comboPropuestas.addActionListener(e -> limpiarCampos());
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
    }

    private void mostrarDatosPropuesta() {
        String seleccionado = (String) comboPropuestas.getSelectedItem();
        if (seleccionado == null) return;

        DTOPropuesta dto = controller.consultarPropuesta(seleccionado);

        titulo.setText(dto.titulo);
        descripcion.setText(dto.descripcion);
        lugar.setText(dto.lugar);
        fecha.setText(dto.fecha.toString());
        montoE.setText(dto.precioEntrada.toString());
        montoR.setText(dto.montoAReunir.toString());
        estadoActual.setText(dto.estadoActual);
        montoRecaudado.setText(dto.montoRecaudado.toString());
        fPubli.setText(dto.fechaPublicacion.toString());

        if (dto.retornos != null && !dto.retornos.isEmpty()) {
            String retornosTexto = dto.retornos.stream()
                    .map(ETipoRetorno::name)
                    .reduce((r1, r2) -> r1 + ", " + r2)
                    .orElse("");
            retornos.setText(retornosTexto);
        } else {
            retornos.setText("Sin retornos definidos");
        }



        colaboradores.setListData(dto.colaboradores.toArray(new String[0]));

        if (dto.imagen != null && !dto.imagen.isBlank()) {
            ImageIcon icon = new ImageIcon(dto.imagen);
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagendir.setIcon(new ImageIcon(img));
        } else {
            imagendir.setIcon(null);
        }
    }

    public JPanel traerPanel() {
        return mainPanel;
    }

    private void limpiarCampos() {
        titulo.setText("");
        descripcion.setText("");
        lugar.setText("");
        fecha.setText("");
        montoE.setText("");
        montoR.setText("");
        estadoActual.setText("");
        montoRecaudado.setText("");
        colaboradores.setListData(new String[0]);
        imagendir.setIcon(null);
        fPubli.setText("");
    }


}
