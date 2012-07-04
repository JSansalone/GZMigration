package br.com.gz.migration.exception;

/**
 * Exce��o que deve ser lan�ada quando alguma coluna estiver fora de ordem
 * 
 * @author Jonathan Sansalone
 *
 */
@Deprecated
public class ColumnOutOfOrderException extends Exception {

	/**
	 * Construtor default
	 */
	public ColumnOutOfOrderException() {
	
		super("Coluna(s) fora de ordem");
		
	}
	
}
