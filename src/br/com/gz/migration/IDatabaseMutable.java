package br.com.gz.migration;

import org.database.connection.DatabaseType;

public interface IDatabaseMutable {

	void chooseDatabase(DatabaseType dbt);
	
}
