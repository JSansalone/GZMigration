package br.com.gz.migration;

/**
 * Interface que auxilia o Motor de migra��o ao terminar a migra��o
 * 
 * @author Jonathan Sansalone
 *
 */
interface IFinalizeMigration {

	/**
	 * M�todo que � executado quando a migra��o � conclu�da
	 */
	void goToLastStep();
	
}
