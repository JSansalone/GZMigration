package br.com.gz.migration.sql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.file.LogFile;
import br.com.gz.util.GZSoftwares;

@Deprecated
public final class DefaultSQLSelectStatements {

	@Deprecated
	public static final String VERSATHO_SELECT_PRODUTO = 
			"select \n"
			+ "		codigo 																					as cdprod,\n"
			+ " 	coalesce(codbarra,codigo) 																as codbarra,\n"
			+ " 	coalesce(substring(descpdv from 1 for 40),' ') 											as descricao,\n"
			+ " 	coalesce(substring(descpdv from 1 for 24),' ') 											as descpdv,\n"
			+ " 	coalesce(replace(icms,',','.'),0) 														as icms,\n"
			+ " 	coalesce(substring(unidade from 1 for 2),'UN') 											as unidade,\n"
			+ " 	case when coalesce(substring(unidade from 1 for 2),'UN') = 'KG' then 1 else 0 end 		as setor,\n"
			+ " 	case when coalesce(substring(unidade from 1 for 2),'UN') = 'KG' then 'S' else 'N' end 	as variavel,\n"
			+ " 	coalesce(replace(precomp,',','.'),0) 													as precocomp,\n"
			+ " 	coalesce(replace(preprom,',','.'),0) 													as precoprom,\n"
			+ " 	coalesce(replace(prevenda,',','.'),0) 													as precovenda,\n"
			+ " 	coalesce(codsecao,0) 																	as depto,\n"
			+ " 	coalesce(replace(precusto,',','.'),0) 													as precocusto,\n"
			+ " 	coalesce(replace(margem,',','.'),0) 													as perclucro,\n"
			+ " 	coalesce(replace(estmin,',','.'),0) 													as estminimo,\n"
			+ " 	coalesce(replace(estmax,',','.'),0) 													as estmaximo,\n"
			+ " 	coalesce(replace(saldoloja,',','.'),0) 													as quant,\n"
			+ " 	coalesce(dtcad,'2012-01-01') 															as cadastro,\n"
			+ " 	coalesce(replace(pis,',','.'),0) 														as pis,\n"
			+ " 	coalesce(replace(cofins,',','.'),0) 													as cofins,\n"
			+ " 	case when coalesce(prodinativo,'f') = 'f' then 'A' else 'I' end 						as situacao,\n"
			+ " 	coalesce(clasfisc,'0') 																	as cfiscal,\n"
			+ " 	case when codaliquota = 1 or codaliquota = 3 then 'I' when codaliquota = 2 then 'F' else 'T' end as trbcompra,\n"
			+ " 	case when codaliquota = 1 then 2 when codaliquota = 2 then 1 when codaliquota = 3 then 2 when codaliquota = 4 then 3 when codaliquota = 5 then 4 when codaliquota = 6 then 5 when codaliquota = 7 then 6 end as tributa,\n"
			+ " 	case when codaliquota = 1 then 0.0 when codaliquota = 2 then 0.0 when codaliquota = 3 then 0.0 when codaliquota = 4 then 7.0 when codaliquota = 5 then 12.0 when codaliquota = 6 then 18.0 when codaliquota = 7 then 25.0 end as icmcompra \n"
			+ "from " 
			+ "		produtos";
	@Deprecated
	public static final String VERSATHO_SELECT_CLIENTE = 
			"select \n"
			+ "		codigo 												as codigo,\n"
			+ "   	coalesce(cpf,'0') 									as cgc,\n"
			+ " 	coalesce(rg,'0') 									as insest,\n"
			+ " 	coalesce(dtnascto,'1980-01-01') 					as dtnasc,\n"
			+ " 	coalesce(situacao,'A') 								as situacao,\n"
			+ " 	coalesce(substring(endereco from 1 for 60),' ') 	as ender,\n"
			+ " 	coalesce(substring(complemento from 1 for 20),' ') 	as complemen,\n"
			+ " 	coalesce(bairro,' ') 								as bairro,\n"
			+ " 	coalesce(cidade,' ') 								as munic,\n"
			+ " 	coalesce(substring(estado from 1 for 2),'SP') 		as estado,\n"
			+ " 	coalesce(cep,'0') 									as cep,\n"
			+ " 	coalesce(email,' ') 								as email,\n"
			+ " 	coalesce(site,' ') 									as endwww,\n"
			+ " 	coalesce(substring(telres from 1 for 8),'0') 		as telefone,\n"
			+ " 	coalesce(substring(telcel from 1 for 8),'0') 		as telefax,\n"
			+ " 	coalesce(dtcadastro,'1980-01-01') 					as cadastro,\n"
			+ " 	coalesce(substring(nome from 1 for 50),' ') 		as razsoc,\n"
			+ " 	coalesce(substring(nomefantasia from 1 for 25),' ') as nomfan \n"
			+ "from \n" 
			+ "		clientes";
	@Deprecated
	public static final String VERSATHO_SELECT_FORNECEDOR = 
			"select \n"
			+ "		codigo 											as codigo,\n"
			+ " 	coalesce(substring(nomfan from 1 for 25),' ')	as nomfan,\n"
			+ " 	coalesce(substring(razaosoc from 1 for 50),' ') as razsoc,\n"
			+ " 	coalesce(substring(endereco from 1 for 60),' ') as ender,\n"
			+ " 	coalesce(numero,0) 								as numero,\n"
			+ " 	coalesce(bairro,' ') 							as bairro,\n"
			+ " 	coalesce(cidade,' ') 							as munic,\n"
			+ " 	coalesce(substring(estado from 1 for 2),'SP') 	as estado,\n"
			+ " 	coalesce(cep,0) 								as cep,\n"
			+ " 	coalesce(substring(tel1 from 1 for 8),0) 		as telefone,\n"
			+ " 	coalesce(substring(fax from 1 for 8),0) 		as telefax,\n"
			+ " 	coalesce(email,' ') 							as email,\n"
			+ " 	coalesce(site,' ') 								as endwww,\n"
			+ " 	coalesce(cgc,0) 								as cgc,\n"
			+ " 	coalesce(inscest,0) 							as insest \n" 
			+ "from \n"
			+ "		fornecedores";
	@Deprecated
	public static final String VERSATHO_SELECT_GRUPO = "select codigo, descricao from grupos";
	@Deprecated
	public static final String VERSATHO_SELECT_DEPARTAMENTO = "select codigo, descricao from secoes";
	@Deprecated
	public static final String VERSATHO_SELECT_ARMACAO = "";
	@Deprecated
	public static final String VERSATHO_SELECT_MARCA = "";
	@Deprecated
	public static final String VERSATHO_SELECT_NF_ENTRADA = "";
	@Deprecated
	public static final String VERSATHO_SELECT_NF_SAIDA = "";
	@Deprecated
	public static final String VERSATHO_SELECT_CONTA_PAGAR = "";
	@Deprecated
	public static final String VERSATHO_SELECT_CONTA_RECEBER = "";
	@Deprecated
	public static final String VERSATHO_SELECT_MOVTO_VENDA = "";
	
