package br.com.gz.migration;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.database.connection.DatabaseType;

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
import br.com.gz.migration.datafile.DataFile;
import br.com.gz.migration.datafile.DepartamentoDataFile;
import br.com.gz.migration.datafile.FornecedorDataFile;
import br.com.gz.migration.datafile.GrupoDataFile;
import br.com.gz.migration.datafile.MarcaDataFile;
import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.migration.exception.SecurityViolationException;
import br.com.gz.migration.file.LogFile;
import br.com.gz.migration.sql.EnMercoFlexInsertStatement;
import br.com.gz.util.GZSoftwares;

/**
 * Classe abstrata que servirá de substituta para a antiga interface IDAO Os
 * benefícios dessa classe é que ela concentra todos os inserts para os sistemas
 * da GZ, facilitando a manutenção.
 * 
 * @author Jonathan Sansalone
 * 
 */
public abstract class SQLDataProvider {

	/**
	 * Constante que representa um tipo de dado vazio. Alguns lugares usam esta constante para dizer que o tipo de dado não será usado
	 */
	public static final int EMPTY_RETURN = -987654321;
	
	/**
	 * Constante que representa um registro com dados inválido
	 */
	public static final int POLICY_VIOLATION = -345612776;
	
	/**
	 * Quando a coleta de dados era feita no banco de origem, esta constante informava a presença de alguma instrução SQL perigosa
	 */
	@Deprecated
	public static final int SECURITY_VIOLATION = -917345787;
	
	/**
	 * Quando a coleta de dados era feita no banco de origem, esta constante informava a presença de erros de sintaxe na instrução SQL
	 */
	@Deprecated
	public static final int SQL_ERROR = -5196673;

	/**
	 * Software da GZ Sistemas que será implantado
	 */
	protected GZSoftwares software;
	
	/**
	 * Software de terceiro usado atualmente
	 */
	@Deprecated
	protected GZSoftwares otherSoftware;
	
	/**
	 * Tipo de banco de dados de destino
	 */
	protected DatabaseType dbTo;
	
	/**
	 * Tipo de banco de dados de origem
	 */
	@Deprecated
	protected DatabaseType dbFrom;

	@Deprecated
	public SQLDataProvider(GZSoftwares software, GZSoftwares otherSoftware,
			DatabaseType dbTo, DatabaseType dbFrom) {

		this.dbTo = dbTo;
		this.dbFrom = dbFrom;
		this.software = software;
		this.otherSoftware = otherSoftware;

	}

	/**
	 * Construtor que inicializa a classe
	 * 
	 * @param software - software que será implantado
	 * @param dbTo - banco de dados de destino
	 */
	public SQLDataProvider(GZSoftwares software, DatabaseType dbTo) {

		this.software = software;
		this.dbTo = dbTo;

	}

