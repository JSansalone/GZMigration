package br.com.gz.migration.teste;

import java.io.IOException;

import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.util.GZSoftwares;


public class Teste {

	public static void main(String[] args) {

		try {

			ProdutoDataFile d = ProdutoDataFile.getInstance(GZSoftwares.MERCOFLEX);

			d.teste();
//			System.out.println(d.getTotalRows());
			

		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();

		}

	}
	
	private static boolean isValid(boolean arg){
		return arg;
	}

}
