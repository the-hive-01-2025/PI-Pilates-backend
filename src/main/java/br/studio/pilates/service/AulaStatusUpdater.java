package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.studio.pilates.model.entity.Aula;
import br.studio.pilates.model.entity.repository.AulaRepository;

import jakarta.annotation.PostConstruct;

/**
 * Componente responsável por atualizar o status das aulas que estejam com
 * status nulo ou vazio, definindo o status padrão "Pendente".
 * 
 * A atualização é realizada automaticamente após a inicialização do contexto
 * da aplicação, graças à anotação @PostConstruct.
 */
@Component
public class AulaStatusUpdater {

    @Autowired
    private AulaRepository aulaRepository;

    /**
     * Método executado após a construção do bean, que percorre todas as aulas
     * e atualiza o status para "Pendente" caso estejam nulas ou vazias.
     */
    @PostConstruct
    public void updateNullStatusToPendente() {
        List<Aula> aulas = aulaRepository.findAll();
        for (Aula aula : aulas) {
            if (aula.getStatus() == null || aula.getStatus().isEmpty()) {
                aula.setStatus("Pendente");
                aulaRepository.save(aula);
            }
        }
    }
}
