package br.com.gz.migration.exception;

public class InvalidSoftwareException extends Exception {
	
	public InvalidSoftwareException() {
		
		super("O software escolhido é inválido!");
		
	}

}
