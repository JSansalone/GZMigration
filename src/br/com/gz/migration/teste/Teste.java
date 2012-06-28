package br.com.gz.migration.teste;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Teste {

	public static void main(String[] args) {

		new Teste();

	}

	public Teste() {

		Migracao mp = new Migracao("Produto", 123, 14);
		Migracao mc = new Migracao("Cliente", 321, 0);

		Collection<Migracao> col = new ArrayList<Migracao>();

		col.add(mc);
		col.add(mp);

		JRBeanCollectionDataSource bclds = new JRBeanCollectionDataSource(col);

		java.awt.Image im = new ImageIcon((URL) getClass().getResource(
				"/img/report/header.gif")).getImage();

		Map parameters = new HashMap();
		parameters.put("logoHeader", im);
		parameters.put("nomeCliente", "Aqui vai o nome do cliente");
		parameters.put("modoMigracao", "Sobrescrever");
		parameters.put("nomeSistema", "MercoFlex");
		parameters.put("tipoBanco", "MySQL");
		parameters.put("ip", "192.168.1.15");
		parameters.put("porta", "3306");
		parameters.put("nomeBanco", "mercodb");
		parameters.put("usuario", "root");
		parameters.put("senha", "mestre");

		// preenche relatorio com parâmetros e datasource
		JasperPrint printer;
		try {
			printer = JasperFillManager.fillReport(
					"report/MigrationReport.jasper", parameters, bclds);
			// abre visualizador
			JasperViewer jv = new JasperViewer(printer, false);
			jv.setTitle("Título da janela do visualizador");
			jv.setVisible(true);
		} catch (JRException e) {
			// johnny Auto-generated catch block
			e.printStackTrace();
		}

	}

}
