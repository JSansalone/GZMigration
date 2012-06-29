package br.com.gz.migration.exception;

/**
 * Exce��o lan�ada quando uma coluna n�o for encontrada
 * 
 * @author Jonathan Sansalone
 *
 */
public class RequiredColumnNotFoundException extends Exception {

	/**
	 * Construtor default
	 */
	public RequiredColumnNotFoundException() {
		super("Coluna(s) obrigat�ria(s) n�o encontradas");
	}
	
	/**
	 * Construtor para exibir uma mensagem espec�fica
	 */
	public RequiredColumnNotFoundException(String message) {
		super(message);
	}
	
}
