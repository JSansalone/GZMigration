package br.com.gz.migration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.database.connection.ConnectionFactory;

import br.com.gz.bean.Armacao;
import br.com.gz.bean.Cliente;
import br.com.gz.bean.Departamento;
import br.com.gz.bean.Fornecedor;
import br.com.gz.bean.Grupo;
import br.com.gz.bean.Loja;
import br.com.gz.bean.Marca;
import br.com.gz.bean.Produto;
import br.com.gz.migration.datafile.ArmacaoDataFile;
import br.com.gz.migration.datafile.ClienteDataFile;
import br.com.gz.migration.datafile.DepartamentoDataFile;
import br.com.gz.migration.datafile.FornecedorDataFile;
import br.com.gz.migration.datafile.GrupoDataFile;
import br.com.gz.migration.datafile.MarcaDataFile;
import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.migration.exception.InvalidCellTypeException;
import br.com.gz.migration.exception.RequiredColumnNotFoundException;
import br.com.gz.migration.exception.SecurityViolationException;
import br.com.gz.migration.file.LogFile;
import br.com.gz.migration.steps.IMigrationInfo;
import br.com.gz.util.GZSoftwares;

/**
 * Motor de migra��o<br>
 * <br>
 * <br>
 * 
 * Classe respons�vel por manipular os dados recuperados pela subclasse de
 * SQLDataProvider
 * 
 * @author Jonathan Sansalone
 * 
 */
class MigrationEngine extends Thread implements IMigrationResults {

	// tipos de migra��o
	/**
	 * Vari�vel que guarda os tipos de migra��o que ser�o utilizados
	 */
	private ArrayList<EnMigrationDataType> migrationType;

	/**
	 * Vari�vel que executa alguns passos ao terminar a migra��o
	 */
	private IFinalizeMigration iFinalize;

	/**
	 * Vari�vel que fornece informa��es sobre a migra��o ap�s o t�rmino
	 */
	private IMigrationInfo iInfo;

	/**
	 * Vari�vel que fornece alguns dados �teis para a migra��o
	 */
	private IMigrationType iType;

	/**
	 * Vari�vel respons�vel por coletar e inserir os dados
	 */
	private SQLDataProvider myDAO;

	/**
	 * Vari�vel que cont�m as configura��es do banco de dados de destino
	 */
	private IDatabaseInfo myCfgTo;

	/**
	 * Vari�vel que cont�m as configura��es do banco de dados de origem. Esta
	 * n�o ser� mais utilizada
	 */
	private IDatabaseInfo myCfgFrom;

	/**
	 * Vari�vel que guarda a conex�o com o banco de dados de destino
	 */
	private Connection cnnTo;

	/**
	 * Vari�vel que guarda a conex�o com o banco de dados de origem
	 */
	private Connection cnnFrom;

	// total geral de dados a serem migrados
	/**
	 * Guarda o total geral de dados a serem migrados
	 */
	private int totalData;

	/**
	 * Guarda o subtotal de produtos
	 */
	private int totalProdutoRetrieved;
	// apenas usado quando tiver que anexar produtos

	/**
	 * Guarda o subtotal de produtos antes de inserir os novos. (Modo anexar)
	 */
	private int totalProdutoInicio;

	/**
	 * Guarda o total final ap�s incluir os produtos novos
	 */
	private int totalProdutoFinal;
	// ------------------------------------

	/**
	 * Guarda o total de departamentos
	 */
	private int totalDepartamentoRetrieved;

	/**
	 * Guarda o total de grupos
	 */
	private int totalGrupoRetrieved;

	/**
	 * Guarda o total de marcas
	 */
	private int totalMarcaRetrieved;

	/**
	 * Guarda o total de arma��es
	 */
	private int totalArmacaoRetrieved;

	/**
	 * Guarda o total de clientes
	 */
	private int totalClienteRetrieved;

	/**
	 * Guarda o total de fornecedores
	 */
	private int totalFornecedorRetrieved;

	/**
	 * Guarda o total de lojas
	 */
	private int totalLoja;

	/**
	 * Guarda o total de notas fiscais de entrada
	 */
	private int totalNFEntradaRetrieved;

	/**
	 * Guarda o total de itens das notas fiscais de entrada
	 */
	private int totalNFEntradaItemRetrieved;

	/**
	 * Guarda o total de notas fiscais de saida
	 */
	private int totalNFSaidaRetrieved;

	/**
	 * Guarda o total de itens das notas fiscais de saida
	 */
	private int totalNFSaidaItemRetrieved;

	/**
	 * Guarda o total de contas a pagar
	 */
	private int totalContaPagarRetrieved;

	/**
	 * Guarda o total de contas a receber
	 */
	private int totalContaReceberRetrieved;

	// ==========================
	/**
	 * Guarda o subtotal de produtos novos cadastrado
	 */
	private int totalProdutoRegistered;

	/**
	 * Guarda o subtotal de produtos que j� est�o cadastrados, mas que foram
	 * inseridos em outras lojas
	 */
	private int totalProdutoIncluded;

