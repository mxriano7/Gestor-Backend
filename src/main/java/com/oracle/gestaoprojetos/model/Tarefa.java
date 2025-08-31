package com.oracle.gestaoprojetos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 255)
    private String descricao;

    private LocalDate prazo;

    @Enumerated(EnumType.STRING)
    private StatusTarefa status;

    // relação N:1 (várias tarefas pertencem a 1 projeto)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    // construtores
    public Tarefa() {}

    public Tarefa(String titulo, String descricao, LocalDate prazo, StatusTarefa status, Projeto projeto) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prazo = prazo;
        this.status = status;
        this.projeto = projeto;
    }

    // getters e setters
    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getPrazo() { return prazo; }
    public void setPrazo(LocalDate prazo) { this.prazo = prazo; }

    public StatusTarefa getStatus() { return status; }
    public void setStatus(StatusTarefa status) { this.status = status; }

    public Projeto getProjeto() { return projeto; }
    public void setProjeto(Projeto projeto) { this.projeto = projeto; }
}
