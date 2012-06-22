package br.com.gz.migration.teste;

import java.io.IOException;

import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.util.GZSoftwares;


public class Teste {

	public static void main(String[] args) {

//		HSSFWorkbook w = new HSSFWorkbook();
//		HSSFCell c = w.createSheet().createRow(0).createCell(0);
//		c.setCellValue("7891037010048");
//		System.out.println(c.getStringCellValue());
		
		try {

			ProdutoDataFile d = ProdutoDataFile.getInstance(GZSoftwares.MERCOFLEX);

			d.teste();

		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();

		}
		
//		try {
//			
//			FileOutputStream fos = new FileOutputStream("data/nao_insere.xls");
//			HSSFWorkbook w = new HSSFWorkbook();
//			w.createSheet().createRow(0).createCell(0).setCellValue("teste");
//			w.write(fos);
//			
//		} catch (FileNotFoundException e) {
//			// johnny Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// johnny Auto-generated catch block
//			e.printStackTrace();
//		}
		

	}
	
	private static boolean isValid(boolean arg){
		return arg;
	}

}
