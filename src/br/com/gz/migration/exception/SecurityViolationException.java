package br.com.gz.migration.exception;

public class SecurityViolationException extends Exception {

	public SecurityViolationException() {
		super("Viola��o das regras de seguran�a! C�digo SQL perigoso encontrado.");
	}
	
}
