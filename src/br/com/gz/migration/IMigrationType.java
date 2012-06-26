package br.com.gz.migration;

import java.util.ArrayList;


public interface IMigrationType {

	ArrayList<EnMigrationDataType> getMigrationType();
	
	int getNumLoja();
	
	boolean toAppend();
	
	boolean ignoreCodes();
	
}
