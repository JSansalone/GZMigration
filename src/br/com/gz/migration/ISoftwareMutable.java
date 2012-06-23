package br.com.gz.migration;

import br.com.gz.migration.exception.InvalidSoftwareException;
import br.com.gz.util.GZSoftwares;

public interface ISoftwareMutable {
	
	void chooseSoftware() throws InvalidSoftwareException;
	GZSoftwares getSoftware();
	void setAvailableSoftwares(GZSoftwares software);

}
