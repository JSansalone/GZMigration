package br.com.gz.migration;

import org.database.connection.DatabaseConfigurations;

import br.com.gz.util.GZSoftwares;

/**
 * Interface que fornece informações sobre bancos de dados e o software utilizado
 * 
 * @author Jonathan Sansalone
 *
 */
public interface IDatabaseInfo {

	/**
	 * Método que retorna as configurações do banco de dados
	 * 
	 * @return - As configurações do banco de dados
	 */
	DatabaseConfigurations getDatabaseInfo();
	
	/**
	 * Método que retorna o software utilizado
	 * 
	 * @return - O software utilizado
	 */
	GZSoftwares getSoftware();
	
}
