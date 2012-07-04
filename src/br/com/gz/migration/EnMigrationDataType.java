package br.com.gz.migration;

/**
 * Enum que contém os tipos de dados suportados pelo aplicativo para fazer migrações
 * 
 * @author Jonathan Sansalone
 *
 */
public enum EnMigrationDataType {

	PRODUTO("Produtos"), 
	CLIENTE("Clientes"), 
	FORNECEDOR("Fornecedores"), 
	NFENTRADA("Notas fiscais de entrada"), 
	NFSAIDA("Notas fiscais de saída"), 
	GRUPO("Grupos"), 
	MARCA("Marcas"), 
	DEPARTAMENTO("Departamentos"), 
	SETOR("Setores"), 
	CONTAPAGAR("Contas a pagar"), 
	CONTARECEBER("Contas a receber"), 
	MOVTOVENDA("Movimentações de venda"), 
	ARMACAO("Armações");
	
	/**
	 * Membro private que guarda a descrição de cada tipo de dado
	 */
	private String description;
	
	/**
	 * Construtor private
	 * 
	 * @param desc - descrição do tipo de dado
	 */
	private EnMigrationDataType(String desc) {
		
		this.description = desc;
		
	}
	
	/**
	 * Retorna a descrição do tipo de dado
	 * 
	 * @return - a descrição do tipo de dado
	 */
	public String getDescription(){
		
		return this.description;
		
	}

}
