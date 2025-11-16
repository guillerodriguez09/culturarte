import com.culturarte.logica.clases.*;
import com.culturarte.logica.controllers.ColaboradorController;
import com.culturarte.logica.dtos.DTOColPropu;
import com.culturarte.logica.dtos.DTOColaborador;
import com.culturarte.logica.enums.ETipoRetorno;
import com.culturarte.logica.fabrica.Fabrica;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.logica.controllers.IColaboradorController;
import com.culturarte.persistencia.ColaboradorDAO;
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
@DisplayName("Tests Unitarios - Controlador de Colaborador")
public class ColaboradorControllerTest {

    @Mock
    private ColaboradorDAO colaboradorDAO;

    ColaboradorController controllerCol;

    @BeforeEach
    void setUp(){
        controllerCol = new ColaboradorController(colaboradorDAO);
    }

    @Test
    @DisplayName("Test - Alta Colaborador")
    public void testAltaColaborador() {

        DTOColaborador dtoC = new DTOColaborador();
        dtoC.setNick("Violencia Rivas");
        dtoC.setNombre("Violencia");
        dtoC.setApellido("Rivas");
        dtoC.setContrasenia("Nada");
        dtoC.setCorreo("violenciaRivas@gmail.com");
        dtoC.setFechaNac(LocalDate.of(1969, 7, 11));
        dtoC.setDirImagen("/imagenes/404.png");

        // El nickname no existe
        when(colaboradorDAO.existe("Violencia Rivas")).thenReturn(false);

        controllerCol.altaColaborador(dtoC);

        //Verificar que se chequeó si existe
        verify(colaboradorDAO).existe("Violencia Rivas");

        //Verificar que se guardó un objeto con esos datos
        verify(colaboradorDAO).guardar(argThat(col ->
                col.getNick().equals("Violencia Rivas") &&
                        col.getNombre().equals("Violencia") &&
                        col.getApellido().equals("Rivas") &&
                        col.getCorreo().equals("violenciaRivas@gmail.com")
        ));
    }

    @Test
    @DisplayName("Test - Alta Colaborador, ya existente")
    public void testAltaColaborador_Existente() {
        DTOColaborador dtoC = new DTOColaborador();
        dtoC.setNick("Violencia Rivas");
        dtoC.setNombre("Violencia");
        dtoC.setApellido("Rivas");
        dtoC.setContrasenia("Nada");
        dtoC.setCorreo("violenciaRivas@gmail.com");
        dtoC.setFechaNac(LocalDate.of(1969, 7, 11));
        dtoC.setDirImagen("/imagenes/404.png");

        when(colaboradorDAO.existe("Violencia Rivas")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            controllerCol.altaColaborador(dtoC);
        });

