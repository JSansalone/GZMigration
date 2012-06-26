package br.com.gz.migration;

/**
 * Enum que cont�m os tipos de dados suportados pelo aplicativo para fazer migra��es
 * 
 * @author Jonathan
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
	
	private String description;
	
	private EnMigrationDataType(String desc) {
		
		this.description = desc;
		
	}
	
	public String getDescription(){
		
		return this.description;
		
	}

}
