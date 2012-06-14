package br.com.gz.migration.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class LogFile {

	private static LogFile instance;
	private File logFile;

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

	public static LogFile getInstance() {

		if (instance == null) {

			instance = new LogFile();

		}

		return instance;

	}

	public void writeInFile(String message) {

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

	private void writeHeader() {

		try {

			BufferedWriter pr = new BufferedWriter(new FileWriter(logFile, true));

			pr.write("=============================================================================="
					+ System.getProperty("line.separator"));
			pr.write("================================ GZ SISTEMAS ================================="
					+ System.getProperty("line.separator"));
			pr.write("======================== MIGRAÇÃO DE BANCOS DE DADOS ========================="
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

	private String fillZero(int num) {

		return num < 10 ? ("0" + num) : ("" + num);

	}

}
