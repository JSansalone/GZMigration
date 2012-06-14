package br.com.gz.migration.sql;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import br.com.gz.migration.EnMigrationDataType;

public interface ColumnsPolicy {

	// verifica se todos os campos requeridos foram trazidos
	/**
	 * 
	 * @param selectInfo - cont�m todos os nomes das colunas resultantes do select
	 * @param type - tipo de dado que est� sendo coletado
	 * @return true se todos os campos requeridos forem encontrados, false se n�o
	 */
	boolean validate(ResultSetMetaData selectInfo,EnMigrationDataType type);
	
	// retorna uma lista com os campos requeridos
	ArrayList<String> getColumnsNeeded();
	
}
