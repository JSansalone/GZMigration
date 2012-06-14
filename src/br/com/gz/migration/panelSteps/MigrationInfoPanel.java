package br.com.gz.migration.panelSteps;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import br.com.gz.migration.GZMigration;

public class MigrationInfoPanel extends JPanel implements IMigrationInfo {

	private JProgressBar progressBar;

	private int n = 1;

	private JLabel lblInfo;

	public MigrationInfoPanel() {

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblBanner = new JLabel(
				"Aguarde enquanto a migra��o est� sendo realizada",
				new ImageIcon((URL) getClass().getResource(
						"/img/navigation/arrow.png")), JLabel.LEFT);
		lblBanner.setBounds(30, 20, 600, 30);
		lblBanner.setFont(GZMigration.TITLE_FONT);
		add(lblBanner);

		progressBar = new JProgressBar(0,100);
		progressBar.setBounds((getWidth() / 2) - 200, (getHeight() / 2) - 10,
				400, 20);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		progressBar.setBackground(new Color(1, 69, 118));
		progressBar.setForeground(new Color(248, 198, 25));
		add(progressBar);

		lblInfo = new JLabel("Analisando os dados a serem migrados...");
		lblInfo.setBounds((getWidth() / 2) - 200, (getHeight() / 2) - 30, 400,
				20);
		add(lblInfo);

		setVisible(false);

	}

	@Override
	public void setTotal(int num) {

		

	}

	@Override
	public void setValue(int num) {

		if(progressBar.isIndeterminate()){
			progressBar.setIndeterminate(false);
		}
		
		progressBar.setValue(num);
		progressBar.setString(num + " %");

	}

	@Override
	public void setText(String text) {

		lblInfo.setText(text);

	}

}
