package br.com.gz.migration;

/**
 * Enum que cont�m os tipos de dados suportados pelo aplicativo para fazer migra��es
 * 
 * @author Jonathan Sansalone
 *
 */
public enum EnMigrationDataType {

	PRODUTO("Produtos"), 
	CLIENTE("Clientes"), 
	FORNECEDOR("Fornecedores"), 
	NFENTRADA("Notas fiscais de entrada"), 
	NFSAIDA("Notas fiscais de sa�da"), 
	GRUPO("Grupos"), 
	MARCA("Marcas"), 
	DEPARTAMENTO("Departamentos"), 
	SETOR("Setores"), 
	CONTAPAGAR("Contas a pagar"), 
	CONTARECEBER("Contas a receber"), 
	MOVTOVENDA("Movimenta��es de venda"), 
	ARMACAO("Arma��es");
	
	/**
	 * Membro private que guarda a descri��o de cada tipo de dado
	 */
	private String description;
	
	/**
	 * Construtor private
	 * 
	 * @param desc - descri��o do tipo de dado
	 */
	private EnMigrationDataType(String desc) {
		
		this.description = desc;
		
	}
	
	/**
	 * Retorna a descri��o do tipo de dado
	 * 
	 * @return - a descri��o do tipo de dado
	 */
	public String getDescription(){
		
		return this.description;
		
	}

}
