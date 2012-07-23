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

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.file.LogFile;
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;

/**
 * Classe que possui métodos úteis para gravar os registros não inseridos em
 * arquivos .xls
 * 
 * @author Jonathan Sansalone
 * 
 */
public class DataFileWriter {

	/**
	 * Método que grava os registros não inseridos em um arquivo .xls
	 * 
	 * @param file
	 *            - O arquivo que conterá os registros
	 * @param header
	 *            - Header com o nome das colunas
	 * @param rows
	 *            - Collection com os registros
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

	public static void createFile(EnMercoFlexRequiredColumns[] columns,
			EnMigrationDataType dataType) {

		try {

			File root = new File("data");
			if (!root.exists())
				root.mkdir();

			File f = new File(root, dataType.toString() + ".xls");

			HSSFWorkbook w = new HSSFWorkbook();
			HSSFRow r = w.createSheet().createRow(0);

			for (int i = 0; i < columns.length; i++) {
				r.createCell(i).setCellValue(columns[i].getLabel());
			}

			w.write(new FileOutputStream(f));

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * Método que remove os arquivos de registros não inseridos. Foi planejado
	 * para ser executado dentro de uma Thread separada ao iniciar a aplicação.
	 */
	public static void removeNotInsertedFiles() {

		File root = new File("data");

		if (root.exists()) {
			for (File xls : root.listFiles()) {
				if (xls.getName().endsWith("NOT_INSERTED.xls"))
					if (!xls.delete())
						LogFile.getInstance().writeInFile(
								"Could not delete the file " + xls.getName());
			}
		}

	}

}
