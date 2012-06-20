package br.com.gz.migration.datafile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidCellTypeException;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;

/**
 * Representa um arquivo de dados e permite manipular as informa��es contidas no arquivo f�sico como: retornar os registros, percorrer os registros,
 * contar a quantidade de registros, etc.
 * 
 * @author Jonathan
 *
 */
public abstract class DataFile {

	/**
	 * Constante usada para representar colunas null
	 */
	public static final String CELL_VALUE_NULL = "<cell_value_null>";
	
	/**
	 * Constante para representar colunas com tipo de dado inv�lido
	 */
	public static final String INVALID_CELL_TYPE = "<invalid_type>";
	
	public static final String NULL_ROW = "<null_row>";
	
	/**
	 * Representa a planilha de dados
	 */
	protected HSSFSheet dataSheet;
	
	/**
	 * Representa a stream de acesso aos dados
	 */
	private FileInputStream stream;

	/**
	 * Construtor que recebe um EnMigrationDataType e de acordo com este, l� o arquivo espec�fico. Este construtor � respons�vel por obter o arquivo f�sico
	 * 
	 * @param dataType - O tipo de dado para definir o tipo de arquivo
	 * @throws InvalidMigrationDataTypeException - Lan�a este exce��o se o tipo passado n�o corresponder aos tipos implementados
	 * @throws IOException - Lan�a esta exce��o se n�o for poss�vel ler o arquivo
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
	 * Obt�m os dados referentes �s colunas do arquivo
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
	 * Move o �ndice de leitura do arquivo para o primeiro registro
	 */
	public abstract void moveToFirst();

	/**
	 * Move o �ndice de leitura do arquivo para o �ltimo registro
	 */
	public abstract void moveToLast();

	/**
	 * Verifica se h� um pr�ximo registro
	 * 
	 * @return - true se houver um pr�ximo registro, false caso contr�rio
	 */
	public abstract boolean hasNext();

	/**
	 * Retorna o pr�ximo registro do arquivo
	 * 
	 * @return - pr�ximo registro como um Object
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
	 * Retorna o registro do arquivo recebendo como argumento o n�mero da linha
	 * 
	 * @param rowIndex - n�mero da linha
	 * @return - o registro como um Object
	 */
	protected abstract Object[] getRowData(int rowIndex);
	
	/**
	 * M�todo para verificar os nomes das colunas.<br><br>
	 * 
	 * Responsabilidades:<br>
	 * 1- Verificar se todas as colunas est�o presentes<br>
	 * 2- Verificar se os nomes das colunas est�o na ordem correta<br>
	 * 3- Verificar se o tipo de dado das colunas est� correto<br>
	 * 4- Lan�ar {@link InvalidCellTypeException} se o tipo de dado da coluna for inv�lido (n�o for String)<br>
	 * 5- Lan�ar {@link RequiredColumnNotFoundException} se alguma coluna obrigat�ria n�o for encontrada<br>
	 * 
	 * 
	 * @return - true se estiver tudo certo, false caso contr�rio
	 */
	public abstract boolean checkHeaderPolicy() throws InvalidCellTypeException, RequiredColumnNotFoundException;
	
	/**
	 * Verifica se as colunas possuem valores e se possuem o tipo de dado correto
	 * 
	 * @param row - A linha a ser verificada
	 * @param cellsLimit - limite de c�lulas a serem lidas
	 * @return - true se estiver tudo certo, false caso contr�rio
	 */
	public abstract boolean checkCellsPolicy(HSSFRow row, int cellsLimit);

}
