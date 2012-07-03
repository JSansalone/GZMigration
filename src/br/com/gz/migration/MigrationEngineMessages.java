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
	 * Mostra mensagem de erro de arquivo não encontrado
	 * 
	 * @param type
	 *            - tipo de dado
	 */
	static void showFileNotFoundErrorMessage(EnMigrationDataType type) {

		LogFile.getInstance().writeInFile("Showing error message");

		JOptionPane.showMessageDialog(null, "O arquivo " + type.toString()
				+ ".xls não foi encontrado!", "Arquivo não encontrado",
				JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * Mostra mensagem de erro genérica
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
							"Não foi possível ler o arquivo para coletar as armações. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar os clientes. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar as contas a pagar. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar as contas a receber. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar os departamento. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar os fornecedores. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar os grupos. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar as marcas. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar as movimentações de vendas. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar as notas fiscais de entrada. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar as notas fiscais de saída. Elas não serão migradas.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar os produtos. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane
					.showMessageDialog(
							null,
							"Não foi possível ler o arquivo para coletar os setores. Eles não serão migrados.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null, "Erro desconhecido.", "Erro",
					JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

	/**
	 * Mostra mensagem de violação de colunas
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
					"Migração de armações não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CLIENTE:

			JOptionPane.showMessageDialog(null,
					"Migração de clientes não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CONTAPAGAR:

			JOptionPane.showMessageDialog(null,
					"Migração de contas a pagar não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case CONTARECEBER:

			JOptionPane.showMessageDialog(null,
					"Migração de contas a receber não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case DEPARTAMENTO:

			JOptionPane.showMessageDialog(null,
					"Migração de departamentos não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case FORNECEDOR:

			JOptionPane.showMessageDialog(null,
					"Migração de fornecedores não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case GRUPO:

			JOptionPane.showMessageDialog(null,
					"Migração de grupos não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case MARCA:

			JOptionPane.showMessageDialog(null,
					"Migração de marcas não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case MOVTOVENDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de movimentações de venda não permitida! \nColuna(s) não encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Violação de política", JOptionPane.ERROR_MESSAGE);

			break;

		case NFENTRADA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de notas fiscais de entrada não permitida! \nColuna(s) não encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Violação de política", JOptionPane.ERROR_MESSAGE);

			break;

		case NFSAIDA:

			JOptionPane
					.showMessageDialog(
							null,
							"Migração de notas fiscais de saída não permitida! \nColuna(s) não encontrada(s):\n"
									+ columnsNeeded.toString().replace("[", "")
											.replace("]", ""),
							"Violação de política", JOptionPane.ERROR_MESSAGE);

			break;

		case PRODUTO:

			JOptionPane.showMessageDialog(null,
					"Migração de produtos não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		case SETOR:

			JOptionPane.showMessageDialog(null,
					"Migração de setores não permitida! \nColuna(s) não encontrada(s):\n"
							+ columnsNeeded.toString().replace("[", "")
									.replace("]", ""), "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		default:

			JOptionPane.showMessageDialog(null,
					"Erro desconhecido de migração.", "Violação de política",
					JOptionPane.ERROR_MESSAGE);

			break;

		}

	}

}
