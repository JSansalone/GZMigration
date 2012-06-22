package br.com.gz.migration;

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
import br.com.gz.migration.exception.SecurityViolationException;
import br.com.gz.migration.file.LogFile;
import br.com.gz.migration.panelSteps.IMigrationInfo;

/**
 * Motor de migração<br><br><br>
 * 
 * Classe responsável por manipular os dados recuperados pela subclasse de SQLDataProvider
 * 
 * @author Jonathan Sansalone
 *
 */
class MigrationEngine extends Thread implements IMigrationResults {

	// tipos de migração
	/**
	 * Variável que guarda os tipos de migração que serão utilizados
	 */
	private ArrayList<EnMigrationDataType> migrationType;
	
	/**
	 * Variável que executa alguns passos ao terminar a migração
	 */
	private IFinalizeMigration iFinalize;
	
	/**
	 * Variável que fornece informações sobre a migração após o término
	 */
	private IMigrationInfo iInfo;
	
	/**
	 * Variável que fornece alguns dados úteis para a migração
	 */
	private IMigrationType iType;
	
	/**
	 * Variável responsável por coletar e inserir os dados
	 */
	private SQLDataProvider myDAO;
	
	/**
	 * Variável que contém as configurações do banco de dados de destino
	 */
	private IDatabaseInfo myCfgTo;
	
	/**
	 * Variável que contém as configurações do banco de dados de origem. Esta não será mais utilizada
	 */
	private IDatabaseInfo myCfgFrom;
	
	/**
	 * Variável que guarda a conexão com o banco de dados de destino
	 */
	private Connection cnnTo;
	
	/**
	 * Variável que guarda a conexão com o banco de dados de origem
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
	private int totalProduto;
	// apenas usado quando tiver que anexar produtos
	
	/**
	 * Guarda o subtotal de produtos antes de inserir os novos. (Modo anexar)
	 */
	private int totalProdutoInicio;
	
	/**
	 * Guarda o total final após incluir os produtos novos
	 */
	private int totalProdutoFinal;
	// ------------------------------------
	
	/**
	 * Guarda o total de departamentos
	 */
	private int totalDepartamento;
	
	/**
	 * Guarda o total de grupos
	 */
	private int totalGrupo;
	
	/**
	 * Guarda o total de marcas
	 */
	private int totalMarca;
	
	/**
	 * Guarda o total de armações
	 */
	private int totalArmacao;
	
	/**
	 * Guarda o total de clientes
	 */
	private int totalCliente;
	
	/**
	 * Guarda o total de fornecedores
	 */
	private int totalFornecedor;
	
	/**
	 * Guarda o total de lojas
	 */
	private int totalLoja;
	
	/**
	 * Guarda o total de notas fiscais de entrada
	 */
	private int totalNFEntrada;
	
	/**
	 * Guarda o total de itens das notas fiscais de entrada
	 */
	private int totalNFEntradaItem;
	
	/**
	 * Guarda o total de notas fiscais de saida
	 */
	private int totalNFSaida;
	
	/**
	 * Guarda o total de itens das notas fiscais de saida
	 */
	private int totalNFSaidaItem;
	
	/**
	 * Guarda o total de contas a pagar
	 */
	private int totalContaPagar;
	
	/**
	 * Guarda o total de contas a receber
	 */
	private int totalContaReceber;
	
	/**
	 * Guarda o total de movimentações de venda
	 */
	private int totalMovtoVenda;

	// sinaliza se vai anexar ou sobrepor cadastros referentes à lojas
	// (produtos)
	/**
	 * Sinaliza se vai incluir ou sobrepor os cadastros de produtos
	 */
	private boolean toAppend;

