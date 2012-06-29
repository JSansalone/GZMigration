package br.com.gz.migration.report;

/**
 * Classe bean que representa um tipo de dado usado na migração. Esta classe alimentará o relatório
 * 
 * @author Jonathan Sansalone
 *
 */
public class MigrationBean {

	/**
	 * Descrição do tipo de dado
	 */
	private String nome;
	
	/**
	 * Total de registros novos cadastrados
	 */
	private int totalCadastrado;
	
	/**
	 * Total de registros já existentes, mas incluídos em outras lojas
	 */
	private int totalIncluido;
	
	/**
	 * Método que retorna a descrição do tipo de dado
	 * 
	 * @return - descrição do tipo de dado
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Método que define a descrição do tipo de dado
	 * 
	 * @param nome - Descrição do tipo de dado
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Método que retorna o total de registros cadastrados
	 * 
	 * @return - total de registros cadastrados
	 */
	public int getTotalCadastrado() {
		return totalCadastrado;
	}
	
	/**
	 * Método que define o total de registros novos cadastrados
	 * 
	 * @param totalCadastrado - total de registros cadastrados
	 */
	public void setTotalCadastrado(int totalCadastrado) {
		this.totalCadastrado = totalCadastrado;
	}
	
	/**
	 * Método que retorna o total de registros incluídos
	 * 
	 * @return - total de registros incluídos
	 */
	public int getTotalIncluido() {
		return totalIncluido;
	}
	
	/**
	 * Método que define o total de registros incluídos
	 * 
	 * @param totalIncluido - total de registros incluídos
	 */
	public void setTotalIncluido(int totalIncluido) {
		this.totalIncluido = totalIncluido;
	}
	
}
