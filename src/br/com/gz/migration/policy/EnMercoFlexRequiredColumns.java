package br.com.gz.migration.policy;

/**
 * Cont�m as colunas obrigat�rias do MercoFlex
 * 
 * @author Jonathan
 *
 */
public enum EnMercoFlexRequiredColumns {

	//=======================================================================================================
	//======================================|       PRODUTOS       |=========================================
	//=======================================================================================================
	/**
	 * C�digo da loja
	 */
	PROD_LOJA("LOJA","loja",EnColumnsCategory.ESTOQUE_LOJA,EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * C�digo interno
	 */
	PROD_CODIGO_INTERNO("CODIGO_INTERNO","cdprod",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_LOJA,EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * C�digo de barras
	 */
	PROD_CODIGO_DE_BARRAS("CODIGO_DE_BARRAS","codbarra",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Descri��o
	 */
	PROD_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Descri��o reduzida, geralmente utilizada no PDV
	 */
	PROD_DESCRICAO_REDUZIDA("DESCRICAO_REDUZIDA","descpdv",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Unidade (KG,UN,etc)
	 */
	PROD_UNIDADE("UNIDADE","unidade",EnColumnsCategory.ESTOQUE),
	
	/**
	 * C�digo do setor
	 */
	PROD_CODIGO_SETOR("CODIGO_SETOR","setor",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Indica se o produto possui peso vari�vel. N - N�o, S - Sim
	 */
	PROD_TEM_PESO_VARIAVEL("TEM_PESO_VARIAVEL","variavel",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Pre�o de compra
	 */
	PROD_PRECO_COMPRA("PRECO_COMPRA","precocomp",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Pre�o de venda
	 */
	PROD_PRECO_VENDA("PRECO_VENDA","precovenda",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * C�digo do departamento
	 */
	PROD_CODIGO_DEPARTAMENTO("CODIGO_DEPARTAMENTO","depto",EnColumnsCategory.ESTOQUE),
	
	/**
	 * C�digo do grupo
	 */
	PROD_CODIGO_GRUPO("CODIGO_GRUPO","grupo",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Pre�o de custo
	 */
	PROD_PRECO_CUSTO("PRECO_CUSTO","precocusto",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Margem de lucro
	 */
	PROD_PORCENTAGEM_DE_LUCRO("PORCENTAGEM_DE_LUCRO","perclucro",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Quantidade m�nima em estoque
	 */
	PROD_QTDE_ESTOQUE_MINIMO("QTDE_ESTOQUE_MINIMO","estminimo",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Quantidade m�xima em estoque
	 */
	PROD_QTDE_ESTOQUE_MAXIMO("QTDE_ESTOQUE_MAXIMO","estmaximo",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Quantidade atual no estoque
	 */
	PROD_QTDE_ESTOQUE_ATUAL("QTDE_ESTOQUE_ATUAL","quant",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Data de cadastro
	 */
	PROD_DATA_CADASTRO("DATA_CADASTRO","cadastro",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Al�quota do pis
	 */
	PROD_ALIQUOTA_PIS("ALIQUOTA_PIS","pis",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Al�quota do cofins
	 */
	PROD_ALIQUOTA_COFINS("ALIQUOTA_COFINS","cofins",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Indica se o produto est� ativo ou n�o. A - Ativo, I - Inativo
	 */
	PROD_ATIVO("ATIVO","situacao",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * C�digo do NCM
	 */
	PROD_CODIGO_NCM("CODIGO_NCM","cfiscal",EnColumnsCategory.ESTOQUE),
	
	/**
	 * CSOSN de venda - 101,102,103,201,202,203,300,400,500 ou 900
	 */
	PROD_CSOSN_VENDA("CSOSN_VENDA","csosn",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Modalidade de venda - 1-Pauta 2-Pre�o tabelado m�ximo A-Valor bruto B-Valor l�quido
	 */
	PROD_MODALIDADE_VENDA("MODALIDADE_VENDA","pautavdtp",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Valor da pauta de venda
	 */
	PROD_VALOR_PAUTA_VENDA("VALOR_PAUTA_VENDA","pautavd",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Tributa��o de compra - F-Subs I-Isento N-N�o tributado D-Diferimento T-Tributado
	 */
	PROD_TRIBUTACAO_COMPRA("TRIBUTACAO_COMPRA","trbcompra",EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * C�digo da tributa��o - 1-Substitui��o 2-Isento 3-Tributado a 7% 4-Tributado a 12% 5-Tributado a 18% 6-Tributado a 25% 7-Tributado a 18% com substitui��o 8-Tributado a 12% com substitui��o
	 */
	PROD_CODIGO_TRIBUTACAO("CODIGO_TRIBUTACAO","tributa",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Valor do ICMS de compra
	 */
	PROD_VALOR_ICMS_COMPRA("VALOR_ICMS_COMPRA","icmcompra",EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Situa��o Tribut�ria de venda - 00,10,20,30,40,41,50,51,60,70 ou 90
	 */
	PROD_SITUACAO_TRIBUTARIA_VENDA("SITUACAO_TRIBUTARIA_VENDA","st",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Valor do ICMS de venda
	 */
	PROD_VALOR_ICMS_VENDA("VALOR_ICMS_VENDA","icmvenda",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Porcentagem de redu��o da base de c�lculo do ICMS de venda
	 */
	PROD_PORCENTAGEM_REDUCAO_ICMS("PORCENTAGEM_REDUCAO_ICMS","baseicmven",EnColumnsCategory.ESTOQUE_TRIBUTACAO),// gravar como 100 - <campo>
	
	/**
	 * Porcentagem de redu��o da base de c�lculo do ICMS com substitui��o
	 */
	PROD_PORCENTAGEM_REDUCAO_ICMS_SUBSTITUICAO("PORCENTAGEM_REDUCAO_ICMS_SUBSTITUICAO","baseicmsub",EnColumnsCategory.ESTOQUE_TRIBUTACAO),// gravar como 100 - <campo>
	
	/**
	 * Valor do ICMS com substitui��o
	 */
	PROD_VALOR_ICMS_SUBSTITUICAO("VALOR_ICMS_SUBSTITUICAO","icmsubs",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Estado da tributa��o
	 */
	PROD_UF_TRIBUTACAO("UF_TRIBUTACAO","uf",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       CLIENTES       |=========================================
	//=======================================================================================================
	/**
	 * C�digo do cliente
	 */
	CLI_CODIGO("CODIGO","codigo",EnColumnsCategory.CLIENTES),
	
	/**
	 * Nome fantasia
	 */
	CLI_NOME_FANTASIA("NOME_FANTASIA","nomfan",EnColumnsCategory.CLIENTES),
	
	/**
	 * Raz�o social
	 */
	CLI_RAZAO_SOCIAL("RAZAO_SOCIAL","razsoc",EnColumnsCategory.CLIENTES),
	
	/**
	 * CPF ou CNPJ
	 */
	CLI_CPF_CNPJ("CPF_CNPJ","cgc",EnColumnsCategory.CLIENTES),
	
	/**
	 * Inscri��o Estadual ou RG
	 */
	CLI_INSCRICAO_ESTADUAL_RG("INSCRICAO_ESTADUAL_RG","insest",EnColumnsCategory.CLIENTES),
	
	/**
	 * Sexo
	 */
	CLI_SEXO("SEXO","sexo",EnColumnsCategory.CLIENTES),
	
	/**
	 * Data de nascimento
	 */
	CLI_DATA_NASCIMENTO("DATA_NASCIMENTO","dtnasc",EnColumnsCategory.CLIENTES),
	
	/**
	 * Data de cadastro
	 */
	CLI_DATA_CADASTRO("DATA_CADASTRO","cadastro",EnColumnsCategory.CLIENTES),
	
	/**
	 * Estado em que reside
	 */
	CLI_ESTADO("ESTADO","estado",EnColumnsCategory.CLIENTES),
	
	/**
	 * Cidade em que reside
	 */
	CLI_CIDADE("CIDADE","munic",EnColumnsCategory.CLIENTES),
	
	/**
	 * Bairro em que reside
	 */
	CLI_BAIRRO("BAIRRO","bairro",EnColumnsCategory.CLIENTES),
	
	/**
	 * Rua em que reside
	 */
	CLI_RUA("RUA","ender",EnColumnsCategory.CLIENTES),
	
	/**
	 * CEP da rua em que reside
	 */
	CLI_CEP("CEP","cep",EnColumnsCategory.CLIENTES),
	
	/**
	 * N�mero da casa em que reside
	 */
	CLI_NUMERO("NUMERO","numero",EnColumnsCategory.CLIENTES),
	
	/**
	 * N�mero de telefone principal
	 */
	CLI_TELEFONE("TELEFONE","telefone",EnColumnsCategory.CLIENTES),
	
	/**
	 * Estado para cobran�a
	 */
	CLI_ESTADO_COBRANCA("ESTADO_COBRANCA","estadocob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Cidade para cobran�a
	 */
	CLI_CIDADE_COBRANCA("CIDADE_COBRANCA","municcob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Bairro para cobran�a
	 */
	CLI_BAIRRO_COBRANCA("BAIRRO_COBRANCA","baicob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Rua para cobran�a
	 */
	CLI_RUA_COBRANCA("RUA_COBRANCA","endecob",EnColumnsCategory.CLIENTES),
	
	/**
	 * CEP para cobran�a
	 */
	CLI_CEP_COBRANCA("CEP_COBRANCA","cepcob",EnColumnsCategory.CLIENTES),
	
	/**
	 * N�mero da casa para cobran�a
	 */
	CLI_NUMERO_COBRANCA("NUMERO_COBRANCA","numerocob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Telefone para cobran�a
	 */
	CLI_TELEFONE_COBRANCA("TELEFONE_COBRANCA","telcob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Estado para entrega
	 */
	CLI_ESTADO_ENTREGA("ESTADO_ENTREGA","estadoent",EnColumnsCategory.CLIENTES),
	
	/**
	 * Cidade para entrega
	 */
	CLI_CIDADE_ENTREGA("CIDADE_ENTREGA","municent",EnColumnsCategory.CLIENTES),
	
	/**
	 * Bairro para entrega
	 */
	CLI_BAIRRO_ENTREGA("BAIRRO_ENTREGA","baient",EnColumnsCategory.CLIENTES),
	
	/**
	 * Rua para entrega
	 */
	CLI_RUA_ENTREGA("RUA_ENTREGA","endeent",EnColumnsCategory.CLIENTES),
	
	/**
	 * CEP para entrega
	 */
	CLI_CEP_ENTREGA("CEP_ENTREGA","cepent",EnColumnsCategory.CLIENTES),
	
	/**
	 * N�mero da casa para entrega
	 */
	CLI_NUMERO_ENTREGA("NUMERO_ENTREGA","numeroent",EnColumnsCategory.CLIENTES),
	
	/**
	 * Telefone para entrega
	 */
	CLI_TELEFONE_ENTREGA("TELEFONE_ENTREGA","telent",EnColumnsCategory.CLIENTES),
	
	/**
	 * Indica se o cliente est� ativo ou n�o
	 */
	CLI_ATIVO("ATIVO","situacao",EnColumnsCategory.CLIENTES),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|     FORNECEDORES     |=========================================
	//=======================================================================================================
	/**
	 * C�digo do fornecedor
	 */
	FOR_CODIGO("CODIGO","codigo",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Nome fantasia
	 */
	FOR_NOME_FANTASIA("NOME_FANTASIA","nomfan",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Raz�o social
	 */
	FOR_RAZAO_SOCIAL("RAZAO_SOCIAL","razsoc",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * CPF ou CNPJ
	 */
	FOR_CPF_CNPJ("CPF_CNPJ","cgc",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Inscri��o Estadual ou RG
	 */
	FOR_INSCRICAO_ESTADUAL_RG("INSCRICAO_ESTADUAL_RG","insest",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Estado onde est� localizado
	 */
	FOR_ESTADO("ESTADO","estado",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Cidade onde est� localizado
	 */
	FOR_CIDADE("CIDADE","munic",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Bairro onde est� localizado
	 */
	FOR_BAIRRO("BAIRRO","bairro",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Rua onde est� localizado
	 */
	FOR_RUA("RUA","ender",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * CEP da rua onde est� localizado
	 */
	FOR_CEP("CEP","cep",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * N�mero de onde est� localizado
	 */
	FOR_NUMERO("NUMERO","numero",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Telefone principal
	 */
	FOR_TELEFONE("TELEFONE","telefone",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Indica se est� ativo ou inativo
	 */
	FOR_ATIVO("ATIVO","situacao",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Nome do contato
	 */
	FOR_CONTATO("CONTATO","contato",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Endere�o principal de e-mail
	 */
	FOR_EMAIL("EMAIL","email",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Endere�o de e-mail para o boleto
	 */
	FOR_EMAIL_BOLETO("EMAIL_BOLETO","emailboleto",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Endere�o de e-mail para a DANFE
	 */
	FOR_EMAIL_DANFE("EMAIL_DANFE","emaildanfe",EnColumnsCategory.FORNECEDOR),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|     DEPARTAMENTOS    |=========================================
	//=======================================================================================================
	/**
	 * C�digo do departamento
	 */
	DEP_CODIGO("CODIGO","codigo",EnColumnsCategory.DEPARTAMENTO),
	
	/**
	 * Descri��o do departamento
	 */
	DEP_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.DEPARTAMENTO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|        GRUPOS        |=========================================
	//=======================================================================================================
	/**
	 * C�digo do grupo
	 */
	GRU_CODIGO("CODIGO","codigo",EnColumnsCategory.GRUPO),
	
	/**
	 * Descri��o do grupo
	 */
	GRU_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.GRUPO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       SETORES        |=========================================
	//=======================================================================================================
	/**
	 * C�digo do setor
	 */
	SET_CODIGO("CODIGO","codigo",EnColumnsCategory.SETOR),
	
	/**
	 * Descri��o do setor
	 */
	SET_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.SETOR),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       ARMA��ES       |=========================================
	//=======================================================================================================
	/**
	 * C�digo da armacao
	 */
	ARM_CODIGO("CODIGO","codigo",EnColumnsCategory.ARMACAO),
	
	/**
	 * Descri��o da armacao
	 */
	ARM_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.ARMACAO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|        MARCAS        |=========================================
	//=======================================================================================================
	/**
	 * C�digo da marca
	 */
	MAR_CODIGO("CODIGO","codigo",EnColumnsCategory.MARCA),
	
	/**
	 * Descri��o da marca
	 */
	MAR_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.MARCA);
	//=======================================================================================================
	
	/**
	 * descri��o do campo
	 */
	private final String label;
	
	/**
	 * Nome da coluna no banco de dados
	 */
	private final String columnName;
	
	/**
	 * Tabelas (categorias) a que a coluna pertence
	 */
	private final EnColumnsCategory[] categories;
	
	/**
	 * Cria nova constante enum passando a descri��o do campo, o nome da coluna no banco de dados e as categorias que ela pertence
	 * 
	 * @param label - Descri��o, nome que aparece no arquivo de dados
	 * @param columnName - nome da coluna no banco de dados
	 * @param categories - categorias que a constante pertence
	 */
	private EnMercoFlexRequiredColumns(String label, String columnName, EnColumnsCategory... categories){
		
		this.label = label;
		this.columnName = columnName;
		this.categories = categories;
		
	}
	
	/**
	 * Retorna a descri��o, nome presente no arquivo
	 * 
	 * @return - descri��o, nome presente no arquivo
	 */
	public String getLabel(){
		
		return label;
		
	}
	
	/**
	 * Retorna o nome da coluna no banco de dados
	 * 
	 * @return - nome da coluna no banco de dados
	 */
	public String getColumnName(){
		
		return columnName;
		
	}
	
	/**
	 * Retorna as categorias que a constante enum pertence
	 * 
	 * @return - categorias que a constante enum pertence
	 */
	public EnColumnsCategory[] getCategories(){
		
		return this.categories;
		
	}
	
	/**
	 * Aprimoramento do m�todo values(), este retorna todos as constantes de acordo com as categorias informadas
	 * 
	 * @param categories - categorias para filtrar
	 * @return - todas as constantes filtradas
	 */
	public static EnMercoFlexRequiredColumns[] filterValues(
			EnColumnsCategory... categories) {

		int i = 0;

		EnMercoFlexRequiredColumns[] en = values();

		LOOP_COLUMNS: for (EnMercoFlexRequiredColumns en2 : en) {
			EnColumnsCategory[] enc = en2.getCategories();
			for (EnColumnsCategory enc2 : enc) {
				for (EnColumnsCategory enc3 : categories) {
					if (enc2 == enc3) {
						i++;
						continue LOOP_COLUMNS;
					}
				}
			}
		}

		EnMercoFlexRequiredColumns[] toReturn = new EnMercoFlexRequiredColumns[i];

		i = 0;

		LOOP_COLUMNS: for (EnMercoFlexRequiredColumns en2 : en) {
			EnColumnsCategory[] enc = en2.getCategories();
			for (EnColumnsCategory enc2 : enc) {
				for (EnColumnsCategory enc3 : categories) {
					if (enc2 == enc3) {
						toReturn[i] = en2;
						i++;
						continue LOOP_COLUMNS;
					}
				}
			}
		}
		
		return toReturn;

	}
	
}
