package br.com.gz.migration.datafile;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.gz.bean.Produto;
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
 * Representa um arquivo de dados para ler os registros dos produtos. Os métodos
 * herdados devem ser implementados especificamente para trabalhar com estes
 * registros.
 * 
 * @author Jonathan Sansalone
 * 
 */
public class ProdutoDataFile extends DataFile {

	/**
	 * Instância da classe.
	 */
	private static ProdutoDataFile instance;

	/**
	 * Construtor default para passar o tipo de dado para o construtor da
	 * superclasse
	 * 
	 * @throws IOException
	 *             - Se não conseguir ler o arquivo
	 * @throws InvalidMigrationDataTypeException
	 *             - Se o tipo de dado não for suportado pela superclasse
	 * @throws RequiredColumnNotFoundException - Se alguma coluna não for encontrada
	 */
	private ProdutoDataFile(GZSoftwares software) throws IOException,
			InvalidMigrationDataTypeException {

		super(software,EnMigrationDataType.PRODUTO);

		requiredColumns = EnMercoFlexRequiredColumns.filterValues(
				EnColumnsCategory.ESTOQUE, EnColumnsCategory.ESTOQUE_LOJA,
				EnColumnsCategory.ESTOQUE_SALDO,
				EnColumnsCategory.ESTOQUE_TRIBUTACAO);

		qtyRequiredColumns = requiredColumns.length;

	}

