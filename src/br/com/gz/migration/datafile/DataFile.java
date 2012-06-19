package br.com.gz.migration.datafile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;

public abstract class DataFile {

	protected HSSFSheet dataSheet;
	
	private FileInputStream stream;

	protected DataFile(EnMigrationDataType dataType) throws InvalidMigrationDataTypeException, IOException {

		File file = new File("data", dataType.toString() + ".xls");

		if (file.exists()) {

			switch (dataType) {

			case PRODUTO: break;
			case DEPARTAMENTO: break;
			case GRUPO: break;
			case SETOR: break;
			case ARMACAO: break;
			case MARCA: break;
			case CLIENTE: break;
			case FORNECEDOR: break;
				
			default:
				
				throw new InvalidMigrationDataTypeException();

			}
			
			stream = new FileInputStream(file);
			dataSheet = new HSSFWorkbook(stream).getSheetAt(0);

		}else{
			
			throw new FileNotFoundException();
			
		}

	}

	public abstract DataFileMetaData getMetaData();

	public abstract String getName();

	public abstract int getTotalRows();

	public abstract void moveToFirst();

	public abstract void moveToLast();

	public abstract boolean hasNext();

	public abstract Object next();

	public abstract Object previous();

	public abstract ArrayList<Object> getAll();

	protected void close(){
		
		try {
			stream.close();
		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected abstract Object getRowData(int rowIndex);

}
