package br.com.gz.migration.datafile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.omg.PortableInterceptor.INACTIVE;

import br.com.gz.bean.Produto;
import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidCellTypeException;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;
import br.com.gz.migration.policy.EnColumnsCategory;
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;
import br.com.gz.util.GZSoftwares;
import br.com.gz.util.MercoFlexFormat;

/**
 * Representa um arquivo de dados para ler os registros dos produtos. Os métodos
 * herdados devem ser implementados especificamente para trabalhar com estes
 * registros.
 * 
 * @author Jonathan
 * 
 */
public class ProdutoDataFile extends DataFile {

	/**
	 * Instância da classe.
	 */
	private static ProdutoDataFile instance;

	/**
	 * Posição atual no arquivo
	 */
	private int currentIndex = 1;

	private final int qtyRequiredColumns;

	private final EnMercoFlexRequiredColumns[] requiredColumns;

	/**
	 * Construtor default para passar o tipo de dado para o construtor da
	 * superclasse
	 * 
	 * @throws IOException
	 *             - Se não conseguir ler o arquivo
	 * @throws InvalidMigrationDataTypeException
	 *             - Se o tipo de dado não for suportado pela superclasse
	 */
	private ProdutoDataFile() throws IOException,
			InvalidMigrationDataTypeException {

		super(EnMigrationDataType.PRODUTO);

		requiredColumns = EnMercoFlexRequiredColumns.filterValues(
				EnColumnsCategory.ESTOQUE, EnColumnsCategory.ESTOQUE_LOJA,
				EnColumnsCategory.ESTOQUE_SALDO,
				EnColumnsCategory.ESTOQUE_TRIBUTACAO);

		qtyRequiredColumns = requiredColumns.length;

	}

	/**
	 * Método que implementa o singleton. Garante que em toda a execução da
	 * aplicação só exista uma instância dessa classe
	 * 
	 * @return - A instância da classe
	 * @throws IOException
	 *             - Se não conseguir ler o arquivo
	 */
	public static ProdutoDataFile getInstance() throws IOException {

		if (instance == null) {

			try {

				instance = new ProdutoDataFile();

			} catch (IOException e) {

				throw e;

			} catch (InvalidMigrationDataTypeException e) {

				// johnny Auto-generated catch block
				e.printStackTrace();

			}

		}

		return instance;

	}

	@Override
	public DataFileMetaData getMetaData() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRows() {

		int i = 0;

		return i;

	}

	@Override
	public void moveToFirst() {
		// johnny Auto-generated method stub

	}

	@Override
	public void moveToLast() {
		// johnny Auto-generated method stub

	}

	@Override
	public boolean hasNext() {

		Object[] data = getRowData(currentIndex);

		for (Object o : data) {
			if (!o.equals(NULL_ROW) && !o.equals(CELL_VALUE_NULL))
				return true;
		}

		return false;

	}

	@Override
	public Object next() {
		return getRowData(currentIndex++);
	}

	@Override
	public Object previous() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Object> getAll() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getRowData(int rowIndex) {

		return DataFileReader.getCellValues(dataSheet.getRow(rowIndex),
				qtyRequiredColumns);

		// return null;
	}

	@Override
	public boolean checkHeaderPolicy() throws InvalidCellTypeException,
			RequiredColumnNotFoundException {

		// obtendo as colunas do header
		String[] columnsFromFile = DataFileReader.getHeader(
				dataSheet.getRow(0), qtyRequiredColumns);

		ArrayList<String> notFound = new ArrayList<String>();
		ArrayList<String> invalidType = new ArrayList<String>();

		// verificando se estão na ordem correta
		for (int i = 0; i < qtyRequiredColumns; i++) {

			if (requiredColumns[i].getLabel().equals(columnsFromFile[i])) {

			} else {

				if (columnsFromFile[i].equals(DataFile.INVALID_CELL_TYPE)) {

					invalidType.add(requiredColumns[i].getLabel());

				} else {

					notFound.add(requiredColumns[i].getLabel());

				}

			}

		}

		if (!notFound.isEmpty()) {

			String message = "As seguintes colunas não foram encontradas: ";
			for (String s : notFound) {
				message += s + ", ";
			}
			message = message.substring(0, message.length() - 2) + ".";
			throw new RequiredColumnNotFoundException(message);

		} else {

			if (!invalidType.isEmpty()) {

				String message = "Os tipos de dados das seguintes colunas são inválidos: ";
				for (String s : invalidType) {
					message += s + ", ";
				}
				message = message.substring(0, message.length() - 2) + ".";
				throw new InvalidCellTypeException(message);

			}

		}

		return true;

	}

	@Override
	public boolean checkCellsPolicy(HSSFRow row, int cellsLimit) {

		return false;

	}

	public void teste() {

		while (hasNext()) {
			Object[] data = (Object[])next();

			for (Object string : data) {
				System.out.printf("%-20s",string);
			}
			System.out.println();
		//	System.out.println("\nhas next=" + hasNext());
		}

	}

}
