package br.com.gz.migration.exception;

/**
 * Exce��o que deve ser lan�ada quando algum tipo de dado passado para algum m�todo for inv�lido
 * 
 * @author Jonathan Sansalone
 *
 */
public class InvalidMigrationDataTypeException extends Exception{

	/**
	 * Construtor default
	 */
	public InvalidMigrationDataTypeException() {
		super("invalid migration data type!");
	}
	
}