	/**
	 * Guarda o total de departamentos inseridos
	 */
	private int totalDepartamentoInserted;

	/**
	 * Guarda o total de grupos inseridos
	 */
	private int totalGrupoInserted;

	/**
	 * Guarda o total de marcas inseridas
	 */
	private int totalMarcaInserted;

	/**
	 * Guarda o total de arma��es inseridas
	 */
	private int totalArmacaoInserted;

	/**
	 * Guarda o total de clientes inseridos
	 */
	private int totalClienteInserted;

	/**
	 * Guarda o total de fornecedores inseridos
	 */
	private int totalFornecedorInserted;

	/**
	 * Guarda o total de notas fiscais de entrada inseridas
	 */
	private int totalNFEntradaInserted;

	/**
	 * Guarda o total de itens das notas fiscais de entrada inseridos
	 */
	private int totalNFEntradaItemInserted;

	/**
	 * Guarda o total de notas fiscais de saida inseridas
	 */
	private int totalNFSaidaInserted;

	/**
	 * Guarda o total de itens das notas fiscais de saida inseridos
	 */
	private int totalNFSaidaItemInserted;

	/**
	 * Guarda o total de contas a pagar inseridas
	 */
	private int totalContaPagarInserted;

	/**
	 * Guarda o total de contas a receber inseridas
	 */
	private int totalContaReceberInserted;
	// ==========================

	/**
	 * Guarda o total de movimenta��es de venda inseridas
	 */
	private int totalMovtoVendaRetrieved;

	// sinaliza se vai anexar ou sobrepor cadastros referentes � lojas
	// (produtos)
	/**
	 * Sinaliza se vai incluir ou sobrepor os cadastros de produtos
	 */
	private boolean toAppend;

	// flags para indicar quais dados ser�o usados
	/**
	 * Flag para indicar se vai ser migrado produtos
	 */
	private boolean useProduto = false;

	/**
	 * Flag para indicar se vai ser migrado grupos
	 */
	private boolean useGrupo = false;

	/**
	 * Flag para indicar se vai ser migrado departamentos
	 */
	private boolean useDepartamento = false;

	/**
	 * Flag para indicar se vai ser migrado marcas
	 */
	private boolean useMarca = false;

	/**
	 * Flag para indicar se vai ser migrado arma��es
	 */
	private boolean useArmacao = false;

	/**
	 * Flag para indicar se vai ser migrado lojas
	 */
	private boolean useLoja = false;

	/**
	 * Flag para indicar se vai ser migrado clientes
	 */
	private boolean useCliente = false;

	/**
	 * Flag para indicar se vai ser migrado
	 */
	private boolean useFornecedor = false;

	/**
	 * Flag para indicar se vai ser migrado notas fiscais de entrada
	 */
	private boolean useNFEntrada = false;

	/**
	 * Flag para indicar se vai ser migrado notas fiscais de saida
	 */
	private boolean useNFSaida = false;

	/**
	 * Flag para indicar se vai ser migrado contas a pagar
	 */
	private boolean useContaPagar = false;

	/**
	 * Flag para indicar se vai ser migrado contas a receber
	 */
	private boolean useContaReceber = false;

	/**
	 * Flag para indicar se vai ser migrado movimenta��es de venda
	 */
	private boolean useMovtoVenda = false;

