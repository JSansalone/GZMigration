package br.com.gz.migration.steps;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.gz.migration.GZMigration;

/**
 * Classe que representa o painél de boas-vindas
 * 
 * @author Jonathan Sansalone
 *
 */
public class WelcomePanel extends JPanel {

	/**
	 * Construtor default
	 */
	public WelcomePanel() {

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblWelcome = new JLabel(
				"<html>Bem-vindo! Clique em <font color=orange>Avançar</font> para iniciar o processo de migração</html>",
				new ImageIcon((URL) getClass().getResource(
						"/img/navigation/arrow.png")), JLabel.LEFT);
		lblWelcome.setBounds(30, 20, 700, 30);
		lblWelcome.setFont(GZMigration.TITLE_FONT);
		add(lblWelcome);

		setVisible(false);

	}

}
