package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IProponenteController;
import com.culturarte.logica.dtos.DTOProponente;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConsultaProponenteForm {
    private JPanel mainPanel;
    private JComboBox <String> cbxPropEleg;
    private JLabel txtProEleg;
    private JLabel lblNickname;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblCorreo;
    private JLabel lblFechaNacimiento;
    private JLabel lblImagen;
    private JLabel lblImagenShow;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JTextField txtNickname;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtFechaNacimiento;
    private JLabel lblBio;
    private JLabel lblLink;
    private JTextField txtBio;
    private JTextField txtLink;

    private final IProponenteController controllerProp = Fabrica.getInstancia().getProponenteController();

    public ConsultaProponenteForm(){

        List<String> proponentes = controllerProp.listarProponentes();
        for (String nick : proponentes) cbxPropEleg.addItem(nick);

        btnAceptar.addActionListener(e ->{
            mostrarDatosProponente();
        });

        btnCancelar.addActionListener(e ->{
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
        });

    }

    private void  mostrarDatosProponente(){

        String prop = (String) cbxPropEleg.getSelectedItem();
        if (prop == null){
            return;
        }

        DTOProponente dtoProp = controllerProp.obtenerProponente(prop);
        //List<Object[]> propConPropu = controllerProp.obtenerTodPropConPropu(prop);

        txtNickname.setText(dtoProp.getNick());
        txtNombre.setText(dtoProp.getNombre());
        txtApellido.setText(dtoProp.getApellido());
        txtCorreo.setText(dtoProp.getCorreo());
        txtFechaNacimiento.setText(dtoProp.getFechaNac().toString());

        if (dtoProp.getDirImagen() != null && !dtoProp.getDirImagen().isBlank()) {
            ImageIcon icon = new ImageIcon(dtoProp.getDirImagen());
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblImagenShow.setIcon(new ImageIcon(img));
        } else {
            lblImagenShow.setIcon(null);
        }

        txtBio.setText(dtoProp.getBiografia());
        txtLink.setText(dtoProp.getLink());

    }

    public JPanel getMainPanel(){

        return mainPanel;

    }

}