	/**
	 * Método que implementa o singleton. Garante que em toda a execução da
	 * aplicação só exista uma instância dessa classe
	 * 
	 * @return - A instância da classe
	 * @throws IOException
	 *             - Se não conseguir ler o arquivo
	 * @throws RequiredColumnNotFoundException 
	 */
	public static ProdutoDataFile getInstance(GZSoftwares software)
			throws IOException {

		if (instance == null) {

			try {

				instance = new ProdutoDataFile(software);

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

//		int i = 0;
//
//		while (hasNextAfter(i))
//			i++;
//
//		return i;
		
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
	public Object next() throws ReachedTheEndOfFileException {// 35

		Object[] o;

		boolean passed = true;

		do {
			if (!hasNext())
				throw new ReachedTheEndOfFileException();
			o = getRowData(currentIndex++);
			if (!checkValuesPolicy(o)) {
				passed = false;
				if (!notInserted.containsKey(currentIndex - 1)) {//44648 42
					notInserted.put(currentIndex - 1, o);
				}
			} else {
				passed = true;
			}
		} while (!passed);

		int i = 0;

		Produto p = new Produto(software);

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

		try {
			p.setLoja(new Integer(format.toNumeric(o[i++].toString(), false)));
		} catch (Exception e) {
			p.setLoja(1);
		}
		p.setCodigoInterno(format.toCodigoInterno(o[i++].toString()));
		p.setCodigoDeBarras(format.toCodigoDeBarras(o[i++].toString()));
		p.setDescricao(format.toDescricaoProduto(o[i++].toString()));
		p.setDescricaoReduzida(format.toDescricaoProdutoReduzida(o[i++]
				.toString()));
		p.setUnidade(format.toUnidade(o[i++].toString()));
		p.setSetor(new Integer(format.toNumeric(o[i++].toString(), false)));
		p.setVariavel(o[i++].toString());
		p.setPrecoCompra(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setPrecoVenda(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setPrecoVendaTerminal(new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setDepartamento(new Integer(
				format.toNumeric(o[i++].toString(), false)));
		p.setGrupo(new Integer(format.toNumeric(o[i++].toString(), false)));
		p.setPrecoCusto(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setPorcentagemLucro(new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setQuantidadeEstoqueMinimo(new Double(format.toNumeric(
				o[i++].toString(), true)));
		p.setQuantidadeEstoqueMaximo(new Double(format.toNumeric(
				o[i++].toString(), true)));
		p.setQuantidade(new Double(format.toNumeric(o[i++].toString(), true)));
		try{
			p.setDataCadastro(DateFormat.getDateInstance().parse((String) o[i++]));
		}catch(Exception e){
			p.setDataCadastro(Calendar.getInstance());
		}
		p.setAliquotaPisCompra(new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setAliquotaCofinsCompra(new Double(format.toNumeric(
				o[i++].toString(), true)));
		p.setAtivo(format.toSituacao(o[i++].toString()));
		p.setNcm(format.toNumeric(o[i++].toString(), false));
		p.setCsosn(new Integer(format.toNumeric(o[i++].toString(), false)));
		p.setModalidatePautaVenda(o[i++].toString());
		p.setPautaVenda(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setTributacaoCompra(o[i++].toString());
		p.setCodigoTributacao(new Integer(format.toNumeric(o[i++].toString(),
				false)));
		p.setIcmCompra(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setSt(o[i++].toString());
		p.setIcmVenda(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setBaseIcmVenda(100 - new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setBaseICMSub(100 - new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setValorICMSSubstituicao(new Double(format.toNumeric(
				o[i++].toString(), true)));
		p.setEstadoTributacao(format.toEstadoSigla(o[i].toString()));

		return p;

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

		Produto p = new Produto(software);

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

		try {
			p.setLoja(new Integer(format.toNumeric(o[i++].toString(), false)));
		} catch (Exception e) {
			p.setLoja(1);
		}
		p.setCodigoInterno(format.toCodigoInterno(o[i++].toString()));
		p.setCodigoDeBarras(format.toCodigoDeBarras(o[i++].toString()));
		p.setDescricao(format.toDescricaoProduto(o[i++].toString()));
		p.setDescricaoReduzida(format.toDescricaoProdutoReduzida(o[i++]
				.toString()));
		p.setUnidade(format.toUnidade(o[i++].toString()));
		p.setSetor(new Integer(format.toNumeric(o[i++].toString(), false)));
		p.setVariavel(o[i++].toString());
		p.setPrecoCompra(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setPrecoVenda(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setPrecoVendaTerminal(new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setDepartamento(new Integer(
				format.toNumeric(o[i++].toString(), false)));
		p.setGrupo(new Integer(format.toNumeric(o[i++].toString(), false)));
		p.setPrecoCusto(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setPorcentagemLucro(new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setQuantidadeEstoqueMinimo(new Double(format.toNumeric(
				o[i++].toString(), true)));
		p.setQuantidadeEstoqueMaximo(new Double(format.toNumeric(
				o[i++].toString(), true)));
		p.setQuantidade(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setDataCadastro(((Date)o[i++]).getTime());
		p.setAliquotaPisCompra(new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setAliquotaCofinsCompra(new Double(format.toNumeric(
				o[i++].toString(), true)));
		p.setAtivo(format.toSituacao(o[i++].toString()));
		p.setNcm(format.toNumeric(o[i++].toString(), false));
		p.setCsosn(new Integer(format.toNumeric(o[i++].toString(), false)));
		p.setModalidatePautaVenda(o[i++].toString());
		p.setPautaVenda(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setTributacaoCompra(o[i++].toString());
		p.setCodigoTributacao(new Integer(format.toNumeric(o[i++].toString(),
				false)));
		p.setIcmCompra(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setSt(o[i++].toString());
		p.setIcmVenda(new Double(format.toNumeric(o[i++].toString(), true)));
		p.setBaseIcmVenda(100 - new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setBaseICMSub(100 - new Double(format.toNumeric(o[i++].toString(),
				true)));
		p.setValorICMSSubstituicao(new Double(format.toNumeric(
				o[i++].toString(), true)));
		p.setEstadoTributacao(format.toEstadoSigla(o[i++].toString()));

		return p;

	}

	@Override
	public ArrayList<Object> getAll() {

		int aux = currentIndex;

		currentIndex = 1;

		ArrayList<Object> arP = new ArrayList<Object>();

		Produto p;

		try {

			while (hasNext()) {

				p = (Produto) next();
				arP.add(p);

			}

		} catch (ReachedTheEndOfFileException e) {
			System.err.println(e.getMessage());
		}

		currentIndex = aux;
		
		return arP;
		
	}

	@Override
	public Object[] getRowData(int rowIndex) {

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

		// verificando se estão na ordem correta
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

			String message = "As seguintes colunas não foram encontradas: ";
			for (String s : notFound) {
				message += s + ", ";
			}
			message = message.substring(0, message.length() - 2) + ".";
			throw new RequiredColumnNotFoundException(message);

		} else {

			if (!invalidType.isEmpty()) {

				String message = "Os tipos de dados das seguintes colunas são inválidos: ";
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

	private void printa(Produto p, int i) {

		System.out.print(i + " --> ");
		System.out.print(p.getLoja() + "|");
		System.out.print(p.getCodigoInterno() + "|");
		System.out.print(p.getCodigoDeBarras() + "|");
		System.out.print(p.getDescricao() + "|");
		System.out.print(p.getDescricaoReduzida() + "|");
		System.out.print(p.getUnidade() + "|");
		System.out.print(p.getSetor() + "|");
		System.out.print(p.getVariavel() + "|");
		System.out.print(p.getPrecoCompra() + "|");
		System.out.print(p.getPrecoVenda() + "|");
		System.out.print(p.getPrecoVendaTerminal() + "|");
		System.out.print(p.getDepartamento() + "|");
		System.out.print(p.getGrupo() + "|");
		System.out.print(p.getPrecoCusto() + "|");
		System.out.print(p.getPorcentagemLucro() + "|");
		System.out.print(p.getQuantidadeEstoqueMinimo() + "|");
		System.out.print(p.getQuantidadeEstoqueMaximo() + "|");
		System.out.print(p.getQuantidade() + "|");

		DateFormat dtf = DateFormat.getDateInstance(DateFormat.DEFAULT);

		Date dt = p.getDataCadastro().getTime();

		System.out.print(dtf.format(dt) + "|");
		System.out.print(p.getAliquotaPisCompra() + "|");
		System.out.print(p.getAliquotaCofinsCompra() + "|");
		System.out.print(p.getAtivo() + "|");
		System.out.print(p.getNcm() + "|");
		System.out.print(p.getCsosn() + "|");
		System.out.print(p.getModalidatePautaVenda() + "|");
		System.out.print(p.getPautaVenda() + "|");
		System.out.print(p.getTributacaoCompra() + "|");
		System.out.print(p.getCodigoTributacao() + "|");
		System.out.print(p.getIcmCompra() + "|");
		System.out.print(p.getSt() + "|");
		System.out.print(p.getIcmVenda() + "|");
		System.out.print(p.getBaseIcmVenda() + "|");
		System.out.print(p.getBaseICMSub() + "|");
		System.out.print(p.getValorICMSSubstituicao() + "|");
		System.out.print(p.getEstadoTributacao() + "|");

		System.out.println();

	}

	public void teste() {

		System.out.println(getTotalRows());
		
//		ArrayList<Object> arP = getAll();
//
//		int i = 1;
//		for (Object o : arP) {
//			printa((Produto) o, i++);
//		}
//
//		// int i = 1;
//		//
//		// try {
//		//
//		// while (hasNext()) {
//		//
//		// Produto p = (Produto) next();
//		//
//		// printa(p, i++);
//		//
//		// }
//		//
//		// } catch (Exception e) {
//		// e.printStackTrace();
//		// }
//
//		 File f = new File("data/" + getFileNameNoExt() +
//		 "_NOT_INSERTED.xls");
//		
//		 DataFileWriter.writeRowBatch(f, DataFileReader.getHeader(
//		 dataSheet.getRow(0), qtyRequiredColumns), notInserted);
//
////		System.out
////				.println("----------------------------------------------------------------------------------------------");
////
////		Collection cl = notInserted.values();
////
////		i = 1;
////
////		for (Object oo : cl) {
////			Object[] ooo = (Object[]) oo;
////			System.out.print((i++) + " --> ");
////			for (Object oooo : ooo) {
////				System.out.print(oooo + "|");
////			}
////			System.out.println();
////		}

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
	public ArrayList<String> getColumnsNotFound() {
		return this.notFound;
	}

}
