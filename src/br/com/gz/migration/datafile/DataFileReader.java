package br.com.gz.migration.datafile;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

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

		// se a linha estiver com null, preenche o array com a constante
		// DataFile.NULL_ROW
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
				// pega o tipo de dado
				cellType = cell.getCellType();
				// se n�o for texto, lan�a uma exception
				if (cellType != HSSFCell.CELL_TYPE_STRING) {
					names[i] = DataFile.INVALID_CELL_TYPE;
				} else {
					// guarda o valor da c�lula
					names[i] = cell.getStringCellValue().toUpperCase();
				}

			}

		}

		return names;

	}

	/**
	 * M�todo para retornar os valores das c�lulas da linha informada e at� o limite de c�lulas informado<br><br>
	 * 
	 * Responsabilidades:<br>
	 * 1- Obter os valores das colunas<br>
	 * 
	 * 
	 * @param row - linha a ser lida
	 * @param maximumColumns - limite de c�lulas a serem lidas
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

					values[i] = cell.getStringCellValue().toUpperCase().trim().equals("") ? DataFile.CELL_VALUE_NULL : cell.getStringCellValue().toUpperCase();
					break;

				case HSSFCell.CELL_TYPE_NUMERIC:

					Double aux = cell.getNumericCellValue();

					if (aux - Math.floor(aux) > 0)
						values[i] = new Double(aux);
					else
						values[i] = new Integer((int) Math.round(aux));
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

}