	// flags para indicar quais dados serão usados
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
	 * Flag para indicar se vai ser migrado armações
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
	 * Flag para indicar se vai ser migrado movimentações de venda
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
	 * Realiza a migração
	 */
	public void run() {

		int count = 0;

		// Listar todos os tipos de migração escolhidos
		Iterator<EnMigrationDataType> it = migrationType.iterator();

		// enquanto houver tipos de migração na lista
		while (it.hasNext()) {

			// escolhendo o tipo de migração
			switch (it.next()) {

			case PRODUTO:

				// conta quantos produtos serão migrados
				try {

					// tenta obter o total de produtos
					// se uma exceção for lançada, houve falha ao ler o arquivo
					LogFile.getInstance().writeInFile(
							"Trying to count 'produto'");

					totalProduto = myDAO.countProduto(cnnFrom);

				} catch (IOException e) {
					showErrorMessage(EnMigrationDataType.PRODUTO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"failed to retrieve data from file");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SecurityViolationException e) {
					showSecurityViolationMessage(EnMigrationDataType.PRODUTO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile("Security violation");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SQLException e) {
					// johnny Auto-generated catch block
					showSyntaxErrorMessage(EnMigrationDataType.PRODUTO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"Syntax error on execute SQL statement");
					LogFile.getInstance().writeInFile(e.getMessage());
					// LogFile.getInstance().writeInFile("");
					continue;
				}
				// se não estiver vazio
				if (totalProduto != SQLDataProvider.EMPTY_RETURN) {
					if (totalProduto != SQLDataProvider.POLICY_VIOLATION) {
						// adiciona o total de produtos ao total geral de dados
						totalData += (totalProduto * iType.getNumLoja());
						useProduto = true;
						System.out.println("usa produto. Sub-total="
								+ totalProduto);
					} else {

						showPolicyViolationMessage(EnMigrationDataType.PRODUTO,
								myDAO.getProdutoColumnsNeeded());
						continue;

					}
				}

				break;

			case DEPARTAMENTO:

				try {
					LogFile.getInstance().writeInFile(
							"Trying to count 'departamento'");
					totalDepartamento = myDAO.countDepartamento(cnnFrom);
				} catch (IOException e) {
					showErrorMessage(EnMigrationDataType.DEPARTAMENTO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"failed to retrieve data from file");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SecurityViolationException e) {
					showSecurityViolationMessage(EnMigrationDataType.DEPARTAMENTO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile("Security violation");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SQLException e) {
					// johnny Auto-generated catch block
					showSyntaxErrorMessage(EnMigrationDataType.DEPARTAMENTO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"Syntax error on execute SQL statement");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				}
				if (totalDepartamento != SQLDataProvider.EMPTY_RETURN) {
					if (totalDepartamento != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalDepartamento;
						useDepartamento = true;
						System.out.println("usa departamento. Sub-total="
								+ totalDepartamento);
					} else {

						showPolicyViolationMessage(
								EnMigrationDataType.DEPARTAMENTO,
								myDAO.getDepartamentoColumnsNeeded());
						continue;

					}
				}

				break;

			case GRUPO:

				try {
					LogFile.getInstance()
							.writeInFile("Trying to count 'grupo'");
					totalGrupo = myDAO.countGrupo(cnnFrom);
				} catch (IOException e) {
					showErrorMessage(EnMigrationDataType.GRUPO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"failed to retrieve data from file");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SecurityViolationException e) {
					showSecurityViolationMessage(EnMigrationDataType.GRUPO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile("Security violation");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SQLException e) {
					// johnny Auto-generated catch block
					showSyntaxErrorMessage(EnMigrationDataType.GRUPO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"Syntax error on execute SQL statement");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				}
				if (totalGrupo != SQLDataProvider.EMPTY_RETURN) {
					if (totalGrupo != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalGrupo;
						useGrupo = true;
						System.out
								.println("usa grupo. Sub-total=" + totalGrupo);
					} else {

						showPolicyViolationMessage(EnMigrationDataType.GRUPO,
								myDAO.getGrupoColumnsNeeded());
						continue;

					}
				}

				break;

			case ARMACAO:

				try {
					LogFile.getInstance().writeInFile(
							"Trying to count 'armacao'");
					totalArmacao = myDAO.countArmacao(cnnFrom);
				} catch (IOException e) {
					showErrorMessage(EnMigrationDataType.ARMACAO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"failed to retrieve data from file");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SecurityViolationException e) {
					showSecurityViolationMessage(EnMigrationDataType.ARMACAO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile("Security violation");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SQLException e) {
					// johnny Auto-generated catch block
					showSyntaxErrorMessage(EnMigrationDataType.ARMACAO);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"Syntax error on execute SQL statement");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				}
				if (totalArmacao != SQLDataProvider.EMPTY_RETURN) {
					if (totalArmacao != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalArmacao;
						useArmacao = true;
						System.out.println("usa armacao. Sub-total="
								+ totalArmacao);
					} else {

						showPolicyViolationMessage(EnMigrationDataType.ARMACAO,
								myDAO.getArmacaoColumnsNeeded());
						continue;

					}
				}

				break;

			case MARCA:

				try {
					LogFile.getInstance()
							.writeInFile("Trying to count 'marca'");
					totalMarca = myDAO.countMarca(cnnFrom);
				} catch (IOException e) {
					showErrorMessage(EnMigrationDataType.MARCA);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"failed to retrieve data from file");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SecurityViolationException e) {
					showSecurityViolationMessage(EnMigrationDataType.MARCA);
					e.printStackTrace();
					LogFile.getInstance().writeInFile("Security violation");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SQLException e) {
					// johnny Auto-generated catch block
					showSyntaxErrorMessage(EnMigrationDataType.MARCA);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"Syntax error on execute SQL statement");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				}
				if (totalMarca != SQLDataProvider.EMPTY_RETURN) {
					if (totalMarca != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalMarca;
						useMarca = true;
						System.out
								.println("usa marca. Sub-total=" + totalMarca);
					} else {

						showPolicyViolationMessage(EnMigrationDataType.MARCA,
								myDAO.getMarcaColumnsNeeded());
						continue;

					}
				}

				break;

			case FORNECEDOR:

				try {
					LogFile.getInstance().writeInFile(
							"Trying to count 'fornecedor'");
					totalFornecedor = myDAO.countFornecedor(cnnFrom);
				} catch (IOException e) {
					showErrorMessage(EnMigrationDataType.FORNECEDOR);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"failed to retrieve data from file");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SecurityViolationException e) {
					showSecurityViolationMessage(EnMigrationDataType.FORNECEDOR);
					e.printStackTrace();
					LogFile.getInstance().writeInFile("Security violation");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SQLException e) {
					// johnny Auto-generated catch block
					showSyntaxErrorMessage(EnMigrationDataType.FORNECEDOR);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"Syntax error on execute SQL statement");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				}
				if (totalFornecedor != SQLDataProvider.EMPTY_RETURN) {
					if (totalFornecedor != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalFornecedor;
						useFornecedor = true;
						System.out.println("usa fornecedor. Sub-total="
								+ totalFornecedor);
					} else {

						showPolicyViolationMessage(
								EnMigrationDataType.FORNECEDOR,
								myDAO.getFornecedorColumnsNeeded());
						continue;

					}
				}

				break;

			case CLIENTE:

				try {
					LogFile.getInstance().writeInFile(
							"Trying to count 'cliente'");
					totalCliente = myDAO.countCliente(cnnFrom);
				} catch (IOException e) {
					showErrorMessage(EnMigrationDataType.CLIENTE);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"failed to retrieve data from file");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SecurityViolationException e) {
					showSecurityViolationMessage(EnMigrationDataType.CLIENTE);
					e.printStackTrace();
					LogFile.getInstance().writeInFile("Security violation");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				} catch (SQLException e) {
					// johnny Auto-generated catch block
					showSyntaxErrorMessage(EnMigrationDataType.CLIENTE);
					e.printStackTrace();
					LogFile.getInstance().writeInFile(
							"Syntax error on execute SQL statement");
					LogFile.getInstance().writeInFile(e.getMessage());
					continue;
				}
				if (totalCliente != SQLDataProvider.EMPTY_RETURN) {
					if (totalCliente != SQLDataProvider.POLICY_VIOLATION) {
						totalData += totalCliente;
						useCliente = true;
						System.out.println("usa cliente. Sub-total="
								+ totalCliente);
					} else {

						showPolicyViolationMessage(EnMigrationDataType.CLIENTE,
								myDAO.getClienteColumnsNeeded());
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
				"Total number of retrieved rows: " + totalData);
		LogFile.getInstance().writeInFile("Append: " + toAppend);

		if (useProduto) {
			// recupera todos os produtos e monta um iterator
			ArrayList<Produto> arP = myDAO.getProduto(cnnFrom);
			Iterator<Produto> itP = arP.iterator();
			// recupera a quantidade atual de produtos na tabela 'estoque'
			// (MercoFlex)
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				totalProdutoInicio = countCurrentProduto(cnnTo,
						myCfgTo.getSoftware(), "estoque");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				totalProdutoInicio = countCurrentProduto(cnnTo,
						myCfgTo.getSoftware(), "produto");
			} else {
				totalProdutoInicio = 0;
			}
			// recupera o maior codigo de loja para ser usado caso seja
			// escolhida a opção de anexar
			int currentLoja = getMaxLoja(cnnTo, myCfgTo.getSoftware());
			// iniciando o processo de limpeza
			if (!toAppend) {
				LogFile.getInstance().writeInFile("Deleting 'produto'");
				iInfo.setText("Excluindo produtos antigos");
				if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
					clear(cnnTo, "estoque");
					clear(cnnTo, "estmix");
					clear(cnnTo, "esttrib");
					clear(cnnTo, "saldos");
				} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
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
				// se conseguir adicionar
				if (myDAO.addProduto(cnnTo, p)) {
					// se o modo de inserção for de sobrepor
					// adiciona para todas as lojas indicadas pela quantidade
					if (!toAppend) {
						for (int i = 0; i < iType.getNumLoja(); i++) {
							// se conseguir inserir atualiza o status da barra
							if (myDAO.addProdutoLoja(cnnTo, p, (i + 1))) {
								iInfo.setValue(((count++) * 100) / totalData);
							}
						}
					} else {
						// parte do maior codigo de loja até atingir a
						// quantidade mais o codigo
						for (int i = currentLoja; i < iType.getNumLoja()
								+ currentLoja; i++) {
							// se conseguir inserir atualiza o status da barra
							if (myDAO.addProdutoLoja(cnnTo, p, (i + 1))) {
								iInfo.setValue(((count++) * 100) / totalData);
							}
						}
					}
				}
			}
			// recupera a quantidade atual de produtos na tabela 'estoque'
			// (MercoFlex)
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				totalProdutoFinal = countCurrentProduto(cnnTo,
						myCfgTo.getSoftware(), "estoque");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				totalProdutoFinal = countCurrentProduto(cnnTo,
						myCfgTo.getSoftware(), "produto");
			}
			{
				totalProdutoFinal = 0;
			}
			itP = null;
			arP = null;
		}
		if (useDepartamento) {
			LogFile.getInstance().writeInFile("Deleting 'departamento'");
			iInfo.setText("Excluindo departamento antigos");
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				clear(cnnTo, "depto");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				clear(cnnTo, "grupo");
			}
			LogFile.getInstance().writeInFile("Inserting 'departamento'");
			iInfo.setText("Inserindo registros na tabela de departamentos");
			ArrayList<Departamento> arD = myDAO.getDepartamento(cnnFrom);
			Iterator<Departamento> itD = arD.iterator();
			while (itD.hasNext()) {
				Departamento d = itD.next();
				myDAO.addDepartamento(cnnTo, d);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itD = null;
			arD = null;
		}
		if (useGrupo) {
			LogFile.getInstance().writeInFile("Deleting 'grupo'");
			iInfo.setText("Excluindo grupos antigos");
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				clear(cnnTo, "grupo");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				clear(cnnTo, "subgrupo");
			}
			LogFile.getInstance().writeInFile("Inserting 'grupo'");
			iInfo.setText("Inserindo registros na tabela de grupos");
			ArrayList<Grupo> arG = myDAO.getGrupo(cnnFrom);
			Iterator<Grupo> itG = arG.iterator();
			while (itG.hasNext()) {
				Grupo g = itG.next();
				myDAO.addGrupo(cnnTo, g);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itG = null;
			arG = null;
		}
		if (useArmacao) {
			LogFile.getInstance().writeInFile("Deleting 'armacao'");
			iInfo.setText("Excluindo armações antigas");
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				clear(cnnTo, "armacao");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				clear(cnnTo, "subgrupo1");
			}
			LogFile.getInstance().writeInFile("Inserting 'armacao'");
			iInfo.setText("Inserindo registros na tabela de armações");
			ArrayList<Armacao> arA = myDAO.getArmacao(cnnFrom);
			Iterator<Armacao> itA = arA.iterator();
			while (itA.hasNext()) {
				Armacao m = itA.next();
				myDAO.addArmacao(cnnTo, m);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itA = null;
			arA = null;
		}
		if (useMarca) {
			LogFile.getInstance().writeInFile("Deleting 'marca'");
			iInfo.setText("Excluindo marcas antigas");
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				clear(cnnTo, "marca");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				clear(cnnTo, "subgrupo1");
			}
			LogFile.getInstance().writeInFile("Inserting 'marca'");
			iInfo.setText("Inserindo registros na tabela de marcas");
			ArrayList<Marca> arM = myDAO.getMarca(cnnFrom);
			Iterator<Marca> itM = arM.iterator();
			while (itM.hasNext()) {
				Marca m = itM.next();
				myDAO.addMarca(cnnTo, m);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itM = null;
			arM = null;
		}
		if (useLoja) {
			iInfo.setText("Inserindo registros na tabela de lojas");
			ArrayList<Loja> arL = myDAO.getLoja(cnnFrom);
			Iterator<Loja> itL = arL.iterator();
			while (itL.hasNext()) {
				Loja l = itL.next();
				myDAO.addLoja(cnnTo, l);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itL = null;
			arL = null;
		}

		if (useCliente) {
			LogFile.getInstance().writeInFile("Deleting 'cliente'");
			iInfo.setText("Excluindo clientes antigos");
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				clear(cnnTo, "clientes");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				clear(cnnTo, "cliente");
			}
			LogFile.getInstance().writeInFile("Inserting 'cliente'");
			iInfo.setText("Inserindo registros na tabela de clientes");
			ArrayList<Cliente> arC = myDAO.getCliente(cnnFrom);
			Iterator<Cliente> itC = arC.iterator();
			while (itC.hasNext()) {
				Cliente c = itC.next();
				myDAO.addCliente(cnnTo, c);
				iInfo.setValue(((count++) * 100) / totalData);
			}
			itC = null;
			arC = null;
		}// fim do use clientes

		if (useFornecedor) {
			LogFile.getInstance().writeInFile("Deleting 'fornecedor'");
			iInfo.setText("Excluindo fornecedores antigos");
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				clear(cnnTo, "credor");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				clear(cnnTo, "fornecedor");
			}
			LogFile.getInstance().writeInFile("Inserting 'fornecedor'");
			iInfo.setText("Inserindo registros na tabela de fornecedores");
			ArrayList<Fornecedor> arF = myDAO.getFornecedor(cnnFrom);
			Iterator<Fornecedor> itF = arF.iterator();
			while (itF.hasNext()) {
				Fornecedor f = itF.next();
				if (myDAO.addFornecedor(cnnTo, f)) {
					iInfo.setValue(((count++) * 100) / totalData);
				}
			}
			itF = null;
			arF = null;
		}// fim do use Fornecedores

		iInfo.setValue(100);

		iInfo.setText("Concluído");
		iFinalize.goToLastStep();

		LogFile.getInstance().writeInFile("Data migration finished");

	}// fim do run

	@Override
	public int getCountProdutos() {
		// TODO Auto-generated method stub
		return (useProduto) ? (toAppend) ? totalProdutoFinal
				- totalProdutoInicio : totalProduto
				: SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountClientes() {
		// TODO Auto-generated method stub
		return (useCliente) ? totalCliente : SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountFornecedores() {
		// TODO Auto-generated method stub
		return (useFornecedor) ? totalFornecedor : SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountMarca() {
		// TODO Auto-generated method stub
		return (useMarca) ? totalMarca : SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountDepartamento() {
		// TODO Auto-generated method stub
		return (useDepartamento) ? totalDepartamento
				: SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountGrupo() {
		// TODO Auto-generated method stub
		return (useGrupo) ? totalGrupo : SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountArmacao() {
		// TODO Auto-generated method stub
		return (useArmacao) ? totalArmacao : SQLDataProvider.EMPTY_RETURN;
	}

	@Override
	public int getCountLoja() {
		// TODO Auto-generated method stub
		return (useLoja) ? totalLoja : SQLDataProvider.EMPTY_RETURN;
	}

	/**
	 * Conta a quantidade de dados nas tabelas de produto
	 * 
	 * @param cnn - variável de conexão com o banco de dados de destino
	 * @param software - software a ser implantado
	 * @param table - nome da tabela
	 * @return - o total de produtos na tabela
	 */
	private int countCurrentProduto(Connection cnn, EnSoftware software,
			String table) {

		PreparedStatement st = null;
		ResultSet rs = null;
		int n = 0;

		try {

			if (software == EnSoftware.MERCOFLEX) {

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

			} else if (software == EnSoftware.MERCATTO) {

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
	 * Busca a última loja cadastrada no banco de destino
	 * 
	 * @param cnn - variável de conexão com o banco de dados de destino
	 * @param software - software a ser implantado
	 * @return - o código d a loja
	 */
	private int getMaxLoja(Connection cnn, EnSoftware software) {

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
				// johnny Auto-generated catch block
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
				// johnny Auto-generated catch block
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
	 * @param cnn - variável de conexão ao banco de dados de destino
	 * @param table - nome da tabela a ser limpada
	 */
	private void clear(Connection cnn, String table) {

		try {
			PreparedStatement st = cnn.prepareStatement("delete from " + table);
			st.execute();
			st.close();
		} catch (SQLException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Mostra mensagem de erro genérica
	 * 
	 * @param type - tipo de dado
	 */
	private void showErrorMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing error message");

		switch (type) {

		case ARMACAO:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar as armações. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar os clientes. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar as contas a pagar. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar as contas a receber. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar os departamento. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar os fornecedores. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar os grupos. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar as marcas. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar as movimentações de vendas. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar as notas fiscais de entrada. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar as notas fiscais de saída. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar os produtos. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível coletar os setores. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null, "Erro desconhecido.", "Erro",
					JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

	/**
	 * Mostra mensagem de violação de colunas
	 * 
	 * @param type - Tipo de dado
	 * @param columnsNeeded - colunas que faltam
	 */
	private void showPolicyViolationMessage(EnMigrationDataType type,
			ArrayList<String> columnsNeeded) {

		LogFile.getInstance().writeInFile("Showing policy violation message");

		switch (type) {
		// .toString().replace("[", "").replace("]", "")
		case ARMACAO:

			JOptionPane.showMessageDialog(null,
					"Migração de armações não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane.showMessageDialog(null,
					"Migração de clientes não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane.showMessageDialog(null,
					"Migração de contas a pagar não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane.showMessageDialog(null,
					"Migração de contas a receber não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane.showMessageDialog(null,
					"Migração de departamentos não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane.showMessageDialog(null,
					"Migração de fornecedores não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane.showMessageDialog(null,
					"Migração de grupos não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane.showMessageDialog(null,
					"Migração de marcas não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de movimentações de venda não permitida! \nColuna(s) não encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Violação de política", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de notas fiscais de entrada não permitida! \nColuna(s) não encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Violação de política", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de notas fiscais de saída não permitida! \nColuna(s) não encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Violação de política", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane.showMessageDialog(null,
					"Migração de produtos não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane.showMessageDialog(null,
					"Migração de setores não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null,
					"Erro desconhecido de migração.", "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

	/**
	 * Mostra mensagem de violação de segurança ao executar código SQL
	 * 
	 * @param type - tipo de dado
	 */
	@Deprecated
	private void showSecurityViolationMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing security violation message");

		switch (type) {
		// .toString().replace("[", "").replace("]", "")
		case ARMACAO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de armações não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de clientes não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de contas a pagar não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de contas a receber não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de departamentos não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de fornecedores não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de grupos não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de marcas não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de movimentações de venda não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de notas fiscais de entrada não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de notas fiscais de saída não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de produtos não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de setores não permitida! \nEncontrado código SQL perigoso ao tentar coletar os registros.",
							"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null, "Violação de segurança!.",
					"Violação de segurança", JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

	/**
	 * Mostra mensagem de erro de sintaxe
	 * 
	 * @param type - Tipo de dado que causou a mensagem
	 */
	@Deprecated
	private void showSyntaxErrorMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing syntax error message");

		switch (type) {
		// .toString().replace("[", "").replace("]", "")
		case ARMACAO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de armações não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de clientes não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de contas a pagar não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de contas a receber não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de departamentos não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de fornecedores não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de grupos não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de marcas não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de movimentações de venda não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de notas fiscais de entrada não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de notas fiscais de saída não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de produtos não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de setores não permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null, "Erro de sintaxe!",
					"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

}
