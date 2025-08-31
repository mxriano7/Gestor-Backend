package com.oracle.gestaoprojetos.repository;

import com.oracle.gestaoprojetos.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    // Aqui podemos criar queries personalizadas se precisar
    // Exemplo: List<Projeto> findByStatus(StatusProjeto status);
}