	/**
	 * Adiciona um cliente
	 * 
	 * @param cnn - Conexão ao banco de dados
	 * @param c - cliente a ser adicionado
	 * @return - true se for adicionado com sucesso, false caso contrário
	 */
	public final boolean addCliente(Connection cnn, Cliente c) {

		try {

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					PreparedStatement st = cnn
							.prepareStatement("insert into clientes(codigo, cgc, insest, dtnasc, situacao, ender, numero, bairro, munic, estado, cep, telefone, cadastro, razsoc, nomfan) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update codigo = codigo");

					st.setInt(1, c.getCodigo());
					st.setString(2, c.getCpf());
					st.setString(3, c.getRg());
					st.setDate(4, new java.sql.Date(c.getDataNascimento()
							.getTimeInMillis()));
					st.setString(5, c.getSituacao());
					st.setString(6, c.getEndereco());
					st.setInt(7, c.getNumero());
					st.setString(8, c.getBairro());
					st.setString(9, c.getCidade());
					st.setString(10, c.getEstado());
					st.setString(11, c.getCep());
					st.setString(12, c.getTelefoneResidencial());
					st.setDate(13, new java.sql.Date(c.getDataCadastro()
							.getTimeInMillis()));
					st.setString(14, c.getRazaoSocial());
					st.setString(15, c.getNomeFantasia());

					st.execute();
					st.close();

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (dbTo == DatabaseType.MySQL) {

					PreparedStatement st = cnn
							.prepareStatement("insert into cliente(idCliente,nome,endereco,bairro,cidade,uf,cep,cpf,rg,credito,limite,dt_nasc,empresa,salario,orgao,tipo,dtAbertura,usado,tipoend,numero,codmunicipio,empresa_convenio,limite_ch,credito_ch,CARENCIA,TIPOFIDELIDADE,PONTUACAO,LOJA,TabelaPreco,IdTipoCliente,fantasia) "
									+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update idCliente = idCliente");

					st.setInt(1, c.getCodigo());
					st.setString(2, c.getRazaoSocial());
					st.setString(3, c.getEndereco());
					st.setString(4, c.getBairro());
					st.setString(5, c.getCidade());
					st.setString(6, c.getEstado());
					st.setString(7, c.getCep());
					st.setString(8, c.getCpf());
					st.setString(9, c.getRg());
					st.setInt(10, 0);
					st.setInt(11, 0);
					st.setDate(12, new java.sql.Date(c.getDataNascimento()
							.getTimeInMillis()));
					st.setString(13, "EMPRESA");
					st.setInt(14, 0);
					st.setString(15, "SSP");
					st.setString(16, "CH");
					st.setString(17, "2011-01-01");
					st.setInt(18, 0);
					st.setString(19, "RUA");
					st.setInt(20, c.getNumero());
					st.setInt(21, 0);
					st.setInt(22, 1);
					st.setInt(23, 0);
					st.setInt(24, 0);
					st.setInt(25, 0);
					st.setInt(26, 2);
					st.setInt(27, 0);
					st.setInt(28, c.getLoja());
					st.setInt(29, 1);
					st.setInt(30, 1);
					st.setString(31, c.getNomeFantasia());

					st.execute();
					st.close();

				}

			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;
		}

	}

	/**
	 * Adiciona um departamento
	 * 
	 * @param cnn - Conexão ao banco de dados
	 * @param d - departamento a ser adicionado
	 * @return - true se for adicionado com sucesso, false caso contrário
	 */
	public final boolean addDepartamento(Connection cnn, Departamento d) {

		try {

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					PreparedStatement st = cnn
							.prepareStatement("insert into depto(codigo, descricao) values(?,?) on duplicate key update codigo = codigo");

					st.setInt(1, d.getCodigo());
					st.setString(2, d.getDescricao());

					st.execute();
					st.close();

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (dbTo == DatabaseType.MySQL) {

					PreparedStatement st = cnn
							.prepareStatement("insert into grupo(IDGRUPO,NOME) values(?,?) on duplicate key update IDGRUPO = IDGRUPO");

					st.setInt(1, d.getCodigo());
					st.setString(2, d.getDescricao());

					st.execute();
					st.close();

				}

			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;
		}

	}

	/**
	 * Adiciona um fornecedor
	 * 
	 * @param cnn - Conexão ao banco de dados
	 * @param f - fornecedor a ser adicionado
	 * @return - true se for adicionado com sucesso, false caso contrário
	 */
	public final boolean addFornecedor(Connection cnn, Fornecedor f) {

		try {

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					PreparedStatement st = cnn
							.prepareStatement("insert into credor(codigo, nomfan, razsoc, ender, numero, bairro, munic, estado, cep, telefone, cgc, insest) values(?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update codigo = codigo");

					st.setInt(1, f.getCodigo());
					st.setString(2, f.getNomeFantasia());
					st.setString(3, f.getRazaoSocial());
					st.setString(4, f.getEndereco());
					st.setInt(5, f.getNumero());
					st.setString(6, f.getBairro());
					st.setString(7, f.getCidade());
					st.setString(8, f.getEstado());
					st.setString(9, f.getCep());
					st.setString(10, f.getTelefonePrincipal());
					st.setString(11, f.getCnpj());
					st.setString(12, f.getInscricaoEstadual());

					st.execute();
					st.close();

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (dbTo == DatabaseType.MySQL) {

					PreparedStatement st = cnn
							.prepareStatement("insert into fornecedor(IDFORNECEDOR,NOME,FANTASIA,ENDERECO,BAIRRO,CIDADE,UF,CEP,CPF_CGC,RG_IE,CONDICAOFAT,Entrega,SenhaCotacao,NUMERO,CODMUNICIPIO,TIPOEND,DTCADASTRO,HabilitaCotacao) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update IDFORNECEDOR = IDFORNECEDOR");

					st.setInt(1, f.getCodigo());
					st.setString(2, f.getRazaoSocial());
					st.setString(3, f.getNomeFantasia());
					st.setString(4, f.getEndereco());
					st.setString(5, f.getBairro());
					st.setString(6, f.getCidade());
					st.setString(7, f.getEstado());
					st.setString(8, f.getCep());
					st.setString(9, f.getCnpj());
					st.setString(10, f.getInscricaoEstadual());
					st.setInt(11, 30);
					st.setInt(12, 30);
					st.setString(13, "1");
					st.setInt(14, f.getNumero());
					st.setInt(15, 0);
					st.setString(16, "RUA");
					st.setDate(17, new java.sql.Date(f.getDataCadastro()
							.getTimeInMillis()));
					st.setString(18, "N");

					st.execute();
					st.close();

				}

			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;
		}

	}

	/**
	 * Adiciona um grupo
	 * 
	 * @param cnn - Conexão ao banco de dados
	 * @param g - grupo a ser adicionado
	 * @return - true se for adicionado com sucesso, false caso contrário
	 */
	public final boolean addGrupo(Connection cnn, Grupo g) {

		PreparedStatement st;

		try {

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into grupo(codigo,descricao) values(?,?) on duplicate key update codigo = codigo");
					st.setInt(1, g.getCodigo());
					st.setString(2, g.getDescricao());
					st.execute();
					st.close();

				} else if (dbTo == DatabaseType.MSSQL) {

				} else if (dbTo == DatabaseType.Oracle) {

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into subgrupo(idSubGrupo,Nome,idGrupo) values(?,?,1) on duplicate key update idSubGrupo = idSubGrupo");

					st.setInt(1, g.getCodigo());
					st.setString(2, g.getDescricao());

					st.execute();
					st.close();

				}

			}

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;

		}

	}

	public final boolean addLoja(Connection cnn, Loja arg1) {

		if (software == GZSoftwares.MERCOFLEX) {
			if (dbTo == DatabaseType.MySQL) {

			} else if (dbTo == DatabaseType.MSSQL) {

			} else if (dbTo == DatabaseType.Oracle) {

			}
		} else if (software == GZSoftwares.MERCATTO) {

		}

		return true;

	}

	/**
	 * Adiciona uma marca
	 * 
	 * @param cnn - Conexão ao banco de dados
	 * @param m - marca a ser adicionada
	 * @return - true se for adicionado com sucesso, false caso contrário
	 */
	public final boolean addMarca(Connection cnn, Marca m) {

		PreparedStatement st;

		try {

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into marca(codigo,descricao) values(?,?) on duplicate key update codigo = codigo");
					st.setInt(1, m.getCodigo());
					st.setString(2, m.getDescricao());
					st.execute();
					st.close();

				} else if (dbTo == DatabaseType.MSSQL) {

				} else if (dbTo == DatabaseType.Oracle) {

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into subgrupo1(idsubgrupo1,Nome,idgrupo,idsubgrupo) values(?,?,1,1) on duplicate key update idsubgrupo1 = idsubgrupo1;");
					st.setInt(1, m.getCodigo());
					st.setString(2, m.getDescricao());
					st.execute();
					st.close();

				}

			}

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;

		}

	}

	/**
	 * Adiciona um produto
	 * 
	 * @param cnn - Conexão ao banco de dados
	 * @param p - produto a ser adicionado
	 * @return - true se for adicionado com sucesso, false caso contrário
	 */
	public final boolean addProduto(Connection cnn, Produto p) {

		try {

			PreparedStatement st;

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement(EnMercoFlexInsertStatement.INSERT_ESTOQUE
							.getSQL(DatabaseType.MySQL));
					st.setString(1, p.getCodigoInterno());
					st.setString(2, p.getCodigoDeBarras());
					st.setString(3, p.getDescricao());
					st.setString(4, p.getDescricaoReduzida());
					st.setString(5, p.getUnidade());
					st.setInt(6, p.getSetor());
					st.setString(7, p.getVariavel());
					st.setInt(8, p.getDepartamento());
					st.setInt(9, p.getGrupo());
					st.setDate(10, new java.sql.Date(p.getDataCadastro()
							.getTime().getTime()));
					st.setString(11, p.getNcm());
					st.setInt(12, p.getCodigoTributacao());
					st.setString(13, p.getSt());
					st.execute();
					st.close();

					st = cnn.prepareStatement(EnMercoFlexInsertStatement.INSERT_ESTOQUE_TRIBUTACAO
							.getSQL(DatabaseType.MySQL));
					st.setString(1, p.getCodigoInterno());
					st.setInt(2, p.getCsosn());
					st.setString(3, p.getModalidatePautaVenda());
					st.setDouble(4, p.getPautaVenda());
					st.setString(5, p.getTributacaoCompra());
					st.setInt(6, p.getCodigoTributacao());
					st.setDouble(7, p.getIcmCompra());
					st.setString(8, p.getSt());
					st.setDouble(9, p.getIcmVenda());
					st.setDouble(10, p.getBaseIcmVenda());
					st.setDouble(11, p.getBaseICMSub());
					st.setDouble(12, p.getValorICMSSubstituicao());
					st.setString(13, p.getEstadoTributacao());
					st.execute();
					st.close();

				} else if (dbTo == DatabaseType.MSSQL) {

				} else if (dbTo == DatabaseType.Oracle) {

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into produto(idProduto,SitTrib,TipoPisCofins,Cofins,Pis,Icms,Descricao,DescrRed,UnidSaida,Custo,Venda1,idGrupo,idSubGrupo,idSubGrupo1,idSituacao,EstMin,EstMax,PesoVariavel,Ean,ClassFiscal,TabIcmsProd,IcmsCompra,SitTribCompra,Etiqueta,TipoProd,OrigemProd) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update idProduto = idProduto");

					st.setInt(1, new Integer(p.getCodigoInterno()));
					st.setInt(2, p.getCodigoTributacao());
					st.setString(3, "T");
					st.setDouble(4, p.getAliquotaCofinsCompra());
					st.setDouble(5, p.getAliquotaPisCompra());
					st.setDouble(6, p.getIcmCompra());
					st.setString(7, p.getDescricao());
					st.setString(8, p.getDescricaoReduzida());
					st.setString(9, p.getUnidade());
					st.setDouble(10, p.getPrecoCusto());
					st.setDouble(11, p.getPrecoVenda());
					st.setInt(12, p.getDepartamento());
					st.setInt(13, p.getGrupo());
					st.setInt(14, p.getArmacao());
					st.setString(15, p.getAtivo());
					st.setDouble(16, p.getQuantidadeEstoqueMinimo());
					st.setDouble(17, p.getQuantidadeEstoqueMaximo());
					st.setString(18, p.getVariavel());
					st.setLong(19, new Long(p.getCodigoDeBarras()));
					st.setString(20, p.getNcm());
					st.setString(21, "00 - TRIBUTADA INTEGRALMENTE");
					st.setDouble(22, p.getIcmCompra());
					st.setInt(23, p.getCodigoTributacao());
					st.setInt(24, 0);
					st.setString(25, "04 - PRODUTO ACABADO");
					st.setString(26, "2 - ESTRANGEIRO - ADQ. MERCADO INTERNO");

					st.execute();

					st = cnn.prepareStatement("insert into produto_ean(idProduto,CodigoEan) values(?,?) on duplicate key update idProduto = idProduto");
					st.setInt(1, new Integer(p.getCodigoInterno()));
					st.setLong(2, new Long(p.getCodigoDeBarras()));

					st.execute();

					st = cnn.prepareStatement("insert into produto_mix(IDPRODUTO,DATAHORA_CADASTRO) values(?,?) on duplicate key update IDPRODUTO = IDPRODUTO");
					st.setInt(1, new Integer(p.getCodigoInterno()));
					st.setDate(2, new java.sql.Date(p.getDataCadastro()
							.getTimeInMillis()));

					st.execute();
					st.close();

				}

			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;
		}

	}

	/**
	 * Adiciona um produto para uma loja específica
	 * 
	 * @param cnn - conexão ao banco de dados
	 * @param p - produto a ser adicionado
	 * @param ignoreCode - true se for para ignorar o código de loja no arquivo, false para usar o código da loja
	 * @param l - código da loja
	 * @return - true se for adicionado com sucesso, false caso contrário
	 */
	public final boolean addProdutoLoja(Connection cnn, Produto p,
			boolean ignoreCode, int l) {

		try {

			PreparedStatement st;

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement(EnMercoFlexInsertStatement.INSERT_ESTOQUE_SALDO
							.getSQL(DatabaseType.MySQL));
					st.setInt(1, ignoreCode ? l : p.getLoja());
					st.setString(2, p.getCodigoInterno());
					st.setDouble(3, p.getPrecoCompra());
					st.setDouble(4, p.getPrecoVenda());
					st.setDouble(5, p.getPrecoVendaTerminal());
					st.setDouble(6, p.getPrecoCusto());
					st.setDouble(7, p.getPorcentagemLucro());
					st.setDouble(8, p.getQuantidadeEstoqueMinimo());
					st.setDouble(9, p.getQuantidadeEstoqueMaximo());
					st.setDouble(10, p.getQuantidade());
					st.setDouble(11, p.getAliquotaPisCompra());
					st.setDouble(12, p.getAliquotaCofinsCompra());
					st.setString(13, p.getAtivo());
					st.setString(14, p.getTributacaoCompra());
					st.setDouble(15, p.getIcmCompra());
					st.execute();
					st.close();

					st = cnn.prepareStatement(EnMercoFlexInsertStatement.INSERT_ESTOQUE_LOJA
							.getSQL(DatabaseType.MySQL));
					st.setInt(1, ignoreCode ? l : p.getLoja());
					st.setString(2, p.getCodigoInterno());
					st.execute();
					st.close();

				}

			} else if (software == GZSoftwares.MERCATTO) {

				st = cnn.prepareStatement("insert into produto_estoque(idProduto,estoque_atual,estoque_minimo,ID_LOJA) values(?,?,?,?) on duplicate key update idProduto = idProduto");
				st.setInt(1, new Integer(p.getCodigoInterno()));
				st.setDouble(2, p.getQuantidade());
				st.setDouble(3, p.getQuantidadeEstoqueMinimo());
				st.setInt(4, l);

				st.execute();

				st = cnn.prepareStatement("insert into produto_preco(IDPRODUTO,ID_LOJA,CUSTO,VENDA1,MARGEM) values(?,?,?,?,?) on duplicate key update idProduto = idProduto");
				st.setInt(1, new Integer(p.getCodigoInterno()));
				st.setInt(2, l);
				st.setDouble(3, p.getPrecoCusto());
				st.setDouble(4, p.getPrecoVenda());
				st.setDouble(5, p.getPorcentagemLucro());

				st.execute();
				st.close();

			}

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;

		}

	}

	/**
	 * Adiciona uma armação
	 * 
	 * @param conn - conexão ao banco de dados
	 * @param armac - armação a ser adicionada
	 * @return - true se for adicionado com sucesso, false caso contrário
	 */
	public final boolean addArmacao(Connection conn, Armacao armac) {

		PreparedStatement st;

		try {

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					st = conn
							.prepareStatement("insert into armacao(codigo,descricao) values(?,?) on duplicate key update codigo = codigo");
					st.setInt(1, armac.getCodigo());
					st.setString(2, armac.getDescricao());
					st.execute();

				} else if (dbTo == DatabaseType.MSSQL) {

				} else if (dbTo == DatabaseType.Oracle) {

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (dbTo == DatabaseType.MySQL) {

					st = conn
							.prepareStatement("insert into subgrupo1(idsubgrupo1,Nome,idgrupo,idsubgrupo) values(?,?,1,1) on duplicate key update idsubgrupo1 = idsubgrupo1;");
					st.setInt(1, armac.getCodigo());
					st.setString(2, armac.getDescricao());
					st.execute();
					st.close();

				}

			}

			return true;

		} catch (SQLException e) {

			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;

		}

	}

	@Deprecated
	public abstract int countProduto(Connection conn) throws IOException,
			SecurityViolationException, SQLException;

	@Deprecated
	public abstract int countDepartamento(Connection conn) throws IOException,
			SecurityViolationException, SQLException;

	@Deprecated
	public abstract int countGrupo(Connection conn) throws IOException,
			SecurityViolationException, SQLException;

	@Deprecated
	public abstract int countArmacao(Connection conn) throws IOException,
			SecurityViolationException, SQLException;

	@Deprecated
	public abstract int countMarca(Connection conn) throws IOException,
			SecurityViolationException, SQLException;

	@Deprecated
	public abstract int countCliente(Connection conn) throws IOException,
			SecurityViolationException, SQLException;

	@Deprecated
	public abstract int countLoja(Connection conn) throws IOException,
			SecurityViolationException, SQLException;

	@Deprecated
	public abstract int countFornecedor(Connection conn) throws IOException,
			SecurityViolationException, SQLException;

	//

	/**
	 * Conta quantos registros são válidos no arquivo
	 * 
	 * @param dataFile - Arquivo de dados
	 * @return - total
	 * @throws IOException - se não conseguir ler o arquivo
	 */
	public abstract int countProduto(ProdutoDataFile dataFile)
			throws IOException;

	/**
	 * Conta quantos registros são válidos no arquivo
	 * 
	 * @param dataFile - Arquivo de dados
	 * @return - total
	 * @throws IOException - se não conseguir ler o arquivo
	 */
	public abstract int countDepartamento(DepartamentoDataFile dataFile)
			throws IOException;

	/**
	 * Conta quantos registros são válidos no arquivo
	 * 
	 * @param dataFile - Arquivo de dados
	 * @return - total
	 * @throws IOException - se não conseguir ler o arquivo
	 */
	public abstract int countGrupo(GrupoDataFile dataFile) throws IOException;

	/**
	 * Conta quantos registros são válidos no arquivo
	 * 
	 * @param dataFile - Arquivo de dados
	 * @return - total
	 * @throws IOException - se não conseguir ler o arquivo
	 */
	public abstract int countArmacao(ArmacaoDataFile dataFile)
			throws IOException;

	/**
	 * Conta quantos registros são válidos no arquivo
	 * 
	 * @param dataFile - Arquivo de dados
	 * @return - total
	 * @throws IOException - se não conseguir ler o arquivo
	 */
	public abstract int countMarca(MarcaDataFile dataFile) throws IOException;

	/**
	 * Conta quantos registros são válidos no arquivo
	 * 
	 * @param dataFile - Arquivo de dados
	 * @return - total
	 * @throws IOException - se não conseguir ler o arquivo
	 */
	public abstract int countCliente(ClienteDataFile dataFile)
			throws IOException;

	/**
	 * Conta quantos registros são válidos no arquivo
	 * 
	 * @param dataFile - Arquivo de dados
	 * @return - total
	 * @throws IOException - se não conseguir ler o arquivo
	 */
	public abstract int countFornecedor(FornecedorDataFile dataFile)
			throws IOException;

	/*
	 * int countNFEntrada(Connection conn) ;
	 * 
	 * int countNFSaida(Connection conn) ;
	 * 
	 * int countContasPagar(Connection conn) ;
	 * 
	 * int countContasReceber(Connection conn) ;
	 * 
	 * int countMovtoVenda(Connection conn) ;
	 */

	@Deprecated
	public abstract ArrayList<Produto> getProduto(Connection conn);

	@Deprecated
	public abstract ArrayList<Departamento> getDepartamento(Connection conn);

	@Deprecated
	public abstract ArrayList<Grupo> getGrupo(Connection conn);

	@Deprecated
	public abstract ArrayList<Armacao> getArmacao(Connection conn);

	@Deprecated
	public abstract ArrayList<Marca> getMarca(Connection conn);

	@Deprecated
	public abstract ArrayList<Cliente> getCliente(Connection conn);

	@Deprecated
	public abstract ArrayList<Loja> getLoja(Connection conn);

	@Deprecated
	public abstract ArrayList<Fornecedor> getFornecedor(Connection conn);

	//

	/**
	 * Retorna um {@link ArrayList} com os dados
	 * 
	 * @param dataFile - arquivo de dados
	 * @return - {@link ArrayList} com os dados
	 */
	public abstract ArrayList<Produto> getProduto(ProdutoDataFile dataFile);

	/**
	 * Retorna um {@link ArrayList} com os dados
	 * 
	 * @param dataFile - arquivo de dados
	 * @return - {@link ArrayList} com os dados
	 */
	public abstract ArrayList<Departamento> getDepartamento(
			DepartamentoDataFile dataFile);

	/**
	 * Retorna um {@link ArrayList} com os dados
	 * 
	 * @param dataFile - arquivo de dados
	 * @return - {@link ArrayList} com os dados
	 */
	public abstract ArrayList<Grupo> getGrupo(GrupoDataFile dataFile);

	/**
	 * Retorna um {@link ArrayList} com os dados
	 * 
	 * @param dataFile - arquivo de dados
	 * @return - {@link ArrayList} com os dados
	 */
	public abstract ArrayList<Armacao> getArmacao(ArmacaoDataFile dataFile);

	/**
	 * Retorna um {@link ArrayList} com os dados
	 * 
	 * @param dataFile - arquivo de dados
	 * @return - {@link ArrayList} com os dados
	 */
	public abstract ArrayList<Marca> getMarca(MarcaDataFile dataFile);

	/**
	 * Retorna um {@link ArrayList} com os dados
	 * 
	 * @param dataFile - arquivo de dados
	 * @return - {@link ArrayList} com os dados
	 */
	public abstract ArrayList<Cliente> getCliente(ClienteDataFile dataFile);

	/**
	 * Retorna um {@link ArrayList} com os dados
	 * 
	 * @param dataFile - arquivo de dados
	 * @return - {@link ArrayList} com os dados
	 */
	public abstract ArrayList<Fornecedor> getFornecedor(
			FornecedorDataFile dataFile);

	@Deprecated
	public abstract ArrayList<String> getProdutoColumnsNeeded();

	@Deprecated
	public abstract ArrayList<String> getDepartamentoColumnsNeeded();

	@Deprecated
	public abstract ArrayList<String> getGrupoColumnsNeeded();

	@Deprecated
	public abstract ArrayList<String> getArmacaoColumnsNeeded();

	@Deprecated
	public abstract ArrayList<String> getMarcaColumnsNeeded();

	@Deprecated
	public abstract ArrayList<String> getClienteColumnsNeeded();

	@Deprecated
	public abstract ArrayList<String> getLojaColumnsNeeded();

	@Deprecated
	public abstract ArrayList<String> getFornecedorColumnsNeeded();

	/**
	 * Retorna quais as colunas estão faltando no arquivo de dados
	 * 
	 * @param dataFile - arquivo de dados
	 * @return - {@link ArrayList} com as colunas que estão faltando
	 */
	public abstract ArrayList<String> getColumnsNeeded(DataFile dataFile);

}
