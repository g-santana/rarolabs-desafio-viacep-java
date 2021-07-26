package br.com.rarolabs.viacep.service;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.rarolabs.viacep.constant.Formato;
import br.com.rarolabs.viacep.dto.CepInfo;
import br.com.rarolabs.viacep.exception.CepNaoEncontradoException;
import br.com.rarolabs.viacep.exception.CepNaoNumericoException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ViaCepServiceImpl implements ViaCepService {

	private static final String PREFIXO_LOG = "[ViaCepServiceImpl] ";
	private static final String URL = "https://viacep.com.br/ws";
	
	@Override
	public CepInfo obterDadosDoCep(String cep) throws CepNaoNumericoException, CepNaoEncontradoException {
		if (valorNumerico(cep)) {
			String consulta = String.join("/", URL, cep, Formato.JSON.getNome());
			var restTemplate = new RestTemplate();
			var resposta = restTemplate.getForObject(consulta, CepInfo.class);
			
			if (Objects.nonNull(resposta) && respostaValida(resposta)) {
				return resposta;
			} else {
				log.error(String.join("", PREFIXO_LOG, "Não foram encontrados dados para o CEP ", cep));
				throw new CepNaoEncontradoException("Não foi possível encontrar dados para o CEP informado. Favor informar um CEP existente.");
			}			
		} else {
			log.error(String.join("", PREFIXO_LOG, "CEP em formato inválido: ", cep));
			throw new CepNaoNumericoException("Valor de CEP inválido. Favor informar um valor inteiramente numérico, sem letras, espaços ou caracteres especiais.");
		}
	}
	
	private boolean valorNumerico(String cep) {
	    if (Objects.isNull(cep) || StringUtils.isBlank(cep)) {
	        return false;
	    }
	    
	    try {
	        Long.parseLong(cep);
	        return true;
	    } catch (NumberFormatException ex) {
	    	log.error(String.join("", PREFIXO_LOG, "Foi informado um valor de CEP não numérico"));
	    	return false;
	    }
	}
	
	private boolean respostaValida(CepInfo cepInfo) {
		return !Boolean.TRUE.equals(cepInfo.getErro());
	}
}
