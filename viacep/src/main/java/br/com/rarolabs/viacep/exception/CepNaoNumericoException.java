package br.com.rarolabs.viacep.exception;

public class CepNaoNumericoException extends Exception {

	private static final long serialVersionUID = -7538788915584629382L;

	public CepNaoNumericoException(String mensagem) {
        super(mensagem);
    }
}