	/**
	 * Construtor que instancia a classe
	 * 
	 * @param migrationType
	 * @param iFinalize
	 * @param iInfo
	 * @param iType
	 * @param myDAO
	 * @param cfgTo
	 * @param cfgFrom
	 */
	@Deprecated
	MigrationEngine(ArrayList<EnMigrationDataType> migrationType,
			IFinalizeMigration iFinalize, IMigrationInfo iInfo,
			IMigrationType iType, SQLDataProvider myDAO, IDatabaseInfo cfgTo,
			IDatabaseInfo cfgFrom) {

		this.iFinalize = iFinalize;
		this.migrationType = migrationType;
		this.iType = iType;
		this.iInfo = iInfo;
		this.myDAO = myDAO;
		this.myCfgTo = cfgTo;
		this.myCfgFrom = cfgFrom;
		this.toAppend = iType.toAppend();

		try {
			// conectando ao banco de destino
			LogFile.getInstance().writeInFile(
					"Trying to connect to server "
							+ myCfgTo.getDatabaseInfo().getIpAddress());
			LogFile.getInstance().writeInFile(
					"Database type: " + myCfgTo.getDatabaseInfo().getDbType());
			LogFile.getInstance().writeInFile(
					"Database name: "
							+ myCfgTo.getDatabaseInfo().getDatabaseName());
			LogFile.getInstance().writeInFile(
					"Port: " + myCfgTo.getDatabaseInfo().getPort());
			LogFile.getInstance().writeInFile(
					"Username: " + myCfgTo.getDatabaseInfo().getUsername());
			LogFile.getInstance()
					.writeInFile(
							"Using password: "
									+ (myCfgTo.getDatabaseInfo().getPassword() != "" ? "yes"
											: "no"));
			cnnTo = ConnectionFactory.getConnection(myCfgTo.getDatabaseInfo()
					.getDbType(), cfgTo.getDatabaseInfo());
			LogFile.getInstance().writeInFile("Connected");
			// conectando ao banco de origem
			LogFile.getInstance().writeInFile(
					"Trying to connect to server "
							+ myCfgFrom.getDatabaseInfo().getIpAddress());
			LogFile.getInstance()
					.writeInFile(
							"Database type: "
									+ myCfgFrom.getDatabaseInfo().getDbType());
			LogFile.getInstance().writeInFile(
					"Database name: "
							+ myCfgFrom.getDatabaseInfo().getDatabaseName());
			LogFile.getInstance().writeInFile(
					"Port: " + myCfgFrom.getDatabaseInfo().getPort());
			LogFile.getInstance().writeInFile(
					"Username: " + myCfgFrom.getDatabaseInfo().getUsername());
			LogFile.getInstance()
					.writeInFile(
							"Using password: "
									+ (myCfgFrom.getDatabaseInfo()
											.getPassword() != "" ? "yes" : "no"));
			cnnFrom = ConnectionFactory.getConnection(myCfgFrom
					.getDatabaseInfo().getDbType(), cfgFrom.getDatabaseInfo());
			LogFile.getInstance().writeInFile("Connected");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Construtor que inicializa o motor de migra��o <br>
	 * 
	 * @param migrationType
	 *            - Lista com todos os tipos de migra��o desejados
	 * @param iFinalize
	 *            - Objeto para executar os �ltimos passos da migra��o
	 * @param iInfo
	 *            - Objeto que atualiza a barra de progresso
	 * @param iType
	 *            - Objeto que fornece a quantidade de lojas
	 * @param myDAO
	 *            - Objeto SQLDataProvider
	 * @param cfgTo
	 *            - Configura��es do banco de dados de origem
	 */
	MigrationEngine(ArrayList<EnMigrationDataType> migrationType,
			IFinalizeMigration iFinalize, IMigrationInfo iInfo,
			IMigrationType iType, SQLDataProvider myDAO, IDatabaseInfo cfgTo) {

		this.iFinalize = iFinalize;
		this.migrationType = migrationType;
		this.iType = iType;
		this.iInfo = iInfo;
		this.myDAO = myDAO;
		this.myCfgTo = cfgTo;
		this.toAppend = iType.toAppend();

		try {

			// conectando SOMENTE ao banco de destino
			LogFile.getInstance().writeInFile(
					"Trying to connect to server "
							+ myCfgTo.getDatabaseInfo().getIpAddress());
			LogFile.getInstance().writeInFile(
					"Database type: " + myCfgTo.getDatabaseInfo().getDbType());
			LogFile.getInstance().writeInFile(
					"Database name: "
							+ myCfgTo.getDatabaseInfo().getDatabaseName());
			LogFile.getInstance().writeInFile(
					"Port: " + myCfgTo.getDatabaseInfo().getPort());
			LogFile.getInstance().writeInFile(
					"Username: " + myCfgTo.getDatabaseInfo().getUsername());
			LogFile.getInstance()
					.writeInFile(
							"Using password: "
									+ (myCfgTo.getDatabaseInfo().getPassword() != "" ? "yes"
											: "no"));
			cnnTo = ConnectionFactory.getConnection(myCfgTo.getDatabaseInfo()
					.getDbType(), cfgTo.getDatabaseInfo());
			LogFile.getInstance().writeInFile("Connected");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
		}

	}

	/**
	 * Realiza a migra��o
	 */
	public void run() {

		ProdutoDataFile produtoDataFile = null;
		DepartamentoDataFile departamentoDataFile = null;
		GrupoDataFile grupoDataFile = null;
		ArmacaoDataFile armacaoDataFile = null;
		MarcaDataFile marcaDataFile = null;
		ClienteDataFile clienteDataFile = null;
		FornecedorDataFile fornecedorDataFile = null;

		int count = 0;

		// Listar todos os tipos de migra��o escolhidos
		Iterator<EnMigrationDataType> it = migrationType.iterator();

		// enquanto houver tipos de migra��o na lista
		while (it.hasNext()) {

			// escolhendo o tipo de migra��o
			switch (it.next()) {

			case PRODUTO:

				// conta quantos produtos ser�o migrados
				try {

					// tenta obter o total de produtos
					// se uma exce��o for lan�ada, houve falha ao ler o arquivo
					LogFile.getInstance().writeInFile(
							"Trying to count 'produto'");

					produtoDataFile = ProdutoDataFile.getInstance(myCfgTo
							.getSoftware());

					totalProdutoRetrieved = myDAO.countProduto(produtoDataFile);

				} catch (IOException e) {

					if (e instanceof FileNotFoundException) {
						MigrationEngineMessages
								.showFileNotFoundErrorMessage(EnMigrationDataType.PRODUTO);
					} else {

						MigrationEngineMessages
								.showErrorMessage(EnMigrationDataType.PRODUTO);
						e.printStackTrace();
						LogFile.getInstance().writeInFile(
								"failed to retrieve data from file");
						LogFile.getInstance().writeInFile(e.getMessage());

					}
					continue;

				}
				// se n�o estiver vazio
				if (totalProdutoRetrieved != SQLDataProvider.EMPTY_RETURN) {
					if (totalProdutoRetrieved != SQLDataProvider.POLICY_VIOLATION) {
						// adiciona o total de produtos ao total geral de dados
						totalData += (totalProdutoRetrieved * iType
								.getNumLoja());
						useProduto = true;
						System.out.println("usa produto. Sub-total="
								+ totalProdutoRetrieved);
					} else {

						MigrationEngineMessages.showPolicyViolationMessage(
								EnMigrationDataType.PRODUTO,
								myDAO.getColumnsNeeded(produtoDataFile));

						continue;

					}
				}

				break;

			case DEPARTAMENTO:

				try {
					LogFile.getInstance().writeInFile(
							"Trying to count 'departamento'");

					departamentoDataFile = DepartamentoDataFile.getInstance(myCfgTo.getSoftware());
					
					totalDepartamentoRetrieved = myDAO
							.countDepartamento(departamentoDataFile);
				} catch (IOException e) {

					if (e instanceof FileNotFoundException) {
						MigrationEngineMessages
								.showFileNotFoundErrorMessage(EnMigrationDataType.DEPARTAMENTO);
					} else {

						MigrationEngineMessages
								.showErrorMessage(EnMigrationDataType.DEPARTAMENTO);
						e.printStackTrace();
						LogFile.getInstance().writeInFile(
								"failed to retrieve data from file");
						LogFile.getInstance().writeInFile(e.getMessage());

					}

					continue;

				}
				if (totalDepartamentoRetrieved != SQLDataProvider.EMPTY_RETURN) {
					if (totalDepartamentoRetrieved != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalDepartamentoRetrieved;
						useDepartamento = true;
						System.out.println("usa departamento. Sub-total="
								+ totalDepartamentoRetrieved);
					} else {

						MigrationEngineMessages.showPolicyViolationMessage(
								EnMigrationDataType.DEPARTAMENTO,
								myDAO.getColumnsNeeded(departamentoDataFile));
						continue;

					}
				}

				break;

			case GRUPO:

				try {
					LogFile.getInstance()
							.writeInFile("Trying to count 'grupo'");

					grupoDataFile = GrupoDataFile.getInstance(myCfgTo.getSoftware());
					
					totalGrupoRetrieved = myDAO.countGrupo(grupoDataFile);
				} catch (IOException e) {

					if (e instanceof FileNotFoundException) {
						MigrationEngineMessages
								.showFileNotFoundErrorMessage(EnMigrationDataType.GRUPO);
					} else {

						MigrationEngineMessages
								.showErrorMessage(EnMigrationDataType.GRUPO);
						e.printStackTrace();
						LogFile.getInstance().writeInFile(
								"failed to retrieve data from file");
						LogFile.getInstance().writeInFile(e.getMessage());

					}

					continue;

				}
				if (totalGrupoRetrieved != SQLDataProvider.EMPTY_RETURN) {
					if (totalGrupoRetrieved != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalGrupoRetrieved;
						useGrupo = true;
						System.out.println("usa grupo. Sub-total="
								+ totalGrupoRetrieved);
					} else {

						MigrationEngineMessages.showPolicyViolationMessage(
								EnMigrationDataType.GRUPO,
								myDAO.getColumnsNeeded(grupoDataFile));
						continue;

					}
				}

				break;

			case ARMACAO:

				try {
					LogFile.getInstance().writeInFile(
							"Trying to count 'armacao'");

					armacaoDataFile = ArmacaoDataFile.getInstance(myCfgTo.getSoftware());
					
					totalArmacaoRetrieved = myDAO.countArmacao(armacaoDataFile);
				} catch (IOException e) {

					if (e instanceof FileNotFoundException) {
						MigrationEngineMessages
								.showFileNotFoundErrorMessage(EnMigrationDataType.ARMACAO);
					} else {

						MigrationEngineMessages
								.showErrorMessage(EnMigrationDataType.ARMACAO);
						e.printStackTrace();
						LogFile.getInstance().writeInFile(
								"failed to retrieve data from file");
						LogFile.getInstance().writeInFile(e.getMessage());

					}

					continue;

				}
				if (totalArmacaoRetrieved != SQLDataProvider.EMPTY_RETURN) {
					if (totalArmacaoRetrieved != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalArmacaoRetrieved;
						useArmacao = true;
						System.out.println("usa armacao. Sub-total="
								+ totalArmacaoRetrieved);
					} else {

						MigrationEngineMessages.showPolicyViolationMessage(
								EnMigrationDataType.ARMACAO,
								myDAO.getColumnsNeeded(armacaoDataFile));

						continue;

					}
				}

				break;

			case MARCA:

				try {
					LogFile.getInstance()
							.writeInFile("Trying to count 'marca'");

					marcaDataFile = MarcaDataFile.getInstance(myCfgTo.getSoftware());
					
					totalMarcaRetrieved = myDAO.countMarca(marcaDataFile);
				} catch (IOException e) {

					if (e instanceof FileNotFoundException) {
						MigrationEngineMessages
								.showFileNotFoundErrorMessage(EnMigrationDataType.MARCA);
					} else {

						MigrationEngineMessages
								.showErrorMessage(EnMigrationDataType.MARCA);
						e.printStackTrace();
						LogFile.getInstance().writeInFile(
								"failed to retrieve data from file");
						LogFile.getInstance().writeInFile(e.getMessage());

					}

					continue;

				}
				if (totalMarcaRetrieved != SQLDataProvider.EMPTY_RETURN) {
					if (totalMarcaRetrieved != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalMarcaRetrieved;
						useMarca = true;
						System.out.println("usa marca. Sub-total="
								+ totalMarcaRetrieved);
					} else {

						MigrationEngineMessages.showPolicyViolationMessage(
								EnMigrationDataType.MARCA,
								myDAO.getColumnsNeeded(marcaDataFile));
						continue;

					}
				}

				break;

			case FORNECEDOR:

				try {
					LogFile.getInstance().writeInFile(
							"Trying to count 'fornecedor'");

					fornecedorDataFile = FornecedorDataFile.getInstance(myCfgTo.getSoftware());
					
					totalFornecedorRetrieved = myDAO
							.countFornecedor(fornecedorDataFile);
				} catch (IOException e) {

					if (e instanceof FileNotFoundException) {
						MigrationEngineMessages
								.showFileNotFoundErrorMessage(EnMigrationDataType.FORNECEDOR);
					} else {

						MigrationEngineMessages
								.showErrorMessage(EnMigrationDataType.FORNECEDOR);
						e.printStackTrace();
						LogFile.getInstance().writeInFile(
								"failed to retrieve data from file");
						LogFile.getInstance().writeInFile(e.getMessage());

					}

					continue;

				}
				if (totalFornecedorRetrieved != SQLDataProvider.EMPTY_RETURN) {
					if (totalFornecedorRetrieved != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalFornecedorRetrieved;
						useFornecedor = true;
						System.out.println("usa fornecedor. Sub-total="
								+ totalFornecedorRetrieved);
					} else {

						MigrationEngineMessages.showPolicyViolationMessage(
								EnMigrationDataType.FORNECEDOR,
								myDAO.getColumnsNeeded(fornecedorDataFile));
						continue;

					}
				}

				break;

			case CLIENTE:

				try {
					LogFile.getInstance().writeInFile(
							"Trying to count 'cliente'");

					clienteDataFile = ClienteDataFile.getInstance(myCfgTo.getSoftware());
					
					totalClienteRetrieved = myDAO.countCliente(clienteDataFile);
					
				} catch (IOException e) {

					if (e instanceof FileNotFoundException) {
						MigrationEngineMessages
								.showFileNotFoundErrorMessage(EnMigrationDataType.CLIENTE);
					} else {

						MigrationEngineMessages
								.showErrorMessage(EnMigrationDataType.CLIENTE);
						e.printStackTrace();
						LogFile.getInstance().writeInFile(
								"failed to retrieve data from file");
						LogFile.getInstance().writeInFile(e.getMessage());

					}

					continue;

				}
				if (totalClienteRetrieved != SQLDataProvider.EMPTY_RETURN) {
					if (totalClienteRetrieved != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalClienteRetrieved;
						useCliente = true;
						System.out.println("usa cliente. Sub-total="
								+ totalClienteRetrieved);
					} else {

						MigrationEngineMessages.showPolicyViolationMessage(
								EnMigrationDataType.CLIENTE,
								myDAO.getColumnsNeeded(clienteDataFile));
						continue;

					}
				}

				break;

			default:

				break;

			}

		}

		System.out.println("total=" + totalData);

		System.out.println("Anexar=" + toAppend);

		LogFile.getInstance().writeInFile(
				"Total number of valid retrieved rows: " + totalData);
		LogFile.getInstance().writeInFile("Append: " + toAppend);

		// ======================= INSERINDO PRODUTOS
		// ===========================
		if (useProduto) {
			// recupera todos os produtos e monta um iterator
			ArrayList<Produto> arP = myDAO.getProduto(produtoDataFile);
			Iterator<Produto> itP = arP.iterator();
			// recupera a quantidade atual de produtos na tabela 'estoque'
			// (MercoFlex)
			if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
				totalProdutoInicio = countCurrentProduto(cnnTo,
						myCfgTo.getSoftware(), "estoque");
			} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
				totalProdutoInicio = countCurrentProduto(cnnTo,
						myCfgTo.getSoftware(), "produto");
			} else {
				totalProdutoInicio = 0;
			}
			// recupera o maior codigo de loja para ser usado caso seja
			// escolhida a op��o de anexar
			int currentLoja = getMaxLoja(cnnTo, myCfgTo.getSoftware());
			// iniciando o processo de limpeza
			if (!toAppend) {
				LogFile.getInstance().writeInFile("Deleting 'produto'");
				iInfo.setText("Excluindo produtos antigos");
				if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
					clear(cnnTo, "estoque");
					clear(cnnTo, "estmix");
					clear(cnnTo, "esttrib");
					clear(cnnTo, "saldos");
				} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
					clear(cnnTo, "produto");
					clear(cnnTo, "produto_ean");
					clear(cnnTo, "produto_mix");
					clear(cnnTo, "produto_estoque");
					clear(cnnTo, "produto_preco");
				}
			}
			// -------------------------------
			// atualiza o texto da barra de progresso
			iInfo.setText("Inserindo registros nas tabelas de produtos");
			LogFile.getInstance().writeInFile("Inserting 'produto'");
			// enquanto houver objetos Produto
			while (itP.hasNext()) {
				// pega o Produto
				Produto p = itP.next();

				boolean added = false;

				if (toAppend) {

					if (!(exists(cnnTo, p.getCodigoInterno(), "estoque",
							"cdprod") || exists(cnnTo, p.getCodigoInterno(),
							"esttrib", "cdprod"))) {
						added = myDAO.addProduto(cnnTo, p);

						if (added)
							totalProdutoRegistered++;

					} else {

						added = true;

					}

				} else {

					added = myDAO.addProduto(cnnTo, p);

					if (added)
						totalProdutoRegistered++;

				}

				// se conseguir adicionar
				if (added) {
					// se o modo de inser��o for de sobrepor
					// adiciona para todas as lojas indicadas pela quantidade
					if (!toAppend) {

						boolean included = false;

						for (int i = 0; i < iType.getNumLoja(); i++) {

							// se conseguir inserir atualiza o status da
							// barra

							included = myDAO.addProdutoLoja(cnnTo, p,
									iType.ignoreCodes(), (i + 1));

							if (included) {

								iInfo.setValue(((count++) * 100) / totalData);

							}

						}

						if (included)
							totalProdutoIncluded++;

					} else {

						if (iType.ignoreCodes()) {

							int aux = totalProdutoIncluded + 1;

							for (int i = currentLoja; i < iType.getNumLoja()
									+ currentLoja; i++) {

								if (!(exists(cnnTo, i + 1,
										p.getCodigoInterno(), "estmix", "loja",
										"cdprod") || exists(cnnTo, i + 1,
										p.getCodigoInterno(), "saldos", "loja",
										"cdprod"))
										&& myDAO.addProdutoLoja(cnnTo, p,
												iType.ignoreCodes(), (i + 1))) {

									iInfo.setValue(((count++) * 100)
											/ totalData);

									totalProdutoIncluded = aux;

								}

							}

						} else {

							int aux = totalProdutoIncluded + 1;

							if (!(exists(cnnTo, p.getLoja(),
									p.getCodigoInterno(), "estmix", "loja",
									"cdprod") || exists(cnnTo, p.getLoja(),
									p.getCodigoInterno(), "saldos", "loja",
									"cdprod"))
									&& myDAO.addProdutoLoja(cnnTo, p,
											iType.ignoreCodes(), 0)) {

								iInfo.setValue(((count++) * 100) / totalData);

								totalProdutoIncluded = aux;

							}

						}

					}

				}

			}
			// recupera a quantidade atual de produtos na tabela 'estoque'
			// (MercoFlex)
			if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
				totalProdutoFinal = countCurrentProduto(cnnTo,
						myCfgTo.getSoftware(), "estoque");
			} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
				totalProdutoFinal = countCurrentProduto(cnnTo,
						myCfgTo.getSoftware(), "produto");
			}
			{
				totalProdutoFinal = 0;
			}
			itP = null;
			arP = null;

			produtoDataFile.writeNotInserteds();

			System.out.println("Total de produtos novos cadastrados: "
					+ totalProdutoRegistered);
			System.out.println("Total de produtos inseridos: "
					+ totalProdutoIncluded);

		}
		// =============================================================================

		if (useDepartamento) {
			LogFile.getInstance().writeInFile("Deleting 'departamento'");
			iInfo.setText("Excluindo departamento antigos");
			if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
				clear(cnnTo, "depto");
			} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
				clear(cnnTo, "grupo");
			}
			LogFile.getInstance().writeInFile("Inserting 'departamento'");
			iInfo.setText("Inserindo registros na tabela de departamentos");
			ArrayList<Departamento> arD = myDAO
					.getDepartamento(departamentoDataFile);
			Iterator<Departamento> itD = arD.iterator();
			while (itD.hasNext()) {
				Departamento d = itD.next();
				myDAO.addDepartamento(cnnTo, d);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itD = null;
			arD = null;

			departamentoDataFile.writeNotInserteds();

		}

		if (useGrupo) {
			LogFile.getInstance().writeInFile("Deleting 'grupo'");
			iInfo.setText("Excluindo grupos antigos");
			if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
				clear(cnnTo, "grupo");
			} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
				clear(cnnTo, "subgrupo");
			}
			LogFile.getInstance().writeInFile("Inserting 'grupo'");
			iInfo.setText("Inserindo registros na tabela de grupos");
			ArrayList<Grupo> arG = myDAO.getGrupo(grupoDataFile);
			Iterator<Grupo> itG = arG.iterator();
			while (itG.hasNext()) {
				Grupo g = itG.next();
				myDAO.addGrupo(cnnTo, g);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itG = null;
			arG = null;

			grupoDataFile.writeNotInserteds();

		}

		if (useArmacao) {
			LogFile.getInstance().writeInFile("Deleting 'armacao'");
			iInfo.setText("Excluindo arma��es antigas");
			if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
				clear(cnnTo, "armacao");
			} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
				clear(cnnTo, "subgrupo1");
			}
			LogFile.getInstance().writeInFile("Inserting 'armacao'");
			iInfo.setText("Inserindo registros na tabela de arma��es");
			ArrayList<Armacao> arA = myDAO.getArmacao(armacaoDataFile);
			Iterator<Armacao> itA = arA.iterator();
			while (itA.hasNext()) {
				Armacao m = itA.next();
				myDAO.addArmacao(cnnTo, m);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itA = null;
			arA = null;

			armacaoDataFile.writeNotInserteds();

		}

		if (useMarca) {
			LogFile.getInstance().writeInFile("Deleting 'marca'");
			iInfo.setText("Excluindo marcas antigas");
			if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
				clear(cnnTo, "marca");
			} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
				clear(cnnTo, "subgrupo1");
			}
			LogFile.getInstance().writeInFile("Inserting 'marca'");
			iInfo.setText("Inserindo registros na tabela de marcas");
			ArrayList<Marca> arM = myDAO.getMarca(marcaDataFile);
			Iterator<Marca> itM = arM.iterator();
			while (itM.hasNext()) {
				Marca m = itM.next();
				myDAO.addMarca(cnnTo, m);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itM = null;
			arM = null;

			marcaDataFile.writeNotInserteds();

		}

		if (useCliente) {
			LogFile.getInstance().writeInFile("Deleting 'cliente'");
			iInfo.setText("Excluindo clientes antigos");
			if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
				clear(cnnTo, "clientes");
			} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
				clear(cnnTo, "cliente");
			}
			LogFile.getInstance().writeInFile("Inserting 'cliente'");
			iInfo.setText("Inserindo registros na tabela de clientes");
			ArrayList<Cliente> arC = myDAO.getCliente(clienteDataFile);
			Iterator<Cliente> itC = arC.iterator();
			while (itC.hasNext()) {
				Cliente c = itC.next();
				myDAO.addCliente(cnnTo, c);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itC = null;
			arC = null;

			clienteDataFile.writeNotInserteds();

		}// fim do use clientes

		if (useFornecedor) {
			LogFile.getInstance().writeInFile("Deleting 'fornecedor'");
			iInfo.setText("Excluindo fornecedores antigos");
			if (myCfgTo.getSoftware() == GZSoftwares.MERCOFLEX) {
				clear(cnnTo, "credor");
			} else if (myCfgTo.getSoftware() == GZSoftwares.MERCATTO) {
				clear(cnnTo, "fornecedor");
			}
			LogFile.getInstance().writeInFile("Inserting 'fornecedor'");
			iInfo.setText("Inserindo registros na tabela de fornecedores");
			ArrayList<Fornecedor> arF = myDAO.getFornecedor(fornecedorDataFile);
			Iterator<Fornecedor> itF = arF.iterator();
			while (itF.hasNext()) {
				Fornecedor f = itF.next();
				if (myDAO.addFornecedor(cnnTo, f)) {
					iInfo.setValue(((count++) * 100) / totalData);
				}
			}
			itF = null;
			arF = null;

			fornecedorDataFile.writeNotInserteds();

		}// fim do use Fornecedores

		iInfo.setValue(100);

		iInfo.setText("Conclu�do");
		iFinalize.goToLastStep();

		LogFile.getInstance().writeInFile("Data migration finished");

	}// fim do run

	@Override
	public int getCountRegisteredProdutos() {
		return (useProduto) ? totalProdutoRegistered
				: SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountClientes() {
		return (useCliente) ? totalClienteRetrieved
				: SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountFornecedores() {
		return (useFornecedor) ? totalFornecedorRetrieved
				: SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountMarca() {
		return (useMarca) ? totalMarcaRetrieved : SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountDepartamento() {
		return (useDepartamento) ? totalDepartamentoRetrieved
				: SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountGrupo() {
		return (useGrupo) ? totalGrupoRetrieved : SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountArmacao() {
		return (useArmacao) ? totalArmacaoRetrieved
				: SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountLoja() {
		return (useLoja) ? totalLoja : SQLDataProvider.EMPTY_RETURN;
	}

	/**
	 * Conta a quantidade de dados nas tabelas de produto
	 * 
	 * @param cnn
	 *            - vari�vel de conex�o com o banco de dados de destino
	 * @param software
	 *            - software a ser implantado
	 * @param table
	 *            - nome da tabela
	 * @return - o total de produtos na tabela
	 */
	private int countCurrentProduto(Connection cnn, GZSoftwares software,
			String table) {

		PreparedStatement st = null;
		ResultSet rs = null;
		int n = 0;

		try {

			if (software == GZSoftwares.MERCOFLEX) {

				if (table.trim().equals("estoque")) {

					st = cnn.prepareStatement("select count(*) from estoque");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else if (table.trim().equals("estmix")) {

					st = cnn.prepareStatement("select count(*) from estmix");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else if (table.trim().equals("esttrib")) {

					st = cnn.prepareStatement("select count(*) from esttrib");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else if (table.trim().equals("saldos")) {

					st = cnn.prepareStatement("select count(*) from saldos");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else {

					n = -2;

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (table.trim().equals("produto")) {

					st = cnn.prepareStatement("select count(*) from produto");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else if (table.trim().equals("produto_ean")) {

					st = cnn.prepareStatement("select count(*) from produto_ean");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else if (table.trim().equals("produto_estoque")) {

					st = cnn.prepareStatement("select count(*) from produto_estoque");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else if (table.trim().equals("produto_mix")) {

					st = cnn.prepareStatement("select count(*) from produto_mix");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else if (table.trim().equals("produto_preco")) {

					st = cnn.prepareStatement("select count(*) from produto_preco");
					rs = st.executeQuery();
					if (rs.next()) {
						n = rs.getInt(1);
					}

				} else {

					n = -2;

				}

			} else {

				n = -2;

			}

			if (!st.isClosed())
				st.close();
			if (!rs.isClosed())
				rs.close();

		} catch (SQLException e) {

			e.printStackTrace();
			n = -1;

		} finally {

			return n;

		}

	}

	/**
	 * Busca a �ltima loja cadastrada no banco de destino
	 * 
	 * @param cnn
	 *            - vari�vel de conex�o com o banco de dados de destino
	 * @param software
	 *            - software a ser implantado
	 * @return - o c�digo d a loja
	 */
	private int getMaxLoja(Connection cnn, GZSoftwares software) {

		PreparedStatement st;

		switch (software) {

		case MERCOFLEX:

			try {

				st = cnn.prepareStatement("select max(loja) from saldos");
				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					int n = rs.getInt(1);
					return n;
				} else {
					rs.close();
					st.close();
					return 0;
				}

			} catch (SQLException e) {

				e.printStackTrace();
				return -1;
			}

		case MERCATTO:

			try {

				st = cnn.prepareStatement("select max(ID_LOJA) from produto_estoque");
				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					int n = rs.getInt(1);
					return n;
				} else {
					rs.close();
					st.close();
					return 0;
				}

			} catch (SQLException e) {

				e.printStackTrace();
				return -1;
			}

		default:

			return 0;

		}

	}

	/**
	 * Limpa a tabela informada no banco de dados de destino
	 * 
	 * @param cnn
	 *            - vari�vel de conex�o ao banco de dados de destino
	 * @param table
	 *            - nome da tabela a ser limpada
	 */
	private void clear(Connection cnn, String table) {

		try {
			PreparedStatement st = cnn.prepareStatement("delete from " + table);
			st.execute();
			st.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private boolean exists(Connection cnn, int codLoja, String codCodigo,
			String table, String fieldLoja, String fieldCodigo) {

		try {

			PreparedStatement st = cnn.prepareStatement("select count(*) from "
					+ table + " where " + fieldCodigo + " = '" + codCodigo
					+ "' and " + fieldLoja + " = " + codLoja);

			ResultSet rs = st.executeQuery();
			rs.next();

			boolean b = rs.getInt(1) > 0;

			rs.close();
			st.close();

			return b;

		} catch (SQLException e) {

			e.printStackTrace();
			return true;

		}

	}

	private boolean exists(Connection cnn, String codCodigo, String table,
			String fieldCodigo) {

		try {

			PreparedStatement st = cnn.prepareStatement("select count(*) from "
					+ table + " where " + fieldCodigo + " = '" + codCodigo
					+ "'");

			ResultSet rs = st.executeQuery();
			rs.next();

			boolean b = rs.getInt(1) > 0;

			rs.close();
			st.close();

			return b;

		} catch (SQLException e) {

			e.printStackTrace();
			return true;

		}

	}

	@Override
	public int getCountIncludedProdutos() {
		return (useProduto) ? totalProdutoIncluded
				: SQLDataProvider.EMPTY_RETURN;
	}

}
