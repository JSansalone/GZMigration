package br.com.gz.migration.datafile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidCellStyleException;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;

public abstract class DataFile {

	/**
	 * Constante usada para representar colunas null
	 */
	public static final String CELL_VALUE_NULL = "<cell_value_null>";
	
	/**
	 * Constante para representar colunas com tipo de dado inválido
	 */
	public static final String INVALID_CELL_TYPE = "<invalid_type>";
	
	/**
	 * Representa a planilha de dados
	 */
	protected HSSFSheet dataSheet;
	
	/**
	 * Representa a stream de acesso aos dados
	 */
	private FileInputStream stream;

	/**
	 * Construtor que recebe um EnMigrationDataType e de acordo com este, lê o arquivo específico
	 * 
	 * @param dataType - O tipo de dado para definir o tipo de arquivo
	 * @throws InvalidMigrationDataTypeException - Lança este exceção se o tipo passado não corresponder aos tipos implementados
	 * @throws IOException - Lança esta exceção se não for possível ler o arquivo
	 */
	protected DataFile(EnMigrationDataType dataType) throws InvalidMigrationDataTypeException, IOException {

		File file = new File("data", dataType.toString() + ".xls");

		if (file.exists()) {

			switch (dataType) {

			case PRODUTO: break;
			case DEPARTAMENTO: break;
			case GRUPO: break;
			case SETOR: break;
			case ARMACAO: break;
			case MARCA: break;
			case CLIENTE: break;
			case FORNECEDOR: break;
				
			default:
				
				throw new InvalidMigrationDataTypeException();

			}
			
			stream = new FileInputStream(file);
			dataSheet = new HSSFWorkbook(stream).getSheetAt(0);

		}else{
			
			throw new FileNotFoundException();
			
		}

	}

	/**
	 * Obtêm os dados referentes às colunas do arquivo
	 * 
	 * @return - Os dados das colunas do arquivo
	 */
	public abstract DataFileMetaData getMetaData();

	/**
	 * Retorna o nome do arquivo
	 * 
	 * @return - O nome do arquivo
	 */
	public abstract String getName();

	/**
	 * Retorna o total de registros no arquivo
	 * 
	 * @return - Total de registros no arquivo
	 */
	public abstract int getTotalRows();

	/**
	 * Move o índice de leitura do arquivo para o primeiro registro
	 */
	public abstract void moveToFirst();

	/**
	 * Move o índice de leitura do arquivo para o último registro
	 */
	public abstract void moveToLast();

	/**
	 * Verifica se há um próximo registro
	 * 
	 * @return - true se houver um próximo registro, false caso contrário
	 */
	public abstract boolean hasNext();

	/**
	 * Retorna o próximo registro do arquivo
	 * 
	 * @return - próximo registro como um Object
	 */
	public abstract Object next();

	/**
	 * Retorna o registro anterior ao atual do arquivo
	 * 
	 * @return - o registro anterior como um Object
	 */
	public abstract Object previous();

	/**
	 * Retorna todos os registros do arquivo
	 * 
	 * @return - Array com todos os registros
	 */
	public abstract ArrayList<Object> getAll();

	/**
	 * Fecha e libera os recursos do arquivo
	 */
	protected void close(){
		
		try {
			stream.close();
		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Retorna o registro do arquivo recebendo como argumento o número da linha
	 * 
	 * @param rowIndex - número da linha
	 * @return - o registro como um Object
	 */
	protected abstract Object getRowData(int rowIndex);
	
	/**
	 * Retorna os nomes das colunas do arquivo
	 * 
	 * @param maximumColumns - máximo de colunas que devem ser lidas
	 * @return - nomes das colunas do arquivo
	 */
	protected abstract String[] getHeader(int maximumColumns);
	
	/**
	 * Verifica se todas as colunas estão presentes no arquivo e se estão na ordem em que devem estar
	 * 
	 * @return - true se estiver tudo certo, false caso contrário
	 */
	public abstract boolean checkColumnsPolicy();

}
