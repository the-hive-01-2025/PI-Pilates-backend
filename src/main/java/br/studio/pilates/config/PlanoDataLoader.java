package br.studio.pilates.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.studio.pilates.model.entity.Plano;
import br.studio.pilates.model.entity.repository.PlanoRepository;

/**
 * Classe de configuração responsável por carregar dados iniciais de planos no banco de dados.
 * 
 * Utiliza o CommandLineRunner para executar código assim que a aplicação Spring Boot iniciar.
 * Serve para garantir que alguns planos padrão existam no sistema, caso o banco esteja vazio.
 */
@Configuration
public class PlanoDataLoader {

    /**
     * Bean que cria um CommandLineRunner para carregar planos padrão.
     * 
     * @param planoRepository repositório que permite operações CRUD na entidade Plano
     * @return CommandLineRunner que será executado na inicialização da aplicação
     */
    @Bean
    CommandLineRunner loadPlanos(PlanoRepository planoRepository) {
        return args -> {
            // Verifica se o banco de dados está vazio (sem planos)
            if (planoRepository.count() == 0) {
                // Insere uma lista de planos padrão no banco
                planoRepository.saveAll(List.of(
                    new Plano(null, "Mensal", 120.00, 5, "Plano mensal com renovação automática."),
                    new Plano(null, "Trimestral", 330.00, 5, "Plano válido por 3 meses. Melhor custo-benefício."),
                    new Plano(null, "Anual", 1200.00, 5, "Plano anual com desconto especial.")
                ));
                // Imprime no console para indicar que os planos foram carregados
                System.out.println("Planos padrão inseridos no banco.");
            }
        };
    }
}
