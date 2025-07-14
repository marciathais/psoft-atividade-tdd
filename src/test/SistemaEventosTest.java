package test;

import main.Evento;
import main.Participante;
import main.SistemaEventos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SistemaEventosTest {
    private SistemaEventos sistema;
    private Evento evento1;
    private Evento evento2;
    private Participante participante1;
    private Participante participante2;

    @BeforeEach
    void setUp() {
        sistema = new SistemaEventos();

        evento1 = new Evento(1, "Conferência de Tecnologia", LocalDate.of(2025, 8, 15), "Centro de Convenções", 100);
        evento2 = new Evento(2, "Workshop de Inovação", LocalDate.of(2025, 9, 10), "Parque Tecnológico", 50);

        participante1 = new Participante("111.111.111-11", "Alice Silva");
        participante2 = new Participante("222.222.222-22", "Bruno Costa");

        sistema.cadastrarEvento(evento1);
        sistema.cadastrarEvento(evento2);
    }

    // Testes para US1: Cadastro e Busca de Eventos 

    @Test
    void testCadastrarNovoEvento() {
        Evento novoEvento = new Evento(3, "Feira de Carreira", LocalDate.of(2025, 10, 5), "Universidade", 200);
        sistema.cadastrarEvento(novoEvento);
        assertNotNull(sistema.buscarEventosPorNome("Feira de Carreira"));
        assertEquals(1, sistema.buscarEventosPorNome("Feira de Carreira").size());
    }

    @Test
    void testCadastrarEventoComIdDuplicadoDeveLancarExcecao() {
        // Tentando cadastrar evento com o mesmo ID do evento1
        Evento eventoDuplicado = new Evento(1, "Outro Nome", LocalDate.of(2025, 8, 16), "Outro Local", 50);
        assertThrows(IllegalArgumentException.class, () -> sistema.cadastrarEvento(eventoDuplicado));
    }

    @Test
    void testBuscarEventosPorNomeCompleto() {
        List<Evento> encontrados = sistema.buscarEventosPorNome("Conferência de Tecnologia");
        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
        assertEquals("Conferência de Tecnologia", encontrados.get(0).getNome());
    }

    @Test
    void testBuscarEventosPorNomeParcial() {
        List<Evento> encontrados = sistema.buscarEventosPorNome("Tecnologia");
        assertNotNull(encontrados);
        assertEquals(1, encontrados.size()); // Apenas Conferência de Tecnologia
    }

    @Test
    void testBuscarEventosPorNomeCaseInsensitive() {
        List<Evento> encontrados = sistema.buscarEventosPorNome("tecnologia");
        assertNotNull(encontrados);
        assertEquals(1, encontrados.size());
    }

    @Test
    void testBuscarEventosNaoEncontradosDeveLancarExcecao() {
        assertThrows(RuntimeException.class, () -> sistema.buscarEventosPorNome("Evento Inexistente"));
    }

    @Test
    void testBuscarEventosComNomeVazioDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> sistema.buscarEventosPorNome(""));
        assertThrows(IllegalArgumentException.class, () -> sistema.buscarEventosPorNome(null));
    }

    // Testes para US2: Inscrição de Participantes e Controle de Vagas

    @Test
    void testInscreverParticipanteComSucesso() {
        sistema.inscreverParticipante(participante1.getCpf(), participante1.getNome(), evento1.getId());
        assertEquals(1, evento1.getParticipantesInscritos().size());
        assertTrue(evento1.getParticipantesInscritos().contains(participante1));
        assertEquals(99, evento1.getVagasDisponiveis()); // 100 - 1
    }

    @Test
    void testInscreverParticipanteEmEventoNaoEncontradoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> sistema.inscreverParticipante(participante1.getCpf(), participante1.getNome(), 999));
    }

    @Test
    void testInscreverParticipanteDuplicadoDeveLancarExcecao() {
        sistema.inscreverParticipante(participante1.getCpf(), participante1.getNome(), evento1.getId());
        assertThrows(IllegalArgumentException.class, () -> sistema.inscreverParticipante(participante1.getCpf(), participante1.getNome(), evento1.getId()));
        assertEquals(1, evento1.getParticipantesInscritos().size()); // Participante não deve ser adicionado novamente
    }

    @Test
    void testInscreverParticipanteAteAtingirCapacidadeMaximaDeveLancarExcecao() {
        Evento eventoPequeno = new Evento(3, "Mini Encontro", LocalDate.now(), "Sala", 1);
        sistema.cadastrarEvento(eventoPequeno);

        sistema.inscreverParticipante(participante1.getCpf(), participante1.getNome(), eventoPequeno.getId());
        assertEquals(0, eventoPequeno.getVagasDisponiveis()); // Vagas esgotadas

        assertThrows(IllegalArgumentException.class, () ->
                sistema.inscreverParticipante(participante2.getCpf(), participante2.getNome(), eventoPequeno.getId()));
    }

    // Testes para US3: Listagem e Gestão de Vagas

    @Test
    void testListarTodosEventosComEventosCadastrados() {
        assertEquals(2, sistema.listarTodosEventos().size());
    }

    @Test
    void testListarTodosEventosComSistemaVazioDeveLancarExcecao() {
        SistemaEventos sistemaVazio = new SistemaEventos();
        assertThrows(RuntimeException.class, () -> sistemaVazio.listarTodosEventos());
    }

    @Test
    void testListarEventosMostraVagasDisponiveisCorretamente() {
        // Inicialmente, evento1 tem 100 vagas, evento2 tem 50
        sistema.inscreverParticipante(participante1.getCpf(), participante1.getNome(), evento1.getId()); // 1 vaga ocupada no evento1

        List<Evento> todosEventos = new ArrayList<>(sistema.listarTodosEventos());

        Evento evento1Atualizado = todosEventos.stream()
                .filter(e -> e.getId() == evento1.getId())
                .findFirst()
                .orElseThrow();

        Evento evento2Atualizado = todosEventos.stream()
                .filter(e -> e.getId() == evento2.getId())
                .findFirst()
                .orElseThrow();

        assertEquals(99, evento1Atualizado.getVagasDisponiveis());
        assertEquals(50, evento2Atualizado.getVagasDisponiveis()); // Nenhuma inscrição
    }
}
