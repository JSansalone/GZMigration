package br.com.gz.migration.exception;

public class RequiredColumnNotFilledException extends Exception{

	public RequiredColumnNotFilledException() {
		super("Coluna(s) obrigat�ria(s) n�o preenchidas");
	}
	
}
