package br.com.gz.migration.panelSteps;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.database.connection.DatabaseType;

import br.com.gz.migration.IDatabaseMutable;

public class ChooseDatabase extends JDialog {

	public ChooseDatabase(final IDatabaseMutable mutable, boolean forGZ) {

		setSize(300, 200);
		setTitle("Escolha o tipo de banco de dados");
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setModal(true);

		final JList<String> list = new JList<String>();
		DefaultListModel<String> m = new DefaultListModel<String>();
		list.setModel(m);

		m.addElement("Oracle");
		m.addElement("Microsoft SQL Server");
		m.addElement("MySQL");
		if (!forGZ) {
			m.addElement("Microsoft Access");
			m.addElement("PostgreSQL");
			m.addElement("Firebird");
		}

		JScrollPane scr = new JScrollPane(list);
		scr.setBounds(20, 20, 255, 130);

		add(scr);

		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				int n = e.getClickCount();

				if (n == 2) {

					String db = list.getSelectedValue();

					if (db.equals("Oracle")) {

						mutable.chooseDatabase(DatabaseType.Oracle);

					} else if (db.equals("MySQL")) {

						mutable.chooseDatabase(DatabaseType.MySQL);

					} else if (db.equals("Microsoft SQL Server")) {

						mutable.chooseDatabase(DatabaseType.MSSQL);

					} else if (db.equals("Microsoft Access")) {

						mutable.chooseDatabase(DatabaseType.MSAccess);

					} else if (db.equals("PostgreSQL")) {

						mutable.chooseDatabase(DatabaseType.PostgreeSQL);

					} else if (db.equals("Firebird")) {

						mutable.chooseDatabase(DatabaseType.Firebird);

					}

					dispose();

				}

			}
		});

		list.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent k) {

			}

			@Override
			public void keyReleased(KeyEvent k) {

				if (k.getKeyCode() == KeyEvent.VK_ENTER) {

					String db = list.getSelectedValue();

					if (db.equals("Oracle")) {

						mutable.chooseDatabase(DatabaseType.Oracle);

					} else if (db.equals("MySQL")) {

						mutable.chooseDatabase(DatabaseType.MySQL);

					} else if (db.equals("Microsoft SQL Server")) {

						mutable.chooseDatabase(DatabaseType.MSSQL);

					} else if (db.equals("Microsoft Access")) {

						mutable.chooseDatabase(DatabaseType.MSAccess);

					} else if (db.equals("PostgreSQL")) {

						mutable.chooseDatabase(DatabaseType.PostgreeSQL);

					} else if (db.equals("Firebird")) {

						mutable.chooseDatabase(DatabaseType.Firebird);

					}

					dispose();

				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		setVisible(true);

	}

}
