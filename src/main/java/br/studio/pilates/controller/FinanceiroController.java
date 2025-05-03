package br.studio.pilates.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.studio.pilates.model.entity.Financeiro;
import br.studio.pilates.service.FinanceiroService;

@RestController
public class FinanceiroController {

	@Autowired
	private FinanceiroService financeiroService;

	@GetMapping("financeiro")
	public List<Financeiro> listar() {
		return financeiroService.getAllFinanceiro();
	}

	@GetMapping("financeiro/{id}")
	public Financeiro getById(@PathVariable("id") String Id) {
		return financeiroService.getFinanceiroById(Id);
	}

	@PostMapping("financeiro")
	public Financeiro insert(@RequestBody Financeiro Financeiro) {
		return financeiroService.saveFinanceiro(Financeiro);

	}

	@DeleteMapping("financeiro/{id}")
	public String delete(@PathVariable("id") String Id) {
		if(financeiroService.getFinanceiroById(Id) != null){

		financeiroService.deleteFinanceiro(Id);
		return "Financeiro Excluido com sucesso!!";
		} else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: recurso não encontrado!").toString();
	}
		}

}