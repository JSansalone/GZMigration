package br.com.gz.migration.exception;

/**
 * Exce��o que deve ser lan�ada quando algum software passado para algum m�todo for inv�lido
 * 
 * @author Jonathan Sansalone
 *
 */
public class InvalidSoftwareException extends Exception {
	
	/**
	 * Construtor default
	 */
	public InvalidSoftwareException() {
		
		super("O software escolhido � inv�lido!");
		
	}

}
