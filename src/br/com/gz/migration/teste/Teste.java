package br.com.gz.migration.teste;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Teste {

	public static void main(String[] args) {

		new Teste();

	}

	public Teste() {

		try {
			
			HSSFWorkbook w = new HSSFWorkbook();
			w.createSheet().createRow(0).createCell(0).setCellValue("11/11/2011");
			w.write(new FileOutputStream("data/teste.xls"));
			
			w = new HSSFWorkbook(new FileInputStream("data/teste.xls"));
			
			Date d = new Date(w.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
			
			System.out.println(d.toString());
			
		} catch (FileNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

	}

}
