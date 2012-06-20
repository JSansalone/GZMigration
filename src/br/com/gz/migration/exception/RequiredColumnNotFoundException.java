package br.com.gz.migration.exception;

public class RequiredColumnNotFoundException extends Exception {

	public RequiredColumnNotFoundException() {
		super("Coluna(s) obrigatória(s) não encontradas");
	}
	
	public RequiredColumnNotFoundException(String message) {
		super(message);
	}
	
}
