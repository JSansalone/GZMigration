package br.com.gz.migration.teste;

import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;

public class Teste {

	public static void main(String[] args) {

		for (EnMercoFlexRequiredColumns col : EnMercoFlexRequiredColumns.values()) {
			System.out.print(col.getLabel()+",");
		}

	}

}
