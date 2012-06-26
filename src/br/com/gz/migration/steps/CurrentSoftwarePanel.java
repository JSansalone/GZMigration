package br.com.gz.migration.steps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.database.connection.DatabaseConfigurations;
import org.database.connection.DatabaseType;
import org.database.connection.InvalidDatabaseException;

import br.com.gz.migration.GZMigration;
import br.com.gz.migration.IDatabaseInfo;
import br.com.gz.migration.IDatabaseMutable;
import br.com.gz.migration.ISoftwareMutable;
import br.com.gz.migration.exception.InvalidSoftwareException;
import br.com.gz.util.GZSoftwares;

public class CurrentSoftwarePanel extends JPanel implements IValidation,
		IDatabaseInfo, IDatabaseMutable, ISoftwareMutable,
		InitialConfigurationsOnVisible {

	private JComboBox<String> cmbSoftware;
	private JLabel lblSoftware;
	private JLabel lblDbName;
	private JLabel lblIPAdress;
	private JLabel lblDot1;
	private JLabel lblDot2;
	private JLabel lblDot3;
	private JLabel lblPort;
	private JLabel lblUserName;
	private JLabel lblPassword;
	private JTextField txtIp1;
	private JTextField txtIp2;
	private JTextField txtIp3;
	private JTextField txtIp4;
	private JTextField txtPort;
	private JTextField txtDbName;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private DatabaseType dbType;
	private JLabel lblDb;
	private JButton btSearch;
	private JButton btSgbd;

	private GZSoftwares software;

	private ISoftwareMutable softMutable;

	private boolean choosedDB = false;

	public CurrentSoftwarePanel(ISoftwareMutable sftmt) {

		this.softMutable = sftmt;

		// software = GZSoftwares.Teste;

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblBanner = new JLabel(
				"Digite as informações do banco de dados do software atual",
				new ImageIcon("img/navigation/arrow.png"), JLabel.LEFT);
		lblBanner.setBounds(30, 20, 600, 30);
		lblBanner.setFont(GZMigration.TITLE_FONT);
		add(lblBanner);

		btSearch = new JButton(new ImageIcon((URL) getClass().getResource(
				"/img/navigation/search.png")));
		btSearch.setVisible(false);

		btSgbd = new JButton(new ImageIcon((URL) getClass().getResource(
				"/img/navigation/change.png")));
		btSgbd.setToolTipText("Alterar o tipo de banco de dados");

		cmbSoftware = new JComboBox<String>();

		txtIp1 = new JTextField();
		txtIp2 = new JTextField();
		txtIp3 = new JTextField();
		txtIp4 = new JTextField();
		txtPort = new JTextField();
		txtDbName = new JTextField();
		txtUserName = new JTextField();
		txtPassword = new JPasswordField();

		lblSoftware = new JLabel("Software");
		lblDbName = new JLabel("Nome do banco de dados");
		lblIPAdress = new JLabel("Endereço IP");
		lblDot1 = new JLabel(".");
		lblDot2 = new JLabel(".");
		lblDot3 = new JLabel(".");
		lblPort = new JLabel("Porta");
		lblUserName = new JLabel("Nome de usuário");
		lblPassword = new JLabel("Senha");

		lblSoftware.setBounds(60, 90, 160, 20);
		lblDbName.setBounds(60, 120, 170, 20);
		lblIPAdress.setBounds(60, 150, 150, 20);
		lblDot1.setBounds(260, 150, 2, 20);
		lblDot2.setBounds(301, 150, 2, 20);
		lblDot3.setBounds(343, 150, 2, 20);
		lblPort.setBounds(60, 180, 90, 20);
		lblUserName.setBounds(60, 210, 150, 20);
		lblPassword.setBounds(60, 240, 150, 20);

		cmbSoftware.setBounds(228, 90, 150, 20);

		btSearch.setBounds(388, 120, 20, 20);

		btSgbd.setBounds(388, 90, 20, 20);

		txtDbName.setBounds(228, 120, 150, 20);
		txtIp1.setBounds(228, 150, 26, 20);
		txtIp2.setBounds(269, 150, 26, 20);
		txtIp3.setBounds(310, 150, 26, 20);
		txtIp4.setBounds(352, 150, 26, 20);
		txtPort.setBounds(228, 180, 40, 20);
		txtUserName.setBounds(228, 210, 150, 20);
		txtPassword.setBounds(228, 240, 150, 20);

		txtIp1.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String aux = txtIp1.getText();
				if (!aux.equals("")) {
					try {
						int n = Integer.parseInt(aux);
						if (n >= 255) {
							JOptionPane.showMessageDialog(null,
									"Numeração inválida para o campo!",
									"Atenção", JOptionPane.WARNING_MESSAGE);
							txtIp1.setText("0");
						} else {
							if (aux.length() > 2) {
								if (n == 127) {
									txtIp2.setText("0");
									txtIp3.setText("0");
									txtIp4.setText("1");
									txtPort.requestFocusInWindow();
								} else {
									txtIp2.requestFocusInWindow();
								}
							}
						}
					} catch (Exception e2) {
						txtIp1.setText("0");
					}
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		txtIp2.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String aux = txtIp2.getText();
				if (!aux.equals("")) {
					try {
						int n = Integer.parseInt(aux);
						if (n >= 255) {
							JOptionPane.showMessageDialog(null,
									"Numeração inválida para o campo!",
									"Atenção", JOptionPane.WARNING_MESSAGE);
							txtIp2.setText("0");
						} else {
							if (aux.length() > 2) {
								txtIp3.requestFocusInWindow();
							}
						}
					} catch (Exception e2) {
						txtIp2.setText("0");
					}
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		txtIp3.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String aux = txtIp3.getText();
				if (!aux.equals("")) {
					try {
						int n = Integer.parseInt(aux);
						if (n >= 255) {
							JOptionPane.showMessageDialog(null,
									"Numeração inválida para o campo!",
									"Atenção", JOptionPane.WARNING_MESSAGE);
							txtIp3.setText("0");
						} else {
							if (aux.length() > 2) {
								txtIp4.requestFocusInWindow();
							}
						}
					} catch (Exception e2) {
						txtIp3.setText("0");
					}
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		txtIp4.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String aux = txtIp4.getText();
				if (!aux.equals("")) {
					try {
						int n = Integer.parseInt(aux);
						if (n >= 255) {
							JOptionPane.showMessageDialog(null,
									"Numeração inválida para o campo!",
									"Atenção", JOptionPane.WARNING_MESSAGE);
							txtIp4.setText("0");
						}
					} catch (Exception e2) {
						txtIp4.setText("0");
					}
				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		btSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser f = new JFileChooser();
				if (f.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					txtDbName.setText(f.getSelectedFile().getAbsolutePath());

				}

			}

		});

		btSgbd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new ChooseDatabase(CurrentSoftwarePanel.this, false);

			}

		});

		cmbSoftware.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {

					chooseSoftware();

				} catch (InvalidSoftwareException e) {
					e.getMessage();
				}

			}

		});

		add(lblSoftware);
		add(lblDbName);
		add(lblIPAdress);
		add(lblDot1);
		add(lblDot2);
		add(lblDot3);
		add(lblPort);
		add(lblUserName);
		add(lblPassword);

		add(cmbSoftware);
		add(btSgbd);
		add(btSearch);
		add(txtDbName);
		add(txtIp1);
		add(txtIp2);
		add(txtIp3);
		add(txtIp4);
		add(txtPort);
		add(txtUserName);
		add(txtPassword);

		lblDb = new JLabel();
		lblDb.setBounds(getWidth() - 210, getHeight() - 120, 200, 100);
		lblDb.setHorizontalAlignment(JLabel.CENTER);
		add(lblDb);

		setVisible(false);

	}

	public void setDatabaseType(DatabaseType type)
			throws InvalidDatabaseException {

		if (type == DatabaseType.MySQL) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/mysql.png")));

			lblDbName.setText("Nome do banco de dados");
			lblIPAdress.setVisible(true);
			lblDot1.setVisible(true);
			lblDot2.setVisible(true);
			lblDot3.setVisible(true);
			lblPort.setVisible(true);
			lblUserName.setVisible(true);
			lblPassword.setVisible(true);

			txtIp1.setVisible(true);
			txtIp2.setVisible(true);
			txtIp3.setVisible(true);
			txtIp4.setVisible(true);
			txtPort.setVisible(true);
			txtUserName.setVisible(true);
			txtPassword.setVisible(true);

			txtDbName.setEditable(true);
			txtDbName.setText("");
			btSearch.setVisible(false);

			txtPort.setText("3306");
			txtUserName.setText("root");

		} else if (type == DatabaseType.MSSQL) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/sqlserver.png")));

			lblDbName.setText("Nome do banco de dados");
			lblIPAdress.setVisible(true);
			lblDot1.setVisible(true);
			lblDot2.setVisible(true);
			lblDot3.setVisible(true);
			lblPort.setVisible(true);
			lblUserName.setVisible(true);
			lblPassword.setVisible(true);

			txtIp1.setVisible(true);
			txtIp2.setVisible(true);
			txtIp3.setVisible(true);
			txtIp4.setVisible(true);
			txtPort.setVisible(true);
			txtUserName.setVisible(true);
			txtPassword.setVisible(true);

			txtDbName.setEditable(true);
			txtDbName.setText("");
			btSearch.setVisible(false);

			txtPort.setText("1433");
			txtUserName.setText("sa");

		} else if (type == DatabaseType.Oracle) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/oracle.png")));

			lblDbName.setText("Nome do banco de dados");
			lblIPAdress.setVisible(true);
			lblDot1.setVisible(true);
			lblDot2.setVisible(true);
			lblDot3.setVisible(true);
			lblPort.setVisible(true);
			lblUserName.setVisible(true);
			lblPassword.setVisible(true);

			txtIp1.setVisible(true);
			txtIp2.setVisible(true);
			txtIp3.setVisible(true);
			txtIp4.setVisible(true);
			txtPort.setVisible(true);
			txtUserName.setVisible(true);
			txtPassword.setVisible(true);

			txtDbName.setEditable(true);
			txtDbName.setText("");
			btSearch.setVisible(false);

			txtPort.setText("1521");
			txtUserName.setText("system");

		} else if (type == DatabaseType.Firebird) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/firebird.png")));

			lblDbName.setText("Nome do banco de dados");
			lblIPAdress.setVisible(true);
			lblDot1.setVisible(true);
			lblDot2.setVisible(true);
			lblDot3.setVisible(true);
			lblPort.setVisible(true);
			lblUserName.setVisible(true);
			lblPassword.setVisible(true);

			txtIp1.setVisible(true);
			txtIp2.setVisible(true);
			txtIp3.setVisible(true);
			txtIp4.setVisible(true);
			txtPort.setVisible(true);
			txtUserName.setVisible(true);
			txtPassword.setVisible(true);

			txtDbName.setEditable(false);
			txtDbName.setText("");
			btSearch.setVisible(true);

			txtPort.setText("3050");
			txtUserName.setText("sysdba");

		} else if (type == DatabaseType.PostgreeSQL) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/postgresql.png")));

			lblIPAdress.setVisible(true);
			lblDot1.setVisible(true);
			lblDot2.setVisible(true);
			lblDot3.setVisible(true);
			lblPort.setVisible(true);
			lblUserName.setVisible(true);
			lblPassword.setVisible(true);

			txtIp1.setVisible(true);
			txtIp2.setVisible(true);
			txtIp3.setVisible(true);
			txtIp4.setVisible(true);
			txtPort.setVisible(true);
			txtUserName.setVisible(true);
			txtPassword.setVisible(true);

			txtDbName.setEditable(true);
			txtDbName.setText("");
			btSearch.setVisible(false);

			txtPort.setText("5432");
			txtUserName.setText("postgres");

		} else if (type == DatabaseType.MSAccess) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/access.png")));

			lblDbName.setText("Nome da fonte de dados");
			lblIPAdress.setVisible(false);
			lblDot1.setVisible(false);
			lblDot2.setVisible(false);
			lblDot3.setVisible(false);
			lblPort.setVisible(false);
			lblUserName.setVisible(false);
			lblPassword.setVisible(false);

			txtIp1.setVisible(false);
			txtIp2.setVisible(false);
			txtIp3.setVisible(false);
			txtIp4.setVisible(false);
			txtPort.setVisible(false);
			txtUserName.setVisible(false);
			txtPassword.setVisible(false);

			txtDbName.setEditable(true);
			txtDbName.setText("");
			btSearch.setVisible(false);

		} else {

			throw new InvalidDatabaseException();

		}

	}

	@Override
	public boolean validateFields() {

		return !(isEmpty(txtDbName) || isEmpty(txtIp1) || isEmpty(txtIp2)
				|| isEmpty(txtIp3) || isEmpty(txtIp4) || isEmpty(txtPassword)
				|| isEmpty(txtPort) || isEmpty(txtUserName) || !choosedDB || cmbSoftware
					.getSelectedIndex() == -1);

	}

	@Override
	public boolean isEmpty(JTextField field) {
		return field.getText().equals("");
	}

	@Override
	public DatabaseConfigurations getDatabaseInfo() {

		DatabaseConfigurations cfg = new DatabaseConfigurations();
		cfg.setDatabaseName(txtDbName.getText());
		cfg.setDbType(dbType);
		cfg.setPort(Integer.parseInt(txtPort.getText()));
		cfg.setIpAddress(txtIp1.getText() + "." + txtIp2.getText() + "."
				+ txtIp3.getText() + "." + txtIp4.getText());
		cfg.setUsername(txtUserName.getText());
		cfg.setPassword(txtPassword.getText());

		return cfg;

	}

	@Override
	public void chooseDatabase(DatabaseType dbt) {

		try {
			setDatabaseType(dbt);
			choosedDB = true;
		} catch (InvalidDatabaseException e) {
			e.printStackTrace();
			choosedDB = false;
		}

	}

	@Override
	public void chooseSoftware() throws InvalidSoftwareException {

		if (cmbSoftware.getItemCount() > 0) {

			if (((String) cmbSoftware.getSelectedItem()).equals("Teste")) {

				software = GZSoftwares.Teste;

			} else if (((String) cmbSoftware.getSelectedItem())
					.equals("Superus")) {

				software = GZSoftwares.SUPERUS;

			} else if (((String) cmbSoftware.getSelectedItem()).equals("MRS")) {

				software = GZSoftwares.MRS;

			} else if (((String) cmbSoftware.getSelectedItem()).equals("AES")) {

				software = GZSoftwares.AES;

			} else if (((String) cmbSoftware.getSelectedItem())
					.equals("Versatho")) {

				software = GZSoftwares.VERSATHO;

			} else if (((String) cmbSoftware.getSelectedItem()).equals("Outro")) {

				software = GZSoftwares.OTHER;

			} else {

				throw new InvalidSoftwareException();

			}

		}

	}

	@Override
	public GZSoftwares getSoftware() {
		// TODO Auto-generated method stub
		return software;
	}

	@Override
	public void setAvailableSoftwares(GZSoftwares software) {

		cmbSoftware.removeAllItems();

		switch (software) {

		case MERCOFLEX:

			cmbSoftware.addItem("AES");
			cmbSoftware.addItem("MRS");
			cmbSoftware.addItem("Superus");
			cmbSoftware.addItem("Versatho");
			cmbSoftware.addItem("Outro");

			break;

		case MERCATTO:

			cmbSoftware.addItem("AES");
			cmbSoftware.addItem("MRS");
			cmbSoftware.addItem("Superus");
			cmbSoftware.addItem("Versatho");
			cmbSoftware.addItem("Outro");
			
			break;

		default:
			break;
		}

	}

	@Override
	public void setInitialFocus() {

		cmbSoftware.requestFocusInWindow();

	}

}
