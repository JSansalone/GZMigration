package br.com.gz.migration.teste;

import java.io.IOException;
import java.util.ArrayList;

import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.migration.exception.InvalidCellTypeException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;
import br.com.gz.util.GZSoftwares;


public class Teste {

	public static void main(String[] args) {

//		HSSFWorkbook w = new HSSFWorkbook();
//		HSSFCell c = w.createSheet().createRow(0).createCell(0);
//		c.setCellValue("7891037010048");
//		System.out.println(c.getStringCellValue());
		
		ProdutoDataFile d=null;
		
		try {// estou usando a branch version2.1

			d = ProdutoDataFile.getInstance(GZSoftwares.MERCOFLEX);

			d.checkHeaderPolicy();

		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();

		} catch (RequiredColumnNotFoundException e) {
			// johnny Auto-generated catch block
			ArrayList<String> ar = d.getColumnsNotFound();
			for (String string : ar) {
				System.out.println(string);
			}
			System.out.println(e.getMessage());
		} catch (InvalidCellTypeException e) {
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
		
//		SQLDataProviderImpl impl = new SQLDataProviderImpl(EnSoftware.MERCOFLEX, EnSoftware.MERCOFLEX, null, null);
//		
//		ProdutoDataFile dataFile;
//		try {
//			dataFile = ProdutoDataFile.getInstance(GZSoftwares.MERCOFLEX);
//			ArrayList<Produto> ar = impl.getProduto(dataFile);
//			
//			for (Produto p : ar) {
//				System.out.printf("%-20s|%-20s\n",p.getCodigoInterno(),p.getCodigoDeBarras());
//			}
//			
//		} catch (IOException e) {
//			// johnny Auto-generated catch block
//			e.printStackTrace();
//		} catch (RequiredColumnNotFoundException e) {
//			// johnny Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	private static boolean isValid(boolean arg){
		return arg;
	}

}
