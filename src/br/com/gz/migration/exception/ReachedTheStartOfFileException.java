package br.com.gz.migration.exception;

/**
 * Exceção lançada quando é atingido o início do arquivo e algum método ainda está tentando ler o registro anterior
 * 
 * @author Jonathan Sansalone
 *
 */
public class ReachedTheStartOfFileException extends Exception {

	/**
	 * Construtor default
	 */
	public ReachedTheStartOfFileException() {
		super("Não há mais registros para ler! Alcançado o início do arquivo.");
	}
	
}
