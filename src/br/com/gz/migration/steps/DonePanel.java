package br.com.gz.migration.steps;

import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.gz.migration.GZMigration;
import br.com.gz.migration.datafile.DataFileReader;

/**
 * Classe que representa o painél informando que a migração foi concluída
 * 
 * @author Jonathan Sansalone
 * 
 */
public class DonePanel extends JPanel {

	/**
	 * JLabel que representa a mensagem de aviso que há registros não inseridos
	 */
	private JLabel lblWarning;

	/**
	 * Construtor default
	 */
	public DonePanel() {

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblBanner = new JLabel("A migração foi concluída!",
				new ImageIcon((URL) getClass().getResource(
						"/img/navigation/arrow.png")), JLabel.LEFT);
		lblBanner.setBounds(30, 20, 600, 30);
		lblBanner.setFont(GZMigration.TITLE_FONT);
		lblWarning = new JLabel(
				"Alguns registros não foram inseridos. Veja os arquivos gerados no diretório 'data' dentro do diretório de instalação.",
				new ImageIcon((URL) getClass().getResource(
						"/img/common/warning.png")), JLabel.LEFT);
		lblWarning.setBounds(30, 240, 700, 20);
		lblWarning.setFont(new Font(null, 0, 10));
		lblWarning.setVisible(false);
		add(lblWarning);
		add(lblBanner);

		setVisible(false);

	}

	public void showWarning() {

		if (DataFileReader.hasNotInsertedFiles())
			lblWarning.setVisible(true);

	}

}
