package br.com.gz.migration.exception;

public class ColumnOutOfOrderException extends Exception {

	public ColumnOutOfOrderException() {
	
		super("Coluna(s) fora de ordem");
		
	}
	
}
