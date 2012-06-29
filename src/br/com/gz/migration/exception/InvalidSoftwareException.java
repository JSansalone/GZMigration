package br.com.gz.migration.exception;

/**
 * Exceção que deve ser lançada quando algum software passado para algum método for inválido
 * 
 * @author Jonathan Sansalone
 *
 */
public class InvalidSoftwareException extends Exception {
	
	/**
	 * Construtor default
	 */
	public InvalidSoftwareException() {
		
		super("O software escolhido é inválido!");
		
	}

}
