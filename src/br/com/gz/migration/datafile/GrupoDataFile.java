package br.com.gz.migration.datafile;

import java.io.IOException;
import java.util.ArrayList;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidCellTypeException;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;
import br.com.gz.migration.exception.ReachedTheEndOfFileException;
import br.com.gz.migration.exception.ReachedTheStartOfFileException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;
import br.com.gz.util.GZSoftwares;

public class GrupoDataFile extends DataFile {

	private static GrupoDataFile instance;
	
	protected GrupoDataFile(GZSoftwares software)
			throws InvalidMigrationDataTypeException, IOException, RequiredColumnNotFoundException {
		super(software,EnMigrationDataType.GRUPO);

		try {
			checkHeaderPolicy();
		} catch (InvalidCellTypeException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String getName() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRows() {
		// johnny Auto-generated method stub
		return 0;
	}

	@Override
	public Object first() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public Object last() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNext() {
		// johnny Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPrevious() {
		// johnny Auto-generated method stub
		return false;
	}

	@Override
	protected boolean hasNextAfter(int currentIndex) {
		// johnny Auto-generated method stub
		return false;
	}

	@Override
	public Object next() throws ReachedTheEndOfFileException {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public Object previous() throws ReachedTheStartOfFileException {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Object> getAll() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	protected Object[] getRowData(int rowIndex) {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkHeaderPolicy() throws InvalidCellTypeException,
			RequiredColumnNotFoundException {
		// johnny Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkValuesPolicy(Object[] values) {
		// johnny Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getColumnsNotFound() {
		// johnny Auto-generated method stub
		return null;
	}

}
