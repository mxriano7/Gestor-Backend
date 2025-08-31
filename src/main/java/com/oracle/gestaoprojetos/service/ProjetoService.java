package com.oracle.gestaoprojetos.service;

import com.oracle.gestaoprojetos.model.Projeto;
import com.oracle.gestaoprojetos.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository repository;

    public Projeto salvar(Projeto projeto) { return repository.save(projeto); }

    public List<Projeto> listar() { return repository.findAll(); }

    public Optional<Projeto> buscar(Long id) { return repository.findById(id); }

    public Projeto editar(Long id, Projeto projeto) {
        return repository.findById(id).map(p -> {
            p.setNome(projeto.getNome());
            p.setDescricao(projeto.getDescricao());
            p.setArquivo(projeto.getArquivo());
            p.setNomeArquivo(projeto.getNomeArquivo());
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));
    }

    public void deletar(Long id) { repository.deleteById(id); }
}
