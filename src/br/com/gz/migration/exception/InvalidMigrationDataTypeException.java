package br.com.gz.migration.exception;

/**
 * Exceção que deve ser lançada quando algum tipo de dado passado para algum método for inválido
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
