package br.com.gz.migration.teste;

import org.database.connection.DatabaseType;

import br.com.gz.migration.sql.EnMercoFlexInsertStatement;

public class Teste {

	public static void main(String[] args) {

		for (EnMercoFlexInsertStatement en : EnMercoFlexInsertStatement.values()) {
			
			System.out.println(en.getSQL(DatabaseType.MySQL));
			
		}
		
	}

}
