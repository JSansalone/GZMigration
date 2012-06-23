package br.com.gz.migration;

import java.io.IOException;
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
import br.com.gz.migration.datafile.DepartamentoDataFile;
import br.com.gz.migration.datafile.FornecedorDataFile;
import br.com.gz.migration.datafile.GrupoDataFile;
import br.com.gz.migration.datafile.MarcaDataFile;
import br.com.gz.migration.datafile.ProdutoDataFile;
import br.com.gz.migration.exception.SecurityViolationException;
import br.com.gz.migration.file.LogFile;
import br.com.gz.util.GZSoftwares;

/**
 * 
 * @author Jonathan Sansalone
 * 
 *         Classe abstrata que servirá de substituta para a antiga interface
 *         IDAO Os benefícios dessa classe é que ela concentra todos os inserts
 *         para os sistemas da GZ, facilitando a manutenção.<br>
 * 
 *         <b>Regras para a implementação:</b><br>
 * 
 *         Regras gerais: primeiro usa-se countX. Se retornar POLICY_VIOLATION
 *         usa-se getXColumnsNeeded, caso contrário usa-sa getX - Devem ter
 *         membros ResultSet e PreparedStatement de acordo com a lista abaixo:
 * 
 *         private PreparedStatement stProduto; private PreparedStatement
 *         stDepartamento; private PreparedStatement stGrupo; private
 *         PreparedStatement stArmacao; private PreparedStatement stMarca;
 *         private PreparedStatement stCliente; private PreparedStatement
 *         stFornecedor; private PreparedStatement stNFEntrada; private
 *         PreparedStatement stNFSaida; private PreparedStatement stContaPagar;
 *         private PreparedStatement stContaReceber; private PreparedStatement
 *         stMovtoVenda;
 * 
 *         private ResultSet rsProduto; private ResultSet rsDepartamento;
 *         private ResultSet rsGrupo; private ResultSet rsArmacao; private
 *         ResultSet rsMarca; private ResultSet rsCliente; private ResultSet
 *         rsFornecedor; private ResultSet rsNFEntrada; private ResultSet
 *         rsNFSaida; private ResultSet rsContaPagar; private ResultSet
 *         rsContaReceber; private ResultSet rsMovtoVenda;
 * 
 *         Contrutor: - Deve implementar o construtor herdado - Deve salvar o
 *         software da GZ, de terceiro e o banco de destino nas variáveis
 *         herdadas - Deve instanciar a política de colunas
 * 
 *         Métodos countX: - Devem retornar a quantidade mais exata possível de
 *         registros que serão possivelmente inseridos - Devem validar as
 *         colunas retornadas usando a política de colunas - Devem retornar
 *         POLICY_VIOLATION se a política de colunas for violada - Devem
 *         retornar EMPTY_RETURN se não forem implementados - Devem obter o
 *         select apropriado da estrutura de diretórios de selects - Devem
 *         instanciar o PreparedStatement apropriado - Devem alimentar o
 *         ResultSet apropriado com os dados - Não devem de maneira nenhuma
 *         fechar o ResultSet apropriado - Não devem de maneira nenhuma fechar o
 *         PreparedStatement apropriado - Devem posicionar o cursor do ResultSet
 *         apropriado antes do primeiro registro (usar beforeFirst())
 * 
 *         Métodos getX: - Só podem ser utilizados se o countX validar a
 *         política de colunas - Não devem consultar os dados novamente no banco
 *         de dados - Devem utilizar o ResultSet alimentado pelo countX - Devem
 *         ser responsáveis por todos os tratamentos de dados usando um
 *         formatador apropriado para o sistema da GZ - São responsáveis por
 *         fechar o ResultSet e o PreparedStatement apropriados - Devem retornar
 *         os dados em ArrayList
 * 
 *         Métodos getXColunmNeeded: - Sua função é fornecer todas as colunas
 *         faltantes de acordo com a política - Só podem ser utilizados caso o
 *         countX retorne POLICY_VIOLATION - São responsáveis por fechar o
 *         ResultSet e o PreparedStatement apropriados caso forem (os métodos
 *         getXColumnsNeeded) utilizados - Devem retornar o ArrayList de colunas
 *         faltantes
 * 
 */
public abstract class SQLDataProvider {

	public static final int EMPTY_RETURN = -987654321;
	public static final int POLICY_VIOLATION = -345612776;
	public static final int SECURITY_VIOLATION = -917345787;
	public static final int SQL_ERROR = -5196673;

	protected GZSoftwares software;
	protected GZSoftwares otherSoftware;
	protected DatabaseType dbTo;
	protected DatabaseType dbFrom;
	
	protected GZSoftwares gzSoftware;

	public SQLDataProvider(GZSoftwares software, GZSoftwares otherSoftware,
			DatabaseType dbTo, DatabaseType dbFrom) {

		this.dbTo = dbTo;
		this.dbFrom = dbFrom;
		this.software = software;
		this.otherSoftware = otherSoftware;

	}
	
	public SQLDataProvider(GZSoftwares software, DatabaseType dbTo){
		
		this.gzSoftware = software;
		this.dbTo = dbTo;
		
	}

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

