package br.com.gz.migration.exception;

/**
 * Exceção lançada quando é atingido o fim do arquivo e algum método ainda está tentando ler o próximo registro
 * 
 * @author Jonathan Sansalone
 *
 */
public class ReachedTheEndOfFileException extends Exception {

	public ReachedTheEndOfFileException() {
		super("Não há mais registros para ler! Alcançado o fim do arquivo.");
	}
	
}
