package br.com.gz.migration;

import java.util.ArrayList;

/**
 * Interface que trabalha com os tipos de dados da migração
 * 
 * @author Jonathan Sansalone
 *
 */
public interface IMigrationType {

	/**
	 * Método que retorna todos os tipos de dados que serão migrados
	 * 
	 * @return - Array com todos os tipos de dados
	 */
	ArrayList<EnMigrationDataType> getMigrationType();
	
	/**
	 * Método que retorna a quantidade de lojas utilizadas
	 * 
	 * @return - quantidade de loja
	 */
	int getNumLoja();
	
	/**
	 * Método que informa se a migração vai sobrepor ou incluir dados
	 * 
	 * @return - true se for incluir, false se for sobrepor
	 */
	boolean toAppend();
	
	/**
	 * Método que informa se o usuário deseja ignorar os códigos de loja presentes nos arquivos de dados
	 * 
	 * @return - true se sim, false se não
	 */
	boolean ignoreCodes();
	
}
