package br.com.gz.migration;

/**
 * Classe que representa um tipo de dado a ser migrado
 * 
 * @author Jonathan Sansalone
 *
 */
public class MigrationDataType {

	/**
	 * Tipo de dado
	 */
	private EnMigrationDataType type;
	
	/**
	 * Total
	 */
	private int total;
	
	/**
	 * Método que retorna o tipo de dado
	 * 
	 * @return - tipo de dado
	 */
	public EnMigrationDataType getType() {
		return type;
	}
	
	/**
	 * Método que define o tipo de dado
	 * 
	 * @param type - tipo de dado
	 */
	public void setType(EnMigrationDataType type) {
		this.type = type;
	}
	
	/**
	 * Método que retorna o total de dados
	 * 
	 * @return - total de dados
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * Método que define o total de dados
	 * 
	 * @param total - total de dados
	 */
	public void setTotal(int total) {
		this.total = total;
	}

}
