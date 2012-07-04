package br.com.gz.migration.datafile;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.gz.bean.Cliente;
import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.exception.InvalidCellTypeException;
import br.com.gz.migration.exception.InvalidMigrationDataTypeException;
import br.com.gz.migration.exception.ReachedTheEndOfFileException;
import br.com.gz.migration.exception.ReachedTheStartOfFileException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;
import br.com.gz.migration.policy.EnColumnsCategory;
import br.com.gz.migration.policy.EnMercoFlexRequiredColumns;
import br.com.gz.util.Formattable;
import br.com.gz.util.GZSoftwares;
import br.com.gz.util.MercattoFormat;
import br.com.gz.util.MercoFlexFormat;

/**
 * Classe que representa o arquivo de dados de clientes
 * 
 * @author Jonathan Sansalone
 *
 */
public class ClienteDataFile extends DataFile {

	/**
	 * inst�ncia
	 */
	private static ClienteDataFile instance;

	/**
	 * Construtor que recebe o software a ser implantado
	 * 
	 * @param software - software a ser implantado
	 * @throws InvalidMigrationDataTypeException - se o tipo de dado n�o for suportado
	 * @throws IOException - se n�o for poss�vel ler o arquivo
	 */
	private ClienteDataFile(GZSoftwares software)
			throws InvalidMigrationDataTypeException, IOException {
		super(software, EnMigrationDataType.CLIENTE);

		requiredColumns = EnMercoFlexRequiredColumns
				.filterValues(EnColumnsCategory.CLIENTES);

		qtyRequiredColumns = requiredColumns.length;

	}

