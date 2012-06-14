package br.com.gz.migration.sql;

final class MercattoRequiredColumns {

	public static final String[] PRODUTO = new String[] { 
		"codigo_interno",				//idProduto
		"codigo_tributacao", 			//SitTrib e SitTribCompra
	//	"tipo_pis_cofins", 				//TipoPisCofins
		"aliquota_cofins",				//Cofins
		"aliquota_pis", 				//Pis
		"aliquota_icms",	 			//Icms e IcmsCompra
		"descricao",					//Descricao
		"descricao_reduzida", 			//DescrRed
		"unidade", 						//UnidSaida
		"preco_custo", 					//Custo
		"preco_venda", 					//Venda1
		"codigo_grupo",					//idGrupo
		"codigo_subgrupo", 				//idSubGrupo
		"codigo_subsubgrupo", 			//idSubGrupo1
		"codigo_ativo", 				//idSituacao
		"qtde_estoque_minimo", 			//EstMin
		"qtde_estoque_maximo",			//EstMax
		"qtde_atual",					//estoque_atual
		"peso_variavel", 				//ResoVariavel
		"codigo_barras", 				//Ean
		"codigo_ncm", 					//ClassFiscal
		"porcentagem_lucro",			//MARGEM
		"data_cadastro"					//DATAHORA_CADASTRO
	};

	public static final String[] CLIENTE = new String[] { 
		"codigo_cliente", 	//idCliente
		"razao_social",		//nome
		"endereco", 		//endereco
		"bairro", 			//bairro
		"cidade", 			//cidade
		"estado", 			//uf
		"cep", 				//cep
		"cpf_cnpj", 		//cpf
		"rg_ie",			//rg
		"data_nascimento",	//dt_nasc
		"numero",  			//numero
		"codigo_loja", 		//LOJA
		"nome_fantasia"		//fantasia 
	};

	public static final String[] FORNECEDOR = new String[] { 
		"codigo_fornecedor",	//IDFORNECEDOR
		"razao_social", 		//NOME
		"nome_fantasia",		//FANTASIA
		"endereco",				//ENDERECO
		"bairro", 				//BAIRRO
		"cidade", 				//CIDADE
		"estado", 				//UF
		"cep",					//CEP
		"cpf_cnpj", 			//CPF_CNPJ
		"rg_ie",  				//RG_IE
		"numero", 				//NUMERO
		"data_cadastro", 		//DTCADASTRO
	};

	public static final String[] DEPARTAMENTO = new String[] { 
		"codigo",
		"descricao" 
	};

	public static final String[] GRUPO = DEPARTAMENTO;

	public static final String[] ARMACAO = DEPARTAMENTO;

	public static final String[] MARCA = DEPARTAMENTO;

	public static final String[] SETOR = DEPARTAMENTO;

}
