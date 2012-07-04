package br.com.gz.migration.exception;

/**
 * Exce��o que deve ser lan�ada quando o tipo de dado da c�lula n�o � o tipo de dado esperado. Exemplo: Espera-se obter o c�digo do produto de uma c�lula que deve estar com o tipo de dado num�rico,
 * mas esta est� com o tipo de dado texto
 * 
 * @author Jonathan
 *
 */
public class InvalidCellTypeException extends Exception {

	/**
	 * Construtor default que exibe na StackTrace uma mensagem padr�o: "Estilo de c�lula inv�lido!"
	 */
	public InvalidCellTypeException() {
		
		super("Estilo de c�lula inv�lido!");
		
	}
	
	/**
	 * Construtor que exibe na StackTrace uma mensagem espec�fica
	 * 
	 * @param message - Mensagem para ser exibida na StackTrace
	 */
	public InvalidCellTypeException(String message){
		
		super(message);
		
	}

}
