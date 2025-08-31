package com.oracle.gestaoprojetos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.gestaoprojetos.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    // Permite buscar projetos pelo nome
    List<Projeto> findByNome(String nome);
}
