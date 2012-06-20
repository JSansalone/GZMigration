package br.com.gz.migration.policy;

/**
 * Contém as colunas obrigatórias do MercoFlex
 * 
 * @author Jonathan
 *
 */
public enum EnMercoFlexRequiredColumns {

	//=======================================================================================================
	//======================================|       PRODUTOS       |=========================================
	//=======================================================================================================
	/**
	 * Código da loja
	 */
	PROD_LOJA("LOJA","loja",EnColumnsCategory.ESTOQUE_LOJA,EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Código interno
	 */
	PROD_CODIGO_INTERNO("CODIGO_INTERNO","cdprod",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_LOJA,EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Código de barras
	 */
	PROD_CODIGO_DE_BARRAS("CODIGO_DE_BARRAS","codbarra",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Descrição
	 */
	PROD_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Descrição reduzida, geralmente utilizada no PDV
	 */
	PROD_DESCRICAO_REDUZIDA("DESCRICAO_REDUZIDA","descpdv",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Unidade (KG,UN,etc)
	 */
	PROD_UNIDADE("UNIDADE","unidade",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Código do setor
	 */
	PROD_CODIGO_SETOR("CODIGO_SETOR","setor",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Indica se o produto possui peso variável. N - Não, S - Sim
	 */
	PROD_TEM_PESO_VARIAVEL("TEM_PESO_VARIAVEL","variavel",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Preço de compra
	 */
	PROD_PRECO_COMPRA("PRECO_COMPRA","precocomp",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Preço de venda
	 */
	PROD_PRECO_VENDA("PRECO_VENDA","precovenda",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Código do departamento
	 */
	PROD_CODIGO_DEPARTAMENTO("CODIGO_DEPARTAMENTO","depto",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Código do grupo
	 */
	PROD_CODIGO_GRUPO("CODIGO_GRUPO","grupo",EnColumnsCategory.ESTOQUE),
	
	/**
	 * Preço de custo
	 */
	PROD_PRECO_CUSTO("PRECO_CUSTO","precocusto",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Margem de lucro
	 */
	PROD_PORCENTAGEM_DE_LUCRO("PORCENTAGEM_DE_LUCRO","perclucro",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Quantidade mínima em estoque
	 */
	PROD_QTDE_ESTOQUE_MINIMO("QTDE_ESTOQUE_MINIMO","estminimo",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Quantidade máxima em estoque
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
	 * Alíquota do pis
	 */
	PROD_ALIQUOTA_PIS("ALIQUOTA_PIS","pis",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Alíquota do cofins
	 */
	PROD_ALIQUOTA_COFINS("ALIQUOTA_COFINS","cofins",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Indica se o produto está ativo ou não. A - Ativo, I - Inativo
	 */
	PROD_ATIVO("ATIVO","situacao",EnColumnsCategory.ESTOQUE_SALDO),
	
	/**
	 * Código do NCM
	 */
	PROD_CODIGO_NCM("CODIGO_NCM","cfiscal",EnColumnsCategory.ESTOQUE),
	
	/**
	 * CSOSN de venda - 101,102,103,201,202,203,300,400,500 ou 900
	 */
	PROD_CSOSN_VENDA("CSOSN_VENDA","csosn",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Modalidade de venda - 1-Pauta 2-Preço tabelado máximo A-Valor bruto B-Valor líquido
	 */
	PROD_MODALIDADE_VENDA("MODALIDADE_VENDA","pautavdtp",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Valor da pauta de venda
	 */
	PROD_VALOR_PAUTA_VENDA("VALOR_PAUTA_VENDA","pautavd",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Tributação de compra - F-Subs I-Isento N-Não tributado D-Diferimento T-Tributado
	 */
	PROD_TRIBUTACAO_COMPRA("TRIBUTACAO_COMPRA","trbcompra",EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Código da tributação - 1-Substituição 2-Isento 3-Tributado a 7% 4-Tributado a 12% 5-Tributado a 18% 6-Tributado a 25% 7-Tributado a 18% com substituição 8-Tributado a 12% com substituição
	 */
	PROD_CODIGO_TRIBUTACAO("CODIGO_TRIBUTACAO","tributa",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Valor do ICMS de compra
	 */
	PROD_VALOR_ICMS_COMPRA("VALOR_ICMS_COMPRA","icmcompra",EnColumnsCategory.ESTOQUE_SALDO,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Situação Tributária de venda - 00,10,20,30,40,41,50,51,60,70 ou 90
	 */
	PROD_SITUACAO_TRIBUTARIA_VENDA("SITUACAO_TRIBUTARIA_VENDA","st",EnColumnsCategory.ESTOQUE,EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Valor do ICMS de venda
	 */
	PROD_VALOR_ICMS_VENDA("VALOR_ICMS_VENDA","icmvenda",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Porcentagem de redução da base de cálculo do ICMS de venda
	 */
	PROD_PORCENTAGEM_REDUCAO_ICMS("PORCENTAGEM_REDUCAO_ICMS","baseicmven",EnColumnsCategory.ESTOQUE_TRIBUTACAO),// gravar como 100 - <campo>
	
	/**
	 * Porcentagem de redução da base de cálculo do ICMS com substituição
	 */
	PROD_PORCENTAGEM_REDUCAO_ICMS_SUBSTITUICAO("PORCENTAGEM_REDUCAO_ICMS_SUBSTITUICAO","baseicmsub",EnColumnsCategory.ESTOQUE_TRIBUTACAO),// gravar como 100 - <campo>
	
	/**
	 * Valor do ICMS com substituição
	 */
	PROD_VALOR_ICMS_SUBSTITUICAO("VALOR_ICMS_SUBSTITUICAO","icmsubs",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	
	/**
	 * Estado da tributação
	 */
	PROD_UF_TRIBUTACAO("UF_TRIBUTACAO","uf",EnColumnsCategory.ESTOQUE_TRIBUTACAO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       CLIENTES       |=========================================
	//=======================================================================================================
	/**
	 * Código do cliente
	 */
	CLI_CODIGO("CODIGO","codigo",EnColumnsCategory.CLIENTES),
	
	/**
	 * Nome fantasia
	 */
	CLI_NOME_FANTASIA("NOME_FANTASIA","nomfan",EnColumnsCategory.CLIENTES),
	
	/**
	 * Razão social
	 */
	CLI_RAZAO_SOCIAL("RAZAO_SOCIAL","razsoc",EnColumnsCategory.CLIENTES),
	
	/**
	 * CPF ou CNPJ
	 */
	CLI_CPF_CNPJ("CPF_CNPJ","cgc",EnColumnsCategory.CLIENTES),
	
	/**
	 * Inscrição Estadual ou RG
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
	 * Número da casa em que reside
	 */
	CLI_NUMERO("NUMERO","numero",EnColumnsCategory.CLIENTES),
	
	/**
	 * Número de telefone principal
	 */
	CLI_TELEFONE("TELEFONE","telefone",EnColumnsCategory.CLIENTES),
	
	/**
	 * Estado para cobrança
	 */
	CLI_ESTADO_COBRANCA("ESTADO_COBRANCA","estadocob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Cidade para cobrança
	 */
	CLI_CIDADE_COBRANCA("CIDADE_COBRANCA","municcob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Bairro para cobrança
	 */
	CLI_BAIRRO_COBRANCA("BAIRRO_COBRANCA","baicob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Rua para cobrança
	 */
	CLI_RUA_COBRANCA("RUA_COBRANCA","endecob",EnColumnsCategory.CLIENTES),
	
	/**
	 * CEP para cobrança
	 */
	CLI_CEP_COBRANCA("CEP_COBRANCA","cepcob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Número da casa para cobrança
	 */
	CLI_NUMERO_COBRANCA("NUMERO_COBRANCA","numerocob",EnColumnsCategory.CLIENTES),
	
	/**
	 * Telefone para cobrança
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
	 * Número da casa para entrega
	 */
	CLI_NUMERO_ENTREGA("NUMERO_ENTREGA","numeroent",EnColumnsCategory.CLIENTES),
	
	/**
	 * Telefone para entrega
	 */
	CLI_TELEFONE_ENTREGA("TELEFONE_ENTREGA","telent",EnColumnsCategory.CLIENTES),
	
	/**
	 * Indica se o cliente está ativo ou não
	 */
	CLI_ATIVO("ATIVO","situacao",EnColumnsCategory.CLIENTES),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|     FORNECEDORES     |=========================================
	//=======================================================================================================
	/**
	 * Código do fornecedor
	 */
	FOR_CODIGO("CODIGO","codigo",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Nome fantasia
	 */
	FOR_NOME_FANTASIA("NOME_FANTASIA","nomfan",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Razão social
	 */
	FOR_RAZAO_SOCIAL("RAZAO_SOCIAL","razsoc",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * CPF ou CNPJ
	 */
	FOR_CPF_CNPJ("CPF_CNPJ","cgc",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Inscrição Estadual ou RG
	 */
	FOR_INSCRICAO_ESTADUAL_RG("INSCRICAO_ESTADUAL_RG","insest",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Estado onde está localizado
	 */
	FOR_ESTADO("ESTADO","estado",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Cidade onde está localizado
	 */
	FOR_CIDADE("CIDADE","munic",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Bairro onde está localizado
	 */
	FOR_BAIRRO("BAIRRO","bairro",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Rua onde está localizado
	 */
	FOR_RUA("RUA","ender",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * CEP da rua onde está localizado
	 */
	FOR_CEP("CEP","cep",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Número de onde está localizado
	 */
	FOR_NUMERO("NUMERO","numero",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Telefone principal
	 */
	FOR_TELEFONE("TELEFONE","telefone",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Indica se está ativo ou inativo
	 */
	FOR_ATIVO("ATIVO","situacao",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Nome do contato
	 */
	FOR_CONTATO("CONTATO","contato",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Endereço principal de e-mail
	 */
	FOR_EMAIL("EMAIL","email",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Endereço de e-mail para o boleto
	 */
	FOR_EMAIL_BOLETO("EMAIL_BOLETO","emailboleto",EnColumnsCategory.FORNECEDOR),
	
	/**
	 * Endereço de e-mail para a DANFE
	 */
	FOR_EMAIL_DANFE("EMAIL_DANFE","emaildanfe",EnColumnsCategory.FORNECEDOR),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|     DEPARTAMENTOS    |=========================================
	//=======================================================================================================
	/**
	 * Código do departamento
	 */
	DEP_CODIGO("CODIGO","codigo",EnColumnsCategory.DEPARTAMENTO),
	
	/**
	 * Descrição do departamento
	 */
	DEP_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.DEPARTAMENTO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|        GRUPOS        |=========================================
	//=======================================================================================================
	/**
	 * Código do grupo
	 */
	GRU_CODIGO("CODIGO","codigo",EnColumnsCategory.GRUPO),
	
	/**
	 * Descrição do grupo
	 */
	GRU_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.GRUPO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       SETORES        |=========================================
	//=======================================================================================================
	/**
	 * Código do setor
	 */
	SET_CODIGO("CODIGO","codigo",EnColumnsCategory.SETOR),
	
	/**
	 * Descrição do setor
	 */
	SET_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.SETOR),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|       ARMAÇÕES       |=========================================
	//=======================================================================================================
	/**
	 * Código da armacao
	 */
	ARM_CODIGO("CODIGO","codigo",EnColumnsCategory.ARMACAO),
	
	/**
	 * Descrição da armacao
	 */
	ARM_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.ARMACAO),
	//=======================================================================================================
	
	//=======================================================================================================
	//======================================|        MARCAS        |=========================================
	//=======================================================================================================
	/**
	 * Código da marca
	 */
	MAR_CODIGO("CODIGO","codigo",EnColumnsCategory.MARCA),
	
	/**
	 * Descrição da marca
	 */
	MAR_DESCRICAO("DESCRICAO","descricao",EnColumnsCategory.MARCA);
	//=======================================================================================================
	
	/**
	 * descrição do campo
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
	 * Cria nova constante enum passando a descrição do campo, o nome da coluna no banco de dados e as categorias que ela pertence
	 * 
	 * @param label - Descrição, nome que aparece no arquivo de dados
	 * @param columnName - nome da coluna no banco de dados
	 * @param categories - categorias que a constante pertence
	 */
	private EnMercoFlexRequiredColumns(String label, String columnName, EnColumnsCategory... categories){
		
		this.label = label;
		this.columnName = columnName;
		this.categories = categories;
		
	}
	
	/**
	 * Retorna a descrição, nome presente no arquivo
	 * 
	 * @return - descrição, nome presente no arquivo
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
	 * Aprimoramento do método values(), este retorna todos as constantes de acordo com as categorias informadas
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