	@Deprecated
	public static final String AES_SELECT_PRODUTO = 
			"select \n"
			+ " 	substring(i1.idItens,4) as cdprod,\n"
			+ " 	i5.codigo as codbarra,\n"
			+ " 	substring(i1.descricao,1,40) as descricao,\n"
			+ " 	substring(i1.descricao,1,24) as descpdv,\n"
			+ " 	i1.idGrupo_itens as grupo,\n"
			+ " 	'A' as situacao,\n"
			+ " 	substring(i1.Estoque_Unid,1,2) as unidade,\n"
			+ " 	'N' as formula,\n"
			+ " 	'N' as descpadrao,\n"
			+ " 	'N' as multiplica,\n"
			+ " 	'N' as embfechada,\n"
			+ " 	'N' as sointeiro,\n"
			+ " 	'N' as emitiq,\n"
			+ " 	'N' as bloqvenda,\n"
			+ " 	'N' as solsenha,\n"
			+ " 	'N' as tipolista,\n"
			+ " 	'A' as iat,\n"
			+ " 	case when substring(i1.Estoque_Unid,1,2) = 'KG' then 'S' else 'N' end as variavel,\n"
			+ " 	case when substring(i1.Estoque_Unid,1,2) = 'KG' then 1 else 0 end as setor,\n"
			+ " 	replace(i2.Preco_venda,',','.') as precovenda,\n"
			+ " 	replace(i2.Preco_venda_terminais,',','.') as termvenda,\n"
			+ " 	case when substring(i3.Unid_compra,1,2) = 'CX' or substring(i3.Unid_compra,1,2) = 'FD' then replace(i3.preco_compra,',','.') else 0 end as precocaixa,\n"
			+ " 	case when substring(i3.Unid_compra,1,2) <> 'CX' and substring(i3.Unid_compra,1,2) <> 'FD' then replace(i3.preco_compra,',','.') else 0 end as precocomp,\n"
			+ " 	replace(i4.Custo_Atual,',','.') as precocusto,\n"
			+ " 	case when i1.Aliquota = 'SubstituiÁ„o Tribut·ria' then 'F' when i1.Aliquota = 'Aliquota por Estado (18%)' or i1.Aliquota = 'Aliquota por Estado (12%)' or i1.Aliquota = 'Aliquota por Estado (7%)' or i1.Aliquota = 'Aliquota por Estado (25%)' then 'T' when i1.Aliquota = 'Isento' then 'I' end as trbcompra,\n"
			+ " 	case when i1.Aliquota = 'Aliquota por Estado (18%)' then 18.0 when i1.Aliquota = 'Aliquota por Estado (12%)' then 12.0 when i1.Aliquota = 'Aliquota por Estado (7%)' then 7.0 when i1.Aliquota = 'Aliquota por Estado (25%)' then 25.0 else 0 end as icmcompra,\n"
			+ " 	case when i1.Aliquota = 'SubstituiÁ„o Tribut·ria' then 1 when i1.Aliquota = 'Aliquota por Estado (18%)' then 5 when i1.Aliquota = 'Aliquota por Estado (12%)' then 4 when i1.Aliquota = 'Aliquota por Estado (7%)' then 3 when i1.Aliquota = 'Aliquota por Estado (25%)' then 6 when i1.Aliquota = 'Isento' then 2 end as tributa \n"
			+ "from \n" 
			+ "		itens as i1 \n" 
			+ "inner join \n"
			+ "		itens_cod_barras as i5 \n" 
			+ "on \n"
			+ " 	i1.idItens = i5.idItens \n" 
			+ "inner join \n"
			+ " 	itens_venda as i2 \n" 
			+ "on \n" 
			+ "		i1.idItens = i2.idItens \n"
			+ "inner join \n" 
			+ " 	itens_compra as i3 \n" 
			+ "on \n"
			+ "		i1.idItens = i3.idItens \n" 
			+ "inner join \n"
			+ "		itens_custo as i4 \n" 
			+ "on \n" 
			+ " 	i1.idItens = i4.idItens";
	@Deprecated
	public static final String AES_SELECT_CLIENTE = "";
	@Deprecated
	public static final String AES_SELECT_FORNECEDOR = 
			"select \n" +
			"		substring(p.idPN,3) as codigo,\n" +
			"		substring(p.Nome,1,25) as nomfan,\n" +
			"		substring(p.Nome,1,50) as razsoc,\n" +
			"		p.Ident_Fiscal as cnpj,\n" +
			"		e.Endereco as ender,\n" +
			"		e.Numero as numero,\n" +
			"		substring(e.Complemento,1,20) as complemen,\n" +
			"		e.Bairro as bairro,\n" +
			"		e.CEP as cep,\n" +
			"		e.idMunicipio as munic,\n" +
			"		e.idEstado as estado \n" +
			"from \n" +
			"		pn as p \n" +
			"inner join \n" +
			"		enderecos as e \n" +
			"on \n" +
			"		p.idEndereco = e.idEnderecos";
	@Deprecated
	public static final String AES_SELECT_GRUPO = "select descricao from grupo_novo";
	@Deprecated
	public static final String AES_SELECT_DEPARTAMENTO = "";
	@Deprecated
	public static final String AES_SELECT_ARMACAO = "";
	@Deprecated
	public static final String AES_SELECT_MARCA = "";
	@Deprecated
	public static final String AES_SELECT_NF_ENTRADA = "";
	@Deprecated
	public static final String AES_SELECT_NF_SAIDA = "";
	@Deprecated
	public static final String AES_SELECT_CONTA_PAGAR = "";
	@Deprecated
	public static final String AES_SELECT_CONTA_RECEBER = "";
	@Deprecated
	public static final String AES_SELECT_MOVTO_VENDA = "";

