package br.com.gz.migration.exception;

/**
 * Exceção que deve ser lançada quando o tipo de dado da célula não é o tipo de dado esperado. Exemplo: Espera-se obter o código do produto de uma célula que deve estar com o tipo de dado numérico,
 * mas esta está com o tipo de dado texto
 * 
 * @author Jonathan
 *
 */
public class InvalidCellTypeException extends Exception {

	/**
	 * Construtor default que exibe na StackTrace uma mensagem padrão: "Estilo de célula inválido!"
	 */
	public InvalidCellTypeException() {
		
		super("Estilo de célula inválido!");
		
	}
	
	/**
	 * Construtor que exibe na StackTrace uma mensagem específica
	 * 
	 * @param message - Mensagem para ser exibida na StackTrace
	 */
	public InvalidCellTypeException(String message){
		
		super(message);
		
	}

}
