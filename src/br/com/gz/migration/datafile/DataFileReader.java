package br.com.gz.migration.datafile;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Classe auxiliar que faz a leitura dos valores das células do arquivo .xls.
 * Suas responsabilidades quanto à validação dos dados são poucas.
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
	 * 2- Se não houver valor, usar DataFile.CELL_VALUE_NULL<br>
	 * 3- Se o tipo não for String, usar DataFile.INVALID_CELL_TYPE
	 * 
	 * @param maximumColumns
	 *            - máximo de colunas que devem ser lidas
	 * @return - nomes das colunas do arquivo
	 */
	public static String[] getHeader(HSSFRow row, int maximumColumns) {

		// célula
		HSSFCell cell = null;
		// tipo de dado da célula
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

			// pega a célula
			cell = row.getCell(i);
			// se for null lança uma exception
			if (cell == null) {
				names[i] = DataFile.CELL_VALUE_NULL;
				// senão
			} else {

				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				names[i] = cell.getStringCellValue().toUpperCase();

			}

		}

		return names;

	}

	/**
	 * Método para retornar os valores das células da linha informada e até o
	 * limite de células informado<br>
	 * <br>
	 * 
	 * Responsabilidades:<br>
	 * 1- Obter os valores das colunas<br>
	 * 
	 * 
	 * @param row
	 *            - linha a ser lida
	 * @param maximumColumns
	 *            - limite de células a serem lidas
	 * @return - os dados das células
	 */
	public static Object[] getCellValues(HSSFRow row, int maximumColumns) {

		// célula
		HSSFCell cell = null;
		// tipo de dado da célula
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

			// pega a célula
			cell = row.getCell(i);

			// se for null lança uma exception
			if (cell == null) {
				values[i] = DataFile.CELL_VALUE_NULL;
				// senão
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
	 * Verifica se a String passada representa um número
	 * 
	 * @param str
	 *            - String a ser testada
	 * @return - true se for um número, false caso contrário
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
	 * Método que verifica se existe arquivos com registros não inseridos
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
