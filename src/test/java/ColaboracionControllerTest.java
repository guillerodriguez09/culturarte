import com.culturarte.logica.controllers.ColaboracionController;
import com.culturarte.logica.clases.*;
import com.culturarte.logica.dtos.*;
import com.culturarte.logica.enums.EEstadoPropuesta;
import com.culturarte.persistencia.ColaboracionDAO;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.PropuestaDAO;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests Unitarios - Controlador de Colaboraciones")
public class ColaboracionControllerTest {

    @InjectMocks
    private ColaboracionController controller;

    @Mock
    private ColaboracionDAO mockColabDAO;

    @Mock
    private PropuestaDAO mockPropuestaDAO;

    @Mock
    private ColaboradorDAO mockColaboradorDAO;

    private Propuesta propuesta;
    private Colaborador colaborador;

    @BeforeEach
    void setup() throws Exception {

        setPrivateField(controller, "colaboracionDAO", mockColabDAO);
        setPrivateField(controller, "propuestaDAO", mockPropuestaDAO);
        setPrivateField(controller, "colaboradorDAO", mockColaboradorDAO);

        Categoria cat = new Categoria();
        cat.setNombre("Música");

        propuesta = new Propuesta(
                cat,
                null,
                "RockFest",
                "desc",
                "Montevideo",
                LocalDate.now(),
                100,
                5000,
                LocalDate.now().plusDays(10),
                List.of(),
                ""
        );

        propuesta.setEstadoActual(new Estado(EEstadoPropuesta.PUBLICADA, LocalDate.now()));

        colaborador = new Colaborador(
                "juan",
                "Juan",
                "Perez",
                "123",
                "jp@gmail.com",
                LocalDate.now(),
                ""
        );
    }

