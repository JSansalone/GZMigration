package br.com.gz.migration.file;

import java.io.File;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.datafile.DataFileWriter;
import br.com.gz.migration.policy.EnColumnsCategory;
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;

/**
 * Classe que trabalha com os arquivos de instruções SQL
 * 
 * @author Jonathan Sansalone
 * 
 */
public class DefaultDirectoryStructure {

	/**
	 * Método que cria os arquivos .xls
	 */
	public void createDefaultStructure() {

		File root = new File("data");

		if (!root.exists()) {

			if (root.mkdir()) {

				DataFileWriter.createFile(EnMercoFlexRequiredColumns
						.filterValues(EnColumnsCategory.ESTOQUE,
								EnColumnsCategory.ESTOQUE_LOJA,
								EnColumnsCategory.ESTOQUE_TRIBUTACAO,
								EnColumnsCategory.ESTOQUE_SALDO),
						EnMigrationDataType.PRODUTO);
				DataFileWriter.createFile(EnMercoFlexRequiredColumns
						.filterValues(EnColumnsCategory.DEPARTAMENTO),
						EnMigrationDataType.DEPARTAMENTO);
				DataFileWriter.createFile(EnMercoFlexRequiredColumns
						.filterValues(EnColumnsCategory.GRUPO),
						EnMigrationDataType.GRUPO);
				DataFileWriter.createFile(EnMercoFlexRequiredColumns
						.filterValues(EnColumnsCategory.ARMACAO),
						EnMigrationDataType.ARMACAO);
				DataFileWriter.createFile(EnMercoFlexRequiredColumns
						.filterValues(EnColumnsCategory.MARCA),
						EnMigrationDataType.MARCA);
				DataFileWriter.createFile(EnMercoFlexRequiredColumns
						.filterValues(EnColumnsCategory.CLIENTES),
						EnMigrationDataType.CLIENTE);
				DataFileWriter.createFile(EnMercoFlexRequiredColumns
						.filterValues(EnColumnsCategory.FORNECEDOR),
						EnMigrationDataType.FORNECEDOR);

			}

		}

	}

}
