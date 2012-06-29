package br.com.gz.migration;

/**
 * Interface que auxilia o Motor de migração ao terminar a migração
 * 
 * @author Jonathan Sansalone
 *
 */
interface IFinalizeMigration {

	/**
	 * Método que é executado quando a migração é concluída
	 */
	void goToLastStep();
	
}
