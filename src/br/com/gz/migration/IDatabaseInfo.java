package br.com.gz.migration;

import org.database.connection.DatabaseConfigurations;

import br.com.gz.util.GZSoftwares;

public interface IDatabaseInfo {

	DatabaseConfigurations getDatabaseInfo();
	GZSoftwares getSoftware();
	
}
