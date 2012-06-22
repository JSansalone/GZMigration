package br.com.gz.migration.exception;

/**
 * Exce��o lan�ada quando � atingido o fim do arquivo e algum m�todo ainda est� tentando ler o pr�ximo registro
 * 
 * @author Jonathan Sansalone
 *
 */
public class ReachedTheEndOfFileException extends Exception {

	public ReachedTheEndOfFileException() {
		super("N�o h� mais registros para ler! Alcan�ado o fim do arquivo.");
	}
	
}
