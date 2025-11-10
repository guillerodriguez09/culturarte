package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IProponenteController;
import com.culturarte.logica.controllers.IPropuestaController;
import com.culturarte.logica.dtos.DTOProponente;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConsultaProponenteElimForm {
    private JPanel mainPanel;
    private JLabel lblNickname;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblCorreo;
    private JLabel lblFechaNacimiento;
    private JLabel lblImagen;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JTextField txtNickname;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtFechaNacimiento;
    private JLabel lblImagenShow;
    private JComboBox <String> cbxPropEleg;
    private JLabel lblBio;
    private JLabel lblLink;
    private JTextField txtLink;
    private JList listPropuDeElim;
    private JLabel Franchesco;
    private JTextPane txtBio;
    private JLabel txtProEleg;
    private JLabel lblFechaElim;
    private JTextField txtFechaElim;

    private final IProponenteController controllerProp = Fabrica.getInstancia().getProponenteController();
    private final IPropuestaController controllerPropuesta = Fabrica.getInstancia().getPropuestaController();

    public ConsultaProponenteElimForm(){

        List<String> proponentes = controllerProp.listarProponentesElim();
        for (String nick : proponentes) cbxPropEleg.addItem(nick);


        btnAceptar.addActionListener(e ->{
            mostrarDatosProponente();
            String prop = (String)cbxPropEleg.getSelectedItem();
            llamarPropuDeElim(prop);
        });

        btnCancelar.addActionListener(e ->{
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

    private void mostrarDatosProponente(){

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
            File file = new File(System.getProperty("user.dir"), dtoProp.getDirImagen());
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblImagenShow.setIcon(new ImageIcon(img));
            } else {
                System.out.println("Imagen de proponente no encontrada: " + file.getAbsolutePath());
                lblImagenShow.setIcon(null);
            }
        } else {
            lblImagenShow.setIcon(null);
        }


        txtBio.setText(dtoProp.getBiografia());
        txtLink.setText(dtoProp.getLink());

    }

    public JPanel getMainPanel(){

        return mainPanel;

    }

    private void llamarPropuDeElim(String prop){

        List<Object[]> propConPropuDeElim = controllerProp.obtenerTodPropConPropuDeEli(prop);

        List<String> elem = new ArrayList<>();

        for(Object[] fila : propConPropuDeElim) {
            DTOProponente dtoCol = (DTOProponente) fila[0];
            DTOPropuesta dtoCP = (DTOPropuesta) fila[1];

            String colaboradores = String.join(", ", dtoCP.getColaboradores());
            String linea = dtoCP.titulo + " - " + colaboradores + " - Monto: " + dtoCP.montoRecaudado;
            elem.add(linea);
        }

        listPropuDeElim.setListData(elem.toArray(new String[0]));
    }

}