	@Deprecated
	public static final String MRS_SELECT_PRODUTO = 
			"select \n" +
			"		p.codigo as cdprod,  \n" +
			"		p.descricao as descricao,  \n" +
			"		p.embalagem2 as unidade,  \n" +
			"		p.codigo_barras as codbarra,  \n" +
			"		case p.setorbalanca  \n" +
			"			when 'N√O' then 'N'  \n" +
			"			else 'S'  \n" +
			"		end as variavel,	 \n" +
			"		p.estoque as quant,	 \n" +
			"		p.precovenda as precovenda,	 \n" +
			"		p.valor_promocao as precoprom,	 \n" +
			"		case p.produto_inativo  \n" +
			"			when 'N' then 'A'  \n" +
			"			else 'I'  \n" +
			"		end as situacao,	 \n" +
			"		p.ipi as ipi,	 \n" +
			"		p.preco_custo_un_total as precocusto,	 \n" +
			"		p.descricao_reduzida as descpdv,	 \n" +
			"		i.valor as icms,	 \n" +
			"		cst_pis_entrada as stpisen,	 \n" +
			"		cst_cofins_entrada as stcofinsen,	 \n" +
			"		cst_pis_saida as stpis, \n" +
			"		cst_cofins_saida as stcofins  \n" +
			"from	 \n" +
			"		produtos as p  \n" +
			"inner join	 \n" +
			"		icms_entrada as i  \n" +
			"on	 \n" +
			"		p.icms_entrada = i.codigo  \n" +
			"order by	 \n" +
			"		p.codigo \n";
	@Deprecated
	public static final String MRS_SELECT_CLIENTE = 
			"select  \n" +
			"		codigo as codigo, \n" +
			"		nome as nomfan, \n" +
			"		'RUA' as tipoender, \n" +
			"		endereco as ender, \n" +
			"		bairro as bairro, \n" +
			"		estado as estado, \n" +
			"		cep as cep, \n" +
			"		cpf as cgc, \n" +
			"		cidade as munic, \n" +
			"		case sexo  \n" +
			"			when 'Masculino' then 'M'  \n" +
			"			else 'F'  \n" +
			"		end as sexo, \n" +
			"		codigo_municipio as ibge  \n" +
			"from  \n" +
			"		clientes \n";
	@Deprecated
	public static final String MRS_SELECT_FORNECEDOR = 
			"select \n"
					+"		distinct  \n"
					+"		pe.codigo as codigo,  \n"
					+"		pe.nome_fantasia as nomfan,\n"
					+"		pe.razao_social as razsoc,  \n"
					+"		pe.cpf as cgc,  \n"
					+"		pe.cep as cep,  \n"
					+"		pe.endereco as ender,  \n"
					+"		pe.bairro as bairro,\n"
					+"		pe.cidade as munic,\n"
					+"		pe.estado as estado,  \n"
					+"		pe.rg as insest,\n"
					+"		' ' as email,\n"
					+"		' ' as endwww,\n"
					+"		pe.fax as telefax,\n"
					+"		pe.numero as numero,\n"
					+"		pe.telefone as telefone\n"
					+"from  \n"
					+"		fornecedores as pe";
	@Deprecated
	public static final String MRS_SELECT_GRUPO = 
			"select  \n" +
			"		distinct  \n" +
			"		sgru_seq as CODIGO,  \n" +
			"		sgru_nome as DESCRICAO  \n" +
			"from  \n" +
			"		cad_log_sgru  \n" +
			"order by  \n" +
			"		sgru_seq";
	@Deprecated
	public static final String MRS_SELECT_DEPARTAMENTO = "";
	@Deprecated
	public static final String MRS_SELECT_ARMACAO = "";
	@Deprecated
	public static final String MRS_SELECT_MARCA = 
			"select distinct  \n" +
			"		cod_marca as CODIGO,  \n" +
			"		marca_nome as DESCRICAO  \n" +
			"from  \n" +
			"		cad_log_marca  \n" +
			"order by  \n" +
			"		cod_marca";
	@Deprecated
	public static final String MRS_SELECT_NF_ENTRADA = "";
	@Deprecated
	public static final String MRS_SELECT_NF_SAIDA = "";
	@Deprecated
	public static final String MRS_SELECT_CONTA_PAGAR = "";
	@Deprecated
	public static final String MRS_SELECT_CONTA_RECEBER = "";
	@Deprecated
	public static final String MRS_SELECT_MOVTO_VENDA = "";

