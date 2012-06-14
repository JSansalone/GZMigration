package br.com.gz.migration.sql;

public class SecurityPolicy {

	/**
	 * 
	 * @param sql
	 * @return true para c�digo seguro, false para c�digo perigoso
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
