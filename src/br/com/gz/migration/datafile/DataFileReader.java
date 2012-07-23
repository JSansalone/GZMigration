package br.com.gz.migration.datafile;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Classe auxiliar que faz a leitura dos valores das c�lulas do arquivo .xls.
 * Suas responsabilidades quanto � valida��o dos dados s�o poucas.
 * 
 * @author Jonathan Sansalone
 * 
 */
public class DataFileReader {

	/**
	 * Retorna os nomes das colunas do arquivo<br>
	 * <br>
	 * 
	 * Responsabilidades:<br>
	 * 1- Retornar os valores de acordo com o limite determinado<br>
	 * 2- Se n�o houver valor, usar DataFile.CELL_VALUE_NULL<br>
	 * 3- Se o tipo n�o for String, usar DataFile.INVALID_CELL_TYPE
	 * 
	 * @param maximumColumns
	 *            - m�ximo de colunas que devem ser lidas
	 * @return - nomes das colunas do arquivo
	 */
	public static String[] getHeader(HSSFRow row, int maximumColumns) {

		// c�lula
		HSSFCell cell = null;
		// tipo de dado da c�lula
		int cellType = 0;

		if (maximumColumns < 0)
			maximumColumns = 1;

		// array de String para guardar os nomes
		String[] names = new String[maximumColumns];

		if (row == null) {

			for (int i = 0; i < maximumColumns; i++) {
				names[i] = DataFile.NULL_ROW;
			}

			return names;

		}

		for (int i = 0; i < maximumColumns; i++) {

			// pega a c�lula
			cell = row.getCell(i);
			// se for null lan�a uma exception
			if (cell == null) {
				names[i] = DataFile.CELL_VALUE_NULL;
				// sen�o
			} else {

				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				names[i] = cell.getStringCellValue().toUpperCase();

			}

		}

		return names;

	}

	/**
	 * M�todo para retornar os valores das c�lulas da linha informada e at� o
	 * limite de c�lulas informado<br>
	 * <br>
	 * 
	 * Responsabilidades:<br>
	 * 1- Obter os valores das colunas<br>
	 * 
	 * 
	 * @param row
	 *            - linha a ser lida
	 * @param maximumColumns
	 *            - limite de c�lulas a serem lidas
	 * @return - os dados das c�lulas
	 */
	public static Object[] getCellValues(HSSFRow row, int maximumColumns) {

		// c�lula
		HSSFCell cell = null;
		// tipo de dado da c�lula
		int cellType = 0;

		if (maximumColumns < 0)
			maximumColumns = 1;

		// array de String para guardar os nomes
		Object[] values = new Object[maximumColumns];

		if (row == null) {

			for (int i = 0; i < maximumColumns; i++) {
				values[i] = DataFile.NULL_ROW;
			}

			return values;

		}

		for (int i = 0; i < maximumColumns; i++) {

			// pega a c�lula
			cell = row.getCell(i);

			// se for null lan�a uma exception
			if (cell == null) {
				values[i] = DataFile.CELL_VALUE_NULL;
				// sen�o
			} else {

				cellType = cell.getCellType();

				switch (cellType) {
				case HSSFCell.CELL_TYPE_STRING:

					values[i] = cell.getStringCellValue().toUpperCase().trim()
							.equals("") ? DataFile.CELL_VALUE_NULL : cell
							.getStringCellValue().toUpperCase();

					break;

				case HSSFCell.CELL_TYPE_NUMERIC:

					if (HSSFDateUtil.isCellDateFormatted(cell)) {

						values[i] = cell.getDateCellValue();
						break;

					}

					cell.setCellType(HSSFCell.CELL_TYPE_STRING);

					values[i] = cell.getStringCellValue().equals("") ? DataFile.CELL_VALUE_NULL
							: cell.getStringCellValue();

					break;

				case HSSFCell.CELL_TYPE_BLANK:
					values[i] = DataFile.CELL_VALUE_NULL;
					break;

				default:
					values[i] = DataFile.INVALID_CELL_TYPE;
					break;
				}

			}

		}

		return values;

	}

	/**
	 * Verifica se a String passada representa um n�mero
	 * 
	 * @param str
	 *            - String a ser testada
	 * @return - true se for um n�mero, false caso contr�rio
	 */
	@Deprecated
	private static boolean isNumber(String str) {

		try {
			Double d = Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * M�todo que verifica se existe arquivos com registros n�o inseridos
	 * 
	 * @return - true se houver, false caso contrario
	 */
	public static boolean hasNotInsertedFiles() {

		File root = new File("data");

		if (root.exists()) {
			for (File xls : root.listFiles()) {
				if (xls.getName().endsWith("NOT_INSERTED.xls")) {
					return true;
				}
			}
		}

		return false;

	}

}
