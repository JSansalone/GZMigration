package br.com.gz.migration.steps;

import javax.swing.JTextField;

/**
 * Interface que auxilia a valida��o dos componentes
 * 
 * @author Jonathan Sansalone
 *
 */
interface IValidation {

	/**
	 * Valida os componentes
	 * 
	 * @return true se estiver validado, false caso contr�rio
	 */
	boolean validateFields();
	
	/**
	 * Verifica se um JTextField est� vazio
	 * 
	 * @param field - componente a ser testado
	 * @return - true se estiver vazio, false caso contr�rio
	 */
	boolean isEmpty(JTextField field);
	
}
