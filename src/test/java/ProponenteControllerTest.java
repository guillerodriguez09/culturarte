import com.culturarte.logica.clases.*;
import com.culturarte.logica.controllers.ProponenteController;
import com.culturarte.logica.dtos.*;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.controllers.IProponenteController;
import com.culturarte.persistencia.ProponenteDAO;
import com.culturarte.persistencia.SeguimientoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - Controlador de Proponente")
public class ProponenteControllerTest {

    @Mock
    private ProponenteDAO proponenteDAO;

    @Mock
    SeguimientoDAO seguimientoDAO;

    ProponenteController controllerPro;

    @BeforeEach
    void setUp(){
        controllerPro = new ProponenteController(proponenteDAO, seguimientoDAO);
    }

    @Test
    @DisplayName("Test - Alta Proponente")
    public void testAltaProponente() {

        DTOProponente dtoP = new DTOProponente();
        dtoP.setNick("Violencia Rivas");
        dtoP.setNombre("Violencia");
        dtoP.setApellido("Rivas");
        dtoP.setContrasenia("Nada");
        dtoP.setCorreo("violenciaRivas@gmail.com");
        dtoP.setFechaNac(LocalDate.of(1969, 7, 11));
        dtoP.setDirImagen("/imagenes/404.png");
        dtoP.setDireccion("La carcel");
        dtoP.setBiografia("Inventora del punk");
        dtoP.setLink("https://www.youtube.com/watch?v=sitA91-zWm0");

        // El nickname no existe
        when(proponenteDAO.existe("Violencia Rivas")).thenReturn(false);

        controllerPro.altaProponente(dtoP);

        //Verificar que se chequeó si existe
        verify(proponenteDAO).existe("Violencia Rivas");

        //Verificar que se guardó un objeto con esos datos
        verify(proponenteDAO).guardar(argThat(pro ->
                pro.getNick().equals("Violencia Rivas") &&
                        pro.getNombre().equals("Violencia") &&
                        pro.getApellido().equals("Rivas") &&
                        pro.getCorreo().equals("violenciaRivas@gmail.com")
        ));
    }

    @Test
    @DisplayName("Test - Alta Proponente, ya existente")
    public void testAltaProponente_Existente() {
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

        when(proponenteDAO.existe("juliob")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            controllerPro.altaProponente(dtoP6);
        });

