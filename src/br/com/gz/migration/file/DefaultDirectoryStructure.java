package br.com.gz.migration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import br.com.gz.migration.sql.DefaultSQLSelectStatements;

public class DefaultDirectoryStructure {

	public void createDefaultStructure() {

		File root = new File("data");
		File fVersatho;
		File fSuperus;
		File fAES;
		File fMRS;
		File fOther;

		if (!root.exists()) {

			if (root.mkdir()) {

				fVersatho = new File(root, "versatho");
				if (fVersatho.mkdir()) {
					File f;
					f = new File(fVersatho, "getProduto.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_PRODUTO);
					}
					f = new File(fVersatho, "getCliente.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_CLIENTE);
					}
					f = new File(fVersatho, "getFornecedor.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_FORNECEDOR);
					}
					f = new File(fVersatho, "getDepartamento.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_DEPARTAMENTO);
					}
					f = new File(fVersatho, "getArmacao.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_ARMACAO);
					}
					f = new File(fVersatho, "getMarca.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_MARCA);
					}
					f = new File(fVersatho, "getGrupo.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_GRUPO);
					}
					f = new File(fVersatho, "getContaPagar.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_CONTA_PAGAR);
					}
					f = new File(fVersatho, "getContaReceber.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_CONTA_RECEBER);
					}
					f = new File(fVersatho, "getNFEntrada.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_NF_ENTRADA);
					}
					f = new File(fVersatho, "getNFSaida.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_NF_SAIDA);
					}
					f = new File(fVersatho, "getMovtoVenda.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.VERSATHO_SELECT_MOVTO_VENDA);
					}

				}

				fSuperus = new File(root, "superus");
				if (fSuperus.mkdir()) {
					File f;
					f = new File(fSuperus, "getProduto.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_PRODUTO);
					}
					f = new File(fSuperus, "getCliente.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_CLIENTE);
					}
					f = new File(fSuperus, "getFornecedor.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_FORNECEDOR);
					}
					f = new File(fSuperus, "getDepartamento.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_DEPARTAMENTO);
					}
					f = new File(fSuperus, "getArmacao.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_ARMACAO);
					}
					f = new File(fSuperus, "getMarca.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_MARCA);
					}
					f = new File(fSuperus, "getGrupo.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_GRUPO);
					}
					f = new File(fSuperus, "getContaPagar.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_CONTA_PAGAR);
					}
					f = new File(fSuperus, "getContaReceber.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_CONTA_RECEBER);
					}
					f = new File(fSuperus, "getNFEntrada.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_NF_ENTRADA);
					}
					f = new File(fSuperus, "getNFSaida.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_NF_SAIDA);
					}
					f = new File(fSuperus, "getMovtoVenda.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.SUPERUS_SELECT_MOVTO_VENDA);
					}

				}

				fAES = new File(root, "aes");
				if (fAES.mkdir()) {
					File f;
					f = new File(fAES, "getProduto.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.AES_SELECT_PRODUTO);
					}
					f = new File(fAES, "getCliente.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.AES_SELECT_CLIENTE);
					}
					f = new File(fAES, "getFornecedor.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.AES_SELECT_FORNECEDOR);
					}
					f = new File(fAES, "getDepartamento.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.AES_SELECT_DEPARTAMENTO);
					}
					f = new File(fAES, "getArmacao.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.AES_SELECT_ARMACAO);
					}
					f = new File(fAES, "getMarca.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.AES_SELECT_MARCA);
					}
					f = new File(fAES, "getGrupo.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.AES_SELECT_GRUPO);
					}
					f = new File(fAES, "getContaPagar.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.AES_SELECT_CONTA_PAGAR);
					}
					f = new File(fAES, "getContaReceber.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.AES_SELECT_CONTA_RECEBER);
					}
					f = new File(fAES, "getNFEntrada.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.AES_SELECT_NF_ENTRADA);
					}
					f = new File(fAES, "getNFSaida.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.AES_SELECT_NF_SAIDA);
					}
					f = new File(fAES, "getMovtoVenda.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.AES_SELECT_MOVTO_VENDA);
					}

				}

				fMRS = new File(root, "mrs");
				if (fMRS.mkdir()) {
					File f;
					f = new File(fMRS, "getProduto.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.MRS_SELECT_PRODUTO);
					}
					f = new File(fMRS, "getCliente.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.MRS_SELECT_CLIENTE);
					}
					f = new File(fMRS, "getFornecedor.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.MRS_SELECT_FORNECEDOR);
					}
					f = new File(fMRS, "getDepartamento.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.MRS_SELECT_DEPARTAMENTO);
					}
					f = new File(fMRS, "getArmacao.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.MRS_SELECT_ARMACAO);
					}
					f = new File(fMRS, "getMarca.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.MRS_SELECT_MARCA);
					}
					f = new File(fMRS, "getGrupo.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.MRS_SELECT_GRUPO);
					}
					f = new File(fMRS, "getContaPagar.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.MRS_SELECT_CONTA_PAGAR);
					}
					f = new File(fMRS, "getContaReceber.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.MRS_SELECT_CONTA_RECEBER);
					}
					f = new File(fMRS, "getNFEntrada.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.MRS_SELECT_NF_ENTRADA);
					}
					f = new File(fMRS, "getNFSaida.txt");
					if (tryToCreate(f)) {
						write(f, DefaultSQLSelectStatements.MRS_SELECT_NF_SAIDA);
					}
					f = new File(fMRS, "getMovtoVenda.txt");
					if (tryToCreate(f)) {
						write(f,
								DefaultSQLSelectStatements.MRS_SELECT_MOVTO_VENDA);
					}

				}

				fOther = new File(root, "outro");
				if (fOther.mkdir()) {
					File f;
					f = new File(fOther, "getProduto.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getCliente.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getFornecedor.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getDepartamento.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getArmacao.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getMarca.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getGrupo.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getContaPagar.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getContaReceber.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getNFEntrada.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getNFSaida.txt");
					if (tryToCreate(f)) {
					}
					f = new File(fOther, "getMovtoVenda.txt");
					if (tryToCreate(f)) {
					}

				}

			}

		}

	}

	private boolean write(File f, String text) {

		try {

			PrintWriter pr = new PrintWriter(f);

			text = text.replace("\n", System.getProperty("line.separator"));

			pr.write(text);
			pr.close();

			return true;

		} catch (FileNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	private boolean tryToCreate(File f) {

		try {

			f.createNewFile();

			return true;

		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

}
