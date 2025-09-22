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

    public void seguidoresDeNick(){

        cbxUsuarioSeguido.removeAllItems();

        List<String> seguimientos = controllerSegui.listarSeguidosDeNick(cbxUsuarioSeguidor.getSelectedItem().toString());
        for (String segui : seguimientos) cbxUsuarioSeguido.addItem(segui);

    }

    public void ejecutadoor(){
        try {
            if (rbtnSeguir.isSelected()) {
                String usrSeguidoor = cbxUsuarioSeguidor.getSelectedItem().toString();
                DTOSeguimiento dtoSegui = new DTOSeguimiento();

                if (controllerPro.obtenerProponente(usrSeguidoor) != null) {

                    DTOProponente pepe = controllerPro.obtenerProponente(usrSeguidoor);
                    Proponente pro = new Proponente(pepe.getNick(), pepe.getNombre(), pepe.getApellido(), pepe.getCorreo(), pepe.getFechaNac(), pepe.getDirImagen(), pepe.getDireccion(), pepe.getBiografia(), pepe.getLink());
                    dtoSegui.setUsuarioSeguidor(pro);

                } else if (controllerCol.obtenerColaborador(usrSeguidoor) != null) {

                    DTOColaborador jaun = controllerCol.obtenerColaborador(usrSeguidoor);
                    Colaborador col = new Colaborador(jaun.getNick(), jaun.getNombre(), jaun.getApellido(), jaun.getCorreo(), jaun.getFechaNac(), jaun.getDirImagen());
                    dtoSegui.setUsuarioSeguidor(col);

                }

                dtoSegui.setUsuarioSeguido(cbxUsuarioSeguido.getSelectedItem().toString());
                controllerSegui.registrarSeguimiento(dtoSegui);
                JOptionPane.showMessageDialog(mainPanel, "Seguimiento Realizado");

            } else if (rbtnDejarSeguir.isSelected()) {

                String nick = cbxUsuarioSeguidor.getSelectedItem().toString();
                String nicky = cbxUsuarioSeguido.getSelectedItem().toString();

                controllerSegui.cancelarSeguimiento(controllerSegui.conseguirId(nick, nicky));
                JOptionPane.showMessageDialog(mainPanel, "Dejar de seguir Realizado");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getMainPanel() {return mainPanel;}
}
