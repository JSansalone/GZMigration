package br.com.gz.migration.steps;

import javax.swing.JTextField;

/**
 * Interface que auxilia a validação dos componentes
 * 
 * @author Jonathan Sansalone
 *
 */
interface IValidation {

	/**
	 * Valida os componentes
	 * 
	 * @return true se estiver validado, false caso contrário
	 */
	boolean validateFields();
	
	/**
	 * Verifica se um JTextField está vazio
	 * 
	 * @param field - componente a ser testado
	 * @return - true se estiver vazio, false caso contrário
	 */
	boolean isEmpty(JTextField field);
	
}