        verify(proponenteDAO, never()).guardar(any());
    }

    @Test
    @DisplayName("Test - Listar Nicks Proponentes")
    void testListarNicksProponentes() {

        //Simulamos que el DAO devuelve una lista de Proponentes
        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", LocalDate.now(), "", "Si", "", "");
        Proponente p2 = new Proponente("Caffa", "Angel", "Taque", "Nada", "angelCaffa@gmail.com", LocalDate.now(), "", "No", "", "");

        List<Proponente> listaProponentes = List.of(p1, p2);

        when(proponenteDAO.obtenerTodos()).thenReturn(listaProponentes);

        List<String> resultado = controllerPro.listarProponentes();

        //Chequeo que me esta devolvioendo las cosas correctas
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0));
        assertEquals("Caffa", resultado.get(1));

        // Verifico que el DAO fue llamado exactamente una vez
        verify(proponenteDAO, times(1)).obtenerTodos();
    }

    @Test
    @DisplayName("Test - Listar Nicks Proponentes, vacío")
    void testListarNicksProponentes_Vacio() {

        when(proponenteDAO.obtenerTodos()).thenReturn(List.of());

        List<String> resultado = controllerPro.listarProponentes();

        assertTrue(resultado.isEmpty());
        verify(proponenteDAO).obtenerTodos();
    }

    @Test
    @DisplayName("Test - Listar Todos los Proponentes")
    void testListarTodosProponente() {

        LocalDate fecha = LocalDate.now();

        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "", "Si", "", "");
        Proponente p2 = new Proponente("Caffa", "Angel", "Taque", "Nada", "angelCaffa@gmail.com", fecha, "", "No", "", "");

        when(proponenteDAO.obtenerTodos()).thenReturn(List.of(p1, p2));

        List<DTOProponente> resultado = controllerPro.listarTodosProponente();

        assertEquals(2, resultado.size());

        DTOProponente dto1 = resultado.get(0);
        assertEquals("Juan", dto1.getNick());
        assertEquals("Juan", dto1.getNombre());
        assertEquals("Caffa", dto1.getApellido());
        assertEquals("juanTaque@gmail.com", dto1.getCorreo());
        assertEquals(fecha, dto1.getFechaNac());
        assertEquals("Si", dto1.getDireccion());

        DTOProponente dto2 = resultado.get(1);
        assertEquals("Caffa", dto2.getNick());
        assertEquals("Angel", dto2.getNombre());
        assertEquals("Taque", dto2.getApellido());
        assertEquals("angelCaffa@gmail.com", dto2.getCorreo());
        assertEquals(fecha, dto2.getFechaNac());
        assertEquals("No", dto2.getDireccion());

        verify(proponenteDAO).obtenerTodos();
    }

    @Test
    @DisplayName("Test - Listar Todos los Proponentes, vacío")
    void testListarTodosProponente_Vacio() {

        // DAO devuelve lista vacía
        when(proponenteDAO.obtenerTodos()).thenReturn(List.of());

        List<DTOProponente> resultado = controllerPro.listarTodosProponente();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(proponenteDAO).obtenerTodos();
    }

    @Test
    @DisplayName("Test - Obtener Proponente")
    void testObtenerProponente() {

        LocalDate fecha = LocalDate.now();

        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "", "Si", "", "");

        when(proponenteDAO.buscarPorNick("Juan")).thenReturn(p1);

        DTOProponente dto = controllerPro.obtenerProponente("Juan");

        assertNotNull(dto);
        assertEquals("Juan", dto.getNick());
        assertEquals("Juan", dto.getNombre());
        assertEquals("Caffa", dto.getApellido());
        assertEquals("Nada", dto.getContrasenia());
        assertEquals("juanTaque@gmail.com", dto.getCorreo());
        assertEquals(fecha, dto.getFechaNac());
        assertEquals("", dto.getDirImagen());
        assertEquals("Si", dto.getDireccion());
        assertEquals("", dto.getBiografia());
        assertEquals("", dto.getLink());

        verify(proponenteDAO).buscarPorNick("Juan");
    }

    @Test
    @DisplayName("Test - Obtener Proponente, no existe")
    void testObtenerProponente_NoExiste() {

        when(proponenteDAO.buscarPorNick("FreddyFazbear")).thenReturn(null);

        DTOProponente resultado = controllerPro.obtenerProponente("FreddyFazbear");

        assertNull(resultado);
        verify(proponenteDAO).buscarPorNick("FreddyFazbear");
    }

    @Test
    @DisplayName("Test - Obtener Proponente por Mail")
    void testObtenerProponentePorMail() {

        LocalDate fecha = LocalDate.now();

        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "", "Si", "", "");

        when(proponenteDAO.buscarPorCorreo("juanTaque@gmail.com")).thenReturn(p1);

        DTOProponente dto = controllerPro.obtenerProponenteCorreo("juanTaque@gmail.com");

        assertNotNull(dto);
        assertEquals("Juan", dto.getNick());
        assertEquals("Juan", dto.getNombre());
        assertEquals("Caffa", dto.getApellido());
        assertEquals("Nada", dto.getContrasenia());
        assertEquals("juanTaque@gmail.com", dto.getCorreo());
        assertEquals(fecha, dto.getFechaNac());
        assertEquals("", dto.getDirImagen());
        assertEquals("Si", dto.getDireccion());
        assertEquals("", dto.getBiografia());
        assertEquals("", dto.getLink());

        verify(proponenteDAO).buscarPorCorreo("juanTaque@gmail.com");
    }

    @Test
    @DisplayName("Test - Obtener Proponente por Mail, no existe")
    void testObtenerProponentePorMail_NoExiste() {

        when(proponenteDAO.buscarPorCorreo("FreddyFazbear")).thenReturn(null);

        DTOProponente resultado = controllerPro.obtenerProponenteCorreo("FreddyFazbear");

        assertNull(resultado);
        verify(proponenteDAO).buscarPorCorreo("FreddyFazbear");
    }

    @Test
    @DisplayName("Test - Obtener Proponente con Propuestas, vacio")
    void testObtenerTodPropConPropu_Vacio() {
        when(proponenteDAO.obtenerTodPropConPropu("Thanos")).thenReturn(List.of());

        List<DTOPropoPropu> resultado = controllerPro.obtenerTodPropConPropu("Thanos");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(proponenteDAO).obtenerTodPropConPropu("Thanos");
    }

    @Test
    @DisplayName("Test - Obtener Proponente con Propuestas")
    void testObtenerTodPropConPropu() {
        LocalDate fecha = LocalDate.now();
        LocalDate fecha2 = LocalDate.now(); //La sequela

        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "", "Si", "", "");
        Colaborador c2 = new Colaborador("marcelot", "Marcelo", "Tinelli", "Nada", "marcelot@ideasdelsur.com.ar", fecha ,"/imagenes/404.png");

        // Propuesta + categoría + estado
        Categoria cat = new Categoria();
        cat.setNombre("Música");

        Estado est = new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now());

        Propuesta p = new Propuesta(cat, p1, "MonaLisa", "Una mona que es lisa", "TeatroSolis", fecha, 100, 5000, fecha2, List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS),"/imagenes/404.png");

        Colaboracion c = new Colaboracion(50000, ETipoRetorno.PORCENTAJE_GANANCIAS , LocalDateTime.of(2025, 05, 20, 14, 30) , p, c2 );

        p.addColaboracion(c);

        Object[] AAAA = new Object[]{p1, p};
        List<Object[]> filas = new ArrayList<>();

        filas.add(AAAA);

        when(proponenteDAO.obtenerTodPropConPropu("Juan")).thenReturn(filas);

        List<DTOPropoPropu> resultado = controllerPro.obtenerTodPropConPropu("Juan");

        assertEquals(1, resultado.size());
        DTOPropoPropu pack = resultado.get(0);

        assertEquals("Juan", pack.getProponente().getNick());
        assertEquals(1, pack.getPropuestas().size());
        assertEquals("MonaLisa", pack.getPropuestas().get(0).titulo);

        verify(proponenteDAO).obtenerTodPropConPropu("Juan");
    }

    @Test
    @DisplayName("Test - Obtener Proponente con Propuestas y Estado, vacío")
    void testObtenerPropConPropuYEstado_Vacio() {

        when(proponenteDAO.obtenerPropConPropuYEstado(EEstadoPropuesta.PUBLICADA, "Juan"))
                .thenReturn(List.of());

        List<Object[]> resultado = controllerPro.obtenerPropConPropuYEstado(EEstadoPropuesta.PUBLICADA, "Juan");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(proponenteDAO).obtenerPropConPropuYEstado(EEstadoPropuesta.PUBLICADA, "Juan");
    }

    @Test
    @DisplayName("Test - Obtener Proponente con Propuestas y Estado")
    void testObtenerPropConPropuYEstado() {

        LocalDate fecha = LocalDate.now();
        LocalDate fecha2 = LocalDate.now(); //La secuela

        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "", "Si", "", "");
        Colaborador c2 = new Colaborador("marcelot", "Marcelo", "Tinelli", "Nada", "marcelot@ideasdelsur.com.ar", fecha ,"/imagenes/404.png");

        Categoria cat = new Categoria();
        cat.setNombre("Música");

        Propuesta p = new Propuesta(cat, p1, "MonaLisa", "Una mona que es lisa", "TeatroSolis", fecha, 100, 5000, fecha2, List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS),"/imagenes/404.png");

        Colaboracion c = new Colaboracion(50000, ETipoRetorno.PORCENTAJE_GANANCIAS , LocalDateTime.of(2025, 05, 20, 14, 30) , p, c2 );

        p.addColaboracion(c);

        Object[] AAAA = new Object[]{p1, p};
        List<Object[]> filas = new ArrayList<>();

        filas.add(AAAA);

        when(proponenteDAO.obtenerPropConPropuYEstado(EEstadoPropuesta.PUBLICADA, "Juan")).thenReturn(filas);

        List<Object[]> resultado = controllerPro.obtenerPropConPropuYEstado(EEstadoPropuesta.PUBLICADA, "Juan");

        assertEquals(1, resultado.size());
        Object[] fila = resultado.get(0);

        assertEquals(2, fila.length);
        assertTrue(fila[0] instanceof DTOProponente);
        assertTrue(fila[1] instanceof DTOPropuesta);

        DTOProponente dtoP = (DTOProponente) fila[0];
        DTOPropuesta dtoPropu = (DTOPropuesta) fila[1];
        dtoPropu.estadoActual = "PUBLICADA";

        // DTOProponente
        assertEquals("Juan", dtoP.getNick());
        assertEquals("Juan", dtoP.getNombre());
        assertEquals("Caffa", dtoP.getApellido());
        assertEquals("juanTaque@gmail.com", dtoP.getCorreo());
        assertEquals("Si", dtoP.getDireccion());

        // DTOPropuesta
        assertEquals("MonaLisa", dtoPropu.titulo);
        assertEquals("PUBLICADA", dtoPropu.estadoActual);

        verify(proponenteDAO).obtenerPropConPropuYEstado(EEstadoPropuesta.PUBLICADA, "Juan");
    }

    @Test
    @DisplayName("Test - Eliminar proponente")
    void testEliminarProponente() {

        String resultado = controllerPro.eliminarProponente("Juan");

        assertEquals("EXITO", resultado);

        // Verificamos que se llamaron los 3 DAOs
        verify(proponenteDAO).eliminarProponente(eq("Juan"), any(LocalDate.class));
        verify(seguimientoDAO).eliminarTodosDeSeguidoor("Juan");
        verify(seguimientoDAO).eliminarTodosDeSeguido("Juan");
    }

    @Test
    @DisplayName("Test - Eliminar proponente, fallo")
    void testEliminarProponente_Fallo() {

        // Simulamos que proponenteDAO lanza una excepción
        doThrow(new RuntimeException("E"))
                .when(proponenteDAO)
                .eliminarProponente(eq("Juan"), any(LocalDate.class));

        String resultado = controllerPro.eliminarProponente("Juan");

        assertEquals("FALLO", resultado);

        // Verificamos que se llamó al primer DAO
        verify(proponenteDAO).eliminarProponente(eq("Juan"), any(LocalDate.class));

        // Y que NO se llamaron los otros, porque la excepción corta la ejecución
        verify(seguimientoDAO, never()).eliminarTodosDeSeguidoor(anyString());
        verify(seguimientoDAO, never()).eliminarTodosDeSeguido(anyString());
    }

    @Test
    @DisplayName("Test - Listar proponentes eliminados")
    void testListarProponentesElim() {

        LocalDate fecha = LocalDate.now();

        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "", "Si", "", "");
        Proponente p2 = new Proponente("Caffa", "Angel", "Taque", "Nada", "angelCaffa@gmail.com", fecha, "", "No", "", "");

        List<Proponente> lista = List.of(p1, p2);

        when(proponenteDAO.obtenerTodosElim()).thenReturn(lista);

        List<String> resultado = controllerPro.listarProponentesElim();

        assertEquals(2, resultado.size());
        assertEquals(List.of("Juan", "Caffa"), resultado);

        verify(proponenteDAO).obtenerTodosElim();
    }

    @Test
    @DisplayName("Test - Listar proponentes eliminados, vacío")
    void testListarProponentesElim_Vacio() {

        when(proponenteDAO.obtenerTodosElim()).thenReturn(List.of());

        List<String> resultado = controllerPro.listarProponentesElim();

        assertTrue(resultado.isEmpty());

        verify(proponenteDAO).obtenerTodosElim();
    }

    @Test
    @DisplayName("Test - Existe Proponente, por nick")
    void testExisteProponente_Nick() {

        when(proponenteDAO.existe("Juan")).thenReturn(true);

        boolean resultado = controllerPro.existeProponente("Juan");

        assertTrue(resultado);
        verify(proponenteDAO).existe("Juan");
        verify(proponenteDAO, never()).existeCorreo(anyString());
    }

    @Test
    @DisplayName("Test - Existe Proponente, por correo")
    void testExisteProponente_Correo() {

        when(proponenteDAO.existe("juanTaque@gmail.com")).thenReturn(false);
        when(proponenteDAO.existeCorreo("juanTaque@gmail.com")).thenReturn(true);

        boolean resultado = controllerPro.existeProponente("juanTaque@gmail.com");

        assertTrue(resultado);
        verify(proponenteDAO).existe("juanTaque@gmail.com");
        verify(proponenteDAO).existeCorreo("juanTaque@gmail.com");
    }

    @Test
    @DisplayName("Test - Existe Proponente, no existe")
    void testExisteProponente_False() {

        when(proponenteDAO.existe("No")).thenReturn(false);
        when(proponenteDAO.existeCorreo("No")).thenReturn(false);

        boolean resultado = controllerPro.existeProponente("No");

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Test - Obtener Proponente eliminado con Propuestas")
    void testObtenerTodPropConPropuDeEli() {
        LocalDate fecha = LocalDate.now();
        LocalDate fecha2 = LocalDate.now(); //La sequela

        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "", "Si", "", "");
        Colaborador c2 = new Colaborador("marcelot", "Marcelo", "Tinelli", "Nada", "marcelot@ideasdelsur.com.ar", fecha ,"/imagenes/404.png");

        // Propuesta + categoría + estado
        Categoria cat = new Categoria();
        cat.setNombre("Música");

        Propuesta p = new Propuesta(cat, p1, "MonaLisa", "Una mona que es lisa", "TeatroSolis", fecha, 100, 5000, fecha2, List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS),"/imagenes/404.png");

        Colaboracion c = new Colaboracion(50000, ETipoRetorno.PORCENTAJE_GANANCIAS , LocalDateTime.of(2025, 05, 20, 14, 30) , p, c2 );
        p.addColaboracion(c);

        Object[] AAAA = new Object[]{p1, p};
        List<Object[]> filas = new ArrayList<>();

        filas.add(AAAA);

        when(proponenteDAO.obtenerTodPropConPropuDeEli("Juan")).thenReturn(filas);

        List<Object[]> resultado = controllerPro.obtenerTodPropConPropuDeEli("Juan");

        assertEquals(1, resultado.size());
        Object[] fila = resultado.get(0);

        assertEquals(2, fila.length);
        assertTrue(fila[0] instanceof DTOProponente);
        assertTrue(fila[1] instanceof DTOPropuesta);

        DTOProponente dtoP = (DTOProponente) fila[0];
        DTOPropuesta dtoPropu = (DTOPropuesta) fila[1];

        // DTOProponente
        assertEquals("Juan", dtoP.getNick());
        assertEquals("Juan", dtoP.getNombre());
        assertEquals("Caffa", dtoP.getApellido());
        assertEquals("juanTaque@gmail.com", dtoP.getCorreo());
        assertEquals("Si", dtoP.getDireccion());

        // DTOPropuesta
        assertEquals("MonaLisa", dtoPropu.titulo);

        verify(proponenteDAO).obtenerTodPropConPropuDeEli( "Juan");
    }

    @Test
    @DisplayName("Test - Obtener Proponente eliminado con Propuestas, vacio")
    void testObtenerTodPropConPropuDeEli_Vacio() {

        when(proponenteDAO.obtenerTodPropConPropuDeEli("Juan"))
                .thenReturn(List.of());

        List<Object[]> resultado = controllerPro.obtenerTodPropConPropuDeEli("Juan");

        assertTrue(resultado.isEmpty());

        verify(proponenteDAO).obtenerTodPropConPropuDeEli("Juan");
    }

}