	/**
	 * M�todo que implementa o singleton. Garante que em toda a execu��o da
	 * aplica��o s� exista uma inst�ncia dessa classe
	 * 
	 * @return - A inst�ncia da classe
	 * @throws IOException
	 *             - Se n�o conseguir ler o arquivo
	 * @throws RequiredColumnNotFoundException
	 */
	public static ClienteDataFile getInstance(GZSoftwares software)
			throws IOException {

		if (instance == null) {

			try {
				System.out.println("aqui");
				instance = new ClienteDataFile(software);

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
	public String getName() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalRows() {
		
		int i = currentIndex;
		int j=0;
		
		while(hasNext()){
			try {
				next();
				j++;
			} catch (ReachedTheEndOfFileException e) {
				break;
			}
		}
		
		currentIndex = i;
		
		return j;
		
	}

	@Override
	public Object first() {

		currentIndex = 2;

		try {
			return previous();
		} catch (ReachedTheStartOfFileException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Object last() {
		
		currentIndex = lastIndex;

		try {
			return next();
		} catch (ReachedTheEndOfFileException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean hasNext() {

		Object[] data = getRowData(currentIndex);

		for (Object o : data) {
			if (!o.equals(NULL_ROW) && !o.equals(CELL_VALUE_NULL))
				return true;
		}

		return false;

	}

	@Override
	public boolean hasPrevious() {

		if (currentIndex - 1 < 1)
			return false;

		Object[] data = getRowData(currentIndex - 1);

		for (Object o : data) {
			if (!o.equals(NULL_ROW) && !o.equals(CELL_VALUE_NULL))
				return true;
		}

		return false;

	}

	@Override
	protected boolean hasNextAfter(int idx) {

		Object[] data = getRowData(idx + 1);

		for (Object o : data) {
			if (!o.equals(NULL_ROW) && !o.equals(CELL_VALUE_NULL))
				return true;
		}

		return false;

	}

	@Override
	public Object next() throws ReachedTheEndOfFileException {

		Object[] o;

		boolean passed = true;

		do {
			if (!hasNext())
				throw new ReachedTheEndOfFileException();
			o = getRowData(currentIndex++);
			if (!checkValuesPolicy(o)) {
				passed = false;
				if (!notInserted.containsKey(currentIndex - 1)) {
					notInserted.put(currentIndex - 1, o);
				}
			} else {
				passed = true;
			}
		} while (!passed);

		int i = 0;

		Cliente c = new Cliente(software);

		Formattable format;

		switch (software) {
		case MERCOFLEX:
			format = new MercoFlexFormat();
			break;

		case MERCATTO:
			format = new MercattoFormat();
			break;

		default:
			format = new MercoFlexFormat();
		}

		c.setCodigo(new Integer(format.toNumeric(o[i++].toString(), false)));
		c.setNomeFantasia(format.toNomeFantasia(o[i++].toString()));
		c.setRazaoSocial(format.toRazaoSocial(o[i++].toString()));
		c.setCgc(format.toNumeric(o[i++].toString(), false));
		c.setRegistroEstadual(format.toInscricaoEstadual(o[i++].toString()));
		c.setSexo(o[i++].toString());
		
		Calendar cldn = Calendar.getInstance();
		Date dtdn = (Date)o[i++];
	//	Date dtdn = new Date();
	//	dtdn.setTime(new Long(o[i++].toString()));
		cldn.setTime(dtdn);
		c.setDataNascimento(cldn);
		
		Calendar cldc = Calendar.getInstance();
		Date dtdc = (Date)o[i++];
//		Date dtdc = new Date();
//		dtdc.setTime(new Long(o[i++].toString()));
		cldc.setTime(dtdc);
		c.setDataCadastro(cldc);
		
		c.setEstado(format.toEstadoSigla(o[i++].toString()));
		c.setCidade(o[i++].toString());
		c.setBairro(format.toBairro(o[i++].toString()));
		c.setEndereco(format.toEndereco(o[i++].toString()));
		c.setCep(format.toNumeric(o[i++].toString(), false));
		c.setNumero(new Integer(format.toNumeric(o[i++].toString(), false)));
		c.setTelefoneResidencial(format.toNumeric(o[i++].toString(), false));
		c.setEstadoCobranca(format.toEstadoSigla(o[i++].toString()));
		c.setCidadeCobranca(o[i++].toString());
		c.setBairroCobranca(format.toBairro(o[i++].toString()));
		c.setEnderecoCobranca(format.toEndereco(o[i++].toString()));
		c.setCepCobranca(format.toNumeric(o[i++].toString(), false));
		c.setNumeroCobranca(new Integer(format.toNumeric(o[i++].toString(), false)));
		c.setTelefoneCobranca(format.toNumeric(o[i++].toString(), false));
		c.setEstadoEntrega(format.toEstadoSigla(o[i++].toString()));
		c.setCidadeEntrega(o[i++].toString());
		c.setBairroEntrega(format.toBairro(o[i++].toString()));
		c.setEnderecoEntrega(format.toEndereco(o[i++].toString()));
		c.setCepEntrega(format.toNumeric(o[i++].toString(), false));
		c.setNumeroEnderecoEntrega(new Integer(format.toNumeric(o[i++].toString(), false)));
		c.setTelefoneEntrega(format.toNumeric(o[i++].toString(), false));
		c.setSituacao(format.toSituacao(o[i++].toString()));
		
		return c;

	}

	@Override
	public Object previous() throws ReachedTheStartOfFileException {

		Object[] o;

		boolean passed = true;

		do {
			if (!hasPrevious())
				throw new ReachedTheStartOfFileException();
			o = getRowData(--currentIndex);
			if (!checkValuesPolicy(o)) {
				passed = false;
				if (!notInserted.containsKey(currentIndex)) {
					notInserted.put(currentIndex, o);
				}
			} else {
				passed = true;
			}
		} while (!passed);

		int i = 0;

		Cliente c = new Cliente(software);

		Formattable format;

		switch (software) {
		case MERCOFLEX:
			format = new MercoFlexFormat();
			break;

		case MERCATTO:
			format = new MercattoFormat();
			break;

		default:
			format = new MercoFlexFormat();
		}

		c.setCodigo(new Integer(format.toNumeric(o[i++].toString(), false)));
		c.setNomeFantasia(format.toNomeFantasia(o[i++].toString()));
		c.setRazaoSocial(format.toRazaoSocial(o[i++].toString()));
		c.setCgc(format.toNumeric(o[i++].toString(), false));
		c.setRegistroEstadual(format.toInscricaoEstadual(o[i++].toString()));
		c.setSexo(o[i++].toString());
		
		Calendar cldn = Calendar.getInstance();
		Date dtdn = (Date)o[i++];
	//	Date dtdn = new Date();
	//	dtdn.setTime(new Long(o[i++].toString()));
		cldn.setTime(dtdn);
		c.setDataNascimento(cldn);
		
		Calendar cldc = Calendar.getInstance();
		Date dtdc = (Date)o[i++];
//		Date dtdc = new Date();
//		dtdc.setTime(new Long(o[i++].toString()));
		cldc.setTime(dtdc);
		c.setDataCadastro(cldc);
		
		c.setEstado(format.toEstadoSigla(o[i++].toString()));
		c.setCidade(o[i++].toString());
		c.setBairro(format.toBairro(o[i++].toString()));
		c.setEndereco(format.toEndereco(o[i++].toString()));
		c.setCep(format.toNumeric(o[i++].toString(), false));
		c.setNumero(new Integer(format.toNumeric(o[i++].toString(), false)));
		c.setTelefoneResidencial(format.toNumeric(o[i++].toString(), false));
		c.setEstadoCobranca(format.toEstadoSigla(o[i++].toString()));
		c.setCidadeCobranca(o[i++].toString());
		c.setBairroCobranca(format.toBairro(o[i++].toString()));
		c.setEnderecoCobranca(format.toEndereco(o[i++].toString()));
		c.setCepCobranca(format.toNumeric(o[i++].toString(), false));
		c.setNumeroCobranca(new Integer(format.toNumeric(o[i++].toString(), false)));
		c.setTelefoneCobranca(format.toNumeric(o[i++].toString(), false));
		c.setEstadoEntrega(format.toEstadoSigla(o[i++].toString()));
		c.setCidadeEntrega(o[i++].toString());
		c.setBairroEntrega(format.toBairro(o[i++].toString()));
		c.setEnderecoEntrega(format.toEndereco(o[i++].toString()));
		c.setCepEntrega(format.toNumeric(o[i++].toString(), false));
		c.setNumeroEnderecoEntrega(new Integer(format.toNumeric(o[i++].toString(), false)));
		c.setTelefoneEntrega(format.toNumeric(o[i++].toString(), false));
		c.setSituacao(format.toSituacao(o[i++].toString()));
		
		return c;
		
	}

	@Override
	public ArrayList<Object> getAll() {
		
		int aux = currentIndex;

		currentIndex = 1;

		ArrayList<Object> arD = new ArrayList<Object>();

		Cliente d;

		try {

			while (hasNext()) {

				d = (Cliente) next();
				arD.add(d);

			}

		} catch (ReachedTheEndOfFileException e) {
			System.err.println(e.getMessage());
		}

		currentIndex = aux;
		
		return arD;
		
	}

	@Override
	protected Object[] getRowData(int rowIndex) {

		return DataFileReader.getCellValues(dataSheet.getRow(rowIndex),
				qtyRequiredColumns);
		
	}

	@Override
	public boolean checkHeaderPolicy() throws InvalidCellTypeException,
			RequiredColumnNotFoundException {

		// obtendo as colunas do header
		String[] columnsFromFile = DataFileReader.getHeader(
				dataSheet.getRow(0), qtyRequiredColumns);

		ArrayList<String> notFound = new ArrayList<String>();
		ArrayList<String> invalidType = new ArrayList<String>();
		
		this.notFound.clear();

		// verificando se est�o na ordem correta
		for (int i = 0; i < qtyRequiredColumns; i++) {

			if (requiredColumns[i].getLabel().equals(columnsFromFile[i])) {

			} else {

				if (columnsFromFile[i].equals(DataFile.INVALID_CELL_TYPE)) {

					invalidType.add(requiredColumns[i].getLabel());

				} else {

					notFound.add(requiredColumns[i].getLabel());
					
					this.notFound.add(requiredColumns[i].getLabel());

				}

			}

		}

		if (!notFound.isEmpty()) {

			String message = "As seguintes colunas n�o foram encontradas: ";
			for (String s : notFound) {
				message += s + ", ";
			}
			message = message.substring(0, message.length() - 2) + ".";
			throw new RequiredColumnNotFoundException(message);

		} else {

			if (!invalidType.isEmpty()) {

				String message = "Os tipos de dados das seguintes colunas s�o inv�lidos: ";
				for (String s : invalidType) {
					message += s + ", ";
				}
				message = message.substring(0, message.length() - 2) + ".";
				throw new InvalidCellTypeException(message);

			}

		}

		return true;

	}

	@Override
	public boolean checkValuesPolicy(Object[] values) {

		for (Object v : values) {

			try {
				if (v.equals(DataFile.CELL_VALUE_NULL)
						|| v.equals(DataFile.INVALID_CELL_TYPE)
						|| v.equals(DataFile.NULL_ROW)) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}

		}

		return true;

	}

	@Override
	public ArrayList<String> getColumnsNotFound() {
		return this.notFound;
	}

}
