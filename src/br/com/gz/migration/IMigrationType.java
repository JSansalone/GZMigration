package br.com.gz.migration;

import java.util.ArrayList;

/**
 * Interface que trabalha com os tipos de dados da migra��o
 * 
 * @author Jonathan Sansalone
 *
 */
public interface IMigrationType {

	/**
	 * M�todo que retorna todos os tipos de dados que ser�o migrados
	 * 
	 * @return - Array com todos os tipos de dados
	 */
	ArrayList<EnMigrationDataType> getMigrationType();
	
	/**
	 * M�todo que retorna a quantidade de lojas utilizadas
	 * 
	 * @return - quantidade de loja
	 */
	int getNumLoja();
	
	/**
	 * M�todo que informa se a migra��o vai sobrepor ou incluir dados
	 * 
	 * @return - true se for incluir, false se for sobrepor
	 */
	boolean toAppend();
	
	/**
	 * M�todo que informa se o usu�rio deseja ignorar os c�digos de loja presentes nos arquivos de dados
	 * 
	 * @return - true se sim, false se n�o
	 */
	boolean ignoreCodes();
	
}
