package com.oracle.gestaoprojetos.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.gestaoprojetos.model.Projeto;
import com.oracle.gestaoprojetos.model.StatusProjeto;
import com.oracle.gestaoprojetos.repository.ProjetoRepository;

@RestController
@RequestMapping("/projetos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProjetoController {

    private final ProjetoRepository repository;

    public ProjetoController(ProjetoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Projeto> listarProjetos() {
        return repository.findAll();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> salvarOuEditarProjeto(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam(value = "dataInicio", required = false) String dataInicio,
            @RequestParam(value = "dataFim", required = false) String dataFim,
            @RequestParam(value = "status", required = false) StatusProjeto status,
            @RequestParam(value = "arquivo", required = false) MultipartFile arquivo
    ) {
        try {
            Projeto projeto;
            if (id != null) { // Editar por ID
                projeto = repository.findById(id).orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));
                boolean existeNome = repository.findByNome(nome).stream().anyMatch(p -> !p.getId().equals(id));
                if (existeNome) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome do projeto já existe!");
                }
            } else { // Criar
                if (!repository.findByNome(nome).isEmpty()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Nome do projeto já existe!");
                }
                projeto = new Projeto();
                projeto.setId(System.currentTimeMillis());
            }

            projeto.setNome(nome);
            projeto.setDescricao(descricao);
            projeto.setDataInicio(dataInicio != null ? LocalDate.parse(dataInicio) : projeto.getDataInicio());
            projeto.setDataFim(dataFim != null ? LocalDate.parse(dataFim) : projeto.getDataFim());
            projeto.setStatus(status != null ? status : StatusProjeto.NAO_INICIADO);

            if (arquivo != null && !arquivo.isEmpty()) {
                String nomeArquivoFinal = System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
                projeto.setArquivo(arquivo.getBytes());
                projeto.setNomeArquivo(nomeArquivoFinal);
            }

            Projeto salvo = repository.save(projeto);
            return ResponseEntity.ok(salvo);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar arquivo");
        }
    }

    // Remover arquivo pelo nome do projeto
    @DeleteMapping("/arquivo")
    public ResponseEntity<?> removerArquivo(@RequestParam("nome") String nome) {
        Projeto projeto = repository.findByNome(nome).stream().findFirst().orElse(null);
        if (projeto == null || projeto.getArquivo() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado");
        }
        projeto.setArquivo(null);
        projeto.setNomeArquivo(null);
        repository.save(projeto);
        return ResponseEntity.ok("Arquivo removido com sucesso");
    }

    // Deletar projeto pelo nome
    @DeleteMapping
    public ResponseEntity<?> deletarProjeto(@RequestParam("nome") String nome) {
        List<Projeto> encontrados = repository.findByNome(nome);
        if (encontrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        }
        encontrados.forEach(repository::delete);
        return ResponseEntity.ok("Projeto deletado com sucesso");
    }

    // Download do arquivo pelo nome do projeto
    @GetMapping("/baixar")
    public ResponseEntity<Resource> downloadArquivo(@RequestParam("nomeArquivo") String nomeArquivo) {
        Projeto projeto = repository.findAll().stream()
                .filter(p -> nomeArquivo.equals(p.getNomeArquivo()))
                .findFirst()
                .orElse(null);
        if (projeto == null || projeto.getArquivo() == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayResource resource = new ByteArrayResource(projeto.getArquivo());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + projeto.getNomeArquivo() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(projeto.getArquivo().length)
                .body(resource);
    }
}
