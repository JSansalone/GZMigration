package br.com.gz.migration.exception;

/**
 * Exce��o lan�ada quando � atingido o in�cio do arquivo e algum m�todo ainda est� tentando ler o registro anterior
 * 
 * @author Jonathan Sansalone
 *
 */
public class ReachedTheStartOfFileException extends Exception {

	/**
	 * Construtor default
	 */
	public ReachedTheStartOfFileException() {
		super("N�o h� mais registros para ler! Alcan�ado o in�cio do arquivo.");
	}
	
}