    private static void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        Field f = obj.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(obj, value);
    }


    @Test
    @DisplayName("registrarColaboracion - Flujo Correcto")
    void testRegistrarColaboracion_OK() {

        DTOColaboracion dto = new DTOColaboracion();
        dto.monto = 1000;
        dto.retorno = null;
        dto.retorno = com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS;
        dto.colaboradorNick = "juan";
        dto.propuestaTitulo = "RockFest";

        when(mockPropuestaDAO.buscarPorTitulo("RockFest")).thenReturn(propuesta);
        when(mockColaboradorDAO.buscarPorNick("juan")).thenReturn(colaborador);
        when(mockColabDAO.existe("juan", "RockFest")).thenReturn(false);

        controller.registrarColaboracion(dto);

        verify(mockColabDAO).guardar(any(Colaboracion.class));
        verify(mockPropuestaDAO, atLeastOnce()).actualizar(propuesta);
        verify(mockColaboradorDAO).actualizar(colaborador);

        assertEquals(EEstadoPropuesta.EN_FINANCIACION, propuesta.getEstadoActual().getNombre());
    }


    @Test
    void testRegistrarColaboracion_PropuestaNoExiste() {

        DTOColaboracion dto = new DTOColaboracion();
        dto.monto = 100;
        dto.retorno = com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS;
        dto.colaboradorNick = "juan";
        dto.propuestaTitulo = "NoExiste";

        when(mockPropuestaDAO.buscarPorTitulo("NoExiste")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> controller.registrarColaboracion(dto));
    }


    @Test
    void testRegistrarColaboracion_ColaboradorNoExiste() {

        DTOColaboracion dto = new DTOColaboracion();
        dto.monto = 100;
        dto.retorno = com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS;
        dto.colaboradorNick = "x";
        dto.propuestaTitulo = "RockFest";

        when(mockPropuestaDAO.buscarPorTitulo("RockFest")).thenReturn(propuesta);
        when(mockColaboradorDAO.buscarPorNick("x")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> controller.registrarColaboracion(dto));
    }


    @Test
    void testRegistrarColaboracion_YaExiste() {

        DTOColaboracion dto = new DTOColaboracion();
        dto.monto = 100;
        dto.retorno = com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS;
        dto.colaboradorNick = "juan";
        dto.propuestaTitulo = "RockFest";

        when(mockPropuestaDAO.buscarPorTitulo("RockFest")).thenReturn(propuesta);
        when(mockColaboradorDAO.buscarPorNick("juan")).thenReturn(colaborador);
        when(mockColabDAO.existe("juan", "RockFest")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> controller.registrarColaboracion(dto));
    }


    @Test
    void testListarColaboraciones() {
        Colaboracion c = new Colaboracion(
                500,
                com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS,
                LocalDateTime.now(),
                propuesta,
                colaborador
        );
        c.setId(10);

        when(mockColabDAO.obtenerTodas()).thenReturn(List.of(c));

        List<DTOColabConsulta> out = controller.listarColaboraciones();

        assertEquals(1, out.size());
        assertEquals(10, out.get(0).getId());
        assertEquals("juan", out.get(0).getColaboradorNick());
        assertEquals("RockFest", out.get(0).getPropuestaNombre());
    }


    @Test
    void testConsultarColaboracionesPorColaborador() {

        Colaboracion c = new Colaboracion(
                500,
                com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS,
                LocalDateTime.now(),
                propuesta,
                colaborador
        );
        c.setId(50);

        colaborador.setColaboraciones(List.of(c));

        when(mockColaboradorDAO.buscarPorNick("juan")).thenReturn(colaborador);

        List<DTOColabConsulta> out = controller.consultarColaboracionesPorColaborador("juan");

        assertEquals(1, out.size());
        assertEquals(50, out.get(0).getId());
        assertEquals("RockFest", out.get(0).getPropuestaNombre());
    }

    @Test
    void testConsultarColaboracionesPorColaborador_NoExiste() {

        when(mockColaboradorDAO.buscarPorNick("x")).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> controller.consultarColaboracionesPorColaborador("x"));
    }


    @Test
    void testCancelarColaboracion() {

        Colaboracion col = new Colaboracion(
                500,
                com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS,
                LocalDateTime.now(),
                propuesta,
                colaborador
        );
        col.setId(3);

        propuesta.setColaboraciones(new ArrayList<>(List.of(col)));
        colaborador.setColaboraciones(new ArrayList<>(List.of(col)));

        when(mockColabDAO.buscarPorId(3)).thenReturn(col);

        controller.cancelarColaboracion(3);

        verify(mockColabDAO).eliminar(col);
        verify(mockPropuestaDAO).actualizar(propuesta);
        verify(mockColaboradorDAO).actualizar(colaborador);

        assertTrue(propuesta.getColaboraciones().isEmpty());
        assertTrue(colaborador.getColaboraciones().isEmpty());
    }

    @Test
    void testCancelarColaboracion_NoExiste() {

        when(mockColabDAO.buscarPorId(99)).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> controller.cancelarColaboracion(99));
    }


    @Test
    void testEmitirConstanciaPago_PrimeraVez() {

        Colaboracion col = new Colaboracion(
                500,
                com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS,
                LocalDateTime.now(),
                propuesta,
                colaborador
        );
        col.setId(200);
        col.setConstanciaEmitida(false);

        when(mockColabDAO.buscarPorId(200)).thenReturn(col);

        DTOConstanciaPago dto = controller.emitirConstanciaPago(200);

        verify(mockColabDAO).actualizar(col);
        assertTrue(col.getConstanciaEmitida());
        assertEquals("juan", dto.getColaboradorNick());
    }

    @Test
    void testEmitirConstanciaPago_YaEmitida() {

        Colaboracion col = new Colaboracion(
                100,
                com.culturarte.logica.enums.ETipoRetorno.ENTRADAS_GRATIS,
                LocalDateTime.now(),
                propuesta,
                colaborador
        );
        col.setId(201);
        col.setConstanciaEmitida(true);

        when(mockColabDAO.buscarPorId(201)).thenReturn(col);

        DTOConstanciaPago dto = controller.emitirConstanciaPago(201);

        verify(mockColabDAO, never()).actualizar(any());
        assertEquals("juan", dto.getColaboradorNick());
    }

    @Test
    void testEmitirConstanciaPago_NoExiste() {
        when(mockColabDAO.buscarPorId(5)).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> controller.emitirConstanciaPago(5));
    }
}
