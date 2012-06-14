package br.com.gz.migration;

import br.com.gz.migration.exception.InvalidSoftwareException;

public interface ISoftwareMutable {
	
	void chooseSoftware() throws InvalidSoftwareException;
	EnSoftware getSoftware();
	void setAvailableSoftwares(EnSoftware software);

}
