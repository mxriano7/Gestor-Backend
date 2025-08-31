package com.oracle.gestaoprojetos.repository;

import com.oracle.gestaoprojetos.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Exemplo de método customizado: buscar tarefas por projeto
    List<Tarefa> findByProjetoId(Long projetoId);
}
