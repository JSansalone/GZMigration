package br.com.gz.migration.report;

/**
 * Classe bean que representa um tipo de dado usado na migra��o. Esta classe alimentar� o relat�rio
 * 
 * @author Jonathan Sansalone
 *
 */
public class MigrationBean {

	/**
	 * Descri��o do tipo de dado
	 */
	private String nome;
	
	/**
	 * Total de registros novos cadastrados
	 */
	private int totalCadastrado;
	
	/**
	 * Total de registros j� existentes, mas inclu�dos em outras lojas
	 */
	private int totalIncluido;
	
	/**
	 * M�todo que retorna a descri��o do tipo de dado
	 * 
	 * @return - descri��o do tipo de dado
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * M�todo que define a descri��o do tipo de dado
	 * 
	 * @param nome - Descri��o do tipo de dado
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * M�todo que retorna o total de registros cadastrados
	 * 
	 * @return - total de registros cadastrados
	 */
	public int getTotalCadastrado() {
		return totalCadastrado;
	}
	
	/**
	 * M�todo que define o total de registros novos cadastrados
	 * 
	 * @param totalCadastrado - total de registros cadastrados
	 */
	public void setTotalCadastrado(int totalCadastrado) {
		this.totalCadastrado = totalCadastrado;
	}
	
	/**
	 * M�todo que retorna o total de registros inclu�dos
	 * 
	 * @return - total de registros inclu�dos
	 */
	public int getTotalIncluido() {
		return totalIncluido;
	}
	
	/**
	 * M�todo que define o total de registros inclu�dos
	 * 
	 * @param totalIncluido - total de registros inclu�dos
	 */
	public void setTotalIncluido(int totalIncluido) {
		this.totalIncluido = totalIncluido;
	}
	
}
