package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IColaboracionController;
import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOColaboracion;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.List;

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

    private final IColaboracionController colaboracionController;
    private final IPropuestaController propuestaController;

    private List<DTOPropuesta> listaPropuestas;

    public RegistrarColaboracion() {
        this.colaboracionController = Fabrica.getInstancia().getColaboracionController();
        this.propuestaController = Fabrica.getInstancia().getPropuestaController();

        cargarPropuestas();
        cargarRetornos();

        verInformacion.addActionListener(e -> mostrarDetallesSeleccion());
        aceptar.addActionListener(e -> registrarColaboracion());
        cancelar.addActionListener(e -> limpiarCampos());
    }

    private void cargarPropuestas() {
        propuestas.removeAllItems();
        listaPropuestas = propuestaController.listarPropuestasConProponente();
        if (listaPropuestas != null && !listaPropuestas.isEmpty()) {
            for (DTOPropuesta p : listaPropuestas) {
                propuestas.addItem(p.getTitulo());
            }
        } else {
            propuestas.addItem("No hay propuestas disponibles");
        }
        propuestas.setSelectedIndex(-1); // nada seleccionado al inicio
    }

    private void cargarRetornos() {
        retorno.removeAllItems();
        for (ETipoRetorno r : ETipoRetorno.values()) {
            retorno.addItem(r);
        }
        retorno.setSelectedIndex(0);
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

    private void registrarColaboracion() {
        int index = propuestas.getSelectedIndex();
        if (index < 0 || listaPropuestas == null || listaPropuestas.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione una propuesta primero");
            return;
        }

        DTOPropuesta propuestaSeleccionada = listaPropuestas.get(index);

        String colaboradorNick = JOptionPane.showInputDialog(mainPanel, "Ingrese nickname del colaborador:");
        if (colaboradorNick == null || colaboradorNick.trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Debe ingresar un colaborador");
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

        try {
            colaboracionController.registrarColaboracion(dto);
            JOptionPane.showMessageDialog(mainPanel, "Colaboración registrada exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, "Error al registrar colaboración: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        montoDigito.setText("");
        retorno.setSelectedIndex(0);
        propuestas.setSelectedIndex(-1);
        infoPropuesta.setModel(new DefaultTableModel());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
