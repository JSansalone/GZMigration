package br.com.gz.migration;

import org.database.connection.DatabaseConfigurations;

import br.com.gz.util.GZSoftwares;

/**
 * Interface que fornece informa��es sobre bancos de dados e o software utilizado
 * 
 * @author Jonathan Sansalone
 *
 */
public interface IDatabaseInfo {

	/**
	 * M�todo que retorna as configura��es do banco de dados
	 * 
	 * @return - As configura��es do banco de dados
	 */
	DatabaseConfigurations getDatabaseInfo();
	
	/**
	 * M�todo que retorna o software utilizado
	 * 
	 * @return - O software utilizado
	 */
	GZSoftwares getSoftware();
	
}
