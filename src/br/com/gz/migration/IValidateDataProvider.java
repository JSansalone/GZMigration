package br.com.gz.migration;

import org.database.connection.DatabaseType;

interface IValidateDataProvider {

	boolean validateDBTo(DatabaseType compare1, DatabaseType compare2);

	boolean validateDBFrom(DatabaseType compare1, DatabaseType compare2);

}