	@Deprecated
	public static final String SUPERUS_SELECT_PRODUTO = 
			"select \n"
			+ "		distinct \n"
			+ "		p2.codigo as cdprod,\n"
			+ "		p2.codbarra as codbarra,\n"
			+ "		p4.nome as descricao,\n"
			+ "		p1.fornecedor as fornec,\n"
			+ "		c.nome as cfiscal,\n"
			+ "		p4.grupo as armacao,\n"
			+ "		p4.categoria as depto,\n"
			+ "		p4.subgrupo as marca,\n"
			+ "		p3.precovenda as precovenda,\n"
			+ "		p3.custoliquido as precocusto,\n"
			+ "		p3.custocaixa as precocaixa,\n"
			+ "		p3.custobruto as precocompra,\n"
			+ "		p3.sugestao as perclucro,\n"
			+ "		case when p3.creditopis > 0 then 1.65 else 0 end as pis,\n"
			+ "		case when p3.creditopis > 0 then 7.6 else 0 end as cofins,\n"
			+ "		case when p3.creditopis > 0 then '01' else '06' end as stcofins,\n"
			+ "		case when p3.creditopis > 0 then '01' else '06' end as stpis,\n"
			+ "		case when p3.creditopis > 0 then '50' else '73' end as stcofinsen,\n"
			+ "		case when p3.creditopis > 0 then '50' else '73' end as stpisen,\n"
			+ "		case \n"
			+ "			when p5.icms = 'F' then 1 \n"
			+ "			when p5.icms = 'I' then 2 \n"
			+ "			when p5.icms = 'T3' then 3 \n"
			+ "			when p5.icms = 'T1' then 4 \n"
			+ "			when p5.icms = 'T0' then 5 \n"
			+ "			when p5.icms = 'T2' then 6 \n"
			+ "			when p5.icms = 'R2' then 7 \n"
			+ "			when p5.icms = 'R0' then 8 \n"
			+ "			when p5.icms = 'R1' then 9 \n"
			+ "			else 2 \n"
			+ "		end as tributa, \n"
			+ "		case \n"
			+ "			when p5.icms = 'F' then 'F' \n"
			+ "			when p5.icms = 'I' then 'I' \n"
			+ "			else 'T' \n"
			+ "		end as trbcompra, \n"
			+ "		case \n"
			+ "			when p5.icms = 'F' then 0.0 \n"
			+ "			when p5.icms = 'I' then 0.0 \n"
			+ "			when p5.icms = 'T3' then 7.0 \n"
			+ "			when p5.icms = 'T1' then 12.0 \n"
			+ "			when p5.icms = 'T0' then 18.0 \n"
			+ "			when p5.icms = 'T2' then 25.0 \n"
			+ "			when p5.icms = 'R2' then 18.0 \n"
			+ "			when p5.icms = 'R0' then 12.0 \n"
			+ "			when p5.icms = 'R1' then 18.0 \n"
			+ "			else 0.0 \n"
			+ "		end as icmcompra, \n"
			+ "		p4.pesvar as variavel, \n"
			+ "		case \n"
			+ "			when p4.pesvar = 'S' then 'KG' \n"
			+ "			else p2.embalagem \n"
			+ "		end as unidade, \n"
			+ "		case \n"
			+ "			when p4.pesvar = 'S' then 1 \n"
			+ "			else 0 \n"
			+ "		end as setor, \n"
			+ "		case \n" +
			"			when p4.inativo = 'N' then 'A' \n" +
			"			else 'I' \n" +
			"		end as situacao \n"
			+ "from \n" 
			+ "		produtos_ean p2 \n" 
			+ "inner join \n"
			+ "		produtos_loja p1 \n" 
			+ "on \n" 
			+ "		p1.codigo = p2.codigo \n"
			+ "inner join \n" 
			+ "		produtos_precos p3 \n" 
			+ "on \n"
			+ "		p2.codigo = p3.codigo \n" 
			+ "inner join \n" 
			+ "		produtos p4 \n"
			+ "on \n" 
			+ "		p2.codigo = p4.codigo \n" 
			+ "inner join \n"
			+ "		produtos_impostos p5 \n" 
			+ "on \n" 
			+ "		p2.codigo = p5.codigo \n"
			+ "inner join \n" +
			"		classificacao c \n" +
			"on \n" +
			"		c.codigo = p5.classificacao \n"
			+ "order by \n" 
			+ "		p2.codigo, " + "p2.codbarra " + " desc ";
	@Deprecated
	public static final String SUPERUS_SELECT_CLIENTE = "";
	@Deprecated
	public static final String SUPERUS_SELECT_FORNECEDOR = "";
	@Deprecated
	public static final String SUPERUS_SELECT_GRUPO = "";
	@Deprecated
	public static final String SUPERUS_SELECT_DEPARTAMENTO = "select codigo, nome as descricao from categoria";
	@Deprecated
	public static final String SUPERUS_SELECT_ARMACAO = "select codigo, nome as descricao from grupo";
	@Deprecated
	public static final String SUPERUS_SELECT_MARCA = "select codigo, nome as descricao from subgrupo";
	@Deprecated
	public static final String SUPERUS_SELECT_NF_ENTRADA = "";
	@Deprecated
	public static final String SUPERUS_SELECT_NF_SAIDA = "";
	@Deprecated
	public static final String SUPERUS_SELECT_CONTA_PAGAR = "";
	@Deprecated
	public static final String SUPERUS_SELECT_CONTA_RECEBER = "";
	@Deprecated
	public static final String SUPERUS_SELECT_MOVTO_VENDA = "";

	@Deprecated
	public static String getFromFile(GZSoftwares software,
			EnMigrationDataType dataType) throws IOException {

		String soft = "data/" + software.toString().toLowerCase();
		String file = "get" + dataType.toString().toLowerCase() + ".txt";

		File f = new File(new File(soft), file);

		if (f.exists()) {

			return readFile(f);
			
		} else {

			throw new FileNotFoundException();

		}

	}

	@Deprecated
	private static String readFile(File f) {

		try {

			LogFile.getInstance().writeInFile("Reading file "+f.getAbsolutePath());
			
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader is = new InputStreamReader(fis);
			BufferedReader bf = new BufferedReader(is);

			String linha = "";
			// vari√°vel para guardar o texto todo
			String select = "";

			do {

				// adiciona a linha lida ao texto total
				select += linha;
				select += " ";
				// l√™ a linha do arquivo texto
				linha = bf.readLine();

				// executa enquanto a linha n√£o for null
				// ou seja, enquanto houver linhas no arquivo
			} while (linha != null);

			bf.close();
			is.close();
			fis.close();

			select = select.replace("\n", " ");
			
			return select;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
