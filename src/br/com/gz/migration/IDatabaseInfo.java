package br.com.gz.migration;

import org.database.connection.DatabaseConfigurations;

public interface IDatabaseInfo {

	DatabaseConfigurations getDatabaseInfo();
	EnSoftware getSoftware();
	
}
