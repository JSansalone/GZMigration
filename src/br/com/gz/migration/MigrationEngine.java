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
 * 
 * @author Jonathan
 * 
 *         O famoso Motor...
 * 
 */
class MigrationEngine extends Thread implements IMigrationResults {

	// tipos de migra��o
	private ArrayList<EnMigrationDataType> migrationType;
	private IFinalizeMigration iFinalize;
	private IMigrationInfo iInfo;
	private IMigrationType iType;
	private SQLDataProvider myDAO;
	private IDatabaseInfo myCfgTo;
	private IDatabaseInfo myCfgFrom;
	private Connection cnnTo;
	private Connection cnnFrom;

	// total geral de dados a serem migrados
	private int totalData;

	private int totalProduto;
	// apenas usado quando tiver que anexar produtos
	private int totalProdutoInicio;
	private int totalProdutoFinal;
	// ------------------------------------
	private int totalDepartamento;
	private int totalGrupo;
	private int totalMarca;
	private int totalArmacao;
	private int totalCliente;
	private int totalFornecedor;
	private int totalLoja;
	private int totalNFEntrada;
	private int totalNFEntradaItem;
	private int totalNFSaida;
	private int totalNFSaidaItem;
	private int totalContaPagar;
	private int totalContaReceber;
	private int totalMovtoVenda;

	// sinaliza se vai anexar ou sobrepor cadastros referentes � lojas
	// (produtos)
	private boolean toAppend;

	// flags para indicar quais dados ser�o usados
	private boolean useProduto = false;
	private boolean useGrupo = false;
	private boolean useDepartamento = false;
	private boolean useMarca = false;
	private boolean useArmacao = false;
	private boolean useLoja = false;
	private boolean useCliente = false;
	private boolean useFornecedor = false;
	private boolean useNFEntrada = false;
	private boolean useNFSaida = false;
	private boolean useContaPagar = false;
	private boolean useContaReceber = false;
	private boolean useMovtoVenda = false;

	/**
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

	public void run() {

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
				// se n�o estiver vazio
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
			// escolhida a op��o de anexar
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
					// se o modo de inser��o for de sobrepor
					// adiciona para todas as lojas indicadas pela quantidade
					if (!toAppend) {
						for (int i = 0; i < iType.getNumLoja(); i++) {
							// se conseguir inserir atualiza o status da barra
							if (myDAO.addProdutoLoja(cnnTo, p, (i + 1))) {
								iInfo.setValue(((count++) * 100) / totalData);
							}
						}
					} else {
						// parte do maior codigo de loja at� atingir a
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
			iInfo.setText("Excluindo arma��es antigas");
			if (myCfgTo.getSoftware() == EnSoftware.MERCOFLEX) {
				clear(cnnTo, "armacao");
			} else if (myCfgTo.getSoftware() == EnSoftware.MERCATTO) {
				clear(cnnTo, "subgrupo1");
			}
			LogFile.getInstance().writeInFile("Inserting 'armacao'");
			iInfo.setText("Inserindo registros na tabela de arma��es");
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

		iInfo.setText("Conclu�do");
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

	private void showErrorMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing error message");

		switch (type) {

		case ARMACAO:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar as arma��es. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar os clientes. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar as contas a pagar. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar as contas a receber. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar os departamento. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar os fornecedores. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar os grupos. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar as marcas. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar as movimenta��es de vendas. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar as notas fiscais de entrada. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar as notas fiscais de sa�da. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar os produtos. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel coletar os setores. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null, "Erro desconhecido.", "Erro",
					JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

	private void showPolicyViolationMessage(EnMigrationDataType type,
			ArrayList<String> columnsNeeded) {

		LogFile.getInstance().writeInFile("Showing policy violation message");

		switch (type) {
		// .toString().replace("[", "").replace("]", "")
		case ARMACAO:

			JOptionPane.showMessageDialog(null,
					"Migra��o de arma��es n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane.showMessageDialog(null,
					"Migra��o de clientes n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane.showMessageDialog(null,
					"Migra��o de contas a pagar n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane.showMessageDialog(null,
					"Migra��o de contas a receber n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane.showMessageDialog(null,
					"Migra��o de departamentos n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane.showMessageDialog(null,
					"Migra��o de fornecedores n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane.showMessageDialog(null,
					"Migra��o de grupos n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane.showMessageDialog(null,
					"Migra��o de marcas n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de movimenta��es de venda n�o permitida! \nColuna(s) n�o encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Viola��o de pol�tica", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de notas fiscais de entrada n�o permitida! \nColuna(s) n�o encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Viola��o de pol�tica", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de notas fiscais de sa�da n�o permitida! \nColuna(s) n�o encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Viola��o de pol�tica", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane.showMessageDialog(null,
					"Migra��o de produtos n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane.showMessageDialog(null,
					"Migra��o de setores n�o permitida! \nColuna(s) n�o encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null,
					"Erro desconhecido de migra��o.", "Viola��o de pol�tica",
					JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

	private void showSecurityViolationMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing security violation message");

		switch (type) {
		// .toString().replace("[", "").replace("]", "")
		case ARMACAO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de arma��es n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de clientes n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de contas a pagar n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de contas a receber n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de departamentos n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de fornecedores n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de grupos n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de marcas n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de movimenta��es de venda n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de notas fiscais de entrada n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de notas fiscais de sa�da n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de produtos n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de setores n�o permitida! \nEncontrado c�digo SQL perigoso ao tentar coletar os registros.",
							"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null, "Viola��o de seguran�a!.",
					"Viola��o de seguran�a", JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

	private void showSyntaxErrorMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing syntax error message");

		switch (type) {
		// .toString().replace("[", "").replace("]", "")
		case ARMACAO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de arma��es n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de clientes n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de contas a pagar n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de contas a receber n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de departamentos n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de fornecedores n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de grupos n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de marcas n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de movimenta��es de venda n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de notas fiscais de entrada n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de notas fiscais de sa�da n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de produtos n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Migra��o de setores n�o permitida! \nEncontrado erro de sintaxe ao tentar coletar os registros.",
							"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null, "Erro de sintaxe!",
					"Erro de sintaxe", JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

}
