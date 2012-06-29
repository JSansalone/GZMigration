package br.com.gz.migration.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe que trabalha com os arquivos de instru��es SQL
 * 
 * @author Jonathan Sansalone
 *
 */
@Deprecated
public class DefaultDirectoryStructure {

	/**
	 * M�todo que cria os arquivos
	 */
	public void createDefaultStructure() {

		File root = new File("data");

		if (!root.exists()) {

			if (root.mkdir()) {

				

			}

		}

	}

	/**
	 * m�todo que grava dados em um arquivo
	 * 
	 * @param f - arquivo
	 * @param text - texto
	 * @return - true se gravar com sucesso, false caso contr�rio
	 */
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

	/**
	 * M�todo que cria um arquivo
	 * 
	 * @param f - arquivo
	 * @return - true se criar com sucesso, false caso contr�rio
	 */
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
