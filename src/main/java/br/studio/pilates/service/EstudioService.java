package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Estudio;
import br.studio.pilates.model.entity.repository.EstudioRepository;

/**
 * Serviço responsável pelas operações relacionadas à entidade Estúdio.
 * 
 * Fornece métodos para buscar, salvar e excluir estúdios no banco de dados.
 */
@Service
public class EstudioService {

    @Autowired
    private EstudioRepository estudioRepository;

    /**
     * Retorna a lista de todos os estúdios cadastrados.
     * 
     * @return lista de objetos Estudio
     */
    public List<Estudio> getAllEstudio() {
        return estudioRepository.findAll();
    }

    /**
     * Busca um estúdio pelo seu ID.
     * 
     * @param Id identificador do estúdio
     * @return objeto Estudio correspondente ao ID informado, ou null se não encontrado
     */
    public Estudio getEstudioById(String Id) {
        return estudioRepository.findEstudioById(Id);
    }

    /**
     * Busca um estúdio pelo seu nome.
     * 
     * @param nome nome do estúdio
     * @return objeto Estudio correspondente ao nome informado, ou null se não encontrado
     */
    public Estudio getByNome(String nome) {
        return estudioRepository.findByNome(nome);
    }

    /**
     * Salva um estúdio no banco de dados. Pode ser usado tanto para criar quanto para atualizar.
     * 
     * @param estudio objeto Estudio a ser salvo
     * @return objeto Estudio salvo, incluindo possíveis alterações feitas pelo banco
     */
    public Estudio saveEstudio(Estudio estudio) {
        return estudioRepository.save(estudio);
    }

    /**
     * Exclui um estúdio pelo seu ID.
     * 
     * @param Id identificador do estúdio a ser excluído
     */
    public void deleteEstudio(String Id) {
        estudioRepository.deleteById(Id);
    }

    /**
     * Exclui um estúdio pelo seu nome.
     * 
     * @param nome nome do estúdio a ser excluído
     */
    public void deleteEstudioByName(String nome) {
        estudioRepository.deleteEstudioByNome(nome);
    }
}
