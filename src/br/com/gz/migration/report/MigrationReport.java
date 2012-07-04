package br.com.gz.migration.report;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Classe que gera o relatório final
 * 
 * @author Jonathan Sansalone
 *
 */
public class MigrationReport {

	/**
	 * Variável que guarda os dados da migração
	 */
	private MigrationReportData data;
	
	/**
	 * Construtor que recebe os dados para alimentar o relatório
	 * 
	 * @param data - dados da migração
	 */
	public MigrationReport(MigrationReportData data) {

		this.data = data;
		
	}
	
	/**
	 * Método que constrói o relatório
	 * 
	 * @return - true se construir com sucesso, false caso contrário
	 */
	public boolean buildReport() {

		JRBeanCollectionDataSource jrb = new JRBeanCollectionDataSource(data.getBeans());
		
		Image im = new ImageIcon((URL)getClass().getResource("/img/report/header.gif")).getImage();
		
		Map params = new HashMap();
		
		params.put("logoHeader", im);
		params.put("nomeCliente", data.getNomeCliente());
		params.put("modoMigracao", data.getModoMigracao());
		params.put("nomeSistema", data.getNomeSistema());
		params.put("tipoBanco", data.getTipoBanco());
		params.put("ip", data.getIp());
		params.put("porta", data.getPorta());
		params.put("nomeBanco", data.getNomeBanco());
		params.put("usuario", data.getUsuario());
		params.put("senha", data.getSenha());
		
		try{
			JasperPrint printer;
			printer = JasperFillManager.fillReport(getClass().getResourceAsStream("/report/MigrationReport.jasper"), params, jrb);
			JasperViewer jvw = new JasperViewer(printer,false);
			jvw.setTitle("Relatório de migração");
			jvw.setVisible(true);
			return true;
		}catch(JRException jre){
			jre.printStackTrace();
			return false;
		}
		
	}

}
