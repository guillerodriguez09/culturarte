import com.culturarte.logica.controllers.SeguimientoController;
import com.culturarte.logica.clases.Colaborador;
import com.culturarte.logica.clases.Proponente;
import com.culturarte.logica.clases.Seguimiento;
import com.culturarte.logica.clases.Usuario;
import com.culturarte.logica.dtos.DTOUsuario;
import com.culturarte.logica.dtos.DTOSeguimiento;
import com.culturarte.persistencia.SeguimientoDAO;
import com.culturarte.persistencia.ColaboradorDAO;
import com.culturarte.persistencia.ProponenteDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeguimientoControllerTest {

    @Mock
    private SeguimientoDAO seguimientoDAO;

    @Mock
    private ColaboradorDAO colaboradorDAO;

    @Mock
    private ProponenteDAO proponenteDAO;

    private SeguimientoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new SeguimientoController(seguimientoDAO, colaboradorDAO, proponenteDAO);
    }


    @Test
    void registrarSeguimiento_DtoNull() {
        assertThrows(IllegalArgumentException.class, () ->
                controller.registrarSeguimiento(null));
    }

    @Test
    void registrarSeguimiento_SeguidorNull() {
        DTOSeguimiento dto = new DTOSeguimiento(null, "A");
        assertThrows(IllegalArgumentException.class, () ->
                controller.registrarSeguimiento(dto));
    }

    @Test
    void registrarSeguimiento_SeguidoNull() {
        DTOUsuario user = new DTOUsuario() {{ setNick("nick1"); }};
        DTOSeguimiento dto = new DTOSeguimiento(user, null);

        assertThrows(IllegalArgumentException.class, () ->
                controller.registrarSeguimiento(dto));
    }

    @Test
    void registrarSeguimiento_YaExiste() {
        DTOUsuario user = new DTOUsuario() {{ setNick("nick1"); }};
        DTOSeguimiento dto = new DTOSeguimiento(user, "otro");

        when(seguimientoDAO.existe("nick1", "otro")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                controller.registrarSeguimiento(dto));
    }

    @Test
    void registrarSeguimiento_UsuarioNoExiste() {
        DTOUsuario usr = new DTOUsuario() {{ setNick("nickX"); }};
        DTOSeguimiento dto = new DTOSeguimiento(usr, "otro");

        when(seguimientoDAO.existe("nickX", "otro")).thenReturn(false);
        when(colaboradorDAO.buscarPorNick("nickX")).thenReturn(null);
        when(proponenteDAO.buscarPorNick("nickX")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                controller.registrarSeguimiento(dto));
    }

    @Test
    void registrarSeguimiento_ComoColaborador() {
        DTOUsuario dtoU = new DTOUsuario() {{ setNick("pepe"); }};
        DTOSeguimiento dto = new DTOSeguimiento(dtoU, "otro");

        Colaborador colab = mock(Colaborador.class);
        when(colab.getNick()).thenReturn("pepe");

        when(seguimientoDAO.existe("pepe", "otro")).thenReturn(false);
        when(colaboradorDAO.buscarPorNick("pepe")).thenReturn(colab);
        when(proponenteDAO.buscarPorNick("pepe")).thenReturn(null);

        controller.registrarSeguimiento(dto);

        verify(seguimientoDAO, times(1)).guardar(any(Seguimiento.class));
    }

    @Test
    void registrarSeguimiento_ComoProponente() {
        DTOUsuario dtoU = new DTOUsuario() {{ setNick("juan"); }};
        DTOSeguimiento dto = new DTOSeguimiento(dtoU, "otro");

        Proponente prop = mock(Proponente.class);
        when(prop.getNick()).thenReturn("juan");

        when(seguimientoDAO.existe("juan", "otro")).thenReturn(false);
        when(colaboradorDAO.buscarPorNick("juan")).thenReturn(null);
        when(proponenteDAO.buscarPorNick("juan")).thenReturn(prop);

        controller.registrarSeguimiento(dto);

        verify(seguimientoDAO).guardar(any(Seguimiento.class));
    }


    @Test
    void conseguirId_Existe() {
        when(seguimientoDAO.conseguirId("a", "b")).thenReturn(5);

        int id = controller.conseguirId("a", "b");

        assertEquals(5, id);
    }

    @Test
    void conseguirId_NoExiste() {
        when(seguimientoDAO.conseguirId("a", "b")).thenReturn(0);

        assertThrows(IllegalArgumentException.class, () ->
                controller.conseguirId("a", "b"));
    }


    @Test
    void cancelarSeguimiento_NoExiste() {
        when(seguimientoDAO.buscarPorId(10)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                controller.cancelarSeguimiento(10));
    }

    @Test
    void cancelarSeguimiento_Exitoso() {
        Seguimiento seg = new Seguimiento(mock(Usuario.class), "otro");

        when(seguimientoDAO.buscarPorId(15)).thenReturn(seg);

        controller.cancelarSeguimiento(15);

        verify(seguimientoDAO).eliminar(seg);
    }


    @Test
    void listarSeguidosDeNick_Test() {
        Seguimiento s1 = new Seguimiento(mock(Usuario.class), "p1");
        Seguimiento s2 = new Seguimiento(mock(Usuario.class), "p2");

        when(seguimientoDAO.obtenerTodosDeNick("nick"))
                .thenReturn(List.of(s1, s2));

        List<String> lista = controller.listarSeguidosDeNick("nick");

        assertEquals(List.of("p1", "p2"), lista);
    }


    @Test
    void listarSeguidoresDeNick_Test() {
        Usuario u1 = mock(Usuario.class);
        when(u1.getNick()).thenReturn("A");

        Usuario u2 = mock(Usuario.class);
        when(u2.getNick()).thenReturn("B");

        Seguimiento s1 = new Seguimiento(u1, "x");
        Seguimiento s2 = new Seguimiento(u2, "x");

        when(seguimientoDAO.obtenerSeguidoresDeNick("x"))
                .thenReturn(List.of(s1, s2));

        List<String> res = controller.listarSeguidoresDeNick("x");

        assertEquals(List.of("A", "B"), res);
    }


    @Test
    void listarSeguimientos_TestCompleto() {
        Proponente p = mock(Proponente.class);
        when(p.getNick()).thenReturn("p1");

        Colaborador c = mock(Colaborador.class);
        when(c.getNick()).thenReturn("c1");

        Seguimiento s1 = new Seguimiento(p, "x");
        Seguimiento s2 = new Seguimiento(c, "y");

        when(seguimientoDAO.obtenerTodos())
                .thenReturn(List.of(s1, s2));

        var lista = controller.listarSeguimientos();

        assertEquals(2, lista.size());
        assertEquals("p1", lista.get(0).getUsuarioSeguidor().getNick());
        assertEquals("c1", lista.get(1).getUsuarioSeguidor().getNick());
    }
}
