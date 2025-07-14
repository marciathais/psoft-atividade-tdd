package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SistemaEventos {
    private Map<Integer, Evento> eventos;

    public SistemaEventos() {
        this.eventos = new HashMap<>();
    }

    // US1: Cadastro de Eventos
    public void cadastrarEvento(Evento evento) {
        if (eventos.containsKey(evento.getId())) {
            throw new IllegalArgumentException("Evento com este ID já cadastrado.");
        }
        eventos.put(evento.getId(), evento);
    }

    // US1: Busca de Eventos
    public List<Evento> buscarEventosPorNome(String nomeBusca) {
        if (nomeBusca == null || nomeBusca.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome de busca não pode ser vazio.");
        }

        List<Evento> eventosEncontrados = eventos.values().stream()
                .filter(evento -> evento.getNome().toLowerCase().contains(nomeBusca.toLowerCase()))
                .collect(Collectors.toList());

        if (eventosEncontrados.isEmpty()) {
            throw new RuntimeException("Nenhum evento encontrado com o nome: " + nomeBusca);
        }
        return eventosEncontrados;
    }

    // US2: Inscrição de Participantes
    public void inscreverParticipante(String cpf, String nome, int idEvento) {
        Evento evento = eventos.get(idEvento);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado com o ID: " + idEvento);
        }

        Participante participante = new Participante(cpf, nome);
        evento.adicionarParticipante(participante);
    }

    // US3: Listagem de Eventos e Vagas
    public Collection<Evento> listarTodosEventos() {
        if (eventos.isEmpty()) {
            throw new RuntimeException("Não há eventos cadastrados no sistema.");
        }
        return eventos.values();
    }
}