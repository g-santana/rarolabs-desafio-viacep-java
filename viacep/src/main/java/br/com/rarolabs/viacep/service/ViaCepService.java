package br.com.rarolabs.viacep.service;

import br.com.rarolabs.viacep.dto.CepInfo;
import br.com.rarolabs.viacep.exception.CepNaoEncontradoException;
import br.com.rarolabs.viacep.exception.CepNaoNumericoException;

public interface ViaCepService {
	
	CepInfo obterDadosDoCep(String cep) throws CepNaoNumericoException, CepNaoEncontradoException;

}
