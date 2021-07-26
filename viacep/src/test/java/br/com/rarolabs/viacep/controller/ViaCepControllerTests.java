package br.com.rarolabs.viacep.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rarolabs.viacep.exception.CepNaoEncontradoException;
import br.com.rarolabs.viacep.exception.CepNaoNumericoException;
import br.com.rarolabs.viacep.service.ViaCepService;

@ExtendWith(MockitoExtension.class)
class ViaCepControllerTests {

	@InjectMocks
	private ViaCepController viaCepController;
	
	@Mock
	private ViaCepService viaCepService;
	
	@BeforeEach
	void setup() {
		viaCepController = new ViaCepController(viaCepService);
	}
	
	@Test
	void testeOk() throws CepNaoNumericoException, CepNaoEncontradoException {
		var response = viaCepController.consultarDadosDoCep("30570010");
		Assertions.assertEquals(200, response.getStatusCodeValue());
	}
	
	@Test
	void testeBadRequestCepNaoNumerico() throws CepNaoNumericoException, CepNaoEncontradoException {
		Mockito.when(viaCepService.obterDadosDoCep(ArgumentMatchers.anyString())).thenThrow(CepNaoNumericoException.class);
		var response = viaCepController.consultarDadosDoCep("0123EFGH");
		Assertions.assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	void testeBadRequestCepNaoEncontrado() throws CepNaoNumericoException, CepNaoEncontradoException {
		Mockito.when(viaCepService.obterDadosDoCep(ArgumentMatchers.anyString())).thenThrow(CepNaoEncontradoException.class);
		var response = viaCepController.consultarDadosDoCep("00000000");
		Assertions.assertEquals(400, response.getStatusCodeValue());
	}
	
	@Test
	void testeInternalServerError() throws CepNaoNumericoException, CepNaoEncontradoException {
		Mockito.when(viaCepService.obterDadosDoCep(ArgumentMatchers.any())).thenThrow(NullPointerException.class);
		var response = viaCepController.consultarDadosDoCep(null);
		Assertions.assertEquals(500, response.getStatusCodeValue());
	}
	
}
