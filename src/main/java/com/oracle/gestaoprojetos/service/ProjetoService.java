package com.oracle.gestaoprojetos.service;

import com.oracle.gestaoprojetos.model.Projeto;
import com.oracle.gestaoprojetos.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto criarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> buscarProjeto(Long id) {
        return projetoRepository.findById(id);
    }

    public Projeto atualizarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public void deletarProjeto(Long id) {
        projetoRepository.deleteById(id);
    }
}
