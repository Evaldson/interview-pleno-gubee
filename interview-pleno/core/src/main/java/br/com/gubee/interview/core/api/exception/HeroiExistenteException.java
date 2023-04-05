package br.com.gubee.interview.core.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HeroiExistenteException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HeroiExistenteException(String nome) {
        super("O Héroi " + nome + " já está cadastrado");
    }
}