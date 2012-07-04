package br.com.gz.migration;

import br.com.gz.migration.exception.InvalidSoftwareException;
import br.com.gz.util.GZSoftwares;

/**
 * Interface que auxilia na escolha do software a ser utilizado
 * 
 * @author Jonathan Sansalone
 *
 */
public interface ISoftwareMutable {
	
	/**
	 * M�todo que trabalha com o software escolhido
	 * 
	 * @throws InvalidSoftwareException - se o software escolhido for inv�lido
	 */
	void chooseSoftware() throws InvalidSoftwareException;
	
	/**
	 * M�todo que retorna o software utilizado
	 * 
	 * @return - o software utilizado
	 */
	GZSoftwares getSoftware();
	
	/**
	 * M�todo que escolhe os software compat�veis com o software passado
	 * 
	 * @param software - software passado
	 */
	@Deprecated
	void setAvailableSoftwares(GZSoftwares software);

}
