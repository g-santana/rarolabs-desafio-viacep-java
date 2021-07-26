package br.com.rarolabs.viacep.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Formato {
	JSON("json"),
	XML("xml");
	
	private final String nome;
}
