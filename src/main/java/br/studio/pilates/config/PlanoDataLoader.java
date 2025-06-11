package br.studio.pilates.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.studio.pilates.model.entity.Plano;
import br.studio.pilates.model.entity.repository.PlanoRepository;

@Configuration
public class PlanoDataLoader {

    @Bean
    CommandLineRunner loadPlanos(PlanoRepository planoRepository) {
        return args -> {
            if (planoRepository.count() == 0) {
                planoRepository.saveAll(List.of(
                    new Plano(null, "Mensal", 120.00, 5, "Plano mensal com renovação automática."),
                    new Plano(null, "Trimestral", 330.00, 5, "Plano válido por 3 meses. Melhor custo-benefício."),
                    new Plano(null, "Anual", 1200.00, 5, "Plano anual com desconto especial.")
                ));
                System.out.println("Planos padrão inseridos no banco.");
            }
        };
    }
}
