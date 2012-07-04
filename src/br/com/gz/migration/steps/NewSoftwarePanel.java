package br.com.gz.migration.steps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

/**
 * Classe que representa o painél de configuração do banco de dados de destino
 * 
 * @author Jonathan Sansalone
 *
 */
public class NewSoftwarePanel extends JPanel implements IValidation,
		IDatabaseInfo, IDatabaseMutable, ISoftwareMutable,
		InitialConfigurationsOnVisible {

	/**
	 * Combo para escolher o software a ser implantado
	 */
	private JComboBox<String> cmbSoftware;
	
	/**
	 * Botão para escolher o banco de dados de destino
	 */
	private JButton btSgbd;
	
	/**
	 * Campo para digitar o 1o grupo do endereço IP
	 */
	private JTextField txtIp1;
	
	/**
	 * Campo para digitar o 2o grupo do endereço IP
	 */
	private JTextField txtIp2;
	
	/**
	 * Campo para digitar o 3o grupo do endereço IP
	 */
	private JTextField txtIp3;
	
	/**
	 * Campo para digitar o 4o grupo do endereço IP
	 */
	private JTextField txtIp4;
	
	/**
	 * Campo para digitar a porta
	 */
	private JTextField txtPort;
	
	/**
	 * Campo para digitar o nome do banco de dados
	 */
	private JTextField txtDbName;
	
	/**
	 * Campo para digitar o nome de usuário
	 */
	private JTextField txtUserName;
	
	/**
	 * Campo para digitar a senha
	 */
	private JPasswordField txtPassword;

	/**
	 * JLabel que guarda a imagem do banco de dados
	 */
	private JLabel lblDb;

	/**
	 * Variável que guarda o banco de dados utilizado
	 */
	private DatabaseType dbType;

	/**
	 * Variável que guarda o software utilizado
	 */
	private GZSoftwares software;

	/**
	 * Flag que informa se o banco de dados já foi definido
	 */
	private boolean choosedDB = false;

	/**
	 * Construtor default
	 */
	public NewSoftwarePanel() {

		software = GZSoftwares.MERCOFLEX;

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblBanner = new JLabel(
				"Digite as informações do banco de dados do novo software",
				new ImageIcon((URL) getClass().getResource(
						"/img/navigation/arrow.png")), JLabel.LEFT);
		lblBanner.setBounds(30, 20, 600, 30);
		lblBanner.setFont(GZMigration.TITLE_FONT);
		add(lblBanner);

		cmbSoftware = new JComboBox<String>();
		cmbSoftware.addItem("MercoFlex");
		//cmbSoftware.addItem("Mercatto");

		btSgbd = new JButton(new ImageIcon((URL) getClass().getResource(
				"/img/navigation/change.png")));
		btSgbd.setToolTipText("Alterar o tipo de banco de dados");

		txtIp1 = new JTextField();
		txtIp2 = new JTextField();
		txtIp3 = new JTextField();
		txtIp4 = new JTextField();
		txtPort = new JTextField();
		txtDbName = new JTextField();
		txtUserName = new JTextField();
		txtPassword = new JPasswordField();

		JLabel lblSoftware = new JLabel("Software");
		JLabel lblDbName = new JLabel("Nome do banco de dados");
		JLabel lblIPAdress = new JLabel("Endereço IP");
		JLabel lblDot1 = new JLabel(".");
		JLabel lblDot2 = new JLabel(".");
		JLabel lblDot3 = new JLabel(".");
		JLabel lblPort = new JLabel("Porta");
		JLabel lblUserName = new JLabel("Nome de usuário");
		JLabel lblPassword = new JLabel("Senha");

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

		btSgbd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				new ChooseDatabase(NewSoftwarePanel.this, true);

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

	/**
	 * define o banco de dados de destino
	 * 
	 * @param type - banco de dados
	 * @throws InvalidDatabaseException - se for um banco de dados não suportado
	 */
	public void setDatabaseType(DatabaseType type)
			throws InvalidDatabaseException {

		if (type == DatabaseType.MySQL) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/mysql.png")));

			txtPort.setText("3306");
			txtUserName.setText("root");

		} else if (type == DatabaseType.MSSQL) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/sqlserver.png")));

			txtPort.setText("1433");
			txtUserName.setText("sa");

		} else if (type == DatabaseType.Oracle) {

			dbType = type;

			lblDb.setIcon(new ImageIcon((URL) getClass().getResource(
					"/img/database/oracle.png")));

			txtPort.setText("1521");
			txtUserName.setText("system");

		} else {

			throw new InvalidDatabaseException();

		}

	}

	@Override
	public boolean validateFields() {

		return !(isEmpty(txtDbName) || isEmpty(txtIp1) || isEmpty(txtIp2)
				|| isEmpty(txtIp3) || isEmpty(txtIp4) || isEmpty(txtPassword)
				|| isEmpty(txtPort) || isEmpty(txtUserName) || !choosedDB);

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

		if (cmbSoftware.getSelectedItem().equals("MercoFlex")) {

			software = GZSoftwares.MERCOFLEX;

		} else if (cmbSoftware.getSelectedItem().equals("Mercatto")) {

			software = GZSoftwares.MERCATTO;

		} else {

			throw new InvalidSoftwareException();

		}

	}

	@Override
	public GZSoftwares getSoftware() {
		// TODO Auto-generated method stub
		return software;
	}

	@Override
	public void setAvailableSoftwares(GZSoftwares software) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInitialFocus() {

		cmbSoftware.requestFocusInWindow();

	}

}
