package com.culturarte.presentacion;

import com.culturarte.logica.clases.*;
import com.culturarte.logica.controllers.*;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.fabrica.Fabrica;
import com.culturarte.logica.dtos.*;
import com.culturarte.logica.enums.ETipoRetorno;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DatosDePrueba {

    private final IColaboradorController controllerCol = Fabrica.getInstancia().getColaboradorController();
    private final IProponenteController controllerPro = Fabrica.getInstancia().getProponenteController();
    private final IPropuestaController controllerP = Fabrica.getInstancia().getPropuestaController();
    private final ICategoriaController controllerCat = Fabrica.getInstancia().getCategoriaController();
    private final IColaboracionController controllerC = Fabrica.getInstancia().getColaboracionController();
    private final ISeguimientoController controllerSegui = Fabrica.getInstancia().getSeguimientoController();

    DefaultMutableTreeNode raiz = Fabrica.getInstancia().getCategoriaController().construirArbolCategorias();

    public void crearDatosPrueba() {
        //

        // Copiar imágenes de prueba desde resources a carpeta externa "imagenes/"
        copiarImagenDeResources("imagenes/hrubino.JPG", "imagenes/hrubino.JPG");
        copiarImagenDeResources("imagenes/mbusca.jpg", "imagenes/mbusca.jpg");
        copiarImagenDeResources("imagenes/losBardo.jpg", "imagenes/losBardo.jpg");
        copiarImagenDeResources("imagenes/sergiop.jpg", "imagenes/sergiop.jpg");
        copiarImagenDeResources("imagenes/tonyp.jpg", "imagenes/tonyp.jpg");
        copiarImagenDeResources("imagenes/nicoj.jpg", "imagenes/nicoj.jpg");

     // Reutilizables como "imagen faltante"
        copiarImagenDeResources("imagenes/404.png", "imagenes/404.png");

     // Propuestas
        copiarImagenDeResources("imagenes/elPimientoIndomable.jpg", "imagenes/elPimientoIndomable.jpg");
        copiarImagenDeResources("imagenes/pilsenRock.jpg", "imagenes/pilsenRock.jpg");
        copiarImagenDeResources("imagenes/unDiaDeJulio.jpg", "imagenes/unDiaDeJulio.jpg");

        //PROPONENTES
        DTOProponente dtoP = new DTOProponente();
        dtoP.setNick("hrubino");
        dtoP.setCorreo("horacio.rubino@guambia.com.uy");
        dtoP.setNombre("Horacio");
        dtoP.setApellido("Rubino");
        dtoP.setContrasenia("Nada");
        dtoP.setFechaNac(LocalDate.of(1962, 02, 25));
        dtoP.setDirImagen("/imagenes/hrubino.JPG");
        dtoP.setDireccion("18 de Julio 1234");
        dtoP.setLink("https://twitter.com/horaciorubino");
        dtoP.setBiografia("Horacio Rubino Torres nace el 25 de febrero de 1962, es conductor, actor y libretista. Debuta en 1982 en carnaval " +
                "en Los \"Klaper´s\", donde estuvo cuatro años, actuando y libretando. Luego para \"Gaby´s\" (6 años), escribió en " +
                "categoría revistas y humoristas y desde el comienzo y hasta el presente en su propio conjunto Momosapiens. ");
        controllerPro.altaProponente(dtoP);

        DTOProponente dtoP2 = new DTOProponente();
        dtoP2.setNick("mbusca");
        dtoP2.setCorreo("Martin.bus@agadu.org.uy");
        dtoP2.setNombre("Martin");
        dtoP2.setApellido("Buscaglia");
        dtoP2.setContrasenia("Nada");
        dtoP2.setFechaNac(LocalDate.of(1972, 06, 25));
        dtoP2.setDirImagen("/imagenes/mbusca.jpg");
        dtoP2.setDireccion("Colonia 4321");
        dtoP2.setLink("http://www.martinbuscaglia.com/");
        dtoP2.setBiografia("Martín Buscaglia (Montevideo, 1972) es un artista, músico, compositor y productor uruguayo. Tanto con su banda " +
                "(“Los Bochamakers”) como en su formato “Hombre orquesta”, o solo con su guitarra, ha recorrido el mundo " +
                "tocando entre otros países en España, Estados Unidos, Inglaterra, Francia, Australia, Brasil, Colombia, Argentina, " +
                "Chile, Paraguay, México y Uruguay. (Actualmente los Bochamakers son Matías Rada, Martín Ibarburu, Mateo " +
                "Moreno, Herman Klang) Paralelamente, tiene proyectos a dúo con el español Kiko Veneno, la cubana Yusa, el " +
                "argentino Lisandro Aristimuño, su compatriota Antolín, y a trío junto a los brasileros Os Mulheres Negras.");
        controllerPro.altaProponente(dtoP2);

        DTOProponente dtoP3 = new DTOProponente();
        dtoP3.setNick("hectorg");
        dtoP3.setCorreo("hector.gui@elgalpon.org.uy");
        dtoP3.setNombre("Héctor");
        dtoP3.setApellido("Guido");
        dtoP3.setContrasenia("Nada");
        dtoP3.setFechaNac(LocalDate.of(1954, 01, 07));
        dtoP3.setDirImagen("/imagenes/404.png");
        dtoP3.setDireccion("Gral. Flores 5645");
        dtoP3.setLink("");
        dtoP3.setBiografia("En 1972 ingresó a la Escuela de Arte Dramático del teatro El Galpón. Participó en más de treinta obras teatrales y " +
                "varios largometrajes. Integró el elenco estable de Radioteatro del Sodre, y en 2006 fue asesor de su Consejo " +
                "Directivo. Como actor recibió múltiples reconocimientos: cuatro premios Florencio, premio al mejor actor " +
                "extranjero del Festival de Miami y premio Mejor Actor de Cine 2008. Durante varios períodos fue directivo del " +
                "teatro El Galpón y dirigente de la Sociedad Uruguaya de Actores (SUA); integró también la Federación Uruguaya " +
                "de Teatros Independientes (FUTI). Formó parte del equipo de gestión de la refacción de los teatros La Máscara, " +
                "Astral y El Galpón, y del equipo de gestión en la construcción del teatro De la Candela y de la sala Atahualpa de El " +
                "Galpón.");
        controllerPro.altaProponente(dtoP3);

        DTOProponente dtoP4 = new DTOProponente();
        dtoP4.setNick("tabarec");
        dtoP4.setCorreo("tabare.car@agadu.org.uy");
        dtoP4.setNombre("Tabaré");
        dtoP4.setApellido("Cardozo");
        dtoP4.setContrasenia("Nada");
        dtoP4.setFechaNac(LocalDate.of(1971, 07, 24));
        dtoP4.setDirImagen("/imagenes/404.png");
        dtoP4.setDireccion("Santiago Rivas 1212");
        dtoP4.setLink("https://www.facebook.com/Tabar%C3%A9-Cardozo-55179094281/?ref=br_rs");
        dtoP4.setBiografia("Tabaré Cardozo (Montevideo, 24 de julio de 1971) es un cantante, compositor y murguista uruguayo; conocido por " +
                "su participación en la murga Agarrate Catalina, conjunto que fundó junto a su hermano Yamandú y Carlos " +
                "Tanco en el año 2001");
        controllerPro.altaProponente(dtoP4);

        DTOProponente dtoP5 = new DTOProponente();
        dtoP5.setNick("cachilas");
        dtoP5.setCorreo("Cachila.sil@c1080.org.uy");
        dtoP5.setNombre("Waldemar “Cachila”");
        dtoP5.setApellido("Silva");
        dtoP5.setContrasenia("Nada");
        dtoP5.setFechaNac(LocalDate.of(1947, 01, 01));
        dtoP5.setDirImagen("/imagenes/404.png");
        dtoP5.setDireccion("Br. Artigas 4567");
        dtoP5.setLink("https://www.facebook.com/C1080?ref=br_rs");
        dtoP5.setBiografia("Nace en el año 1947 en el conventillo “Medio Mundo” ubicado en pleno Barrio Sur. Es heredero parcialmente- " +
                "junto al resto de sus hermanos- de la Comparsa “Morenada” (inactiva desde el fallecimiento de Juan Ángel Silva), " +
                "en 1999 forma su propia Comparsa de negros y lubolos “Cuareim 1080”. Director responsable, compositor y " +
                "cantante de la misma.");
        controllerPro.altaProponente(dtoP5);

        DTOProponente dtoP6 = new DTOProponente();
        dtoP6.setNick("juliob");
        dtoP6.setCorreo("juliobocca@sodre.com.uy");
        dtoP6.setNombre("Julio");
        dtoP6.setApellido("Bocca");
        dtoP6.setContrasenia("Nada");
        dtoP6.setFechaNac(LocalDate.of(1967, 03, 16));
        dtoP6.setDirImagen("");
        dtoP6.setDireccion("Benito Blanco 4321");
        dtoP6.setLink("");
        dtoP6.setBiografia("");
        controllerPro.altaProponente(dtoP6);

        DTOProponente dtoP7 = new DTOProponente();
        dtoP7.setNick("diegop");
        dtoP7.setCorreo("diego@efectocine.com");
        dtoP7.setNombre("Diego");
        dtoP7.setApellido("Parodi");
        dtoP7.setContrasenia("Nada");
        dtoP7.setFechaNac(LocalDate.of(1975, 01, 01));
        dtoP7.setDirImagen("");
        dtoP7.setDireccion("Emilio Frugoni 1138 Ap. 02");
        dtoP7.setLink("http://www.efectocine.com");
        dtoP7.setBiografia("");
        controllerPro.altaProponente(dtoP7);

        DTOProponente dtoP8 = new DTOProponente();
        dtoP8.setNick("kairoh");
        dtoP8.setCorreo("kairoher@pilsenrock.com.uy");
        dtoP8.setNombre("Kairo");
        dtoP8.setApellido("Herrera");
        dtoP8.setContrasenia("Nada");
        dtoP8.setFechaNac(LocalDate.of(1840, 04, 25));
        dtoP8.setDirImagen("/imagenes/404.png");
        dtoP8.setDireccion("Paraguay 1423");
        dtoP8.setLink("");
        dtoP8.setBiografia("");
        controllerPro.altaProponente(dtoP8);

        DTOProponente dtoP9 = new DTOProponente();
        dtoP9.setNick("losBardo");
        dtoP9.setCorreo("losbardo@bardocientifico.com");
        dtoP9.setNombre("Los");
        dtoP9.setApellido("Bardo");
        dtoP9.setContrasenia("Nada");
        dtoP9.setFechaNac(LocalDate.of(1980, 10, 31));
        dtoP9.setDirImagen("/imagenes/losBardo.jpg");
        dtoP9.setDireccion("8 de Octubre 1429");
        dtoP9.setLink("https://bardocientifico.com/");
        dtoP9.setBiografia("Queremos ser vistos y reconocidos como una organización: referente en divulgación científica con un fuerte " +
                "espíritu didáctico y divertido, a través de acciones coordinadas con otros divulgadores científicos, que permitan " +
                "establecer puentes de comunicación. Impulsora en la generación de espacios de democratización y apropiación " +
                "social del conocimiento científico.");
        controllerPro.altaProponente(dtoP9);
        //PROPONENTES
        //

        //
        //COLABORADORES
        DTOColaborador dtoC = new DTOColaborador();
        dtoC.setNick("robinh");
        dtoC.setCorreo("Robin.h@tinglesa.com.uy");
        dtoC.setNombre("Robin");
        dtoC.setApellido("Henderson");
        dtoC.setContrasenia("Nada");
        dtoC.setFechaNac(LocalDate.of(1940, 8, 03));
        dtoC.setDirImagen("");
        controllerCol.altaColaborador(dtoC);

        DTOColaborador dtoC2 = new DTOColaborador();
        dtoC2.setNick("marcelot");
        dtoC2.setCorreo("marcelot@ideasdelsur.com.ar");
        dtoC2.setNombre("Marcelo");
        dtoC2.setApellido("Tinelli");
        dtoC2.setContrasenia("Nada");
        dtoC2.setFechaNac(LocalDate.of(1960, 04, 01));
        dtoC2.setDirImagen("/imagenes/404.png");
        controllerCol.altaColaborador(dtoC2);

        DTOColaborador dtoC3 = new DTOColaborador();
        dtoC3.setNick("novick");
        dtoC3.setCorreo("edgardo@novick.com.uy");
        dtoC3.setNombre("Edgardo");
        dtoC3.setApellido("Novick");
        dtoC3.setContrasenia("Nada");
        dtoC3.setFechaNac(LocalDate.of(1952, 07, 17));
        dtoC3.setDirImagen("/imagenes/404.png");
        controllerCol.altaColaborador(dtoC3);

        DTOColaborador dtoC4 = new DTOColaborador();
        dtoC4.setNick("sergiop");
        dtoC4.setCorreo("puglia@alpanpan.com.uy");
        dtoC4.setNombre("Sergio");
        dtoC4.setApellido("Puglia");
        dtoC4.setContrasenia("Nada");
        dtoC4.setFechaNac(LocalDate.of(1950, 01, 28));
        dtoC4.setDirImagen("/imagenes/sergiop.jpg");
        controllerCol.altaColaborador(dtoC4);

        DTOColaborador dtoC5 = new DTOColaborador();
        dtoC5.setNick("chino");
        dtoC5.setCorreo("chino@trico.org.uy");
        dtoC5.setNombre("Alvaro");
        dtoC5.setApellido("Recoba");
        dtoC5.setContrasenia("Nada");
        dtoC5.setFechaNac(LocalDate.of(1976, 03, 17));
        dtoC5.setDirImagen("");
        controllerCol.altaColaborador(dtoC5);

        DTOColaborador dtoC6 = new DTOColaborador();
        dtoC6.setNick("tonyp");
        dtoC6.setCorreo("eltony@manya.org.uy");
        dtoC6.setNombre("Antonio");
        dtoC6.setApellido("Pacheco");
        dtoC6.setContrasenia("Nada");
        dtoC6.setFechaNac(LocalDate.of(1955, 02, 14));
        dtoC6.setDirImagen("/imagenes/tonyp.jpg");
        controllerCol.altaColaborador(dtoC6);

        DTOColaborador dtoC7 = new DTOColaborador();
        dtoC7.setNick("nicoj");
        dtoC7.setCorreo("jodal@artech.com.uy");
        dtoC7.setNombre("Nicolás");
        dtoC7.setApellido("Jodal");
        dtoC7.setContrasenia("Nada");
        dtoC7.setFechaNac(LocalDate.of(1960, 8, 9));
        dtoC7.setDirImagen("/imagenes/nicoj.jpg");
        controllerCol.altaColaborador(dtoC7);

        DTOColaborador dtoC8 = new DTOColaborador();
        dtoC8.setNick("juanP");
        dtoC8.setCorreo("juanp@elpueblo.com");
        dtoC8.setNombre("Juan");
        dtoC8.setApellido("Perez");
        dtoC8.setContrasenia("Nada");
        dtoC8.setFechaNac(LocalDate.of(1970, 01, 01));
        dtoC8.setDirImagen("");
        controllerCol.altaColaborador(dtoC8);

        DTOColaborador dtoC9 = new DTOColaborador();
        dtoC9.setNick("Mengano");
        dtoC9.setCorreo("menganog@elpueblo.com");
        dtoC9.setNombre("Mengano");
        dtoC9.setApellido("Gómez");
        dtoC9.setContrasenia("Nada");
        dtoC9.setFechaNac(LocalDate.of(1982, 02, 02));
        dtoC9.setDirImagen("");
        controllerCol.altaColaborador(dtoC9);

        DTOColaborador dtoC10 = new DTOColaborador();
        dtoC10.setNick("Perengano");
        dtoC10.setCorreo("pere@elpueblo.com");
        dtoC10.setNombre("Perengano");
        dtoC10.setApellido("López");
        dtoC10.setContrasenia("Nada");
        dtoC10.setFechaNac(LocalDate.of(1985, 03, 03));
        dtoC10.setDirImagen("");
        controllerCol.altaColaborador(dtoC10);

        DTOColaborador dtoC11 = new DTOColaborador();
        dtoC11.setNick("Tiajaci");
        dtoC11.setCorreo("jacinta@elpueblo.com");
        dtoC11.setNombre("Tía");
        dtoC11.setApellido("Jacinta");
        dtoC11.setContrasenia("Nada");
        dtoC11.setFechaNac(LocalDate.of(1990, 04, 04));
        dtoC11.setDirImagen("");
        controllerCol.altaColaborador(dtoC11);
        //COLABORADORES
        //

        //45
        //SEGUIMIENTOS
        DTOSeguimiento dtoSegui = new DTOSeguimiento();

        DTOProponente dtoPP = controllerPro.obtenerProponente(dtoP.getNick());
        Proponente pro = new Proponente(dtoPP.getNick(), dtoPP.getNombre(), dtoPP.getApellido(), dtoPP.getContrasenia(), dtoPP.getCorreo(), dtoPP.getFechaNac(), dtoPP.getDirImagen(), dtoPP.getDireccion(), dtoPP.getBiografia(), dtoPP.getLink());

        dtoSegui.setUsuarioSeguidor(pro);
        dtoSegui.setUsuarioSeguido(dtoP3.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui);

        DTOSeguimiento dtoSegui2 = new DTOSeguimiento();

        dtoSegui2.setUsuarioSeguidor(pro);
        dtoSegui2.setUsuarioSeguido(dtoP7.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui2);

        DTOSeguimiento dtoSegui3 = new DTOSeguimiento();

        dtoSegui3.setUsuarioSeguidor(pro);
        dtoSegui3.setUsuarioSeguido(dtoP9.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui3);

        DTOSeguimiento dtoSegui4 = new DTOSeguimiento();

        DTOProponente dtoPP2 = controllerPro.obtenerProponente(dtoP2.getNick());
        Proponente pro2 = new Proponente(dtoPP2.getNick(), dtoPP2.getNombre(), dtoPP2.getApellido(), dtoPP2.getContrasenia(), dtoPP2.getCorreo(), dtoPP2.getFechaNac(), dtoPP2.getDirImagen(), dtoPP2.getDireccion(), dtoPP2.getBiografia(), dtoPP2.getLink());

        dtoSegui4.setUsuarioSeguidor(pro2);
        dtoSegui4.setUsuarioSeguido(dtoP4.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui4);

        DTOSeguimiento dtoSegui5 = new DTOSeguimiento();

        dtoSegui5.setUsuarioSeguidor(pro2);
        dtoSegui5.setUsuarioSeguido(dtoP5.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui5);

        DTOSeguimiento dtoSegui6 = new DTOSeguimiento();

        dtoSegui6.setUsuarioSeguidor(pro2);
        dtoSegui6.setUsuarioSeguido(dtoP8.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui6);

        DTOSeguimiento dtoSegui7 = new DTOSeguimiento();

        DTOProponente dtoPP3 = controllerPro.obtenerProponente(dtoP3.getNick());
        Proponente pro3 = new Proponente(dtoPP3.getNick(), dtoPP3.getNombre(), dtoPP3.getApellido(), dtoPP3.getContrasenia(), dtoPP3.getCorreo(), dtoPP3.getFechaNac(), dtoPP3.getDirImagen(), dtoPP3.getDireccion(), dtoPP3.getBiografia(), dtoPP3.getLink());

        dtoSegui7.setUsuarioSeguidor(pro3);
        dtoSegui7.setUsuarioSeguido(dtoP2.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui7);

        DTOSeguimiento dtoSegui8 = new DTOSeguimiento();

        dtoSegui8.setUsuarioSeguidor(pro3);
        dtoSegui8.setUsuarioSeguido(dtoP6.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui8);

        DTOSeguimiento dtoSegui9 = new DTOSeguimiento();

        DTOProponente dtoPP4 = controllerPro.obtenerProponente(dtoP4.getNick());
        Proponente pro4 = new Proponente(dtoPP4.getNick(), dtoPP4.getNombre(), dtoPP4.getApellido(), dtoPP4.getContrasenia(), dtoPP4.getCorreo(), dtoPP4.getFechaNac(), dtoPP4.getDirImagen(), dtoPP4.getDireccion(), dtoPP4.getBiografia(), dtoPP4.getLink());

        dtoSegui9.setUsuarioSeguidor(pro4);
        dtoSegui9.setUsuarioSeguido(dtoP.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui9);

        DTOSeguimiento dtoSegui10 = new DTOSeguimiento();

        dtoSegui10.setUsuarioSeguidor(pro4);
        dtoSegui10.setUsuarioSeguido(dtoP5.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui10);

        DTOSeguimiento dtoSegui11 = new DTOSeguimiento();

        DTOProponente dtoPP5 = controllerPro.obtenerProponente(dtoP5.getNick());
        Proponente pro5 = new Proponente(dtoPP5.getNick(), dtoPP5.getNombre(), dtoPP5.getApellido(), dtoPP5.getContrasenia(), dtoPP5.getCorreo(), dtoPP5.getFechaNac(), dtoPP5.getDirImagen(), dtoPP5.getDireccion(), dtoPP5.getBiografia(), dtoPP5.getLink());

        dtoSegui11.setUsuarioSeguidor(pro5);
        dtoSegui11.setUsuarioSeguido(dtoP.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui11);

        DTOSeguimiento dtoSegui12 = new DTOSeguimiento();

        DTOProponente dtoPP6 = controllerPro.obtenerProponente(dtoP6.getNick());
        Proponente pro6 = new Proponente(dtoPP6.getNick(), dtoPP6.getNombre(), dtoPP6.getApellido(), dtoPP6.getContrasenia(), dtoPP6.getCorreo(), dtoPP6.getFechaNac(), dtoPP6.getDirImagen(), dtoPP6.getDireccion(), dtoPP6.getBiografia(), dtoPP6.getLink());

        dtoSegui12.setUsuarioSeguidor(pro6);
        dtoSegui12.setUsuarioSeguido(dtoP2.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui12);

        DTOSeguimiento dtoSegui13 = new DTOSeguimiento();

        dtoSegui13.setUsuarioSeguidor(pro6);
        dtoSegui13.setUsuarioSeguido(dtoP7.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui13);

        DTOSeguimiento dtoSegui14 = new DTOSeguimiento();

        DTOProponente dtoPP7 = controllerPro.obtenerProponente(dtoP7.getNick());
        Proponente pro7 = new Proponente(dtoPP7.getNick(), dtoPP7.getNombre(), dtoPP7.getApellido(), dtoPP7.getContrasenia(), dtoPP7.getCorreo(), dtoPP7.getFechaNac(), dtoPP7.getDirImagen(), dtoPP7.getDireccion(), dtoPP7.getBiografia(), dtoPP7.getLink());

        dtoSegui14.setUsuarioSeguidor(pro7);
        dtoSegui14.setUsuarioSeguido(dtoP3.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui14);

        DTOSeguimiento dtoSegui15 = new DTOSeguimiento();

        dtoSegui15.setUsuarioSeguidor(pro7);
        dtoSegui15.setUsuarioSeguido(dtoP9.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui15);

        DTOSeguimiento dtoSegui16 = new DTOSeguimiento();

        DTOProponente dtoPP8 = controllerPro.obtenerProponente(dtoP8.getNick());
        Proponente pro8 = new Proponente(dtoPP8.getNick(), dtoPP8.getNombre(), dtoPP8.getApellido(), dtoPP8.getContrasenia(), dtoPP8.getCorreo(), dtoPP8.getFechaNac(), dtoPP8.getDirImagen(), dtoPP8.getDireccion(), dtoPP8.getBiografia(), dtoPP8.getLink());

        dtoSegui16.setUsuarioSeguidor(pro8);
        dtoSegui16.setUsuarioSeguido(dtoC4.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui16);

        DTOSeguimiento dtoSegui17 = new DTOSeguimiento();

        DTOProponente dtoPP9 = controllerPro.obtenerProponente(dtoP9.getNick());
        Proponente pro9 = new Proponente(dtoPP9.getNick(), dtoPP9.getNombre(), dtoPP9.getApellido(), dtoPP9.getContrasenia(), dtoPP9.getCorreo(), dtoPP9.getFechaNac(), dtoPP9.getDirImagen(), dtoPP9.getDireccion(), dtoPP9.getBiografia(), dtoPP9.getLink());

        dtoSegui17.setUsuarioSeguidor(pro9);
        dtoSegui17.setUsuarioSeguido(dtoP.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui17);

        DTOSeguimiento dtoSegui18 = new DTOSeguimiento();

        dtoSegui18.setUsuarioSeguidor(pro9);
        dtoSegui18.setUsuarioSeguido(dtoC7.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui18);

        DTOSeguimiento dtoSegui19 = new DTOSeguimiento();

        DTOColaborador CC = controllerCol.obtenerColaborador(dtoC.getNick());
        Colaborador col = new Colaborador(CC.getNick(), CC.getNombre(), CC.getApellido(), CC.getContrasenia(), CC.getCorreo(), CC.getFechaNac(), CC.getDirImagen());

        dtoSegui19.setUsuarioSeguidor(col);
        dtoSegui19.setUsuarioSeguido(dtoP3.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui19);

        DTOSeguimiento dtoSegui20 = new DTOSeguimiento();

        dtoSegui20.setUsuarioSeguidor(col);
        dtoSegui20.setUsuarioSeguido(dtoP6.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui20);

        DTOSeguimiento dtoSegui21 = new DTOSeguimiento();

        dtoSegui21.setUsuarioSeguidor(col);
        dtoSegui21.setUsuarioSeguido(dtoP7.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui21);

        DTOSeguimiento dtoSegui22 = new DTOSeguimiento();

        DTOColaborador CC2 = controllerCol.obtenerColaborador(dtoC2.getNick());
        Colaborador col2 = new Colaborador(CC2.getNick(), CC2.getNombre(), CC2.getApellido(), CC2.getContrasenia(), CC2.getCorreo(), CC2.getFechaNac(), CC2.getDirImagen());

        dtoSegui22.setUsuarioSeguidor(col2);
        dtoSegui22.setUsuarioSeguido(dtoP5.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui22);

        DTOSeguimiento dtoSegui23 = new DTOSeguimiento();

        dtoSegui23.setUsuarioSeguidor(col2);
        dtoSegui23.setUsuarioSeguido(dtoP6.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui23);

        DTOSeguimiento dtoSegui24 = new DTOSeguimiento();

        dtoSegui24.setUsuarioSeguidor(col2);
        dtoSegui24.setUsuarioSeguido(dtoP8.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui24);

        DTOSeguimiento dtoSegui25 = new DTOSeguimiento();

        DTOColaborador CC3 = controllerCol.obtenerColaborador(dtoC3.getNick());
        Colaborador col3 = new Colaborador(CC3.getNick(), CC3.getNombre(), CC3.getApellido(), CC3.getContrasenia(), CC3.getCorreo(), CC3.getFechaNac(), CC3.getDirImagen());

        dtoSegui25.setUsuarioSeguidor(col3);
        dtoSegui25.setUsuarioSeguido(dtoP.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui25);

        DTOSeguimiento dtoSegui26 = new DTOSeguimiento();

        dtoSegui26.setUsuarioSeguidor(col3);
        dtoSegui26.setUsuarioSeguido(dtoP4.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui26);

        DTOSeguimiento dtoSegui27 = new DTOSeguimiento();

        dtoSegui27.setUsuarioSeguidor(col3);
        dtoSegui27.setUsuarioSeguido(dtoP5.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui27);

        DTOSeguimiento dtoSegui28 = new DTOSeguimiento();

        DTOColaborador CC4 = controllerCol.obtenerColaborador(dtoC4.getNick());
        Colaborador col4 = new Colaborador(CC4.getNick(), CC4.getNombre(), CC4.getApellido(), CC4.getContrasenia(), CC4.getCorreo(), CC4.getFechaNac(), CC4.getDirImagen());

        dtoSegui28.setUsuarioSeguidor(col4);
        dtoSegui28.setUsuarioSeguido(dtoP2.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui28);

        DTOSeguimiento dtoSegui29 = new DTOSeguimiento();

        dtoSegui29.setUsuarioSeguidor(col4);
        dtoSegui29.setUsuarioSeguido(dtoP6.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui29);

        DTOSeguimiento dtoSegui30 = new DTOSeguimiento();

        dtoSegui30.setUsuarioSeguidor(col4);
        dtoSegui30.setUsuarioSeguido(dtoP7.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui30);

        DTOSeguimiento dtoSegui31 = new DTOSeguimiento();

        DTOColaborador CC5 = controllerCol.obtenerColaborador(dtoC5.getNick());
        Colaborador col5 = new Colaborador(CC5.getNick(), CC5.getNombre(), CC5.getApellido(), CC5.getContrasenia(), CC5.getCorreo(), CC5.getFechaNac(), CC5.getDirImagen());

        dtoSegui31.setUsuarioSeguidor(col5);
        dtoSegui31.setUsuarioSeguido(dtoC6.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui31);

        DTOSeguimiento dtoSegui32 = new DTOSeguimiento();

        DTOColaborador CC6 = controllerCol.obtenerColaborador(dtoC6.getNick());
        Colaborador col6 = new Colaborador(CC6.getNick(), CC6.getNombre(), CC6.getApellido(), CC6.getContrasenia(), CC6.getCorreo(), CC6.getFechaNac(), CC6.getDirImagen());

        dtoSegui32.setUsuarioSeguidor(col6);
        dtoSegui32.setUsuarioSeguido(dtoC5.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui32);

        DTOSeguimiento dtoSegui33 = new DTOSeguimiento();

        DTOColaborador CC7 = controllerCol.obtenerColaborador(dtoC7.getNick());
        Colaborador col7 = new Colaborador(CC7.getNick(), CC7.getNombre(), CC7.getApellido(), CC7.getContrasenia(), CC7.getCorreo(), CC7.getFechaNac(), CC7.getDirImagen());

        dtoSegui33.setUsuarioSeguidor(col7);
        dtoSegui33.setUsuarioSeguido(dtoP7.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui33);

        DTOSeguimiento dtoSegui34 = new DTOSeguimiento();

        dtoSegui34.setUsuarioSeguidor(col7);
        dtoSegui34.setUsuarioSeguido(dtoP9.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui34);

        DTOSeguimiento dtoSegui35 = new DTOSeguimiento();

        DTOColaborador CC8 = controllerCol.obtenerColaborador(dtoC8.getNick());
        Colaborador col8 = new Colaborador(CC8.getNick(), CC8.getNombre(), CC8.getApellido(), CC8.getContrasenia(), CC8.getCorreo(), CC8.getFechaNac(), CC8.getDirImagen());

        dtoSegui35.setUsuarioSeguidor(col8);
        dtoSegui35.setUsuarioSeguido(dtoP4.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui35);

        DTOSeguimiento dtoSegui36 = new DTOSeguimiento();

        dtoSegui36.setUsuarioSeguidor(col8);
        dtoSegui36.setUsuarioSeguido(dtoP5.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui36);

        DTOSeguimiento dtoSegui37 = new DTOSeguimiento();

        dtoSegui37.setUsuarioSeguidor(col8);
        dtoSegui37.setUsuarioSeguido(dtoP8.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui37);

        DTOSeguimiento dtoSegui38 = new DTOSeguimiento();

        DTOColaborador CC9 = controllerCol.obtenerColaborador(dtoC9.getNick());
        Colaborador col9 = new Colaborador(CC9.getNick(), CC9.getNombre(), CC9.getApellido(), CC9.getContrasenia(), CC9.getCorreo(), CC9.getFechaNac(), CC9.getDirImagen());

        dtoSegui38.setUsuarioSeguidor(col9);
        dtoSegui38.setUsuarioSeguido(dtoP3.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui38);

        DTOSeguimiento dtoSegui39 = new DTOSeguimiento();

        dtoSegui39.setUsuarioSeguidor(col9);
        dtoSegui39.setUsuarioSeguido(dtoP6.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui39);

        DTOSeguimiento dtoSegui40 = new DTOSeguimiento();

        dtoSegui40.setUsuarioSeguidor(col9);
        dtoSegui40.setUsuarioSeguido(dtoC5.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui40);

        DTOSeguimiento dtoSegui41 = new DTOSeguimiento();

        DTOColaborador CC10 = controllerCol.obtenerColaborador(dtoC10.getNick());
        Colaborador col10 = new Colaborador(CC10.getNick(), CC10.getNombre(), CC10.getApellido(), CC10.getContrasenia(), CC10.getCorreo(), CC10.getFechaNac(), CC10.getDirImagen());

        dtoSegui41.setUsuarioSeguidor(col10);
        dtoSegui41.setUsuarioSeguido(dtoP7.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui41);

        DTOSeguimiento dtoSegui42 = new DTOSeguimiento();

        dtoSegui42.setUsuarioSeguidor(col10);
        dtoSegui42.setUsuarioSeguido(dtoC6.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui42);

        DTOSeguimiento dtoSegui43 = new DTOSeguimiento();

        DTOColaborador CC11 = controllerCol.obtenerColaborador(dtoC11.getNick());
        Colaborador col11 = new Colaborador(CC11.getNick(), CC11.getNombre(), CC11.getApellido(), CC11.getContrasenia(), CC11.getCorreo(), CC11.getFechaNac(), CC11.getDirImagen());

        dtoSegui43.setUsuarioSeguidor(col11);
        dtoSegui43.setUsuarioSeguido(dtoP6.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui43);

        DTOSeguimiento dtoSegui44 = new DTOSeguimiento();

        dtoSegui44.setUsuarioSeguidor(col11);
        dtoSegui44.setUsuarioSeguido(dtoP8.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui44);

        DTOSeguimiento dtoSegui45 = new DTOSeguimiento();

        dtoSegui45.setUsuarioSeguidor(col11);
        dtoSegui45.setUsuarioSeguido(dtoC4.getNick());
        controllerSegui.registrarSeguimiento(dtoSegui45);
        //SEGUIMIENTOS
        //


// cats "raíces"
        controllerCat.altaCategoria(new DTOCategoria("Teatro", null));
        controllerCat.altaCategoria(new DTOCategoria("Literatura", null));
        controllerCat.altaCategoria(new DTOCategoria("Música", null));
        controllerCat.altaCategoria(new DTOCategoria("Cine", null));
        controllerCat.altaCategoria(new DTOCategoria("Danza", null));
        controllerCat.altaCategoria(new DTOCategoria("Carnaval", null));

// Recuperar cats
        List<Categoria> categoriasExistentes = controllerCat.listarCategoriasC();

        Categoria teatro = buscarCategoriaPorNombre(categoriasExistentes, "Teatro");
        Categoria musica = buscarCategoriaPorNombre(categoriasExistentes, "Música");
        Categoria cine = buscarCategoriaPorNombre(categoriasExistentes, "Cine");
        Categoria danza = buscarCategoriaPorNombre(categoriasExistentes, "Danza");
        Categoria carnaval = buscarCategoriaPorNombre(categoriasExistentes, "Carnaval");
        Categoria comedia = buscarCategoriaPorNombre(categoriasExistentes, "Comedia");

// Hijas de Teatro
        controllerCat.altaCategoria(new DTOCategoria("Teatro Dramático", teatro));
        controllerCat.altaCategoria(new DTOCategoria("Teatro Musical", teatro));
        controllerCat.altaCategoria(new DTOCategoria("Comedia", teatro));

// Hija de Comedia
        controllerCat.altaCategoria(new DTOCategoria("Stand-up", comedia));

// Hijas de Música
        controllerCat.altaCategoria(new DTOCategoria("Festival", musica));
        controllerCat.altaCategoria(new DTOCategoria("Concierto", musica));

// Hijas de Cine
        controllerCat.altaCategoria(new DTOCategoria("Cine al Aire Libre", cine));
        controllerCat.altaCategoria(new DTOCategoria("Cine a Pedal", cine));

// Hijas de Danza
        controllerCat.altaCategoria(new DTOCategoria("Ballet", danza));
        controllerCat.altaCategoria(new DTOCategoria("Flamenco", danza));

// Hijas de Carnaval
        controllerCat.altaCategoria(new DTOCategoria("Murga", carnaval));
        controllerCat.altaCategoria(new DTOCategoria("Humoristas", carnaval));
        controllerCat.altaCategoria(new DTOCategoria("Parodistas", carnaval));
        controllerCat.altaCategoria(new DTOCategoria("Lubolos", carnaval));
        controllerCat.altaCategoria(new DTOCategoria("Revista", carnaval));

        //

        //
        //PROPUESTAS

        DTOPropuesta dtoProp = new DTOPropuesta();
        dtoProp.setProponenteNick(dtoP7.getNick());
        dtoProp.setTitulo("Cine en el Botánico");
        dtoProp.setCategoriaNombre("Cine al Aire Libre");
        dtoProp.setFecha(LocalDate.of(2025, 9, 16));
        dtoProp.setLugar("Jardín Botánico");
        dtoProp.setPrecioEntrada(200);
        dtoProp.setMontoAReunir(150000);
        dtoProp.setRetornos(List.of(ETipoRetorno.PORCENTAJE_GANANCIAS));
        dtoProp.setDescripcion("El 16 de Diciembre a la hora 20 se proyectará la película \"Clever\", en el Jardín Botánico (Av. 19 de Abril 1181) en el marco " +
                "de las actividades realizadas por el ciclo Cultura al Aire Libre. El largometraje uruguayo de ficción Clever es dirigido por " +
                "Federico Borgia y Guillermo Madeiro. Es apto para mayores de 15 años.");
        dtoProp.setImagen("");
        dtoProp.setFechaPublicacion(LocalDate.now());
        controllerP.altaPropuesta(dtoProp);

        DTOPropuesta dtoProp2 = new DTOPropuesta();
        dtoProp2.setProponenteNick(dtoP.getNick());
        dtoProp2.setTitulo("Religiosamente");
        dtoProp2.setCategoriaNombre("Parodistas");
        dtoProp2.setFecha(LocalDate.of(2025, 10, 07));
        dtoProp2.setLugar("Teatro de Verano");
        dtoProp2.setPrecioEntrada(300);
        dtoProp2.setMontoAReunir(300000);
        dtoProp2.setRetornos(List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS));
        dtoProp2.setDescripcion("MOMOSAPIENS presenta \"Religiosamente\". Mediante dos parodias y un hilo conductor que aborda la temática de la " +
                "religión Momosapiens, mediante el humor y la reflexión, hilvana una historia que muestra al hombre inmerso en el tema " +
                "religioso. El libreto está escrito utilizando diferentes lenguajes de humor, dando una visión satírica y reflexiva desde " +
                "distintos puntos de vista, logrando mediante situaciones paródicas armar una propuesta plena de arte carnavalero.");
        dtoProp2.setImagen("/imagenes/404.png");
        dtoProp2.setFechaPublicacion(LocalDate.now());
        controllerP.altaPropuesta(dtoProp2);

        DTOPropuesta dtoProp3 = new DTOPropuesta();
        dtoProp3.setProponenteNick(dtoP2.getNick());
        dtoProp3.setTitulo("El Pimiento Indomable");
        dtoProp3.setCategoriaNombre("Concierto");
        dtoProp3.setFecha(LocalDate.of(2025, 10, 19));
        dtoProp3.setLugar("Teatro Solís");
        dtoProp3.setPrecioEntrada(400);
        dtoProp3.setMontoAReunir(400000);
        dtoProp3.setRetornos(List.of(ETipoRetorno.PORCENTAJE_GANANCIAS));
        dtoProp3.setDescripcion("El Pimiento Indomable, formación compuesta por Kiko Veneno y el uruguayo Martín Buscaglia, presentará este 19 de " +
                "Octubre, su primer trabajo. Bajo un título homónimo al del grupo, es un disco que según los propios protagonistas “no se " +
                "parece al de ninguno de los dos por separado. Entre los títulos que se podrán escuchar se encuentran “Nadador salvador”, " +
                "“América es más grande”, “Pescaito Enroscado” o “La reina del placer”.");
        dtoProp3.setImagen("/imagenes/elPimientoIndomable.jpg");
        dtoProp3.setFechaPublicacion(LocalDate.now());
        controllerP.altaPropuesta(dtoProp3);

        DTOPropuesta dtoProp4 = new DTOPropuesta();
        dtoProp4.setProponenteNick(dtoP8.getNick());
        dtoProp4.setTitulo("Pilsen Rock");
        dtoProp4.setCategoriaNombre("Festival");
        dtoProp4.setFecha(LocalDate.of(2025, 10, 21));
        dtoProp4.setLugar("Rural de Prado");
        dtoProp4.setPrecioEntrada(1000);
        dtoProp4.setMontoAReunir(900000);
        dtoProp4.setRetornos(List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS));
        dtoProp4.setDescripcion("La edición 2017 del Pilsen Rock se celebrará el 21 de Octubre en la Rural del Prado y contará con la participación de más " +
                "de 15 bandas nacionales. Quienes no puedan trasladarse al lugar, tendrán la posibilidad de disfrutar los shows a través de " +
                "Internet, así como entrevistas en vivo a los músicos una vez finalizados los conciertos.");
        dtoProp4.setImagen("/imagenes/pilsenRock.jpg");
        dtoProp4.setFechaPublicacion(LocalDate.now());
        controllerP.altaPropuesta(dtoProp4);

        DTOPropuesta dtoProp5 = new DTOPropuesta();
        dtoProp5.setProponenteNick(dtoP6.getNick());
        dtoProp5.setTitulo("Romeo y Julieta");
        dtoProp5.setCategoriaNombre("Ballet");
        dtoProp5.setFecha(LocalDate.of(2025, 11, 05));
        dtoProp5.setLugar("Rural de Prado");
        dtoProp5.setPrecioEntrada(800);
        dtoProp5.setMontoAReunir(750000);
        dtoProp5.setRetornos(List.of(ETipoRetorno.PORCENTAJE_GANANCIAS));
        dtoProp5.setDescripcion("Romeo y Julieta de Kenneth MacMillan, uno de los ballets favoritos del director artístico Julio Bocca, se presentará " +
                "nuevamente el 5 de Noviembre en el Auditorio Nacional del Sodre. Basada en la obra homónima de William Shakespeare, " +
                "Romeo y Julieta es considerada la coreografía maestra del MacMillan. La producción de vestuario y escenografía se realizó " +
                "en los Talleres del Auditorio Adela Reta, sobre los diseños originales.");
        dtoProp5.setImagen("/imagenes/404.png");
        dtoProp5.setFechaPublicacion(LocalDate.now());
        controllerP.altaPropuesta(dtoProp5);

        DTOPropuesta dtoProp6 = new DTOPropuesta();
        dtoProp6.setProponenteNick(dtoP4.getNick());
        dtoProp6.setTitulo("Un día de Julio");
        dtoProp6.setCategoriaNombre("Murga");
        dtoProp6.setFecha(LocalDate.of(2025, 11, 16));
        dtoProp6.setLugar("Landia");
        dtoProp6.setPrecioEntrada(650);
        dtoProp6.setMontoAReunir(300000);
        dtoProp6.setRetornos(List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS));
        dtoProp6.setDescripcion("La Catalina presenta el espectáculo \"Un Día de Julio\" en Landia. Un hombre misterioso y solitario vive encerrado entre las " +
                "cuatro paredes de su casa. Intenta, con sus teorías extravagantes, cambiar el mundo exterior que le resulta inhabitable. Un " +
                "día de Julio sucederá algo que cambiará su vida y la de su entorno para siempre.");
        dtoProp6.setImagen("/imagenes/unDiaDeJulio.jpg");
        dtoProp6.setFechaPublicacion(LocalDate.now());
        controllerP.altaPropuesta(dtoProp6);

        DTOPropuesta dtoProp7 = new DTOPropuesta();
        dtoProp7.setProponenteNick(dtoP3.getNick());
        dtoProp7.setTitulo("El Lazarillo de Tormes");
        dtoProp7.setCategoriaNombre("Teatro Dramático");
        dtoProp7.setFecha(LocalDate.of(2025, 12, 03));
        dtoProp7.setLugar("Teatro el Galpón");
        dtoProp7.setPrecioEntrada(350);
        dtoProp7.setMontoAReunir(175000);
        dtoProp7.setRetornos(List.of(ETipoRetorno.ENTRADAS_GRATIS));
        dtoProp7.setDescripcion("Vuelve unas de las producciones de El Galpón más aclamadas de los últimos tiempos. Esta obra se ha presentado en " +
                "Miami, Nueva York, Washington, México, Guadalajara, Río de Janeiro y La Habana. En nuestro país, El Lazarillo de " +
                "Tormes fue nominado en los rubros mejor espectáculo y mejor dirección a los Premios Florencio 1995, obteniendo su " +
                "protagonista Héctor Guido el Florencio a Mejor actor de ese año.");
        dtoProp7.setImagen("");
        dtoProp7.setFechaPublicacion(LocalDate.now());
        controllerP.altaPropuesta(dtoProp7);

        DTOPropuesta dtoProp8 = new DTOPropuesta();
        dtoProp8.setProponenteNick(dtoP9.getNick());
        dtoProp8.setTitulo("Bardo en la FING");
        dtoProp8.setCategoriaNombre("Stand-up");
        dtoProp8.setFecha(LocalDate.of(2025, 12, 10));
        dtoProp8.setLugar("Anfiteatro Edificio “José Luis Massera”");
        dtoProp8.setPrecioEntrada(200);
        dtoProp8.setMontoAReunir(100000);
        dtoProp8.setRetornos(List.of(ETipoRetorno.ENTRADAS_GRATIS));
        dtoProp8.setDescripcion("El 10 de Diciembre se presentará Bardo Científico en la FING. El humor puede ser usado como una herramienta " +
                "importante para el aprendizaje y la democratización de la ciencia, los monólogos científicos son una forma didáctica de " +
                "apropiación del conocimiento científico y contribuyen a que el público aprenda ciencia de forma amena. Los invitamos a " +
                "pasar un rato divertido, en un espacio en el cual aprenderán cosas de la ciencia que los sorprenderán. ¡Los esperamos!");
        dtoProp8.setImagen("");
        dtoProp8.setFechaPublicacion(LocalDate.now());
        controllerP.altaPropuesta(dtoProp8);
        //PROPUESTAS
        //

        //ESTADOS DE LAS PROP
        controllerP.asignarEstado("Cine en el Botánico", EEstadoPropuesta.INGRESADA, LocalDateTime.of(2025, 5, 15, 15, 30).toLocalDate());
        controllerP.asignarEstado("Cine en el Botánico", EEstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 5, 17, 8, 30).toLocalDate());
        controllerP.asignarEstado("Cine en el Botánico", EEstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2025, 5, 20, 14, 30).toLocalDate());

        DTOColaboracion dto = new DTOColaboracion();
        dto.setColaboradorNick(dtoC3.getNick());
        dto.setPropuestaTitulo(dtoProp.getTitulo());
        dto.setFecha(LocalDateTime.of(2025, 05, 20, 14, 30));
        dto.setMonto(50000);
        dto.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto);

        DTOColaboracion dto2 = new DTOColaboracion();
        dto2.setColaboradorNick(dtoC.getNick());
        dto2.setPropuestaTitulo(dtoProp.getTitulo());
        dto2.setFecha(LocalDateTime.of(2025, 05, 24, 17, 25));
        dto2.setMonto(50000);
        dto2.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto2);

        DTOColaboracion dto3 = new DTOColaboracion();
        dto3.setColaboradorNick(dtoC7.getNick());
        dto3.setPropuestaTitulo(dtoProp.getTitulo());
        dto3.setFecha(LocalDateTime.of(2025, 05, 30, 18, 30));
        dto3.setMonto(50000);
        dto3.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto3);

        controllerP.asignarEstado("Cine en el Botánico", EEstadoPropuesta.FINANCIADA, LocalDateTime.of(2025, 5, 30, 18, 30).toLocalDate());
        controllerP.asignarEstado("Cine en el Botánico", EEstadoPropuesta.CANCELADA, LocalDateTime.of(2025, 6, 15, 14, 50).toLocalDate());

        controllerP.asignarEstado("Religiosamente", EEstadoPropuesta.INGRESADA, LocalDateTime.of(2025, 6, 18, 4, 28).toLocalDate());
        controllerP.asignarEstado("Religiosamente", EEstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 6, 20, 4, 56).toLocalDate());
        controllerP.asignarEstado("Religiosamente", EEstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2025, 6, 30, 14, 25).toLocalDate());

        DTOColaboracion dto4 = new DTOColaboracion();
        dto4.setColaboradorNick(dtoC2.getNick());
        dto4.setPropuestaTitulo(dtoProp2.getTitulo());
        dto4.setFecha(LocalDateTime.of(2025, 06, 30, 14, 25));
        dto4.setMonto(200000);
        dto4.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto4);

        DTOColaboracion dto5 = new DTOColaboracion();
        dto5.setColaboradorNick(dtoC11.getNick());
        dto5.setPropuestaTitulo(dtoProp2.getTitulo());
        dto5.setFecha(LocalDateTime.of(2025, 07, 01, 18, 05));
        dto5.setMonto(500);
        dto5.setRetorno(ETipoRetorno.ENTRADAS_GRATIS);
        controllerC.registrarColaboracion(dto5);

        DTOColaboracion dto6 = new DTOColaboracion();
        dto6.setColaboradorNick(dtoC9.getNick());
        dto6.setPropuestaTitulo(dtoProp2.getTitulo());
        dto6.setFecha(LocalDateTime.of(2025, 07, 07, 17, 45));
        dto6.setMonto(600);
        dto6.setRetorno(ETipoRetorno.ENTRADAS_GRATIS);
        controllerC.registrarColaboracion(dto6);

        DTOColaboracion dto7 = new DTOColaboracion();
        dto7.setColaboradorNick(dtoC3.getNick());
        dto7.setPropuestaTitulo(dtoProp2.getTitulo());
        dto7.setFecha(LocalDateTime.of(2025, 07, 10, 14, 35));
        dto7.setMonto(50000);
        dto7.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto7);

        DTOColaboracion dto8 = new DTOColaboracion();
        dto8.setColaboradorNick(dtoC4.getNick());
        dto8.setPropuestaTitulo(dtoProp2.getTitulo());
        dto8.setFecha(LocalDateTime.of(2025, 07, 15, 9, 45));
        dto8.setMonto(50000);
        dto8.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto8);

        controllerP.asignarEstado("Religiosamente", EEstadoPropuesta.FINANCIADA, LocalDateTime.of(2025, 7, 15, 9, 45).toLocalDate());

        controllerP.asignarEstado("El Pimiento Indomable", EEstadoPropuesta.INGRESADA, LocalDateTime.of(2025, 7, 26, 15, 30).toLocalDate());
        controllerP.asignarEstado("El Pimiento Indomable", EEstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 7, 31, 8, 30).toLocalDate());
        controllerP.asignarEstado("El Pimiento Indomable", EEstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2025, 8, 1, 7, 40).toLocalDate());

        controllerP.asignarEstado("Pilsen Rock", EEstadoPropuesta.INGRESADA, LocalDateTime.of(2025, 7, 30, 15, 40).toLocalDate());
        controllerP.asignarEstado("Pilsen Rock", EEstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 8, 1, 14, 30).toLocalDate());
        controllerP.asignarEstado("Pilsen Rock", EEstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2025, 8, 5, 16, 50).toLocalDate());

        controllerP.asignarEstado("Romeo y Julieta", EEstadoPropuesta.INGRESADA, LocalDateTime.of(2025, 8, 4, 12, 20).toLocalDate());
        controllerP.asignarEstado("Romeo y Julieta", EEstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 8, 10, 10, 25).toLocalDate());
        controllerP.asignarEstado("Romeo y Julieta", EEstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2025, 8, 13, 4, 58).toLocalDate());

        controllerP.asignarEstado("Un día de Julio", EEstadoPropuesta.INGRESADA, LocalDateTime.of(2025, 8, 6, 2, 0).toLocalDate());
        controllerP.asignarEstado("Un día de Julio", EEstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 8, 12, 4, 50).toLocalDate());
        controllerP.asignarEstado("Un día de Julio", EEstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2025, 8, 15, 4, 48).toLocalDate());

        controllerP.asignarEstado("El Lazarillo de Tormes", EEstadoPropuesta.INGRESADA, LocalDateTime.of(2025, 8, 18, 2, 40).toLocalDate());
        controllerP.asignarEstado("El Lazarillo de Tormes", EEstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 8, 20, 21, 58).toLocalDate());

        controllerP.asignarEstado("Bardo en la FING", EEstadoPropuesta.INGRESADA, LocalDateTime.of(2025, 8, 23, 2, 12).toLocalDate());
        //

        //
        //COLABORACIONES

        DTOColaboracion dto9 = new DTOColaboracion();
        dto9.setColaboradorNick(dtoC2.getNick());
        dto9.setPropuestaTitulo(dtoProp3.getTitulo());
        dto9.setFecha(LocalDateTime.of(2025, 8, 01, 07, 40));
        dto9.setMonto(200000);
        dto9.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto9);

        DTOColaboracion dto10 = new DTOColaboracion();
        dto10.setColaboradorNick(dtoC4.getNick());
        dto10.setPropuestaTitulo(dtoProp3.getTitulo());
        dto10.setFecha(LocalDateTime.of(2025, 8, 03, 9, 25));
        dto10.setMonto(80000);
        dto10.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto10);

        DTOColaboracion dto11 = new DTOColaboracion();
        dto11.setColaboradorNick(dtoC5.getNick());
        dto11.setPropuestaTitulo(dtoProp4.getTitulo());
        dto11.setFecha(LocalDateTime.of(2025, 8, 05, 16, 50));
        dto11.setMonto(50000);
        dto11.setRetorno(ETipoRetorno.ENTRADAS_GRATIS);
        controllerC.registrarColaboracion(dto11);

        DTOColaboracion dto12 = new DTOColaboracion();
        dto12.setColaboradorNick(dtoC3.getNick());
        dto12.setPropuestaTitulo(dtoProp4.getTitulo());
        dto12.setFecha(LocalDateTime.of(2025, 8, 10, 15, 50));
        dto12.setMonto(120000);
        dto12.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto12);

        DTOColaboracion dto13 = new DTOColaboracion();
        dto13.setColaboradorNick(dtoC6.getNick());
        dto13.setPropuestaTitulo(dtoProp4.getTitulo());
        dto13.setFecha(LocalDateTime.of(2025, 8, 15, 19, 30));
        dto13.setMonto(120000);
        dto13.setRetorno(ETipoRetorno.ENTRADAS_GRATIS);
        controllerC.registrarColaboracion(dto13);

        DTOColaboracion dto14 = new DTOColaboracion();
        dto14.setColaboradorNick(dtoC4.getNick());
        dto14.setPropuestaTitulo(dtoProp5.getTitulo());
        dto14.setFecha(LocalDateTime.of(2025, 8, 13, 04, 58));
        dto14.setMonto(100000);
        dto14.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto14);

        DTOColaboracion dto15 = new DTOColaboracion();
        dto15.setColaboradorNick(dtoC2.getNick());
        dto15.setPropuestaTitulo(dtoProp5.getTitulo());
        dto15.setFecha(LocalDateTime.of(2025, 8, 14, 11, 25));
        dto15.setMonto(200000);
        dto15.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto15);

        DTOColaboracion dto16 = new DTOColaboracion();
        dto16.setColaboradorNick(dtoC6.getNick());
        dto16.setPropuestaTitulo(dtoProp6.getTitulo());
        dto16.setFecha(LocalDateTime.of(2025, 8, 15, 04, 48));
        dto16.setMonto(30000);
        dto16.setRetorno(ETipoRetorno.ENTRADAS_GRATIS);
        controllerC.registrarColaboracion(dto16);

        DTOColaboracion dto17 = new DTOColaboracion();
        dto17.setColaboradorNick(dtoC2.getNick());
        dto17.setPropuestaTitulo(dtoProp6.getTitulo());
        dto17.setFecha(LocalDateTime.of(2025, 8, 17, 15, 30));
        dto17.setMonto(150000);
        dto17.setRetorno(ETipoRetorno.PORCENTAJE_GANANCIAS);
        controllerC.registrarColaboracion(dto17);
        //COLABORACIONES
        //

    }
    //si no hago esto no me funciona porque al pasarle una categoria con el string no basta je
    private Categoria buscarCategoriaPorNombre(List<Categoria> lista, String nombre) {
        return lista.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    private static void copiarImagenDeResources(String resourcePath, String destino) {
        try (InputStream is = DatosDePrueba.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is != null) {
                File destFile = new File(System.getProperty("user.dir"), destino);
                destFile.getParentFile().mkdirs(); // crea carpeta "imagenes" si no existe
                Files.copy(is, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("✅ Imagen copiada: " + destFile.getAbsolutePath());
            } else {
                System.out.println("⚠ No se encontró resource: " + resourcePath);
            }
        } catch (IOException e) {
            System.out.println("⚠ Error copiando " + resourcePath + ": " + e.getMessage());
        }
    }


}
