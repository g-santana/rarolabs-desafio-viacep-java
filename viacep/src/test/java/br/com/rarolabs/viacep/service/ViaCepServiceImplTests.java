package br.com.rarolabs.viacep.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rarolabs.viacep.dto.CepInfo;
import br.com.rarolabs.viacep.exception.CepNaoEncontradoException;
import br.com.rarolabs.viacep.exception.CepNaoNumericoException;

@ExtendWith(MockitoExtension.class)
class ViaCepServiceImplTests {
	
	@InjectMocks
	private ViaCepServiceImpl viaCepServiceImpl;
	
	private CepInfo obterDadosCepEsperados() {
		return CepInfo.builder()
				.cep("30880-140")
				.logradouro("Rua Ilíada")
				.complemento("")
				.bairro("Glória")
				.localidade("Belo Horizonte")
				.uf("MG")
				.ibge("3106200")
				.gia("")
				.ddd("31")
				.siafi("4123")
				.erro(null)
				.build();
	}
	
	@Test
	void testeCepValido() throws CepNaoNumericoException, CepNaoEncontradoException {
		var dados = viaCepServiceImpl.obterDadosDoCep("30880140");
		Assertions.assertEquals(obterDadosCepEsperados(), dados);
	}
	
	@Test
	void testeCepInvalido() {
		Assertions.assertThrows(CepNaoNumericoException.class, () -> viaCepServiceImpl.obterDadosDoCep("0123EFGH"));
		Assertions.assertThrows(CepNaoNumericoException.class, () -> viaCepServiceImpl.obterDadosDoCep(""));
		Assertions.assertThrows(CepNaoNumericoException.class, () -> viaCepServiceImpl.obterDadosDoCep(null));
	}
	
	@Test
	void testeCepInexistente() {
		Assertions.assertThrows(CepNaoEncontradoException.class, () -> viaCepServiceImpl.obterDadosDoCep("00000000"));
	}
}
