package br.com.gz.migration.steps;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.gz.migration.GZMigration;

/**
 * Classe que representa o pain�l informando que a migra��o foi conclu�da
 * 
 * @author Jonathan Sansalone
 *
 */
public class DonePanel extends JPanel {

	/**
	 * Construtor default
	 */
	public DonePanel() {

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblBanner = new JLabel("A migra��o foi conclu�da!",
				new ImageIcon((URL) getClass().getResource("/img/navigation/arrow.png")), JLabel.LEFT);
		lblBanner.setBounds(30, 20, 600, 30);
		lblBanner.setFont(GZMigration.TITLE_FONT);
		add(lblBanner);

		setVisible(false);

	}

}
