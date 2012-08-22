package br.com.gz.migration.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Classe que trabalha com os logs de opera��o
 * 
 * @author Jonathan Sansalone
 *
 */
public class LogFile {

	/**
	 * Inst�ncia static da classe
	 */
	private static LogFile instance;
	
	/**
	 * Arquivo de log
	 */
	private File logFile;

	/**
	 * Construtor default
	 */
	private LogFile() {

		Calendar c = Calendar.getInstance();

		String name = fillZero(c.get(Calendar.YEAR))
				+ fillZero(c.get(Calendar.MONTH)+1)
				+ fillZero(c.get(Calendar.DAY_OF_MONTH))
				+ fillZero(c.get(Calendar.HOUR_OF_DAY))
				+ fillZero(c.get(Calendar.MINUTE))
				+ fillZero(c.get(Calendar.SECOND));

		File root = new File("log");
		root.mkdir();

		logFile = new File(root, name+".log");

		try {
			logFile.createNewFile();
			writeHeader();
		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * M�todo que segue o padr�o singleton para garantir a grava��o dos logs em um �nico arquivo durante a execu��o do aplicativo
	 * @return - uma �nica inst�ncia da classe
	 */
	public static LogFile getInstance() {

		if (instance == null) {

			instance = new LogFile();

		}

		return instance;

	}

	/**
	 * M�todo que grava uma mensagem de log no arquivo
	 * 
	 * @param message - mensagem de log
	 */
	public synchronized void writeInFile(String message) {

		Calendar c = Calendar.getInstance();

		String time = "[" + fillZero(c.get(Calendar.HOUR_OF_DAY)) + ":"
				+ fillZero(c.get(Calendar.MINUTE)) + ":"
				+ fillZero(c.get(Calendar.SECOND)) + "]";

		try {

			BufferedWriter pr = new BufferedWriter(new FileWriter(logFile, true));
			pr.write(time + " " + message + System.getProperty("line.separator"));
			pr.close();

		} catch (FileNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * M�todo que grava o header do arquivo
	 */
	private void writeHeader() {

		try {

			BufferedWriter pr = new BufferedWriter(new FileWriter(logFile, true));

			pr.write("=============================================================================="
					+ System.getProperty("line.separator"));
			pr.write("================================ GZ SISTEMAS ================================="
					+ System.getProperty("line.separator"));
			pr.write("======================== MIGRA��O DE BANCOS DE DADOS ========================="
					+ System.getProperty("line.separator"));
			pr.write("=============================================================================="
					+ System.getProperty("line.separator"));
			pr.write(System.getProperty("line.separator"));
			pr.close();

		} catch (FileNotFoundException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * M�todo que preenche com 0 n�meros menores que 10. Usado para data
	 * 
	 * @param num - n�mero
	 * @return - o n�mero com 0 na frente se for menor que 10
	 */
	private String fillZero(int num) {

		return num < 10 ? ("0" + num) : ("" + num);

	}

}
