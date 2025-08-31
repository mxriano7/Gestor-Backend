package com.oracle.gestaoprojetos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/teste")
    public String teste() {
        return "Sistema de Gestão de Projetos iniciado com sucesso!";
    }
}
