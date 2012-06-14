package br.com.gz.migration.panelSteps;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.database.connection.DatabaseType;
import org.database.connection.InvalidDatabaseException;

import br.com.gz.migration.GZMigration;

public class ConfirmDataPanel extends JPanel {

	private JLabel lblTo;
	private JLabel lblFrom;

	public ConfirmDataPanel() {

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblBanner = new JLabel(
				"<html>Clique em <font color=orange>Iniciar</font> para iniciar a migração<html>", new ImageIcon(
						(URL) getClass().getResource(
								"/img/navigation/arrow.png")), JLabel.LEFT);
		lblBanner.setBounds(30, 20, 600, 30);
		lblBanner.setFont(GZMigration.TITLE_FONT);
		add(lblBanner);

		JLabel lblArrow = new JLabel(new ImageIcon((URL) getClass()
				.getResource("/img/common/from_to.png")));
		lblArrow.setBounds((getWidth() / 2) - 70, (getHeight() / 2) - 27, 140,
				55);
		add(lblArrow);
		lblFrom = new JLabel();
		lblFrom.setBounds(lblArrow.getX() - 200, (getHeight() / 2) - 50, 200,
				100);
		add(lblFrom);
		lblTo = new JLabel();
		lblTo.setBounds(lblArrow.getX() + lblArrow.getWidth(),
				(getHeight() / 2) - 50, 200, 100);
		add(lblTo);

		lblTo.setHorizontalAlignment(JLabel.CENTER);
		lblFrom.setHorizontalAlignment(JLabel.CENTER);
		
		setVisible(false);

	}

	public void setDB(DatabaseType to, DatabaseType from)
			throws InvalidDatabaseException {

		if (to == DatabaseType.MSSQL) {

			lblTo.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/sqlserver.png")));

		} else if (to == DatabaseType.MySQL) {

			lblTo.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/mysql.png")));

		} else if (to == DatabaseType.Oracle) {

			lblTo.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/oracle.png")));

		} else {
			throw new InvalidDatabaseException();
		}

		switch (from) {
		case MSSQL:

			lblFrom.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/sqlserver.png")));

			break;

		case MySQL:

			lblFrom.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/mysql.png")));

			break;

		case Oracle:

			lblFrom.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/oracle.png")));

			break;

		case Firebird:

			lblFrom.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/firebird.png")));

			break;

		case PostgreeSQL:

			lblFrom.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/postgresql.png")));

			break;

		default:
			
			throw new InvalidDatabaseException();
			
		}

	}

}
