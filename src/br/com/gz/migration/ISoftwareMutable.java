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
	 * Método que trabalha com o software escolhido
	 * 
	 * @throws InvalidSoftwareException - se o software escolhido for inválido
	 */
	void chooseSoftware() throws InvalidSoftwareException;
	
	/**
	 * Método que retorna o software utilizado
	 * 
	 * @return - o software utilizado
	 */
	GZSoftwares getSoftware();
	
	/**
	 * Método que escolhe os software compatíveis com o software passado
	 * 
	 * @param software - software passado
	 */
	@Deprecated
	void setAvailableSoftwares(GZSoftwares software);

}
