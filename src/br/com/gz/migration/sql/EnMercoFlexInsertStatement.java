package br.com.gz.migration.sql;

import org.database.connection.DatabaseType;

import br.com.gz.bean.Produto;
import br.com.gz.migration.policy.EnColumnsCategory;
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;

public enum EnMercoFlexInsertStatement {

	INSERT_ESTOQUE(EnColumnsCategory.ESTOQUE),

	INSERT_ESTOQUE_LOJA(EnColumnsCategory.ESTOQUE_LOJA),

	INSERT_ESTOQUE_TRIBUTACAO(EnColumnsCategory.ESTOQUE_TRIBUTACAO),

	INSERT_ESTOQUE_SALDO(EnColumnsCategory.ESTOQUE_SALDO),

	INSERT_CLIENTE(EnColumnsCategory.CLIENTES),

	INSERT_FORNECEDOR(EnColumnsCategory.FORNECEDOR),

	INSERT_DEPARTAMENTO(EnColumnsCategory.DEPARTAMENTO),

	INSERT_GRUPO(EnColumnsCategory.GRUPO),

	INSERT_SETOR(EnColumnsCategory.SETOR),

	INSERT_ARMACAO(EnColumnsCategory.ARMACAO),

	INSERT_MARCA(EnColumnsCategory.MARCA);

	private final EnColumnsCategory category;

	private EnMercoFlexInsertStatement(EnColumnsCategory category) {

		this.category = category;

	}

	public String getSQL(DatabaseType dbType) {

		String sql = "";
		
		if (dbType == DatabaseType.MySQL) {

			sql = "insert into " + category.getTableName();

			String columns = "";
			String values = "";

			for (EnMercoFlexRequiredColumns requiredColumn : EnMercoFlexRequiredColumns
					.values()) {

				for (EnColumnsCategory categ : requiredColumn.getCategories()) {

					if (categ == this.category) {

						columns += requiredColumn.getColumnName() + ",";
						values += "?,";

					}

				}

			}

			columns = "(" + columns.substring(0, columns.length() - 1) + ")";
			values = "(" + values.substring(0, values.length() - 1) + ")";

			sql += columns + " values" + values;

		}

		return sql;

	}

}
