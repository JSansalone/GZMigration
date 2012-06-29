package br.com.gz.migration.exception;

/**
 * Exceção lançada quando uma coluna não for encontrada
 * 
 * @author Jonathan Sansalone
 *
 */
public class RequiredColumnNotFoundException extends Exception {

	/**
	 * Construtor default
	 */
	public RequiredColumnNotFoundException() {
		super("Coluna(s) obrigatória(s) não encontradas");
	}
	
	/**
	 * Construtor para exibir uma mensagem específica
	 */
	public RequiredColumnNotFoundException(String message) {
		super(message);
	}
	
}
