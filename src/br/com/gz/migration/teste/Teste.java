package br.com.gz.migration.teste;

import br.com.gz.migration.sql.EnDefaultInsertStatement;

public class Teste {

	public static void main(String[] args) {

		for (EnDefaultInsertStatement en : EnDefaultInsertStatement.values()) {
			
			System.out.println(en.getSQL());
			
		}
		
	}

}
