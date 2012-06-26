package br.com.gz.migration.datafile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;
import br.com.gz.util.GZSoftwares;

/**
 * Representa um arquivo de dados e permite manipular as informações contidas no
 * arquivo físico como: retornar os registros, percorrer os registros, contar a
 * quantidade de registros, etc.
 * 
 * As subclasses são responsáveis por implementar opcionalmente o singleton para
 * garantir a segurança do acesso ao arquivo de dados.
 * 
 * @author Jonathan Sansalone
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
	 * Nome do arquivo em uso
	 */
	private final String fileNameNoExt;
	//
	/**
	 * Posição atual no arquivo
	 */
	protected int currentIndex = 1;

	/**
	 * Posição do último registro do arquivo. Já inicializada pelo construtor
	 * chamando o método getTotalRows()
	 */
	protected int lastIndex;

	/**
	 * Guarda a quantidade de colunas obrigatórias para não pesquisar todas as
	 * vezes que precisar. É responsabilidade de cada subclasse atribuir à ela
	 * um valor
	 */
	protected int qtyRequiredColumns;

	/**
	 * Array que guarda todas as colunas obrigatórias. É responsabilidade de
	 * cada subclasse preencher esta variável com suas colunas obrigatórias
	 */
	protected EnMercoFlexRequiredColumns[] requiredColumns;

	/**
	 * Software que está sendo implantado
	 */
	protected GZSoftwares software;

	/**
	 * Variável que guarda todos os que não foram inseridos por não possuirem
	 * todos os valores obrigatórios ou com valores inválidos. Já inicializada
	 * pelo construtor
	 */
	protected HashMap<Integer, Object[]> notInserted;

	/**
	 * Variável que guarda todas as colunas não encontradas. Já inicializada
	 * pelo construtor
	 */
	protected ArrayList<String> notFound;

	/**
	 * Construtor que recebe um EnMigrationDataType e de acordo com este, lê o
	 * arquivo específico. Este construtor é responsável por obter o arquivo
	 * físico
	 * 
	 * @param dataType
	 *            - O tipo de dado para definir o tipo de arquivo
	 * @throws InvalidMigrationDataTypeException
	 *             - Lança este exceção se o tipo passado não corresponder aos
	 *             tipos implementados
	 * @throws IOException
	 *             - Lança esta exceção se não for possível ler o arquivo
	 * @throws RequiredColumnNotFoundException
	 * 			   - Lança esta exceção se não forem encontradas todas as colunas
	 */
	protected DataFile(GZSoftwares software, EnMigrationDataType dataType)
			throws InvalidMigrationDataTypeException, IOException{

		File file = new File("data", dataType.toString() + ".xls");

		if (file.exists()) {

			switch (dataType) {

			case PRODUTO:
				break;
			case DEPARTAMENTO:
				break;
			case GRUPO:
				break;
			case SETOR:
				break;
			case ARMACAO:
				break;
			case MARCA:
				break;
			case CLIENTE:
				break;
			case FORNECEDOR:
				break;

			default:

				throw new InvalidMigrationDataTypeException();

			}

			stream = new FileInputStream(file);
			dataSheet = new HSSFWorkbook(stream).getSheetAt(0);

		} else {

			throw new FileNotFoundException();

		}

		fileNameNoExt = dataType.toString();

		notInserted = new HashMap<Integer, Object[]>();

		notFound = new ArrayList<String>();

		this.software = software;

		lastIndex = getTotalRows();

	}

	/**
	 * Método que retorna o nome do arquivo em uso
	 * 
	 * @return- O nome do arquivo
	 */
	public final String getFileNameNoExt() {

		return this.fileNameNoExt;

	}

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
	 * @param currentIndex
	 *            - Índice atual
	 * @return - true se existir, false caso contrário
	 */
	protected abstract boolean hasNextAfter(int currentIndex);

	/**
	 * Retorna o próximo registro do arquivo
	 * 
	 * @return - próximo registro como um Object
	 * @throws ReachedTheEndOfFileException
	 *             - Se o fim do arquivo for alcançado
	 */
	public abstract Object next() throws ReachedTheEndOfFileException;

	/**
	 * Retorna o registro anterior ao atual do arquivo
	 * 
	 * @return - o registro anterior como um Object
	 * @throws ReachedTheStartOfFileException
	 *             - Se o início do arquivo for alcançado
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
	protected void close() {

		try {
			stream.close();
		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	protected final void writeNotInserteds() {

		File f = new File("data/" + getFileNameNoExt() + "_NOT_INSERTED.xls");

		if (!notInserted.isEmpty()) {

			DataFileWriter.writeRowBatch(f, DataFileReader.getHeader(
					dataSheet.getRow(0), qtyRequiredColumns), notInserted);

		}

	}

	/**
	 * Retorna o registro do arquivo recebendo como argumento o número da linha
	 * 
	 * @param rowIndex
	 *            - número da linha
	 * @return - o registro como um Object
	 */
	protected abstract Object[] getRowData(int rowIndex);

	/**
	 * Método para verificar os nomes das colunas.<br>
	 * <br>
	 * 
	 * Responsabilidades:<br>
	 * 1- Verificar se todas as colunas estão presentes<br>
	 * 2- Verificar se os nomes das colunas estão na ordem correta<br>
	 * 3- Verificar se o tipo de dado das colunas está correto<br>
	 * 4- Lançar {@link InvalidCellTypeException} se o tipo de dado da coluna
	 * for inválido (não for String)<br>
	 * 5- Lançar {@link RequiredColumnNotFoundException} se alguma coluna
	 * obrigatória não for encontrada<br>
	 * 
	 * 
	 * @return - true se estiver tudo certo, false caso contrário
	 * @throws InvalidCellTypeException
	 *             - Se o tipo de célula não for String
	 * @throws RequiredColumnNotFoundException
	 *             - Se faltar alguma coluna
	 */
	public abstract boolean checkHeaderPolicy()
			throws InvalidCellTypeException, RequiredColumnNotFoundException;

	/**
	 * Método que valida se o array de valores não possue as constantes
	 * CELL_VALUE_NULL, NULL_ROW ou INVALID_CELL_TYPE
	 * 
	 * @param values
	 *            - O array com valores
	 * @return - true se estiver tudo certo, false caso contrário
	 */
	public abstract boolean checkValuesPolicy(Object[] values);

	/**
	 * Método que retorna as colunas faltantes
	 * 
	 * @return - Array com as colunas faltantes
	 */
	public abstract ArrayList<String> getColumnsNotFound();

}
