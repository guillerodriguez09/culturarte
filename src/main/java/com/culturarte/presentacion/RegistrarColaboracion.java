package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IColaboracionController;
import com.culturarte.logica.controllers.IColaboradorController;
import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOColaboracion;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class RegistrarColaboracion {
    private JPanel mainPanel;
    private JComboBox<String> propuestas;
    private JButton verInformacion;
    private JTable infoPropuesta;
    private JLabel abTipoDeRetorno;
    private JComboBox<ETipoRetorno> retorno;
    private JLabel abMonto;
    private JTextField montoDigito;
    private JButton aceptar;
    private JButton cancelar;
    private JLabel abSeleccionLaPropuesta;
    private JLabel abIngresarColaboracion;
    private JComboBox<String> SeleccionColaborador;
    private JLabel Colaborador;

    private final IColaboradorController controllerCol = Fabrica.getInstancia().getColaboradorController();
    private final IPropuestaController controllerPro = Fabrica.getInstancia().getPropuestaController();
    private final IColaboracionController controllerColab = Fabrica.getInstancia().getColaboracionController();

    private List<DTOPropuesta> listaPropuestas;

    public RegistrarColaboracion() {
        List<String> colaboradores = controllerCol.listarColaboradores();
        for (String nick : colaboradores) {
            SeleccionColaborador.addItem(nick);
        }

        listaPropuestas = controllerPro.listarPropuestasConProponente();
        for (DTOPropuesta dto : listaPropuestas) {
            propuestas.addItem(dto.getTitulo() + " (" + dto.getProponenteNick() + ")");
        }

        for (ETipoRetorno tipo : ETipoRetorno.values()) {
            retorno.addItem(tipo);
        }

        aceptar.addActionListener(e -> registrarColaboracion());

        cancelar.addActionListener(e -> {
            JInternalFrame internal = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, mainPanel);
            if (internal != null) {
                internal.dispose();
            } else {
                Window ventana = SwingUtilities.getWindowAncestor(mainPanel);
                if (ventana != null) {
                    ventana.setVisible(false);
                }
            }
        });

        verInformacion.addActionListener(e -> mostrarDetallesSeleccion());
    }

    private void registrarColaboracion() {
        int index = propuestas.getSelectedIndex();
        if (index < 0 || listaPropuestas == null || listaPropuestas.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar una propuesta");
            return;
        }
        DTOPropuesta propuestaSeleccionada = listaPropuestas.get(index);

        String colaboradorNick = (String) SeleccionColaborador.getSelectedItem();
        if (colaboradorNick == null || colaboradorNick.trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Debe seleccionar un colaborador");
            return;
        }

        ETipoRetorno tipoRetorno = (ETipoRetorno) retorno.getSelectedItem();

        int monto;
        try {
            monto = Integer.parseInt(montoDigito.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Monto inválido");
            return;
        }

        DTOColaboracion dto = new DTOColaboracion();
        dto.setPropuestaTitulo(propuestaSeleccionada.getTitulo());
        dto.setColaboradorNick(colaboradorNick);
        dto.setMonto(monto);
        dto.setRetorno(tipoRetorno);
        dto.setFecha(LocalDateTime.now());
        dto.setMonto(monto);

        try {
            controllerColab.registrarColaboracion(dto);
            JOptionPane.showMessageDialog(mainPanel, "Colaboración registrada exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage());
        }
    }

    private void mostrarDetallesSeleccion() {
        int index = propuestas.getSelectedIndex();
        if (index < 0 || listaPropuestas == null || listaPropuestas.isEmpty()) return;

        DTOPropuesta p = listaPropuestas.get(index);

        String[] columnas = {"Campo", "Valor"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        model.addRow(new Object[]{"Título", p.getTitulo()});
        model.addRow(new Object[]{"Descripción", p.getDescripcion()});
        model.addRow(new Object[]{"Lugar", p.getLugar()});
        model.addRow(new Object[]{"Fecha", p.getFecha()});
        model.addRow(new Object[]{"Precio Entrada", p.getPrecioEntrada()});
        model.addRow(new Object[]{"Monto a Reunir", p.getMontoAReunir()});
        model.addRow(new Object[]{"Monto Recaudado", p.getMontoRecaudado()});
        model.addRow(new Object[]{"Proponente", p.getProponenteNick()});
        model.addRow(new Object[]{"Categoría", p.getCategoria()});
        model.addRow(new Object[]{"Retornos Disponibles", p.getRetornos()});
        model.addRow(new Object[]{"Estado Actual", p.getEstadoActual()});

        infoPropuesta.setModel(model);
    }

    private void limpiarCampos() {
        SeleccionColaborador.setSelectedIndex(-1);
        propuestas.setSelectedIndex(-1);
        retorno.setSelectedIndex(-1);
        montoDigito.setText("");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
