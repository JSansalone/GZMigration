package br.com.gz.migration.software;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.database.connection.DatabaseType;

import br.com.gz.bean.Armacao;
import br.com.gz.bean.Cliente;
import br.com.gz.bean.Departamento;
import br.com.gz.bean.Fornecedor;
import br.com.gz.bean.Grupo;
import br.com.gz.bean.Loja;
import br.com.gz.bean.Marca;
import br.com.gz.bean.Produto;
import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.EnSoftware;
import br.com.gz.migration.SQLDataProvider;
import br.com.gz.migration.datafile.ArmacaoDataFile;
import br.com.gz.migration.datafile.ClienteDataFile;
import br.com.gz.migration.datafile.DepartamentoDataFile;
import br.com.gz.migration.datafile.FornecedorDataFile;
import br.com.gz.migration.datafile.GrupoDataFile;
import br.com.gz.migration.datafile.MarcaDataFile;
import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.migration.exception.SecurityViolationException;
import br.com.gz.migration.file.LogFile;
import br.com.gz.migration.policy.ColumnsPolicy;
import br.com.gz.migration.policy.MercattoPolicy;
import br.com.gz.migration.policy.MercoFlexPolicy;
import br.com.gz.migration.policy.SecurityPolicy;
import br.com.gz.migration.sql.DefaultSQLSelectStatements;
import br.com.gz.util.GZSoftwares;
import br.com.gz.util.MercattoFormat;
import br.com.gz.util.MercoFlexFormat;

/**
 * 
 * @Autor Jonathan
 * 
 * @Migra Produto, Fornecedor, Cliente, Armacao, Marca, Departamento, Grupo
 * 
 */
public class SQLDataProviderImpl extends SQLDataProvider {

	private PreparedStatement stProduto;
	private PreparedStatement stDepartamento;
	private PreparedStatement stGrupo;
	private PreparedStatement stArmacao;
	private PreparedStatement stMarca;
	private PreparedStatement stCliente;
	private PreparedStatement stFornecedor;
	private PreparedStatement stNFEntrada;
	private PreparedStatement stNFSaida;
	private PreparedStatement stContaPagar;
	private PreparedStatement stContaReceber;
	private PreparedStatement stMovtoVenda;

	private ResultSet rsProduto;
	private ResultSet rsDepartamento;
	private ResultSet rsGrupo;
	private ResultSet rsArmacao;
	private ResultSet rsMarca;
	private ResultSet rsCliente;
	private ResultSet rsFornecedor;
	private ResultSet rsNFEntrada;
	private ResultSet rsNFSaida;
	private ResultSet rsContaPagar;
	private ResultSet rsContaReceber;
	private ResultSet rsMovtoVenda;

	private ColumnsPolicy policy;

	public SQLDataProviderImpl(EnSoftware software, EnSoftware otherSoftware,
			DatabaseType dbTo, DatabaseType dbFrom) {
		super(software, otherSoftware, dbTo, dbFrom);
		// guarda o software da GZ
		this.software = software;
		LogFile.getInstance().writeInFile(
				"New software: " + this.software.toString().toLowerCase());
		// guarda o software terceiro
		this.otherSoftware = otherSoftware;
		LogFile.getInstance().writeInFile(
				"Old software: " + this.otherSoftware.toString().toLowerCase());
		// guarda o banco de dados de destino
		this.dbTo = dbTo;

		this.dbFrom = dbFrom;

		// instancia a nova política de colunas
		if (software == EnSoftware.MERCOFLEX) {
			policy = new MercoFlexPolicy();
		} else {
			policy = new MercattoPolicy();
		}

	}

