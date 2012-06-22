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
import br.com.gz.migration.exception.ReachedTheEndOfFileException;
import br.com.gz.migration.exception.ReachedTheStartOfFileException;
import br.com.gz.migration.exception.RequiredColumnNotFilledException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;

/**
 * Representa um arquivo de dados e permite manipular as informações contidas no arquivo físico como: retornar os registros, percorrer os registros,
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
	 * Constante para representar colunas com tipo de dado inválido
	 */
	public static final String INVALID_CELL_TYPE = "<invalid_type>";
	
	/**
	 * Constante que representa uma linha totalmente vazia
	 */
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
	 * Construtor que recebe um EnMigrationDataType e de acordo com este, lê o arquivo específico. Este construtor é responsável por obter o arquivo físico
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
	public abstract Object first();

	/**
	 * Move o índice de leitura do arquivo para o último registro
	 */
	public abstract Object last();

	/**
	 * Verifica se há um próximo registro
	 * 
	 * @return - true se houver um próximo registro, false caso contrário
	 */
	public abstract boolean hasNext();
	
	/**
	 * Verifica se há um registro anterior ao atual
	 * 
	 * @return - true se houver um registro anterior, false caso contrário
	 */
	public abstract boolean hasPrevious();
	
	/**
	 * Verifica se há um próximo registro após o índice informado
	 * 
	 * @param currentIndex - Índice atual
	 * @return - true se existir, false caso contrário
	 */
	protected abstract boolean hasNextAfter(int currentIndex);

	/**
	 * Retorna o próximo registro do arquivo
	 * 
	 * @return - próximo registro como um Object
	 * @throws ReachedTheEndOfFileException - Se o fim do arquivo for alcançado
	 */
	public abstract Object next() throws ReachedTheEndOfFileException;

	/**
	 * Retorna o registro anterior ao atual do arquivo
	 * 
	 * @return - o registro anterior como um Object
	 * @throws ReachedTheStartOfFileException - Se o início do arquivo for alcançado
	 */
	public abstract Object previous() throws ReachedTheStartOfFileException;

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
	protected abstract Object[] getRowData(int rowIndex);
	
	/**
	 * Método para verificar os nomes das colunas.<br><br>
	 * 
	 * Responsabilidades:<br>
	 * 1- Verificar se todas as colunas estão presentes<br>
	 * 2- Verificar se os nomes das colunas estão na ordem correta<br>
	 * 3- Verificar se o tipo de dado das colunas está correto<br>
	 * 4- Lançar {@link InvalidCellTypeException} se o tipo de dado da coluna for inválido (não for String)<br>
	 * 5- Lançar {@link RequiredColumnNotFoundException} se alguma coluna obrigatória não for encontrada<br>
	 * 
	 * 
	 * @return - true se estiver tudo certo, false caso contrário
	 * @throws InvalidCellTypeException - Se o tipo de célula não for String
	 * @throws RequiredColumnNotFoundException - Se faltar alguma coluna
	 */
	public abstract boolean checkHeaderPolicy() throws InvalidCellTypeException, RequiredColumnNotFoundException;
	
	/**
	 * Método que valida se o array de valores não possue as constantes CELL_VALUE_NULL, NULL_ROW ou INVALID_CELL_TYPE
	 * 
	 * @param values - O array com valores
	 * @return - true se estiver tudo certo, false caso contrário
	 */
	public abstract boolean checkValuesPolicy(Object[] values);

}
