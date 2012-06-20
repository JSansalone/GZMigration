package br.com.gz.migration.teste;

import java.io.IOException;
import java.math.BigInteger;
import java.text.NumberFormat;

import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.migration.exception.InvalidCellTypeException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;

public class Teste {

	public static void main(String[] args) {

		try {

			ProdutoDataFile d = ProdutoDataFile.getInstance();

			d.teste();

		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();

		}

	}

}
