package br.com.gz.migration.exception;

/**
 * Exceção lançada quando uma coluna estiver vazia
 * 
 * @author Jonathan Sansalone
 *
 */
public class RequiredColumnNotFilledException extends Exception{

	/**
	 * Construtor default
	 */
	public RequiredColumnNotFilledException() {
		super("Coluna(s) obrigatória(s) não preenchidas");
	}
	
}
