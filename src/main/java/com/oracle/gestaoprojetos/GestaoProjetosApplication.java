package com.oracle.gestaoprojetos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestaoProjetosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoProjetosApplication.class, args);
        System.out.println("Sistema de Gestão de Projetos iniciado com sucesso!");
    }
}
