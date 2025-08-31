package com.oracle.gestaoprojetos.service;

import com.oracle.gestaoprojetos.model.Tarefa;
import com.oracle.gestaoprojetos.model.Projeto;
import com.oracle.gestaoprojetos.repository.TarefaRepository;
import com.oracle.gestaoprojetos.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;

    public TarefaService(TarefaRepository tarefaRepository, ProjetoRepository projetoRepository) {
        this.tarefaRepository = tarefaRepository;
        this.projetoRepository = projetoRepository;
    }

    public Tarefa criarTarefa(Long projetoId, Tarefa tarefa) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        tarefa.setProjeto(projeto);
        return tarefaRepository.save(tarefa);
    }

    public Optional<Tarefa> buscarTarefa(Long id) {
        return tarefaRepository.findById(id);
    }

    public List<Tarefa> listarTarefasDoProjeto(Long projetoId) {
        return tarefaRepository.findByProjetoId(projetoId);
    }

    public Tarefa atualizarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }
}
