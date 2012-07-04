package br.com.gz.migration;

/**
 * Interface que auxilia na obten��o de informa��es ap�s o t�rmino da migra��o
 * 
 * @author Jonathan Sansalone
 *
 */
public interface IMigrationResults {

	/**
	 * M�todo que retorna a quantidade de produtos novos cadastrados
	 * 
	 * @return - Quantidade de produtos
	 */
	int getCountRegisteredProdutos();
	
	/**
	 * M�todo que retorna a quantidade de produtos que j� existem, mas que foram inclu�dos em outras lojas
	 * 
	 * @return - Quantidade de produtos
	 */
	int getCountIncludedProdutos();
	
	/**
	 * M�todo que retorna a quantidade de clientes cadastrados
	 * 
	 * @return - Quantidade de clientes
	 */
	int getCountClientes();
	
	/**
	 * M�todo que retorna a quantidade de fornecedores cadastrados
	 * 
	 * @return - Quantidade de fornecedores
	 */
	int getCountFornecedores();
	
	/**
	 * M�todo que retorna a quantidade de marcas cadastradas
	 * 
	 * @return - Quantidade de marcas
	 */
	int getCountMarca();
	
	/**
	 * M�todo que retorna a quantidade de departamentos cadastrados
	 * 
	 * @return - Quantidade de departamentos
	 */
	int getCountDepartamento();
	
	/**
	 * M�todo que retorna a quantidade de grupos cadastrados
	 * 
	 * @return - Quantidade de grupos
	 */
	int getCountGrupo();
	
	/**
	 * M�todo que retorna a quantidade de lojas cadastradas
	 * 
	 * @return - Quantidade de lojas
	 */
	@Deprecated
	int getCountLoja();
	
	/**
	 * M�todo que retorn a quantidade de arma��es cadastradas
	 * 
	 * @return - Quantidade de arma��es
	 */
	int getCountArmacao();
	
}