	// obtendo a contagem de registros que possivelmente serão inseridos
	@Override
	public int countProduto(Connection conn) throws IOException,
			SecurityViolationException, SQLException {

		// obtendo o select que coleta os produtos
		// o select vem do arquivo data\versatho\getProduto.txt
		// LogFile.getInstance().writeInFile("");
		LogFile.getInstance().writeInFile(
				"Retrieving SQL statement from file 'getProduto.txt'");
		String sql = DefaultSQLSelectStatements.getFromFile(otherSoftware,
				EnMigrationDataType.PRODUTO);

		if (!SecurityPolicy.checkSecurity(sql))
			throw new SecurityViolationException();

		try {

			// prepara o comando sql para ser executado
			// o segundo parâmetro permite navegar em ambas as direções do
			// resultset

			int count = 0;

			if (dbFrom != DatabaseType.Firebird
					&& dbFrom != DatabaseType.PostgreeSQL) {

				stProduto = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE);
				rsProduto = stProduto.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsProduto.getMetaData(),
						EnMigrationDataType.PRODUTO)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsProduto.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsProduto.beforeFirst();

			} else {

				stProduto = conn.prepareStatement(sql);
				rsProduto = stProduto.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsProduto.getMetaData(),
						EnMigrationDataType.PRODUTO)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsProduto.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsProduto = stProduto.executeQuery();

			}

			return count;

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public int countDepartamento(Connection conn) throws IOException,
			SecurityViolationException, SQLException {

		// obtendo o select que coleta os produtos
		// o select vem do arquivo data\versatho\getProduto.txt
		LogFile.getInstance().writeInFile(
				"Retrieving SQL statement from file 'getDepartamento.txt'");
		String sql = DefaultSQLSelectStatements.getFromFile(otherSoftware,
				EnMigrationDataType.DEPARTAMENTO);

		if (!SecurityPolicy.checkSecurity(sql))
			throw new SecurityViolationException();

		try {

			// prepara o comando sql para ser executado
			// o segundo parâmetro permite navegar em ambas as direções do
			// resultset

			int count = 0;

			if (dbFrom != DatabaseType.Firebird
					&& dbFrom != DatabaseType.PostgreeSQL) {

				stDepartamento = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE);
				rsDepartamento = stDepartamento.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsDepartamento.getMetaData(),
						EnMigrationDataType.DEPARTAMENTO)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsDepartamento.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsDepartamento.beforeFirst();

			} else {

				stDepartamento = conn.prepareStatement(sql);
				rsDepartamento = stDepartamento.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsDepartamento.getMetaData(),
						EnMigrationDataType.DEPARTAMENTO)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsDepartamento.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsDepartamento = stDepartamento.executeQuery();

			}

			return count;

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public int countGrupo(Connection conn) throws IOException,
			SecurityViolationException, SQLException {

		// obtendo o select que coleta os produtos
		// o select vem do arquivo data\versatho\getProduto.txt
		LogFile.getInstance().writeInFile(
				"Retrieving SQL statement from file 'getGrupo.txt'");
		String sql = DefaultSQLSelectStatements.getFromFile(otherSoftware,
				EnMigrationDataType.GRUPO);

		if (!SecurityPolicy.checkSecurity(sql))
			throw new SecurityViolationException();

		try {

			// prepara o comando sql para ser executado
			// o segundo parâmetro permite navegar em ambas as direções do
			// resultset

			int count = 0;

			if (dbFrom != DatabaseType.Firebird
					&& dbFrom != DatabaseType.PostgreeSQL) {

				stGrupo = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE);
				rsGrupo = stGrupo.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsGrupo.getMetaData(),
						EnMigrationDataType.GRUPO)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsGrupo.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsGrupo.beforeFirst();

			} else {

				stGrupo = conn.prepareStatement(sql);
				rsGrupo = stGrupo.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsGrupo.getMetaData(),
						EnMigrationDataType.GRUPO)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsGrupo.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsGrupo = stGrupo.executeQuery();

			}

			return count;

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public int countArmacao(Connection conn) throws IOException,
			SecurityViolationException, SQLException {

		// obtendo o select que coleta os produtos
		// o select vem do arquivo data\versatho\getProduto.txt
		LogFile.getInstance().writeInFile(
				"Retrieving SQL statement from file 'getArmacao.txt'");
		String sql = DefaultSQLSelectStatements.getFromFile(otherSoftware,
				EnMigrationDataType.ARMACAO);

		if (!SecurityPolicy.checkSecurity(sql))
			throw new SecurityViolationException();

		try {

			// prepara o comando sql para ser executado
			// o segundo parâmetro permite navegar em ambas as direções do
			// resultset

			int count = 0;

			if (dbFrom != DatabaseType.Firebird
					&& dbFrom != DatabaseType.PostgreeSQL) {

				stArmacao = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE);
				rsArmacao = stArmacao.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsArmacao.getMetaData(),
						EnMigrationDataType.ARMACAO)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsArmacao.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsArmacao.beforeFirst();

			} else {

				stArmacao = conn.prepareStatement(sql);
				rsArmacao = stArmacao.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsArmacao.getMetaData(),
						EnMigrationDataType.ARMACAO)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsArmacao.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsArmacao = stArmacao.executeQuery();

			}

			return count;

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public int countMarca(Connection conn) throws IOException,
			SecurityViolationException, SQLException {

		// obtendo o select que coleta os produtos
		// o select vem do arquivo data\versatho\getProduto.txt
		LogFile.getInstance().writeInFile(
				"Retrieving SQL statement from file 'getMarca.txt'");
		String sql = DefaultSQLSelectStatements.getFromFile(otherSoftware,
				EnMigrationDataType.MARCA);

		if (!SecurityPolicy.checkSecurity(sql))
			throw new SecurityViolationException();

		try {

			// prepara o comando sql para ser executado
			// o segundo parâmetro permite navegar em ambas as direções do
			// resultset

			int count = 0;

			if (dbFrom != DatabaseType.Firebird
					&& dbFrom != DatabaseType.PostgreeSQL) {

				stMarca = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE);
				rsMarca = stMarca.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsMarca.getMetaData(),
						EnMigrationDataType.MARCA)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsMarca.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsMarca.beforeFirst();

			} else {

				stMarca = conn.prepareStatement(sql);
				rsMarca = stMarca.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsMarca.getMetaData(),
						EnMigrationDataType.MARCA)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsMarca.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsMarca = stMarca.executeQuery();

			}

			return count;

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public int countCliente(Connection conn) throws IOException,
			SecurityViolationException, SQLException {

		LogFile.getInstance().writeInFile(
				"Retrieving SQL statement from file 'getCliente.txt'");
		String sql = DefaultSQLSelectStatements.getFromFile(otherSoftware,
				EnMigrationDataType.CLIENTE);

		if (!SecurityPolicy.checkSecurity(sql))
			throw new SecurityViolationException();

		try {

			// prepara o comando sql para ser executado
			// o segundo parâmetro permite navegar em ambas as direções do
			// resultset

			int count = 0;

			if (dbFrom != DatabaseType.Firebird
					&& dbFrom != DatabaseType.PostgreeSQL) {

				stCliente = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE);
				rsCliente = stCliente.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsCliente.getMetaData(),
						EnMigrationDataType.CLIENTE)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsCliente.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsCliente.beforeFirst();

			} else {

				stCliente = conn.prepareStatement(sql);
				rsCliente = stCliente.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsCliente.getMetaData(),
						EnMigrationDataType.CLIENTE)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsCliente.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsCliente = stCliente.executeQuery();

			}

			return count;

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public int countLoja(Connection conn) throws IOException,
			SecurityViolationException, SQLException {
		// johnny Auto-generated method stub
		return EMPTY_RETURN;
	}

	@Override
	public int countFornecedor(Connection conn) throws IOException,
			SecurityViolationException, SQLException {

		LogFile.getInstance().writeInFile(
				"Retrieving SQL statement from file 'getFornecedor.txt'");
		String sql = DefaultSQLSelectStatements.getFromFile(otherSoftware,
				EnMigrationDataType.FORNECEDOR);

		if (!SecurityPolicy.checkSecurity(sql))
			throw new SecurityViolationException();

		try {

			// prepara o comando sql para ser executado
			// o segundo parâmetro permite navegar em ambas as direções do
			// resultset

			int count = 0;

			if (dbFrom != DatabaseType.Firebird
					&& dbFrom != DatabaseType.PostgreeSQL) {

				stFornecedor = conn.prepareStatement(sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE);
				rsFornecedor = stFornecedor.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsFornecedor.getMetaData(),
						EnMigrationDataType.FORNECEDOR)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsFornecedor.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsFornecedor.beforeFirst();

			} else {

				stFornecedor = conn.prepareStatement(sql);
				rsFornecedor = stFornecedor.executeQuery();

				// verifica a política de colunas
				if (!policy.validate(rsFornecedor.getMetaData(),
						EnMigrationDataType.FORNECEDOR)) {
					return POLICY_VIOLATION;
				}

				// enquanto houver registros itera
				while (rsFornecedor.next()) {

					count++;

				}

				// volta o resultset para o início
				// antes do 1º registro
				rsFornecedor = stFornecedor.executeQuery();

			}

			return count;

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	// ---------------------- IMPORTANTE ----------------------
	// este método só será executado no motor de migração se o método count
	// correspondente retornar uma quantidade válida
	// isto é, não pode retornar -1, EMPTY_RETURN e POLICY_VIOLATION

	// AQUI É ONDE ======> DEVE <====== SER FEITO TODOS OS TRATAMENTOS DE DADOS
	@Override
	public ArrayList<Produto> getProduto(Connection conn) {

		try {

			// cria novo arraylist para armazenar
			ArrayList<Produto> ar = new ArrayList();

			Produto p = null;

			if (software == EnSoftware.MERCOFLEX) {

				// instancia novo formatador de dados
				MercoFlexFormat myFormatter = new MercoFlexFormat();

				// enquanto o resultset conter dados
				while (rsProduto.next()) {

					// cria novo bean
					p = new Produto(GZSoftwares.MERCOFLEX);

					p.setCodigoInterno(myFormatter.toCodigoInterno(rsProduto
							.getString("cdprod")));

					try {

						p.setCodigoDeBarras(myFormatter
								.toCodigoDeBarras(rsProduto
										.getString("codbarra")));

					} catch (Exception e) {

						p.setCodigoDeBarras(p.getCodigoInterno());

					}

					p.setDescricao(myFormatter.toDescricaoProduto(rsProduto
							.getString("descricao")));
					p.setDescricaoReduzida(myFormatter
							.toDescricaoProdutoReduzida(rsProduto
									.getString("descricao")));
					p.setUnidade(myFormatter.toUnidade(rsProduto
							.getString("unidade")));
					p.setSetor(new Integer(myFormatter.toNumeric(
							rsProduto.getString("setor"), false)));
					p.setVariavel(rsProduto.getString("variavel"));
					p.setPrecoCompra(new Double(myFormatter.toNumeric(
							rsProduto.getString("precocomp"), true)));
					p.setPrecoPromocao(new Double(myFormatter.toNumeric(
							rsProduto.getString("precoprom"), true)));
					p.setPrecoVenda(new Double(myFormatter.toNumeric(
							rsProduto.getString("precovenda"), true)));
					p.setDepartamento(new Integer(myFormatter.toNumeric(
							rsProduto.getString("depto"), false)));
					p.setGrupo(new Integer(myFormatter.toNumeric(
							rsProduto.getString("grupo"), false)));
					p.setPrecoCusto(new Double(myFormatter.toNumeric(
							rsProduto.getString("precocusto"), true)));
					p.setPorcentagemLucro(new Double(myFormatter.toNumeric(
							rsProduto.getString("perclucro"), true)));
					p.setQuantidadeEstoqueMinimo(new Double(myFormatter
							.toNumeric(rsProduto.getString("estminimo"), true)));
					p.setQuantidadeEstoqueMaximo(new Double(myFormatter
							.toNumeric(rsProduto.getString("estmaximo"), true)));
					p.setQuantidade(new Double(myFormatter.toNumeric(
							rsProduto.getString("quant"), true)));
					Calendar c = Calendar.getInstance();
					c.setTime(rsProduto.getDate("cadastro"));
					p.setDataCadastro(c);
					p.setAliquotaPisCompra(new Double(myFormatter.toNumeric(
							rsProduto.getString("pis"), true)));
					p.setAliquotaCofinsCompra(new Double(myFormatter.toNumeric(
							rsProduto.getString("cofins"), true)));
					p.setAtivo(myFormatter.toSituacao(rsProduto
							.getString("situacao")));
					p.setNcm(myFormatter.toNCM(rsProduto.getString("cfiscal")));
					p.setCodigoTributacao(new Integer(myFormatter.toNumeric(
							rsProduto.getString("tributa"), false)));
					p.setIcmCompra(new Double(myFormatter.toNumeric(
							rsProduto.getString("icmcompra"), true)));
					p.setTributacaoCompra(rsProduto.getString("trbcompra"));

					ar.add(p);

				}

			} else if (software == EnSoftware.MERCATTO) {

				MercattoFormat myFormatter = new MercattoFormat();

				// enquanto o resultset conter dados
				while (rsProduto.next()) {

					// cria novo bean
					p = new Produto(GZSoftwares.MERCATTO);

					// System.out.println(rsProduto.getString("codigo_interno"));

					p.setCodigoInterno(myFormatter.toCodigoInterno(rsProduto
							.getString("codigo_interno")));

					try {

						p.setCodigoDeBarras(myFormatter
								.toCodigoDeBarras(rsProduto
										.getString("codigo_barras")));

					} catch (Exception e) {

						p.setCodigoDeBarras(p.getCodigoInterno());

					}

					p.setDescricao(myFormatter.toDescricaoProduto(rsProduto
							.getString("descricao")));
					p.setDescricaoReduzida(myFormatter
							.toDescricaoProdutoReduzida(rsProduto
									.getString("descricao_reduzida")));
					p.setUnidade(myFormatter.toUnidade(rsProduto
							.getString("unidade")));
					p.setVariavel(rsProduto.getString("peso_variavel"));
					p.setPrecoCompra(new Double(myFormatter.toNumeric(
							rsProduto.getString("preco_compra"), true)));
					p.setPrecoVenda(new Double(myFormatter.toNumeric(
							rsProduto.getString("preco_venda"), true)));
					p.setDepartamento(new Integer(myFormatter.toNumeric(
							rsProduto.getString("codigo_grupo"), false)));
					p.setGrupo(new Integer(myFormatter.toNumeric(
							rsProduto.getString("codigo_subgrupo"), false)));
					p.setArmacao(new Integer(myFormatter.toNumeric(
							rsProduto.getString("codigo_subsubgrupo"), false)));
					p.setPrecoCusto(new Double(myFormatter.toNumeric(
							rsProduto.getString("preco_custo"), true)));
					p.setPorcentagemLucro(new Double(myFormatter.toNumeric(
							rsProduto.getString("porcentagem_lucro"), true,
							"100.00")));
					p.setQuantidadeEstoqueMinimo(new Double(myFormatter
							.toNumeric(
									rsProduto.getString("qtde_estoque_minimo"),
									true)));
					p.setQuantidadeEstoqueMaximo(new Double(myFormatter
							.toNumeric(
									rsProduto.getString("qtde_estoque_maximo"),
									true)));
					p.setQuantidade(new Double(myFormatter.toNumeric(
							rsProduto.getString("qtde_atual"), true)));
					Calendar c = Calendar.getInstance();
					c.setTime(rsProduto.getDate("data_cadastro"));
					p.setDataCadastro(c);
					p.setAliquotaPisCompra(new Double(myFormatter.toNumeric(
							rsProduto.getString("aliquota_pis"), true)));
					p.setAliquotaCofinsCompra(new Double(myFormatter.toNumeric(
							rsProduto.getString("aliquota_cofins"), true)));
					p.setAtivo(myFormatter.toSituacao(rsProduto
							.getString("codigo_ativo")));
					p.setNcm(myFormatter.toNCM(rsProduto
							.getString("codigo_ncm")));
					p.setCodigoTributacao(new Integer(myFormatter.toNumeric(
							rsProduto.getString("codigo_tributacao"), false)));
					p.setIcmCompra(new Double(myFormatter.toNumeric(
							rsProduto.getString("aliquota_icms"), true)));

					ar.add(p);

				}

			}

			rsProduto.close();
			stProduto.close();

			return ar;

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public ArrayList<Departamento> getDepartamento(Connection conn) {

		try {

			ArrayList<Departamento> ar = new ArrayList();

			Departamento d = null;

			MercoFlexFormat formatter = new MercoFlexFormat();

			while (rsDepartamento.next()) {

				d = new Departamento();

				d.setCodigo(new Integer(formatter.toNumeric(
						rsDepartamento.getString("codigo"), false)));
				d.setDescricao(formatter.toDescricaoGeral(
						rsDepartamento.getString("descricao"), 20));

				ar.add(d);

			}

			rsDepartamento.close();
			stDepartamento.close();

			return ar;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<Grupo> getGrupo(Connection conn) {

		try {

			ArrayList<Grupo> ar = new ArrayList();

			Grupo d = null;

			MercoFlexFormat formatter = new MercoFlexFormat();

			while (rsGrupo.next()) {

				d = new Grupo();

				d.setCodigo(new Integer(formatter.toNumeric(
						rsGrupo.getString("codigo"), false)));
				d.setDescricao(formatter.toDescricaoGeral(
						rsGrupo.getString("descricao"), 20));

				ar.add(d);

			}

			rsGrupo.close();
			stGrupo.close();

			return ar;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<Armacao> getArmacao(Connection conn) {

		try {

			ArrayList<Armacao> ar = new ArrayList();

			Armacao d = null;

			MercoFlexFormat formatter = new MercoFlexFormat();

			while (rsArmacao.next()) {

				d = new Armacao();

				d.setCodigo(new Integer(formatter.toNumeric(
						rsArmacao.getString("codigo"), false)));
				d.setDescricao(formatter.toDescricaoGeral(
						rsArmacao.getString("descricao"), 20));

				ar.add(d);

			}

			rsArmacao.close();
			stArmacao.close();

			return ar;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<Marca> getMarca(Connection conn) {

		try {

			ArrayList<Marca> ar = new ArrayList();

			Marca d = null;

			MercoFlexFormat formatter = new MercoFlexFormat();

			while (rsMarca.next()) {

				d = new Marca();

				d.setCodigo(new Integer(formatter.toNumeric(
						rsMarca.getString("codigo"), false)));
				d.setDescricao(formatter.toDescricaoGeral(
						rsMarca.getString("descricao"), 20));

				ar.add(d);

			}

			rsMarca.close();
			stMarca.close();

			return ar;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<Cliente> getCliente(Connection conn) {

		try {

			ArrayList<Cliente> ar = new ArrayList();

			Cliente c = null;

			if (software == EnSoftware.MERCOFLEX) {

				MercoFlexFormat format = new MercoFlexFormat();

				while (rsCliente.next()) {

					c = new Cliente();
					c.setCodigo(rsCliente.getInt("codigo"));
					c.setCpf(format.toNumeric(rsCliente.getString("cgc"), false));
					c.setRg(format.toNumeric(rsCliente.getString("insest"),
							false));
					Calendar cl = Calendar.getInstance();
					cl.setTime(rsCliente.getDate("dtnasc"));
					c.setDataNascimento(cl);
					c.setSituacao(rsCliente.getString("situacao"));
					c.setEndereco(format.toEndereco(rsCliente
							.getString("ender")));
					c.setBairro(format.toBairro(rsCliente.getString("bairro")));
					c.setCidade(format.toMunicipio(rsCliente.getString("munic")));
					c.setEstado(format.toEstadoSigla(rsCliente
							.getString("estado")));
					c.setNumero(new Integer(format.toNumeric(
							rsCliente.getString("numero"), false)));
					c.setCep(format.toCEP(rsCliente.getString("cep")));
					c.setTelefoneResidencial(rsCliente.getString("telefone"));
					cl = Calendar.getInstance();
					cl.setTime(rsCliente.getDate("cadastro"));
					c.setDataCadastro(cl);
					c.setRazaoSocial(format.toRazaoSocial(rsCliente
							.getString("razsoc")));
					c.setNomeFantasia(format.toNomeFantasia(rsCliente
							.getString("nomfan")));

					ar.add(c);

				}

			} else if (software == EnSoftware.MERCATTO) {

				MercattoFormat format = new MercattoFormat();

				while (rsCliente.next()) {

					c = new Cliente();
					c.setCodigo(rsCliente.getInt("codigo_cliente"));
					c.setCpf(format.toNumeric(rsCliente.getString("cpf_cnpj"),
							false));
					c.setRg(format.toNumeric(rsCliente.getString("rg_ie"),
							false));
					Calendar cl = Calendar.getInstance();
					cl.setTime(rsCliente.getDate("data_nascimento"));
					c.setDataNascimento(cl);
					c.setSituacao(rsCliente.getString("situacao"));
					c.setEndereco(format.toEndereco(rsCliente
							.getString("endereco")));
					c.setBairro(format.toBairro(rsCliente.getString("bairro")));
					c.setCidade(format.toMunicipio(rsCliente
							.getString("cidade")));
					c.setEstado(format.toEstadoSigla(rsCliente
							.getString("estado")));
					c.setNumero(new Integer(format.toNumeric(
							rsCliente.getString("numero"), false)));
					c.setCep(format.toCEP(rsCliente.getString("cep")));
					c.setTelefoneResidencial(rsCliente.getString("telefone"));
					cl = Calendar.getInstance();
					cl.setTime(rsCliente.getDate("cadastro"));
					c.setDataCadastro(cl);
					c.setRazaoSocial(format.toRazaoSocial(rsCliente
							.getString("razao_social")));
					c.setNomeFantasia(format.toNomeFantasia(rsCliente
							.getString("nome_fantasia")));
					c.setLoja(rsCliente.getInt("codigo_loja"));

					ar.add(c);

				}

			}

			rsCliente.close();
			stCliente.close();

			return ar;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<Loja> getLoja(Connection conn) {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Fornecedor> getFornecedor(Connection conn) {

		try {

			ArrayList<Fornecedor> ar = new ArrayList();

			Fornecedor f = null;

			if (software == EnSoftware.MERCOFLEX) {

				MercoFlexFormat format = new MercoFlexFormat();

				while (rsFornecedor.next()) {

					f = new Fornecedor();
					f.setCodigo(new Integer(format.toNumeric(
							rsFornecedor.getString("codigo"), false)));
					f.setNomeFantasia(format.toNomeFantasia(rsFornecedor
							.getString("nomfan")));
					f.setRazaoSocial(format.toRazaoSocial(rsFornecedor
							.getString("razsoc")));
					f.setEndereco(format.toEndereco(rsFornecedor
							.getString("ender")));
					f.setNumero(new Integer(format.toNumeric(
							rsFornecedor.getString("numero"), false)));
					f.setBairro(format.toBairro(rsFornecedor
							.getString("bairro")));
					f.setCidade(format.toMunicipio(rsFornecedor
							.getString("munic")));
					f.setEstado(format.toEstadoSigla(rsFornecedor
							.getString("estado")));
					f.setCep(format.toCEP(rsFornecedor.getString("cep")));
					f.setTelefonePrincipal(rsFornecedor.getString("telefone"));
					f.setCnpj(format.toCNPJ(rsFornecedor.getString("cgc")));
					f.setInscricaoEstadual(format
							.toInscricaoEstadual(rsFornecedor
									.getString("insest")));

					ar.add(f);

				}

			} else if (software == EnSoftware.MERCATTO) {

				MercoFlexFormat format = new MercoFlexFormat();

				while (rsFornecedor.next()) {

					f = new Fornecedor();
					f.setCodigo(new Integer(format.toNumeric(
							rsFornecedor.getString("codigo_fornecedor"), false)));
					f.setNomeFantasia(format.toNomeFantasia(rsFornecedor
							.getString("nome_fantasia")));
					f.setRazaoSocial(format.toRazaoSocial(rsFornecedor
							.getString("razao_social")));
					f.setEndereco(format.toEndereco(rsFornecedor
							.getString("endereco")));
					f.setNumero(new Integer(format.toNumeric(
							rsFornecedor.getString("numero"), false)));
					f.setBairro(format.toBairro(rsFornecedor
							.getString("bairro")));
					f.setCidade(format.toMunicipio(rsFornecedor
							.getString("cidade")));
					f.setEstado(format.toEstadoSigla(rsFornecedor
							.getString("estado")));
					f.setCep(format.toCEP(rsFornecedor.getString("cep")));
					f.setCnpj(format.toCNPJ(rsFornecedor.getString("cpf_cnpj")));
					f.setInscricaoEstadual(format
							.toInscricaoEstadual(rsFornecedor
									.getString("rg_ie")));
					Calendar cl = Calendar.getInstance();
					cl.setTime(rsFornecedor.getDate("data_cadastro"));
					f.setDataCadastro(cl);

					ar.add(f);

				}

			}

			rsFornecedor.close();
			stFornecedor.close();

			return ar;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	// retorna as informações da tabela gerada
	// só é executado se houver um retorno POLICY_VIOLATION
	// cuidado!!! se o método getProduto for executado, o resultset estará
	// fechado
	@Override
	public ArrayList<String> getProdutoColumnsNeeded() {
		// johnny Auto-generated method stub
		try {

			ResultSetMetaData rst = rsProduto.getMetaData();

			rsProduto.close();
			stProduto.close();

			policy.validate(rst, EnMigrationDataType.PRODUTO);

			LogFile.getInstance().writeInFile(
					"File 'getProduto.txt': Columns "
							+ policy.getColumnsNeeded().toString()
									.replace("[", "").replace("]", "")
							+ " not found in SQL statement");

			return policy.getColumnsNeeded();

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<String> getDepartamentoColumnsNeeded() {
		// johnny Auto-generated method stub
		try {

			ResultSetMetaData rst = rsDepartamento.getMetaData();

			rsDepartamento.close();
			stDepartamento.close();

			policy.validate(rst, EnMigrationDataType.DEPARTAMENTO);

			LogFile.getInstance().writeInFile(
					"File 'getDepartamento.txt': Columns "
							+ policy.getColumnsNeeded().toString()
									.replace("[", "").replace("]", "")
							+ " not found in SQL statement");

			return policy.getColumnsNeeded();

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<String> getGrupoColumnsNeeded() {
		// johnny Auto-generated method stub
		try {

			ResultSetMetaData rst = rsGrupo.getMetaData();

			rsGrupo.close();
			stGrupo.close();

			policy.validate(rst, EnMigrationDataType.GRUPO);

			LogFile.getInstance().writeInFile(
					"File 'getGrupo.txt': Columns "
							+ policy.getColumnsNeeded().toString()
									.replace("[", "").replace("]", "")
							+ " not found in SQL statement");

			return policy.getColumnsNeeded();

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<String> getArmacaoColumnsNeeded() {

		// johnny Auto-generated method stub
		try {

			ResultSetMetaData rst = rsArmacao.getMetaData();

			rsArmacao.close();
			stArmacao.close();

			policy.validate(rst, EnMigrationDataType.ARMACAO);

			LogFile.getInstance().writeInFile(
					"File 'getArmacao.txt': Columns "
							+ policy.getColumnsNeeded().toString()
									.replace("[", "").replace("]", "")
							+ " not found in SQL statement");

			return policy.getColumnsNeeded();

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<String> getMarcaColumnsNeeded() {
		// johnny Auto-generated method stub
		try {

			ResultSetMetaData rst = rsMarca.getMetaData();

			rsMarca.close();
			stMarca.close();

			policy.validate(rst, EnMigrationDataType.MARCA);

			LogFile.getInstance().writeInFile(
					"File 'getMarca.txt': Columns "
							+ policy.getColumnsNeeded().toString()
									.replace("[", "").replace("]", "")
							+ " not found in SQL statement");

			return policy.getColumnsNeeded();

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public ArrayList<String> getClienteColumnsNeeded() {
		// johnny Auto-generated method stub
		try {

			ResultSetMetaData rst = rsCliente.getMetaData();

			rsCliente.close();
			stCliente.close();

			policy.validate(rst, EnMigrationDataType.CLIENTE);

			LogFile.getInstance().writeInFile(
					"File 'getCliente.txt': Columns "
							+ policy.getColumnsNeeded().toString()
									.replace("[", "").replace("]", "")
							+ " not found in SQL statement");

			return policy.getColumnsNeeded();

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<String> getLojaColumnsNeeded() {
		// johnny Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getFornecedorColumnsNeeded() {
		// johnny Auto-generated method stub
		try {

			ResultSetMetaData rst = rsFornecedor.getMetaData();

			rsFornecedor.close();
			stFornecedor.close();

			policy.validate(rst, EnMigrationDataType.FORNECEDOR);

			LogFile.getInstance().writeInFile(
					"File 'getFornecedor.txt': Columns "
							+ policy.getColumnsNeeded().toString()
									.replace("[", "").replace("]", "")
							+ " not found in SQL statement");

			return policy.getColumnsNeeded();

		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public int countProduto(ProdutoDataFile dataFile) throws IOException {
		return dataFile.getTotalRows();
	}

	@Override
	public int countDepartamento(DepartamentoDataFile dataFile)
			throws IOException {
		return dataFile.getTotalRows();
	}

	@Override
	public int countGrupo(GrupoDataFile dataFile) throws IOException {
		return dataFile.getTotalRows();
	}

	@Override
	public int countArmacao(ArmacaoDataFile dataFile) throws IOException {
		return dataFile.getTotalRows();
	}

	@Override
	public int countMarca(MarcaDataFile dataFile) throws IOException {
		return dataFile.getTotalRows();
	}

	@Override
	public int countCliente(ClienteDataFile dataFile) throws IOException {
		return dataFile.getTotalRows();
	}

	@Override
	public int countFornecedor(FornecedorDataFile dataFile) throws IOException {
		return dataFile.getTotalRows();
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

}