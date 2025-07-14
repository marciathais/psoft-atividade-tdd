package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Evento {
    private int id;
    private String nome;
    private LocalDate data;
    private String local;
    private int capacidadeMaxima;
    private List<Participante> participantesInscritos;

    public Evento(int id, String nome, LocalDate data, String local, int capacidadeMaxima) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do evento deve ser positivo.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do evento não pode ser vazio.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data do evento não pode ser nula.");
        }
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("Local do evento não pode ser vazio.");
        }
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Capacidade máxima do evento deve ser positiva.");
        }

        this.id = id;
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
        this.participantesInscritos = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public LocalDate getData() {
        return this.data;
    }

    public String getLocal() {
        return this.local;
    }

    public int getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }

    public List<Participante> getParticipantesInscritos() {
        return this.participantesInscritos;
    }

    public int getVagasDisponiveis() {
        return this.capacidadeMaxima - this.participantesInscritos.size();
    }

    public void adicionarParticipante(Participante participante) {
        if (participantesInscritos.size() >= capacidadeMaxima) {
            throw new IllegalArgumentException("Capacidade máxima do evento atingida.");
        }
        if (participantesInscritos.contains(participante)) {
            throw new IllegalArgumentException("Participante já inscrito.");
        }
        participantesInscritos.add(participante);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return id == evento.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}