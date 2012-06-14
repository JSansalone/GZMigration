package br.com.gz.migration.datafile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;
import br.com.gz.util.MercoFlexFormat;

public class ProdutoDataFile extends DataFile {

	private static ProdutoDataFile instance;

	private ProdutoDataFile() throws IOException,
			InvalidMigrationDataTypeException {

		super(EnMigrationDataType.PRODUTO);

	}

	public static ProdutoDataFile getInstance() throws IOException {

		if (instance == null) {

			try {

				instance = new ProdutoDataFile();

			} catch (IOException e) {

				throw e;

			} catch (InvalidMigrationDataTypeException e) {

				// johnny Auto-generated catch block
				e.printStackTrace();

			}

		}

		return instance;

	}

	@Override
	public DataFileMetaData getMetaData() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRows() {

		int i = 0;

		HSSFRow r;
		
		MercoFlexFormat format = new MercoFlexFormat();
		
		while((r = dataSheet.getRow(i+1)) != null){
			
			String aux;
			
			try{aux = new Double(r.getCell(0).getNumericCellValue()).toString();}catch(Exception e){aux = format.toNumeric(r.getCell(0).getStringCellValue(),false);}
			
			if(aux.equals("") || new Integer(aux) <= 0)
			
			System.out.println(r.getCell(0).getNumericCellValue());i++;
			
		}

		return i;

	}

	@Override
	public void moveToFirst() {
		// johnny Auto-generated method stub

	}

	@Override
	public void moveToLast() {
		// johnny Auto-generated method stub

	}

	@Override
	public boolean hasNext() {
		// John Auto-generated method stub
		return false;
	}

	@Override
	public Object next() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public Object previous() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Object> getAll() {
		// johnny Auto-generated method stub
		return null;
	}

}
