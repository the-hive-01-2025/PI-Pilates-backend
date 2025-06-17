package br.studio.pilates.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.studio.pilates.model.entity.Usuario;
import br.studio.pilates.model.entity.repository.UsuarioRepository;

@Configuration
public class UsuarioDataLoader {

    @Bean
    CommandLineRunner loadUsuarios(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                usuarioRepository.saveAll(List.of(
                        new Usuario(null, "recepção", "recepcao@teste.com", "123456", "RECEPCAO"),
                        new Usuario(null, "user", "usuario@teste.com", "123456", "USER"),
                        new Usuario(null, "instrutor", "instrutor@teste.com", "123456", "INTRUTOR"),
                        new Usuario(null, "admin", "admin@teste.com", "123456", "ADMIN")));
                System.out.println("Planos padrão inseridos no banco.");
            }
        };
    }
}
