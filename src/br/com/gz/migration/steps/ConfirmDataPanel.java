package br.com.gz.migration.steps;

import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.database.connection.DatabaseType;
import org.database.connection.InvalidDatabaseException;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.GZMigration;

/**
 * Classe que representa o pain�l de confirma��o dos dados da migra��o
 * 
 * @author Jonathan Sansalone
 *
 */
public class ConfirmDataPanel extends JPanel {

	/**
	 * Label que guarda a imagem do banco de dados de destino
	 */
	private JLabel lblTo;
	
	/**
	 * Label que guarda a imagem do arquivo de dados
	 */
	private JLabel lblFrom;

	/**
	 * Label que cont�m o t�tulo do pain�l
	 */
	private JLabel lblBanner;
	
	/**
	 * Label que guarda a imagem da seta que aponta o arquivo de dados para o banco de dados de destino
	 */
	private JLabel lblArrow;
	
	/**
	 * Scroll que cont�m o JTextArea dos dados da migra��o
	 */
	private JScrollPane scrPane;
	
	/**
	 * �rea onde fica a lista dos dados da migra��o
	 */
	private JTextArea area;

	/**
	 * Construtor default
	 */
	public ConfirmDataPanel() {

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);

		initComponents();

		add(lblBanner);
		add(lblArrow);
		add(lblFrom);
		add(lblTo);
		add(scrPane);
		
		setVisible(false);

	}

	/**
	 * Constr�i os componentes
	 */
	private void initComponents() {

		lblBanner = new JLabel(
				"<html>Clique em <font color=orange>Iniciar</font> para iniciar a migra��o<html>",
				new ImageIcon((URL) getClass().getResource(
						"/img/navigation/arrow.png")), JLabel.LEFT);
		lblBanner.setBounds(30, 20, 600, 30);
		lblBanner.setFont(GZMigration.TITLE_FONT);

		lblArrow = new JLabel(new ImageIcon((URL) getClass().getResource(
				"/img/common/from_to.png")));
		lblArrow.setBounds((getWidth() / 2) - 70, (getHeight() / 2) - 27, 140,
				55);

		lblTo = new JLabel();
		lblTo.setBounds(lblArrow.getX() + lblArrow.getWidth(),
				(getHeight() / 2) - 50, 200, 100);
		lblTo.setHorizontalAlignment(JLabel.CENTER);
		
		area = new JTextArea();
		area.setEditable(false);
		scrPane = new JScrollPane(area);
		scrPane.setBounds(lblArrow.getX() - 160, (getHeight() / 2) - 50, 160,
				160);
		scrPane.setBorder(null);
		
		lblFrom = new JLabel();
		lblFrom.setBounds(scrPane.getX() - 60, (getHeight() / 2) - 50, 50,
				50);
		lblFrom.setIcon(new ImageIcon((URL) getClass().getResource(
				"/img/database/excel_50x50.png")));
		lblFrom.setHorizontalAlignment(JLabel.CENTER);

	}

	/**
	 * Define a imagem do banco de dados de destino
	 * 
	 * @param to - banco de dados
	 */
	private void setDB(DatabaseType to) {

		if (to == DatabaseType.MSSQL) {

			lblTo.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/sqlserver.png")));

		} else if (to == DatabaseType.MySQL) {

			lblTo.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/mysql.png")));

		} else if (to == DatabaseType.Oracle) {

			lblTo.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/oracle.png")));

		}

	}
	
	/**
	 * Carrega as informa��es no textArea
	 * 
	 * @param dataTypes - lista com os dados
	 */
	private void loadTextArea(ArrayList<EnMigrationDataType> dataTypes){
		
		area.setText("");
		
		for (EnMigrationDataType d : dataTypes) {
			area.append(d.getDescription()+"\n");
		}
		
		area.setText(area.getText().substring(0,area.getText().length()-1));
		
	}
	
	/**
	 * Executa os m�todos setDB e loadTextArea
	 * 
	 * @param dataTypes - dados da migra��o
	 * @param dbTo - banco de dados de destino
	 */
	public void configure(ArrayList<EnMigrationDataType> dataTypes, DatabaseType dbTo){
		
		setDB(dbTo);
		loadTextArea(dataTypes);
		
	}
	
}
