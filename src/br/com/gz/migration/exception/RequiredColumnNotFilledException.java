package br.com.gz.migration.exception;

/**
 * Exce��o lan�ada quando uma coluna estiver vazia
 * 
 * @author Jonathan Sansalone
 *
 */
public class RequiredColumnNotFilledException extends Exception{

	/**
	 * Construtor default
	 */
	public RequiredColumnNotFilledException() {
		super("Coluna(s) obrigat�ria(s) n�o preenchidas");
	}
	
}
