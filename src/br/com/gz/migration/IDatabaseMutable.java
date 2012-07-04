package br.com.gz.migration;

import org.database.connection.DatabaseType;

/**
 * Classe que auxilia nas escolhas de banco de dados
 * 
 * @author Jonathan Sansaloe
 *
 */
public interface IDatabaseMutable {

	/**
	 * Método que define o que deve ser feito quando certo banco de dados é escolhido
	 * 
	 * @param dbt - o banco de dados escolhido
	 */
	void chooseDatabase(DatabaseType dbt);
	
}
