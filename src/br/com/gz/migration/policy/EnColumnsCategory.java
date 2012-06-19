package br.com.gz.migration.policy;

public enum EnColumnsCategory {

	ESTOQUE("estoque"),
	ESTOQUE_LOJA("estmix"),
	ESTOQUE_TRIBUTACAO("esttrib"),
	ESTOQUE_SALDO("saldos"),
	CLIENTES("clientes"),
	FORNECEDOR("credor"),
	DEPARTAMENTO("depto"),
	ARMACAO("armacao"),
	GRUPO("grupo"),
	MARCA("marca"),
	SETOR("setor");
	
	private final String tableName;

	private EnColumnsCategory(String tableName){
		
		this.tableName = tableName;
		
	}
	
	public String getTableName(){
		
		return this.tableName;
		
	}
	
}
