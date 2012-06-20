package br.com.gz.migration.exception;

public class RequiredColumnNotFoundException extends Exception {

	public RequiredColumnNotFoundException() {
		super("Coluna(s) obrigat�ria(s) n�o encontradas");
	}
	
	public RequiredColumnNotFoundException(String message) {
		super(message);
	}
	
}
