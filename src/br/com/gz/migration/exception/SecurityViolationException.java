package br.com.gz.migration.exception;

/**
 * Exce��o lan�ada quando h� uma viola��o de seguran�a na instru��o SQL
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
		super("Viola��o das regras de seguran�a! C�digo SQL perigoso encontrado.");
	}
	
}
