package br.studio.pilates.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.studio.pilates.model.entity.Financeiro;
import br.studio.pilates.model.entity.repository.FinanceiroRepository;

@Service
public class FinanceiroService {

    @Autowired
    private FinanceiroRepository financeiroRepository;

   public List<Financeiro> getAllFinanceiro() {
        return financeiroRepository.findAll();
    }

    public Financeiro getFinanceiroById(String Id) {
        return financeiroRepository.findFinanceiroById(Id);
    }

    public Financeiro saveFinanceiro(Financeiro financeiro) {
        return financeiroRepository.save(financeiro);
    }

    public void deleteFinanceiro(String Id) {
        financeiroRepository.deleteById(Id);
    }
}