        verify(colaboradorDAO, never()).guardar(any());
    }

    @Test
    @DisplayName("Test - Listar Nicks Colaboradores")
    void testListarNicksColaboradores() {

        //Simulamos que el DAO devuelve una lista de Proponentes
        Colaborador c1 = new Colaborador("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", LocalDate.now(), "");
        Colaborador c2 = new Colaborador("Caffa", "Angel", "Taque", "Nada", "angelCaffa@gmail.com", LocalDate.now(), "");

        List<Colaborador> listaColaboradores = List.of(c1, c2);

        when(colaboradorDAO.obtenerTodos()).thenReturn(listaColaboradores);

        List<String> resultado = controllerCol.listarColaboradores();

        //Chequeo que me esta devolvioendo las cosas correctas
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0));
        assertEquals("Caffa", resultado.get(1));

        // Verifico que el DAO fue llamado exactamente una vez
        verify(colaboradorDAO, times(1)).obtenerTodos();
    }

    @Test
    @DisplayName("Test - Listar Nicks Colaboradores, vacío")
    void testListarNicksColaboradores_Vacio() {

        when(colaboradorDAO.obtenerTodos()).thenReturn(List.of());

        List<String> resultado = controllerCol.listarColaboradores();

        assertTrue(resultado.isEmpty());
        verify(colaboradorDAO).obtenerTodos();
    }

    @Test
    @DisplayName("Test - Listar Todos los Colaboradores")
    void testListarTodosColaboradores() {

        LocalDate fecha = LocalDate.now();

        Colaborador c1 = new Colaborador("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", LocalDate.now(), "");
        Colaborador c2 = new Colaborador("Caffa", "Angel", "Taque", "Nada", "angelCaffa@gmail.com", LocalDate.now(), "");

        when(colaboradorDAO.obtenerTodos()).thenReturn(List.of(c1, c2));

        List<DTOColaborador> resultado = controllerCol.listarTodos();

        assertEquals(2, resultado.size());

        DTOColaborador dto1 = resultado.get(0);
        assertEquals("Juan", dto1.getNick());
        assertEquals("Juan", dto1.getNombre());
        assertEquals("Caffa", dto1.getApellido());
        assertEquals("juanTaque@gmail.com", dto1.getCorreo());
        assertEquals(fecha, dto1.getFechaNac());

        DTOColaborador dto2 = resultado.get(1);
        assertEquals("Caffa", dto2.getNick());
        assertEquals("Angel", dto2.getNombre());
        assertEquals("Taque", dto2.getApellido());
        assertEquals("angelCaffa@gmail.com", dto2.getCorreo());
        assertEquals(fecha, dto2.getFechaNac());

        verify(colaboradorDAO).obtenerTodos();
    }

    @Test
    @DisplayName("Test - Listar Todos los Colaboradores, vacío")
    void testListarTodosColaboradores_Vacio() {

        // DAO devuelve lista vacía
        when(colaboradorDAO.obtenerTodos()).thenReturn(List.of());

        List<DTOColaborador> resultado = controllerCol.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(colaboradorDAO).obtenerTodos();
    }

    @Test
    @DisplayName("Test - Obtener Colaborador Con Propuestas")
    void testObtenerTodColConPropu() {
        LocalDate fecha = LocalDate.now();
        LocalDate fecha2 = LocalDate.now(); //La sequela

        Colaborador c1 = new Colaborador("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", LocalDate.now(), "");

        Proponente p1 = new Proponente("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "", "Si", "", "");

        Categoria cat = new Categoria();
        cat.setNombre("Música");

        Propuesta p = new Propuesta(cat, p1, "MonaLisa", "Una mona que es lisa", "TeatroSolis", fecha, 100, 5000, fecha2, List.of(ETipoRetorno.ENTRADAS_GRATIS, ETipoRetorno.PORCENTAJE_GANANCIAS),"/imagenes/404.png");

        Colaboracion c = new Colaboracion(50000, ETipoRetorno.PORCENTAJE_GANANCIAS , LocalDateTime.of(2025, 05, 20, 14, 30) , p, c1 );

        p.addColaboracion(c);

        // Simulación fila: { Colaborador, cualquier cosa, Propuesta }
        Object[] BBBB = new Object[]{ c1, null, p };
        List<Object[]> filas = new ArrayList<>();

        filas.add(BBBB);

        when(colaboradorDAO.obtenerTodColConPropu("Juan")).thenReturn(filas);

        List<DTOColPropu> resultado = controllerCol.obtenerTodColConPropu("Juan");

        assertEquals(1, resultado.size());
        DTOColPropu pack = resultado.get(0);

        // Verificar DTOColaborador
        assertEquals("Juan", pack.getColaborador().getNick());

        // Verificar que tiene 1 propuesta
        assertEquals(1, pack.getPropuestas().size());
        assertEquals("MonaLisa", pack.getPropuestas().get(0).titulo);

        verify(colaboradorDAO).obtenerTodColConPropu("Juan");
    }

    @Test
    @DisplayName("Test - Obtener Colaborador Con Propuestas, vacío")
    void testObtenerColConPropu_Vacio() {

        when(colaboradorDAO.obtenerTodColConPropu("Juan"))
                .thenReturn(List.of());

        List<DTOColPropu> resultado = controllerCol.obtenerTodColConPropu("Juan");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(colaboradorDAO).obtenerTodColConPropu("Juan");
    }

    @Test
    @DisplayName("Test - Obtener Colaborador")
    void testObtenerColaborador() {

        LocalDate fecha = LocalDate.now();

        Colaborador c1 = new Colaborador("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "");

        when(colaboradorDAO.buscarPorNick("Juan")).thenReturn(c1);

        DTOColaborador dto = controllerCol.obtenerColaborador("Juan");

        assertNotNull(dto);
        assertEquals("Juan", dto.getNick());
        assertEquals("Juan", dto.getNombre());
        assertEquals("Caffa", dto.getApellido());
        assertEquals("Nada", dto.getContrasenia());
        assertEquals("juanTaque@gmail.com", dto.getCorreo());
        assertEquals(fecha, dto.getFechaNac());
        assertEquals("", dto.getDirImagen());

        verify(colaboradorDAO).buscarPorNick("Juan");
    }

    @Test
    @DisplayName("Test - Obtener Colaborador, no existe")
    void testObtenerColaborador_NoExiste() {

        when(colaboradorDAO.buscarPorNick("FreddyFazbear")).thenReturn(null);

        DTOColaborador resultado = controllerCol.obtenerColaborador("FreddyFazbear");

        assertNull(resultado);
        verify(colaboradorDAO).buscarPorNick("FreddyFazbear");
    }

    @Test
    @DisplayName("Test - Obtener Colaborador por Mail")
    void testObtenerColaboradorPorMail() {

        LocalDate fecha = LocalDate.now();

        Colaborador c1 = new Colaborador("Juan", "Juan", "Caffa", "Nada", "juanTaque@gmail.com", fecha, "");

        when(colaboradorDAO.buscarPorCorreo("juanTaque@gmail.com")).thenReturn(c1);

        DTOColaborador dto = controllerCol.obtenerColaboradorCorreo("juanTaque@gmail.com");

        assertNotNull(dto);
        assertEquals("Juan", dto.getNick());
        assertEquals("Juan", dto.getNombre());
        assertEquals("Caffa", dto.getApellido());
        assertEquals("Nada", dto.getContrasenia());
        assertEquals("juanTaque@gmail.com", dto.getCorreo());
        assertEquals(fecha, dto.getFechaNac());
        assertEquals("", dto.getDirImagen());

        verify(colaboradorDAO).buscarPorCorreo("juanTaque@gmail.com");
    }

    @Test
    @DisplayName("Test - Obtener Colaborador por Mail, no existe")
    void testObtenerColaboradorPorMail_NoExiste() {

        when(colaboradorDAO.buscarPorCorreo("FreddyFazbear")).thenReturn(null);

        DTOColaborador resultado = controllerCol.obtenerColaboradorCorreo("FreddyFazbear");

        assertNull(resultado);
        verify(colaboradorDAO).buscarPorCorreo("FreddyFazbear");
    }

    @Test
    @DisplayName("Test - Eliminar Colaborador")
    void testEliminarColaborador() {

        String resultado = controllerCol.eliminarColaborador("Juan");

        assertEquals("EXITO", resultado);

        verify(colaboradorDAO).eliminarColaborador(eq("Juan"), any(LocalDate.class));
    }

    @Test
    @DisplayName("Test - Eliminar Colaborador, fallo")
    void testEliminarColaborador_Fallo() {

        doThrow(new RuntimeException("E"))
                .when(colaboradorDAO)
                .eliminarColaborador(eq("Juan"), any(LocalDate.class));

        String resultado = controllerCol.eliminarColaborador("Juan");

        assertEquals("FALLO", resultado);

        verify(colaboradorDAO).eliminarColaborador(eq("Juan"), any(LocalDate.class));
    }

}
