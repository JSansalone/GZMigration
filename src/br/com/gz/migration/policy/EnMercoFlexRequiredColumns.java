package br.com.gz.migration.policy;

public enum EnMercoFlexRequiredColumns {

	//=======================================================================================================
	//======================================|       PRODUTOS       |=========================================
	//=======================================================================================================
	PROD_LOJA("LOJA","loja",EnColumnsCategory.ESTOQUE_LOJA,EnColumnsCategory.ESTOQUE_SALDO),
	PROD_CODIGO_INTERNO("CODIGO_INTERNO","cdprod",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_LOJA,EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO), 
	PROD_CODIGO_DE_BARRAS("CODIGO_DE_BARRAS","codbarra",EnColumnsCategory.ESTOQUE),
	PROD_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.ESTOQUE), 
	PROD_DESCRICAO_REDUZIDA("DESCRICAO_REDUZIDA","descpdv",EnColumnsCategory.ESTOQUE), 
	PROD_UNIDADE("UNIDADE","unidade",EnColumnsCategory.ESTOQUE), 
	PROD_CODIGO_SETOR("CODIGO_SETOR","setor",EnColumnsCategory.ESTOQUE), 
	PROD_TEM_PESO_VARIAVEL("TEM_PESO_VARIAVEL","variavel",EnColumnsCategory.ESTOQUE),//N S
	PROD_PRECO_COMPRA("PRECO_COMPRA","precocomp",EnColumnsCategory.ESTOQUE_SALDO), 
	PROD_PRECO_VENDA("PRECO_VENDA","precovenda",EnColumnsCategory.ESTOQUE_SALDO), 
	PROD_CODIGO_DEPARTAMENTO("CODIGO_DEPARTAMENTO","depto",EnColumnsCategory.ESTOQUE),
	PROD_CODIGO_GRUPO("CODIGO_GRUPO","grupo",EnColumnsCategory.ESTOQUE),
	PROD_PRECO_CUSTO("PRECO_CUSTO","precocusto",EnColumnsCategory.ESTOQUE_SALDO), 
	PROD_PORCENTAGEM_DE_LUCRO("PORCENTAGEM_DE_LUCRO","perclucro",EnColumnsCategory.ESTOQUE_SALDO),
	PROD_QTDE_ESTOQUE_MINIMO("QTDE_ESTOQUE_MINIMO","estminimo",EnColumnsCategory.ESTOQUE_SALDO), 
	PROD_QTDE_ESTOQUE_MAXIMO("QTDE_ESTOQUE_MAXIMO","estmaximo",EnColumnsCategory.ESTOQUE_SALDO), 
	PROD_QTDE_ESTOQUE_ATUAL("QTDE_ESTOQUE_ATUAL","quant",EnColumnsCategory.ESTOQUE_SALDO), 
	PROD_DATA_CADASTRO("DATA_CADASTRO","cadastro",EnColumnsCategory.ESTOQUE), 
	PROD_ALIQUOTA_PIS("ALIQUOTA_PIS","pis",EnColumnsCategory.ESTOQUE_SALDO), 
	PROD_ALIQUOTA_COFINS("ALIQUOTA_COFINS","cofins",EnColumnsCategory.ESTOQUE_SALDO),
	PROD_ATIVO("ATIVO","situacao",EnColumnsCategory.ESTOQUE_SALDO), //A, I
	PROD_CODIGO_NCM("CODIGO_NCM","cfiscal",EnColumnsCategory.ESTOQUE),
	PROD_CSOSN_VENDA("CSOSN_VENDA","csosn",EnColumnsCategory.ESTOQUE_TRIBUTACAO),//101,102,103,201,202,203,300,400,500,900
	PROD_MODALIDADE_VENDA("MODALIDADE_VENDA","pautavdtp",EnColumnsCategory.ESTOQUE_TRIBUTACAO),// 1-Pauta 2-Preço tabelado máximo A-Valor bruto B-Valor líquido
	PROD_VALOR_PAUTA_VENDA("VALOR_PAUTA_VENDA","pautavd",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	PROD_TRIBUTACAO_COMPRA("TRIBUTACAO_COMPRA","trbcompra",EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO),//F-Subs I-Isento N-Não tributado D-Diferimento T-Tributado
	PROD_CODIGO_TRIBUTACAO("CODIGO_TRIBUTACAO","tributa",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_TRIBUTACAO),//1-Substituição 2-Isento 3-Tr7% 4-Tr12% 5-Tr18% 6-Tr25% 7-Tr18%Sub 8-Tr12%Sub
	PROD_VALOR_ICMS_COMPRA("VALOR_ICMS_COMPRA","icmcompra",EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	PROD_SITUACAO_TRIBUTARIA_VENDA("SITUACAO_TRIBUTARIA_VENDA","st",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_TRIBUTACAO),//00,10,20,30,40,41,50,51,60,70,90
	PROD_PORCENTAGEM_ICMS_VENDA("PORCENTAGEM_ICMS_VENDA","icmvenda",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	PROD_PORCENTAGEM_REDUCAO_ICMS("PORCENTAGEM_REDUCAO_ICMS","baseicmven",EnColumnsCategory.ESTOQUE_TRIBUTACAO),// gravar como 100 - <campo>
	PROD_PORCENTAGEM_REDUCAO_ICMS_SUBSTITUICAO("PORCENTAGEM_REDUCAO_ICMS_SUBSTITUICAO","baseicmsub",EnColumnsCategory.ESTOQUE_TRIBUTACAO),// gravar como 100 - <campo>
	PROD_VALOR_ICMS_SUBSTITUICAO("VALOR_ICMS_SUBSTITUICAO","icmsubs",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	PROD_UF_TRIBUTACAO("UF_TRIBUTACAO","uf",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       CLIENTES       |=========================================
	//=======================================================================================================
	CLI_CODIGO("CODIGO","codigo",EnColumnsCategory.CLIENTES),
	CLI_NOME_FANTASIA("NOME_FANTASIA","nomfan",EnColumnsCategory.CLIENTES),
	CLI_RAZAO_SOCIAL("RAZAO_SOCIAL","razsoc",EnColumnsCategory.CLIENTES),
	CLI_CPF_CNPJ("CPF_CNPJ","cgc",EnColumnsCategory.CLIENTES),
	CLI_INSCRICAO_ESTADUAL_RG("INSCRICAO_ESTADUAL_RG","insest",EnColumnsCategory.CLIENTES),
	CLI_SEXO("SEXO","sexo",EnColumnsCategory.CLIENTES),
	CLI_DATA_NASCIMENTO("DATA_NASCIMENTO","dtnasc",EnColumnsCategory.CLIENTES),
	CLI_DATA_CADASTRO("DATA_CADASTRO","cadastro",EnColumnsCategory.CLIENTES),
	CLI_ESTADO("ESTADO","estado",EnColumnsCategory.CLIENTES),
	CLI_CIDADE("CIDADE","munic",EnColumnsCategory.CLIENTES),
	CLI_BAIRRO("BAIRRO","bairro",EnColumnsCategory.CLIENTES),
	CLI_RUA("RUA","ender",EnColumnsCategory.CLIENTES),
	CLI_CEP("CEP","cep",EnColumnsCategory.CLIENTES),
	CLI_NUMERO("NUMERO","numero",EnColumnsCategory.CLIENTES),
	CLI_TELEFONE("TELEFONE","telefone",EnColumnsCategory.CLIENTES),
	CLI_ESTADO_COBRANCA("ESTADO_COBRANCA","estadocob",EnColumnsCategory.CLIENTES),
	CLI_CIDADE_COBRANCA("CIDADE_COBRANCA","municcob",EnColumnsCategory.CLIENTES),
	CLI_BAIRRO_COBRANCA("BAIRRO_COBRANCA","baicob",EnColumnsCategory.CLIENTES),
	CLI_RUA_COBRANCA("RUA_COBRANCA","endecob",EnColumnsCategory.CLIENTES),
	CLI_CEP_COBRANCA("CEP_COBRANCA","cepcob",EnColumnsCategory.CLIENTES),
	CLI_NUMERO_COBRANCA("NUMERO_COBRANCA","numerocob",EnColumnsCategory.CLIENTES),
	CLI_TELEFONE_COBRANCA("TELEFONE_COBRANCA","telcob",EnColumnsCategory.CLIENTES),
	CLI_ESTADO_ENTREGA("ESTADO_ENTREGA","estadoent",EnColumnsCategory.CLIENTES),
	CLI_CIDADE_ENTREGA("CIDADE_ENTREGA","municent",EnColumnsCategory.CLIENTES),
	CLI_BAIRRO_ENTREGA("BAIRRO_ENTREGA","baient",EnColumnsCategory.CLIENTES),
	CLI_RUA_ENTREGA("RUA_ENTREGA","endeent",EnColumnsCategory.CLIENTES),
	CLI_CEP_ENTREGA("CEP_ENTREGA","cepent",EnColumnsCategory.CLIENTES),
	CLI_NUMERO_ENTREGA("NUMERO_ENTREGA","numeroent",EnColumnsCategory.CLIENTES),
	CLI_TELEFONE_ENTREGA("TELEFONE_ENTREGA","telent",EnColumnsCategory.CLIENTES),
	CLI_ATIVO("ATIVO","situacao",EnColumnsCategory.CLIENTES),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|     FORNECEDORES     |=========================================
	//=======================================================================================================
	FOR_CODIGO("CODIGO","codigo",EnColumnsCategory.FORNECEDOR),
	FOR_NOME_FANTASIA("NOME_FANTASIA","nomfan",EnColumnsCategory.FORNECEDOR),
	FOR_RAZAO_SOCIAL("RAZAO_SOCIAL","razsoc",EnColumnsCategory.FORNECEDOR),
	FOR_CPF_CNPJ("CPF_CNPJ","cgc",EnColumnsCategory.FORNECEDOR),
	FOR_INSCRICAO_ESTADUAL_RG("INSCRICAO_ESTADUAL_RG","insest",EnColumnsCategory.FORNECEDOR),
	FOR_ESTADO("ESTADO","estado",EnColumnsCategory.FORNECEDOR),
	FOR_CIDADE("CIDADE","munic",EnColumnsCategory.FORNECEDOR),
	FOR_BAIRRO("BAIRRO","bairro",EnColumnsCategory.FORNECEDOR),
	FOR_RUA("RUA","ender",EnColumnsCategory.FORNECEDOR),
	FOR_CEP("CEP","cep",EnColumnsCategory.FORNECEDOR),
	FOR_NUMERO("NUMERO","numero",EnColumnsCategory.FORNECEDOR),
	FOR_TELEFONE("TELEFONE","telefone",EnColumnsCategory.FORNECEDOR),
	FOR_ATIVO("ATIVO","situacao",EnColumnsCategory.FORNECEDOR),
	FOR_CONTATO("CONTATO","contato",EnColumnsCategory.FORNECEDOR),
	FOR_EMAIL("EMAIL","email",EnColumnsCategory.FORNECEDOR),
	FOR_EMAIL_BOLETO("EMAIL_BOLETO","emailboleto",EnColumnsCategory.FORNECEDOR),
	FOR_EMAIL_DANFE("EMAIL_DANFE","emaildanfe",EnColumnsCategory.FORNECEDOR),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|     DEPARTAMENTOS    |=========================================
	//=======================================================================================================
	DEP_CODIGO("CODIGO","codigo",EnColumnsCategory.DEPARTAMENTO),
	DEP_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.DEPARTAMENTO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|        GRUPOS        |=========================================
	//=======================================================================================================
	GRU_CODIGO("CODIGO","codigo",EnColumnsCategory.GRUPO),
	GRU_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.GRUPO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       SETORES        |=========================================
	//=======================================================================================================
	SET_CODIGO("CODIGO","codigo",EnColumnsCategory.SETOR),
	SET_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.SETOR),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       ARMAÇÕES       |=========================================
	//=======================================================================================================
	ARM_CODIGO("CODIGO","codigo",EnColumnsCategory.ARMACAO),
	ARM_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.ARMACAO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|        MARCAS        |=========================================
	//=======================================================================================================
	MAR_CODIGO("CODIGO","codigo",EnColumnsCategory.MARCA),
	MAR_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.MARCA);
	//=======================================================================================================
	
	private final String label;
	private final String columnName;
	private final EnColumnsCategory[] categories;
	
	private EnMercoFlexRequiredColumns(String label, String columnName, EnColumnsCategory... categories){
		
		this.label = label;
		this.columnName = columnName;
		this.categories = categories;
		
	}
	
	public String getLabel(){
		
		return label;
		
	}
	
	public String getColumnName(){
		
		return columnName;
		
	}
	
	public EnColumnsCategory[] getCategories(){
		
		return this.categories;
		
	}
	
}
