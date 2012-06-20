package br.com.gz.migration.datafile;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;

import br.com.gz.bean.Produto;
import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidCellStyleException;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;
import br.com.gz.migration.policy.EnColumnsCategory;
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;
import br.com.gz.util.GZSoftwares;
import br.com.gz.util.MercoFlexFormat;

public class ProdutoDataFile extends DataFile {

	private static ProdutoDataFile instance;

	private int currentIndex = 0;

	private ProdutoDataFile() throws IOException,
			InvalidMigrationDataTypeException {

		super(EnMigrationDataType.PRODUTO);

	}

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

		try {

			double d = dataSheet.getRow(currentIndex + 1).getCell(0)
					.getNumericCellValue();

			if (d > 0)
				return true;
			else
				return false;

		} catch (NullPointerException e) {

			return false;

		}

	}

	@Override
	public Object next() {
		return null;
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
	protected Object getRowData(int rowIndex) {

		Produto p = new Produto(GZSoftwares.MERCOFLEX);

		p.setLoja(new Integer(new Double(dataSheet.getRow(rowIndex).getCell(0)
				.getNumericCellValue()).toString()));

		return null;
	}

	@Override
	public boolean checkColumnsPolicy() {

		EnMercoFlexRequiredColumns[] requiredColumns = EnMercoFlexRequiredColumns
				.filterValues(EnColumnsCategory.ESTOQUE,
						EnColumnsCategory.ESTOQUE_LOJA,
						EnColumnsCategory.ESTOQUE_SALDO,
						EnColumnsCategory.ESTOQUE_TRIBUTACAO);

		String[] columnsFromFile = getHeader(requiredColumns.length);

		for (String string : columnsFromFile) {
			System.out.print(string + "|");
		}

		return true;

	}

	@Override
	protected String[] getHeader(int maximumColumns) {

		// célula
		HSSFCell cell = null;
		// tipo de dado da célula
		int cellType = 0;
		// array de String para guardar os nomes
		String[] names = new String[maximumColumns];

		for (int i = 0; i < maximumColumns; i++) {

			// pega a célula
			cell = dataSheet.getRow(0).getCell(i);
			// se for null lança uma exception
			if (cell == null) {
				names[i] = DataFile.CELL_VALUE_NULL;
				// senão
			} else {
				// pega o tipo de dado
				cellType = cell.getCellType();
				// se não for texto, lança uma exception
				if (cellType != HSSFCell.CELL_TYPE_STRING) {
					names[i] = DataFile.INVALID_CELL_TYPE;
				} else {
					// guarda o valor da célula
					names[i] = cell.getStringCellValue().toUpperCase();
				}

			}

		}

		return names;

	}

}
