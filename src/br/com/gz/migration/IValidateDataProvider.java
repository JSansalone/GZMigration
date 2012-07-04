package br.com.gz.migration;

import org.database.connection.DatabaseType;

/**
 * Interface que auxilia na comparação de bancos de dados
 * 
 * @author Jonathan Sansalone
 *
 */
@Deprecated
interface IValidateDataProvider {

	/**
	 * Método que compara bancos de dados
	 * 
	 * @param compare1 - banco de dados
	 * @param compare2 - banco de dados
	 * @return - true se forem iguais, false caso contrário
	 */
	@Deprecated
	boolean validateDBTo(DatabaseType compare1, DatabaseType compare2);

	/**
	 * Método que compara bancos de dados
	 * 
	 * @param compare1 - banco de dados
	 * @param compare2 - banco de dados
	 * @return - true se forem iguais, false caso contrário
	 */
	@Deprecated
	boolean validateDBFrom(DatabaseType compare1, DatabaseType compare2);

}
