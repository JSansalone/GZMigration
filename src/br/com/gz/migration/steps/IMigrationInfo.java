package br.com.gz.migration.steps;

/**
 * Interface que auxilia o motor de migra��o a atualizar a barra de progresso. A
 * classe MigrationInfoPanel deve implementar esta interface e passar a si mesmo
 * como par�metro para o motor de migra��o atualizar a barra de progresso.
 * 
 * @author Jonathan Sansalone
 * 
 */
public interface IMigrationInfo {

	/**
	 * Define o total de valores para a barra de progresso
	 * 
	 * @param num - total
	 */
	void setTotal(int num);

	/**
	 * Atualiza o valor da barra de progresso
	 * 
	 * @param num - valor
	 */
	void setValue(int num);

	/**
	 * Define o texto a ser mostrado acima da barra de progresso
	 * 
	 * @param text - texto
	 */
	void setText(String text);

}
