package br.com.gz.migration.sql;

public class SecurityPolicy {

	/**
	 * 
	 * @param sql
	 * @return true para código seguro, false para código perigoso
	 * 
	 */
	public static boolean checkSecurity(String sql) {

		String[] dangerousCode = { "drop", "delete", "truncate", "alter",
				"update", "into", "create", "insert" };

		for (String dangerous : dangerousCode) {

			if (sql.contains(dangerous))
				return false;

		}

		return true;

	}

}
