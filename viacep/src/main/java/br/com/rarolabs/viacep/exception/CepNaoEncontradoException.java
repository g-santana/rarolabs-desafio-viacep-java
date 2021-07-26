package br.com.rarolabs.viacep.exception;

public class CepNaoEncontradoException extends Exception {

	private static final long serialVersionUID = 1514752853299520782L;

    public CepNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
