package com.culturarte.presentacion;

import com.culturarte.logica.clases.Colaboracion;
import com.culturarte.logica.controllers.IColaboradorController;
import com.culturarte.logica.dtos.DTOColPropu;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.logica.dtos.DTOPropuesta;
import com.culturarte.logica.fabrica.Fabrica;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
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

    private void mostrarDatosColaboraciones(){

        String col = (String) cbxColEleg.getSelectedItem();
        if (col == null){
            return;
        }

        DTOColaborador dtoCola = controllerCol.obtenerColaborador(col);
        List<DTOColPropu> colConProp = controllerCol.obtenerTodColConPropu(col);

        txtNickname.setText(dtoCola.getNick());
        txtNombre.setText(dtoCola.getNombre());
        txtApellido.setText(dtoCola.getApellido());
        txtCorreo.setText(dtoCola.getCorreo());
        txtFechaNacimiento.setText(dtoCola.getFechaNac().toString());

        if (dtoCola.getDirImagen() != null && !dtoCola.getDirImagen().isBlank()) {
            File file = new File(System.getProperty("user.dir"), dtoCola.getDirImagen());
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblImagenShow.setIcon(new ImageIcon(img));
            } else {
                System.out.println("Imagen de colaborador no encontrada: " + file.getAbsolutePath());
                lblImagenShow.setIcon(null);
            }
        } else {
            lblImagenShow.setIcon(null);
        }


        /*
        for(Object[] fila : colConProp) {
            //DTOColaborador dtoCol = (DTOColaborador) fila[0];
            DTOPropuesta dtoCP = (DTOPropuesta) fila[1];
            dtoCP.getTitulo();
            dtoCP.getProponenteNick();
            dtoCP.montoRecaudado;
            dtoCP.getEstadoActual();
            listColaboraciones.setListData(dtoCP.getTitulo().toArray(new String[0])); //esto no esta funcionando bien

        }
        */

        List<String> datos = new ArrayList<>();

        if (colConProp != null && !colConProp.isEmpty()) {
            for (DTOColPropu pack : colConProp) {
                List<DTOPropuesta> propuestas = pack.getPropuestas();

                if (propuestas != null) {
                    for (DTOPropuesta p : propuestas) {
                        String item = "Título: " + p.getTitulo()
                                + " | Proponente: " + p.getProponenteNick()
                                + " | Monto: $" + p.montoRecaudado
                                + " | Estado: " + p.getEstadoActual();
                        datos.add(item);
                    }
                }
            }
        }

        // Mostrar la lista
        listColaboraciones.setListData(datos.toArray(new String[0]));
    }

    public JPanel getMainPanel() {return mainPanel;}

}
