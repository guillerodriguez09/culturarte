package com.culturarte.presentacion;

import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.controllers.IColaboradorController;
import com.culturarte.logica.controllers.IProponenteController;
import com.culturarte.logica.controllers.ISeguimientoController;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.logica.dtos.DTOProponente;
import com.culturarte.logica.dtos.DTOSeguimiento;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SeguimientoUsuarioForm {
    private JRadioButton rbtnSeguir;
    private JPanel mainPanel;
    private JRadioButton rbtnDejarSeguir;
    private JComboBox<String> cbxUsuarioSeguidor;
    private JComboBox<String> cbxUsuarioSeguido;
    private JLabel lblUsuarioSeguidor;
    private JLabel lblUsuarioSeguido;
    private JButton btnAceptar;
    private JButton btnCancelar;

    private final ISeguimientoController controllerSegui = Fabrica.getInstancia().getSeguimientoController();
    //Estos 2 son necesarios para encontrar los usuarios ya que no hice un UsuarioDAO. Tengo que hacer un UsuarioDAO y mover
    //funciones genericas a ahí.
    private final IColaboradorController controllerCol = Fabrica.getInstancia().getColaboradorController();
    private final IProponenteController controllerPro = Fabrica.getInstancia().getProponenteController();

    public SeguimientoUsuarioForm() {

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbtnSeguir);
        grupo.add(rbtnDejarSeguir);

        btnAceptar.addActionListener(e -> {
            ejecutadoor();
        });

        btnCancelar.addActionListener(e -> {
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

        rbtnSeguir.addActionListener(e -> {
            paraSeguir();
        });

        rbtnDejarSeguir.addActionListener(e -> {
           paraDejarSeguir();
        });

        cbxUsuarioSeguidor.addActionListener(e -> {
            seguidoresDeNick();
        });

    }

    public void paraSeguir(){
        cbxUsuarioSeguidor.removeAllItems();
        cbxUsuarioSeguido.removeAllItems();

        List<String> proponentes = controllerPro.listarProponentes();
        List<String> colaborador = controllerCol.listarColaboradores();

        for (String prop : proponentes) cbxUsuarioSeguidor.addItem(prop);
        for (String cola : colaborador) cbxUsuarioSeguidor.addItem(cola);

        for (String prop : proponentes) cbxUsuarioSeguido.addItem(prop);
        for (String cola : colaborador) cbxUsuarioSeguido.addItem(cola);
    }

    public void paraDejarSeguir(){

        cbxUsuarioSeguidor.removeAllItems();
        cbxUsuarioSeguido.removeAllItems();

        List<String> proponentes = controllerPro.listarProponentes();
        List<String> colaborador = controllerCol.listarColaboradores();

        for (String prop : proponentes) cbxUsuarioSeguidor.addItem(prop);
        for (String cola : colaborador) cbxUsuarioSeguidor.addItem(cola);

        seguidoresDeNick();
    }

    public void seguidoresDeNick() {
        Object seleccionado = cbxUsuarioSeguidor.getSelectedItem();
        if (seleccionado == null) {
            return; //evita nullexcep
        }

        String nickSeleccionado = seleccionado.toString();
        cbxUsuarioSeguido.removeAllItems();

        List<String> seguimientos = controllerSegui.listarSeguidosDeNick(nickSeleccionado);
        for (String segui : seguimientos) {
            cbxUsuarioSeguido.addItem(segui);
        }
    }


    public void ejecutadoor(){
        try {
            if (rbtnSeguir.isSelected()) {

                String nickSeguidor = cbxUsuarioSeguidor.getSelectedItem().toString();
                String nickSeguido = cbxUsuarioSeguido.getSelectedItem().toString();

                DTOSeguimiento dtoSegui = new DTOSeguimiento();

                // obtenemos el DTO del seguidor según su tipo
                DTOProponente dtoProp = controllerPro.obtenerProponente(nickSeguidor);
                DTOColaborador dtoCol = controllerCol.obtenerColaborador(nickSeguidor);

                if (dtoProp != null) {
                    dtoSegui.setUsuarioSeguidor(dtoProp);
                } else if (dtoCol != null) {
                    dtoSegui.setUsuarioSeguidor(dtoCol);
                } else {
                    throw new Exception("No se encontró el usuario seguidor: " + nickSeguidor);
                }

                dtoSegui.setUsuarioSeguido(nickSeguido);
                controllerSegui.registrarSeguimiento(dtoSegui);
                JOptionPane.showMessageDialog(mainPanel, "Seguimiento realizado");

            } else if (rbtnDejarSeguir.isSelected()) {
                String nick = cbxUsuarioSeguidor.getSelectedItem().toString();
                String nicky = cbxUsuarioSeguido.getSelectedItem().toString();

                controllerSegui.cancelarSeguimiento(controllerSegui.conseguirId(nick, nicky));
                JOptionPane.showMessageDialog(mainPanel, "Dejar de seguir realizado");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public JPanel getMainPanel() {return mainPanel;}
}
