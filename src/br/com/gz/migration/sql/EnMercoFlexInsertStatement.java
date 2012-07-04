package br.com.gz.migration.sql;

import org.database.connection.DatabaseType;

import br.com.gz.bean.Produto;
import br.com.gz.migration.policy.EnColumnsCategory;
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;

public enum EnMercoFlexInsertStatement {

	/**
	 * esqueleto do insert na tabela estoque
	 */
	INSERT_ESTOQUE(EnColumnsCategory.ESTOQUE),

	/**
	 * esqueleto do insert na tabela estmix
	 */
	INSERT_ESTOQUE_LOJA(EnColumnsCategory.ESTOQUE_LOJA),

	/**
	 * esqueleto do insert na tabela esttrib
	 */
	INSERT_ESTOQUE_TRIBUTACAO(EnColumnsCategory.ESTOQUE_TRIBUTACAO),

	/**
	 * esqueleto do insert na tabela saldos
	 */
	INSERT_ESTOQUE_SALDO(EnColumnsCategory.ESTOQUE_SALDO),

	/**
	 * esqueleto do insert na tabela cliente
	 */
	INSERT_CLIENTE(EnColumnsCategory.CLIENTES),

	/**
	 * esqueleto do insert na tabela credor
	 */
	INSERT_FORNECEDOR(EnColumnsCategory.FORNECEDOR),

	/**
	 * esqueleto do insert na tabela depto
	 */
	INSERT_DEPARTAMENTO(EnColumnsCategory.DEPARTAMENTO),

	/**
	 * esqueleto do insert na tabela grupo
	 */
	INSERT_GRUPO(EnColumnsCategory.GRUPO),

	/**
	 * esqueleto do insert na tabela setor
	 */
	INSERT_SETOR(EnColumnsCategory.SETOR),

	/**
	 * esqueleto do insert na tabela armacao
	 */
	INSERT_ARMACAO(EnColumnsCategory.ARMACAO),

	/**
	 * esqueleto do insert na tabela marca
	 */
	INSERT_MARCA(EnColumnsCategory.MARCA);

	/**
	 * categoria do insert
	 */
	private final EnColumnsCategory category;

	/**
	 * Define a categoria do insert
	 * 
	 * @param category - categoria
	 */
	private EnMercoFlexInsertStatement(EnColumnsCategory category) {

		this.category = category;

	}

	/**
	 * Retorna o esqueleto do insert
	 * 
	 * @param dbType - tipo de banco de dados
	 * @return - insert
	 */
	public String getSQL(DatabaseType dbType) {

		StringBuilder sql = new StringBuilder();

		if (dbType == DatabaseType.MySQL) {

			sql.append("insert into " + category.getTableName());

			String columns = "";
			String values = "";
			String onDuplicate = "";

			for (EnMercoFlexRequiredColumns requiredColumn : EnMercoFlexRequiredColumns
					.values()) {

				for (EnColumnsCategory categ : requiredColumn.getCategories()) {

					if (categ == this.category) {

						columns += requiredColumn.getColumnName() + ",";
						values += "?,";

						if (onDuplicate.equals("")) {

							onDuplicate += " on duplicate key update "
									+ requiredColumn.getColumnName() + " = "
									+ requiredColumn.getColumnName();

						}

					}

				}

			}

			columns = "(" + columns.substring(0, columns.length() - 1) + ")";
			values = "(" + values.substring(0, values.length() - 1) + ")";

			sql.append(columns + " values" + values + onDuplicate);

		}else if(dbType == DatabaseType.MSSQL){
			
//			BEGIN TRAN
//			-- if row already exists in a table update it else insert new row
//			IF EXISTS (SELECT * FROM t1 WHERE id = 6)
//			BEGIN 
//			    -- see what locks are being held from select 
//			    SELECT  resource_type, request_mode, resource_description, 'ROW NOT IN TABLE - Exists - after select'
//			    FROM    sys.dm_tran_locks
//
//			    UPDATE  t1
//			    SET     name1 = 'name 6'
//			    WHERE   id = 6
//
//			    SELECT  resource_type, request_mode, resource_description, 'ROW NOT IN TABLE - Exists - after update'
//			    FROM    sys.dm_tran_locks
//			END 
//			ELSE
//			BEGIN 
//			    -- see what locks are being held from select 
//			    SELECT  resource_type, request_mode, resource_description, 'ROW NOT IN TABLE - Exists - after select'
//			    FROM    sys.dm_tran_locks
//
//			    INSERT INTO t1
//			    SELECT  6, 'name 6'
//			    
//			    SELECT  resource_type, request_mode, resource_description, 'ROW NOT IN TABLE - Exists - after insert'
//			    FROM    sys.dm_tran_locks
//			END 
//			ROLLBACK
			
			sql.append("begin tran ");
			sql.append("if not exists (select * from "+category.getTableName()+" where ");
			for(EnMercoFlexRequiredColumns rq : EnMercoFlexRequiredColumns.filterValues(this.category)){
				if(rq.isKey()){
					sql.append(rq.getColumnName()+"=? and ");
				}
			}
			sql.delete(sql.length()-5, sql.length());
			sql.append(")");
			sql.append(" begin ");
			sql.append("insert into " + category.getTableName());

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

			sql.append(columns + " values" + values);
			sql.append(" end ");
			sql.append("commit");
			
		}

		return sql.toString();

	}

}
