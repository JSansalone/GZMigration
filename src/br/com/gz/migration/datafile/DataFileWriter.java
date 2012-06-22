package br.com.gz.migration.datafile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Classe que possui métodos úteis para gravar os registros não inseridos em
 * arquivos .xls
 * 
 * @author Jonathan
 * 
 */
public class DataFileWriter {

	/**
	 * Método que grava os registros não inseridos em um arquivo .xls
	 * 
	 * @param file - O arquivo que conterá os registros
	 * @param header - Header com o nome das colunas
	 * @param rows - Collection com os registros
	 */
	public static void writeRowBatch(File file, Object[] header,
			HashMap<Integer, Object[]> rows) {

		try {

			FileOutputStream fos = new FileOutputStream(file);

			HSSFWorkbook w = new HSSFWorkbook();

			HSSFSheet sheet = w.createSheet();

			HSSFRow r;

			int i = 1;
			int j = 0;

			Collection c = rows.values();

			r = sheet.createRow(0);
			for (Object o : header) {

				r.createCell(j++).setCellValue((String) o);

			}

			j = 0;
			for (Object o : c) {
				Object[] ooo = (Object[]) o;
				r = sheet.createRow(i++);
				for (Object oo : ooo) {
					r.createCell(j++).setCellValue(
							(oo.toString().equals(DataFile.CELL_VALUE_NULL)
									|| oo.toString().equals(
											DataFile.INVALID_CELL_TYPE) || oo
									.toString().equals(DataFile.NULL_ROW)) ? ""
									: oo.toString());
				}
				j = 0;
			}

			w.write(fos);
			fos.close();

		} catch (FileNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

	}

}
