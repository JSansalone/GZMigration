package br.com.gz.migration.teste;

import java.io.IOException;

import br.com.gz.migration.datafile.ProdutoDataFile;

public class Teste {

	public static void main(String[] args) {

		try {
			
			ProdutoDataFile d = ProdutoDataFile.getInstance();
			d.checkColumnsPolicy();
			
		} catch (IOException e) {
			
			// johnny Auto-generated catch block
			e.printStackTrace();
			
		}

	}

}
