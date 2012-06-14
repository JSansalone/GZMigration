package br.com.gz.migration.exception;

public class SecurityViolationException extends Exception {

	public SecurityViolationException() {
		super("Violação das regras de segurança! Código SQL perigoso encontrado.");
	}
	
}
