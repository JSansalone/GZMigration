package br.com.gz.migration.teste;

import java.io.IOException;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.datafile.DataFile;
import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;

public class Teste {

	public static void main(String[] args) {

		ProdutoDataFile dataProduto;

		try {

			dataProduto = ProdutoDataFile.getInstance();

		} catch (IOException e) {

			System.out.println("Arquivo não encontrado!");
			
			return;

		}
		
		System.out.println(dataProduto.getTotalRows());

	}

}
