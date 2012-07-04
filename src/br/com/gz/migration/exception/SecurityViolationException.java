package br.com.gz.migration.exception;

/**
 * Exceção lançada quando há uma violação de segurança na instrução SQL
 * 
 * @author Jonathan
 *
 */
@Deprecated
public class SecurityViolationException extends Exception {

	/**
	 * Construtor default
	 */
	public SecurityViolationException() {
		super("Violação das regras de segurança! Código SQL perigoso encontrado.");
	}
	
}
