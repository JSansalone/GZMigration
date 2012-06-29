package br.com.gz.migration.policy;

/**
 * Enum que representa a categoria das colunas
 * 
 * @author Jonathan Sansalone
 *
 */
public enum EnColumnsCategory {

	/**
	 * Tabela estoque
	 */
	ESTOQUE("estoque"),
	
	/**
	 * Tabela estmix
	 */
	ESTOQUE_LOJA("estmix"),
	
	/**
	 * Tabela esttrib
	 */
	ESTOQUE_TRIBUTACAO("esttrib"),
	
	/**
	 * Tabela saldos
	 */
	ESTOQUE_SALDO("saldos"),
	
	/**
	 * Tabela clientes
	 */
	CLIENTES("clientes"),
	
	/**
	 * Tabela credor
	 */
	FORNECEDOR("credor"),
	
	/**
	 * Tabela depto
	 */
	DEPARTAMENTO("depto"),
	
	/**
	 * Tabela armacao
	 */
	ARMACAO("armacao"),
	
	/**
	 * Tabela grupo
	 */
	GRUPO("grupo"),
	
	/**
	 * Tabela marca
	 */
	MARCA("marca"),
	
	/**
	 * Tabela setor
	 */
	SETOR("setor");
	
	/**
	 * variável que guarda o nome da tabela
	 */
	private final String tableName;

	/**
	 * Construtor que guarda o nome da tabela
	 * 
	 * @param tableName - nome da tabela
	 */
	private EnColumnsCategory(String tableName){
		
		this.tableName = tableName;
		
	}
	
	/**
	 * Método que retorna o nome da tabela
	 * 
	 * @return - nome da tabela
	 */
	public String getTableName(){
		
		return this.tableName;
		
	}
	
}
