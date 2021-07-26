package br.com.rarolabs.viacep.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rarolabs.viacep.exception.CepNaoEncontradoException;
import br.com.rarolabs.viacep.exception.CepNaoNumericoException;
import br.com.rarolabs.viacep.service.ViaCepService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/cep")
public class ViaCepController {
	
	private static final String PREFIXO_LOG = "[ViaCepController] ";

	private final ViaCepService viaCepService;

	public ViaCepController(ViaCepService viaCepService) {
		this.viaCepService = viaCepService;
	}

	@GetMapping("/info/{cep}")
	public ResponseEntity<Object> consultarDadosDoCep(@PathVariable("cep") String cep) {
		try {
			return ResponseEntity.ok().body(viaCepService.obterDadosDoCep(cep));
		} catch (CepNaoNumericoException | CepNaoEncontradoException ex) {
			log.error(String.join("", PREFIXO_LOG, ex.getMessage()));
			return ResponseEntity.badRequest().body(ex.getMessage());
		} catch (Exception ex) {
			log.error(String.join("", PREFIXO_LOG, "Ocorreu um problema não previsto: ", ex.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
