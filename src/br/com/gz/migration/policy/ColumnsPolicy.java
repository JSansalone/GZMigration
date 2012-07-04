package br.com.gz.migration.policy;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import br.com.gz.migration.EnMigrationDataType;

/**
 * Interface que auxilia na valida��o das colunas resultantes da coleta de dados
 * 
 * @author Jonathan Sansalone
 *
 */
public interface ColumnsPolicy {

	// verifica se todos os campos requeridos foram trazidos
	/**
	 * 
	 * @param selectInfo - cont�m todos os nomes das colunas resultantes do select
	 * @param type - tipo de dado que est� sendo coletado
	 * @return true se todos os campos requeridos forem encontrados, false se n�o
	 */
	@Deprecated
	boolean validate(ResultSetMetaData selectInfo,EnMigrationDataType type);
	
	// retorna uma lista com os campos requeridos
	/**
	 * M�todo que retorna as colunas faltantes
	 * 
	 * @return - {@link ArrayList} com as colunas faltantes
	 */
	ArrayList<String> getColumnsNeeded();
	
}
