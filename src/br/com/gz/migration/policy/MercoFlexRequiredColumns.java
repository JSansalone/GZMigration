package br.com.gz.migration.policy;


final class MercoFlexRequiredColumns {

	public static final String[] PRODUTO = new String[] { 
		"cdprod", 
		"codbarra",
		"descricao", 
		"descpdv", 
		"icms", 
		"unidade", 
		"setor", 
		"variavel",
		"precocomp", 
		"precovenda", 
		"depto",
		"grupo",
		"precocusto", 
		"perclucro",
		"estminimo", 
		"estmaximo", 
		"quant", 
		"cadastro", 
		"pis", 
		"cofins",
		"situacao", 
		"cfiscal", 
		"trbcompra", 
		"tributa", 
		"icmcompra" 
	};

	public static final String[] CLIENTE = new String[] { 
		"codigo", 
		"cgc",
		"insest", 
		"dtnasc", 
		"situacao", 
		"ender", 
		"bairro", 
		"numero",
		"munic", 
		"estado", 
		"cep", 
		"telefone", 
		"razsoc", 
		"nomfan" 
	};

	public static final String[] FORNECEDOR = new String[] { 
		"codigo", 
		"cgc",
		"insest", 
		"situacao", 
		"ender", 
		"bairro", 
		"numero",
		"munic", 
		"estado", 
		"cep", 
		"telefone", 
		"razsoc", 
		"nomfan" 
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
