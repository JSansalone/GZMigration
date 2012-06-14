package br.com.gz.migration;

import org.database.connection.DatabaseType;
import org.database.connection.InvalidDatabaseException;

public class SpecialConfigurations {

	private DatabaseType typeTo;
	private DatabaseType typeFrom;

	public DatabaseType getTypeTo() {
		return typeTo;
	}

	public void setTypeTo(DatabaseType typeTo) throws InvalidDatabaseException {

		if (typeTo != DatabaseType.MSSQL && typeTo != DatabaseType.MySQL
				&& typeTo != DatabaseType.Oracle) {
			throw new InvalidDatabaseException();
		}

		this.typeTo = typeTo;

	}

	public DatabaseType getTypeFrom() {
		return typeFrom;
	}

	public void setTypeFrom(DatabaseType typeFrom) {
		this.typeFrom = typeFrom;
	}

}
