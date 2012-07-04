package br.com.gz.migration;

/**
 * Interface que auxilia na obtenção de informações após o término da migração
 * 
 * @author Jonathan Sansalone
 *
 */
public interface IMigrationResults {

	/**
	 * Método que retorna a quantidade de produtos novos cadastrados
	 * 
	 * @return - Quantidade de produtos
	 */
	int getCountRegisteredProdutos();
	
	/**
	 * Método que retorna a quantidade de produtos que já existem, mas que foram incluídos em outras lojas
	 * 
	 * @return - Quantidade de produtos
	 */
	int getCountIncludedProdutos();
	
	/**
	 * Método que retorna a quantidade de clientes cadastrados
	 * 
	 * @return - Quantidade de clientes
	 */
	int getCountClientes();
	
	/**
	 * Método que retorna a quantidade de fornecedores cadastrados
	 * 
	 * @return - Quantidade de fornecedores
	 */
	int getCountFornecedores();
	
	/**
	 * Método que retorna a quantidade de marcas cadastradas
	 * 
	 * @return - Quantidade de marcas
	 */
	int getCountMarca();
	
	/**
	 * Método que retorna a quantidade de departamentos cadastrados
	 * 
	 * @return - Quantidade de departamentos
	 */
	int getCountDepartamento();
	
	/**
	 * Método que retorna a quantidade de grupos cadastrados
	 * 
	 * @return - Quantidade de grupos
	 */
	int getCountGrupo();
	
	/**
	 * Método que retorna a quantidade de lojas cadastradas
	 * 
	 * @return - Quantidade de lojas
	 */
	@Deprecated
	int getCountLoja();
	
	/**
	 * Método que retorn a quantidade de armações cadastradas
	 * 
	 * @return - Quantidade de armações
	 */
	int getCountArmacao();
	
}
