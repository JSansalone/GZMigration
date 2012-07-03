package br.com.gz.migration;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.gz.migration.file.LogFile;

/**
 * Fornece as mensagens exibidas pela classe MigrationEngine
 * 
 * @author Jonathan Sansalone
 * 
 */
class MigrationEngineMessages {

	/**
	 * Mostra mensagem de erro de arquivo n�o encontrado
	 * 
	 * @param type
	 *            - tipo de dado
	 */
	static void showFileNotFoundErrorMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing error message");

		JOptionPane.showMessageDialog(null, "O arquivo " + type.toString()
				+ ".xls n�o foi encontrado!", "Arquivo n�o encontrado",
				JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * Mostra mensagem de erro gen�rica
	 * 
	 * @param type
	 *            - tipo de dado
	 */
	static void showErrorMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing error message");

		switch (type) {

		case ARMACAO:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar as arma��es. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar os clientes. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar as contas a pagar. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar as contas a receber. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar os departamento. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar os fornecedores. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar os grupos. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar as marcas. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar as movimenta��es de vendas. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar as notas fiscais de entrada. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar as notas fiscais de sa�da. Elas n�o ser�o migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar os produtos. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel ler o arquivo para coletar os setores. Eles n�o ser�o migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null, "Erro desconhecido.", "Erro",
					JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

	/**
	 * Mostra mensagem de viola��o de colunas
	 * 
	 * @param type
	 *            - Tipo de dado
	 * @param columnsNeeded
	 *            - colunas que faltam
	 */
	static void showPolicyViolationMessage(EnMigrationDataType type,
			ArrayList<String> columnsNeeded) {

		LogFile.getInstance().writeInFile("Showing policy violation message");

		switch (type) {

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

}
