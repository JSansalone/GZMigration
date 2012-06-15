package br.com.gz.migration.datafile;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;
import br.com.gz.util.MercoFlexFormat;

public class ProdutoDataFile extends DataFile {

	private static ProdutoDataFile instance;
	
	private int currentIndex = 0;

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
		
	//	while((r = dataSheet.getRow(i+1)) != null){
			
		//	System.out.println(r.getCell(0).getNumericCellValue());i++;
			
	//	}
		
		HSSFCellStyle style = (r = dataSheet.getRow(1)).getCell(0).getCellStyle();

		return inserirFuncaoCountA(dataSheet, 1, style, 0, 4, "1");

	}
	
	private int inserirFuncaoCountA(HSSFSheet sheet, int indexLinha, HSSFCellStyle style, int primeiraCelula, int ultimaCelula, String titulo) {  
	    indexLinha++;  
	    HSSFCell cell;  
	    cell = sheet.createRow(indexLinha).createCell((short) 1);  
	    cell.setCellValue(new HSSFRichTextString(titulo) );  
	    cell.setCellStyle(style);  
	    sheet.autoSizeColumn((short)(2));  
	    cell = sheet.createRow(indexLinha).createCell((short) 2);  
	    cell.setCellFormula("countA(A"+(primeiraCelula+1)+":A"+(ultimaCelula+1)+")");  
	    cell.setCellStyle(style);  
	    indexLinha++;  
	    return new Integer(new Double(cell.getNumericCellValue()).toString());  
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
		
		try{
			
			double d = dataSheet.getRow(currentIndex+1).getCell(0).getNumericCellValue();
			
			if(d > 0) return true;
			else return false;
		
		}catch(NullPointerException e){
			
			return false;
			
		}
		
	}

	@Override
	public Object next() {
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