	public final boolean addMarca(Connection cnn, Marca arg1) {

		PreparedStatement st;

		try {

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into marca(codigo,descricao) values(?,?) on duplicate key update codigo = codigo");
					st.setInt(1, arg1.getCodigo());
					st.setString(2, arg1.getDescricao());
					st.execute();
					st.close();

				} else if (dbTo == DatabaseType.MSSQL) {

				} else if (dbTo == DatabaseType.Oracle) {

				}

			} else if (software == GZSoftwares.MERCATTO) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into subgrupo1(idsubgrupo1,Nome,idgrupo,idsubgrupo) values(?,?,1,1) on duplicate key update idsubgrupo1 = idsubgrupo1;");
					st.setInt(1, arg1.getCodigo());
					st.setString(2, arg1.getDescricao());
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

	public final boolean addProduto(Connection cnn, Produto p) {

		try {

			PreparedStatement st;

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into estoque(cdprod, codbarra, descricao, descpdv, unidade, setor, variavel, depto, quantprod, cadastro, contsaldo, sointeiro, cfiscal, tributa, grupo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update cdprod = cdprod");

					st.setString(1, p.getCodigoInterno());
					st.setString(2, p.getCodigoDeBarras());
					st.setString(3, p.getDescricao());
					st.setString(4, p.getDescricaoReduzida());
					st.setString(5, p.getUnidade());
					st.setInt(6, p.getSetor());
					st.setString(7, p.getVariavel());
					st.setInt(8, p.getDepartamento());
					st.setDouble(9, p.getQuantidade());
					st.setDate(10, new java.sql.Date(p.getDataCadastro()
							.getTimeInMillis()));
					st.setString(11, p.getControlaSaldo());
					st.setString(12, p.getSoInteiro());
					st.setString(13, p.getNcm());
					st.setInt(14, p.getCodigoTributacao());
					st.setInt(15, p.getGrupo());
					st.execute();

					st = cnn.prepareStatement("insert into esttrib(cdprod, icmcompra, tributa, trbcompra) values(?,?,?,?) on duplicate key update cdprod = cdprod");
					st.setString(1, p.getCodigoInterno());
					st.setDouble(2, p.getIcmCompra());
					st.setInt(3, p.getCodigoTributacao());
					st.setString(4, p.getTributacaoCompra());
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

	public final boolean addProdutoLoja(Connection cnn, Produto p, int l) {

		try {

			PreparedStatement st;

			if (software == GZSoftwares.MERCOFLEX) {

				if (dbTo == DatabaseType.MySQL) {

					st = cnn.prepareStatement("insert into saldos(cdprod, icmcompra, precocomp, ipi, precoprom, precovenda, precocusto, perclucro, estminimo, estmaximo, quant,  pis, cofins, situacao, trbcompra, loja) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update cdprod = cdprod");
					st.setString(1, p.getCodigoInterno());
					st.setDouble(2, p.getIcmCompra());
					st.setDouble(3, p.getPrecoCompra());
					st.setDouble(4, p.getIpi());
					st.setDouble(5, p.getPrecoPromocao());
					st.setDouble(6, p.getPrecoVenda());
					st.setDouble(7, p.getPrecoCusto());

					if (p.getPorcentagemLucro() > 100.00
							|| p.getPorcentagemLucro() < 0) {
						st.setDouble(8, 100.00);
					} else {
						st.setDouble(8, p.getPorcentagemLucro());
					}
					st.setDouble(9, p.getQuantidadeEstoqueMinimo());
					st.setDouble(10, p.getQuantidadeEstoqueMaximo());
					st.setDouble(11, p.getQuantidade());
					st.setDouble(12, p.getAliquotaPisCompra());
					st.setDouble(13, p.getAliquotaCofinsCompra());
					st.setString(14, p.getAtivo());
					st.setString(15, p.getTributacaoCompra());
					st.setInt(16, l);
					st.execute();

					st = cnn.prepareStatement("insert into estmix(cdprod, loja) values(?,?) on duplicate key update cdprod = cdprod");
					st.setString(1, p.getCodigoInterno());
					st.setInt(2, l);
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

	public abstract int countProduto(ProdutoDataFile dataFile) throws IOException;

	public abstract int countDepartamento(DepartamentoDataFile dataFile) throws IOException;

	public abstract int countGrupo(GrupoDataFile dataFile) throws IOException;

	public abstract int countArmacao(ArmacaoDataFile dataFile) throws IOException;

	public abstract int countMarca(MarcaDataFile dataFile) throws IOException;

	public abstract int countCliente(ClienteDataFile dataFile) throws IOException;

	public abstract int countFornecedor(FornecedorDataFile dataFile) throws IOException;

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
	
	public abstract ArrayList<Produto> getProduto(ProdutoDataFile dataFile);

	public abstract ArrayList<Departamento> getDepartamento(
			DepartamentoDataFile dataFile);

	public abstract ArrayList<Grupo> getGrupo(GrupoDataFile dataFile);

	public abstract ArrayList<Armacao> getArmacao(ArmacaoDataFile dataFile);

	public abstract ArrayList<Marca> getMarca(MarcaDataFile dataFile);

	public abstract ArrayList<Cliente> getCliente(ClienteDataFile dataFile);

	public abstract ArrayList<Fornecedor> getFornecedor(
			FornecedorDataFile dataFile);

	public abstract ArrayList<String> getProdutoColumnsNeeded();

	public abstract ArrayList<String> getDepartamentoColumnsNeeded();

	public abstract ArrayList<String> getGrupoColumnsNeeded();

	public abstract ArrayList<String> getArmacaoColumnsNeeded();

	public abstract ArrayList<String> getMarcaColumnsNeeded();

	public abstract ArrayList<String> getClienteColumnsNeeded();

	public abstract ArrayList<String> getLojaColumnsNeeded();

	public abstract ArrayList<String> getFornecedorColumnsNeeded();

}
