package com.culturarte.presentacion;

import com.culturarte.logica.controllers.IColaboradorController;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConsultaColaboradorFrom {
    private JComboBox <String> cbxColEleg;
    private JPanel mainPanel;
    private JLabel lblColEleg;
    private JLabel lblNickname;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblCorreo;
    private JLabel lblFechaNacimiento;
    private JLabel lblImagen;
    private JLabel lblImagenShow;
    private JLabel lblColaboraciones;
    private JList<String> listColaboraciones;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JTextField txtNickname;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtFechaNacimiento;

    private final IColaboradorController controllerCol = Fabrica.getInstancia().getColaboradorController();


    public ConsultaColaboradorFrom(){

        List<String> colaboradores = controllerCol.listarColaboradores();
        for (String nick : colaboradores) cbxColEleg.addItem(nick);

        btnAceptar.addActionListener(e ->{
            mostrarDatosColaboraciones();
        });

        btnCancelar.addActionListener(e ->{
            SwingUtilities.getWindowAncestor(mainPanel).dispose();
        });

    }

    private void mostrarDatosColaboraciones(){

        String col = (String) cbxColEleg.getSelectedItem();
        if (col == null){
            return;
        }

        DTOColaborador dtoCola = controllerCol.obtenerColaborador(col);
        List<Object[]> colConProp = controllerCol.obtenerTodColConPropu(col);

        txtNickname.setText(dtoCola.getNick());
        txtNombre.setText(dtoCola.getNombre());
        txtApellido.setText(dtoCola.getApellido());
        txtCorreo.setText(dtoCola.getCorreo());
        txtFechaNacimiento.setText(dtoCola.getFechaNac().toString());

        if (dtoCola.getDirImagen() != null && !dtoCola.getDirImagen().isBlank()) {
            ImageIcon icon = new ImageIcon(dtoCola.getDirImagen());
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblImagenShow.setIcon(new ImageIcon(img));
        } else {
            lblImagenShow.setIcon(null);
        }

        for(Object[] fila : colConProp) {
            //DTOColaborador dtoCol = (DTOColaborador) fila[0];
            DTOPropuesta dtoCP = (DTOPropuesta) fila[1];

            listColaboraciones.setListData(dtoCP.colaboradores.toArray(new String[0]));

        }

    }

    public JPanel getMainPanel() {return mainPanel;}

}
