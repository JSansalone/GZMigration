package br.com.gz.migration.exception;

/**
 * Exceção que deve ser lançada quando alguma coluna estiver fora de ordem
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
