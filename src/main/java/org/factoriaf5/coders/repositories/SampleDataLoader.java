package org.factoriaf5.coders.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SampleDataLoader {
    private CoderRepository coderRepository;

    @Autowired
    public SampleDataLoader(CoderRepository coderRepository) {
        this.coderRepository = coderRepository;
    }

    @PostConstruct
    public void loadSampleData() {
        coderRepository.saveAll(List.of(
                new Coder("Desirée", "Moreno", 42, "Venezuela", "Técnico Superior", "Calle Lletres 24, 1-2,Barcelona", "Femtech P2"),
                new Coder("Carmen", "Gamarra", 35, "Argentina", "Ingeniero", "Calle Petrarca 28, 1-8,Barcelona", "Femtech P2"),
                new Coder("Alexia", "Cabre", 38, "España", "Músico", "Calle San Juan 48, 2-8,Barcelona", "Femtech P2"),
                new Coder("Alisa", "Bayanova", 25, "Rusia", "Desarrolladora Web", "Calle San Pedro 12, 2-A,Barcelona", "Femtech P2"),
                new Coder("Ana", "Casas", 35, "Venezuela", "Modelo", "Calle Catalunya 22, 2-D,Barcelona", "Femtech P1"),
                new Coder("Anna", "Girona", 33, "España", "Fotógrafo", "Calle Ramoneda 15, 2-2,Barcelona", "Femtech P1"),
                new Coder("Candy", "Montero", 20, "Honduras", "Estudiante", "Calle Restrepo 18, 5-8,Barcelona", "Femtech P2"),
                new Coder("Faby", "Broc", 55, "Argentina", "Superior", "Calle Dante 50, 2-4,Barcelona", "Femtech P2"),
                new Coder("Gabrielle", "Hurtado", 33, "Colombia", "Superior", "Calle Cantabria 58, 1-2,Barcelona", "Femtech P1"),
                new Coder("Gràcia", "Gràcia", 30, "España", "Técnico", "Calle Passeig de Grácia 600, bajo 1,Barcelona", "Femtech P2"),
                new Coder("Helen", "Didsbury", 40, "Inglaterra", "Superior", "Calle Passeig de Font 15, Ático, Barcelona", "Femtech P1")
        ));
    }
}
