package br.com.gz.migration.sql;

import br.com.gz.bean.Produto;
import br.com.gz.migration.policy.EnColumnsCategory;
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;

public enum EnDefaultInsertStatement {

	MERCOFLEX_INSERT_ESTOQUE(EnColumnsCategory.ESTOQUE),

	MERCOFLEX_INSERT_ESTOQUE_LOJA(EnColumnsCategory.ESTOQUE_LOJA),

	MERCOFLEX_INSERT_ESTOQUE_TRIBUTACAO(EnColumnsCategory.ESTOQUE_TRIBUTACAO),

	MERCOFLEX_INSERT_ESTOQUE_SALDO(EnColumnsCategory.ESTOQUE_SALDO),

	MERCOFLEX_INSERT_CLIENTE(EnColumnsCategory.CLIENTES),

	MERCOFLEX_INSERT_FORNECEDOR(EnColumnsCategory.FORNECEDOR),

	MERCOFLEX_INSERT_DEPARTAMENTO(EnColumnsCategory.DEPARTAMENTO),

	MERCOFLEX_INSERT_GRUPO(EnColumnsCategory.GRUPO),

	MERCOFLEX_INSERT_SETOR(EnColumnsCategory.SETOR),

	MERCOFLEX_INSERT_ARMACAO(EnColumnsCategory.ARMACAO),

	MERCOFLEX_INSERT_MARCA(EnColumnsCategory.MARCA);
	
	private final EnColumnsCategory category;

	private EnDefaultInsertStatement(EnColumnsCategory category){
		
		this.category = category;
		
	}
	
	public String getSQL(){
		
		String sql = "insert into "+category.getTableName();
		
		String columns="";
		String values="";
		
		for (EnMercoFlexRequiredColumns requiredColumn : EnMercoFlexRequiredColumns.values()) {
			
			for (EnColumnsCategory categ : requiredColumn.getCategories()) {
				
				if(categ == this.category){
					
					columns += requiredColumn.getColumnName() + ",";
					values += "?,";
					
				}
				
			}
			
		}
		
		columns = "(" + columns.substring(0, columns.length()-1) + ")";
		values = "(" + values.substring(0, values.length()-1) + ")";
		
		sql += columns + " values"+values;
		
		return sql;
		
	}

}
