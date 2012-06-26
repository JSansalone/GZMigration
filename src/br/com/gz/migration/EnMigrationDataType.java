package br.com.gz.migration;

/**
 * Enum que contém os tipos de dados suportados pelo aplicativo para fazer migrações
 * 
 * @author Jonathan
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
	
	private String description;
	
	private EnMigrationDataType(String desc) {
		
		this.description = desc;
		
	}
	
	public String getDescription(){
		
		return this.description;
		
	}

}
