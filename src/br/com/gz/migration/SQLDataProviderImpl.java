package br.com.gz.migration;

import java.io.IOException;
import java.util.ArrayList;

import org.database.connection.DatabaseType;

import br.com.gz.bean.Armacao;
import br.com.gz.bean.Cliente;
import br.com.gz.bean.Departamento;
import br.com.gz.bean.Fornecedor;
import br.com.gz.bean.Grupo;
import br.com.gz.bean.Marca;
import br.com.gz.bean.Produto;
import br.com.gz.migration.datafile.ArmacaoDataFile;
import br.com.gz.migration.datafile.ClienteDataFile;
import br.com.gz.migration.datafile.DataFile;
import br.com.gz.migration.datafile.DepartamentoDataFile;
import br.com.gz.migration.datafile.FornecedorDataFile;
import br.com.gz.migration.datafile.GrupoDataFile;
import br.com.gz.migration.datafile.MarcaDataFile;
import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.migration.exception.InvalidCellTypeException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;
import br.com.gz.migration.file.LogFile;
import br.com.gz.migration.policy.ColumnsPolicy;
import br.com.gz.migration.policy.MercattoPolicy;
import br.com.gz.migration.policy.MercoFlexPolicy;
import br.com.gz.util.GZSoftwares;

/**
 * Classe que implementa a classe abstrata SQLDataProvider que fornece meios de
 * obter e inserir dados
 * 
 * @author Jonathan Sansalone
 * 
 */
public class SQLDataProviderImpl extends SQLDataProvider {

	/**
	 * Variável da política de colunas
	 */
	private ColumnsPolicy policy;

	/**
	 * Construtor que recebe o software a ser implantado e o tipo de banco de
	 * dados de destino
	 * 
	 * @param software
	 *            - software a ser implantado
	 * @param dbTo
	 *            - tipo de banco de dados de destino
	 */
	public SQLDataProviderImpl(GZSoftwares software, DatabaseType dbTo) {

		super(software, dbTo);

		// guarda o software da GZ
		this.software = software;

		LogFile.getInstance().writeInFile(
				"New software: " + this.software.toString().toLowerCase());

		// guarda o banco de dados de destino
		this.dbTo = dbTo;

		// instancia a nova política de colunas
		if (software == GZSoftwares.MERCOFLEX) {
			policy = new MercoFlexPolicy();
		} else {
			policy = new MercattoPolicy();
		}

	}

	@Override
	public int countProduto(ProdutoDataFile dataFile) throws IOException {

		try {

			if (dataFile.checkHeaderPolicy()) {

				return dataFile.getTotalRows();

			} else {

				return POLICY_VIOLATION;

			}

		} catch (InvalidCellTypeException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		} catch (RequiredColumnNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		}

	}

	@Override
	public int countDepartamento(DepartamentoDataFile dataFile)
			throws IOException {

		try {

			if (dataFile.checkHeaderPolicy()) {

				return dataFile.getTotalRows();

			} else {

				return POLICY_VIOLATION;

			}

		} catch (InvalidCellTypeException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		} catch (RequiredColumnNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		}

	}

	@Override
	public int countGrupo(GrupoDataFile dataFile) throws IOException {

		try {

			if (dataFile.checkHeaderPolicy()) {

				return dataFile.getTotalRows();

			} else {

				return POLICY_VIOLATION;

			}

		} catch (InvalidCellTypeException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		} catch (RequiredColumnNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		}

	}

	@Override
	public int countArmacao(ArmacaoDataFile dataFile) throws IOException {

		try {

			if (dataFile.checkHeaderPolicy()) {

				return dataFile.getTotalRows();

			} else {

				return POLICY_VIOLATION;

			}

		} catch (InvalidCellTypeException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		} catch (RequiredColumnNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		}

	}

	@Override
	public int countMarca(MarcaDataFile dataFile) throws IOException {

		try {

			if (dataFile.checkHeaderPolicy()) {

				return dataFile.getTotalRows();

			} else {

				return POLICY_VIOLATION;

			}

		} catch (InvalidCellTypeException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		} catch (RequiredColumnNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		}

	}

	@Override
	public int countCliente(ClienteDataFile dataFile) throws IOException {

		try {

			if (dataFile.checkHeaderPolicy()) {

				return dataFile.getTotalRows();

			} else {

				return POLICY_VIOLATION;

			}

		} catch (InvalidCellTypeException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		} catch (RequiredColumnNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		}

	}

	@Override
	public int countFornecedor(FornecedorDataFile dataFile) throws IOException {

		try {

			if (dataFile.checkHeaderPolicy()) {

				return dataFile.getTotalRows();

			} else {

				return POLICY_VIOLATION;

			}

		} catch (InvalidCellTypeException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		} catch (RequiredColumnNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return POLICY_VIOLATION;
		}

	}

	@Override
	public ArrayList<Produto> getProduto(ProdutoDataFile dataFile) {
		// johnny Auto-generated method stub

		ArrayList<Produto> ar = new ArrayList<Produto>();

		for (Object o : dataFile.getAll()) {
			ar.add((Produto) o);
		}

		return ar;

	}

	@Override
	public ArrayList<Departamento> getDepartamento(DepartamentoDataFile dataFile) {
		// johnny Auto-generated method stub

		ArrayList<Departamento> ar = new ArrayList<Departamento>();

		for (Object o : dataFile.getAll()) {
			ar.add((Departamento) o);
		}

		return ar;

	}

	@Override
	public ArrayList<Grupo> getGrupo(GrupoDataFile dataFile) {
		// johnny Auto-generated method stub

		ArrayList<Grupo> ar = new ArrayList<Grupo>();

		for (Object o : dataFile.getAll()) {
			ar.add((Grupo) o);
		}

		return ar;

	}

	@Override
	public ArrayList<Armacao> getArmacao(ArmacaoDataFile dataFile) {
		// johnny Auto-generated method stub

		ArrayList<Armacao> ar = new ArrayList<Armacao>();

		for (Object o : dataFile.getAll()) {
			ar.add((Armacao) o);
		}

		return ar;

	}

	@Override
	public ArrayList<Marca> getMarca(MarcaDataFile dataFile) {
		// johnny Auto-generated method stub

		ArrayList<Marca> ar = new ArrayList<Marca>();

		for (Object o : dataFile.getAll()) {
			ar.add((Marca) o);
		}

		return ar;

	}

	@Override
	public ArrayList<Cliente> getCliente(ClienteDataFile dataFile) {
		// johnny Auto-generated method stub

		ArrayList<Cliente> ar = new ArrayList<Cliente>();

		for (Object o : dataFile.getAll()) {
			ar.add((Cliente) o);
		}

		return ar;

	}

	@Override
	public ArrayList<Fornecedor> getFornecedor(FornecedorDataFile dataFile) {
		// johnny Auto-generated method stub

		ArrayList<Fornecedor> ar = new ArrayList<Fornecedor>();

		for (Object o : dataFile.getAll()) {
			ar.add((Fornecedor) o);
		}

		return ar;

	}

	@Override
	public ArrayList<String> getColumnsNeeded(DataFile dataFile) {

		return dataFile.getColumnsNotFound();

	}

}