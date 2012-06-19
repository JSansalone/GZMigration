package br.com.gz.migration;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.database.connection.ConnectionFactory;
import org.database.connection.DatabaseConfigurations;
import org.database.connection.DatabaseType;
import org.database.connection.InvalidDatabaseException;

import br.com.gz.migration.file.DefaultDirectoryStructure;
import br.com.gz.migration.file.LogFile;
import br.com.gz.migration.panelSteps.ConfirmDataPanel;
import br.com.gz.migration.panelSteps.CurrentSoftwarePanel;
import br.com.gz.migration.panelSteps.CustomerNamePanel;
import br.com.gz.migration.panelSteps.DonePanel;
import br.com.gz.migration.panelSteps.MigrationInfoPanel;
import br.com.gz.migration.panelSteps.MigrationTypePanel;
import br.com.gz.migration.panelSteps.NewSoftwarePanel;
import br.com.gz.migration.panelSteps.WelcomePanel;
import br.com.gz.migration.report.MigrationReportData;
import br.com.gz.migration.report.PDFReport;
import br.com.gz.migration.software.SQLDataProviderImpl;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;

/**
 * GZ SIstemas - Migra��o de banco de dados
 * 
 * Data de in�cio: Mar�o de 2012
 * 
 * Projetista respons�vel: Jonathan Sansalone Pacheco Rolim
 * 
 * Desenvolvedor respons�vel: Jonathan Sansalone Pacheco Rolim
 * 
 * Descri��o b�sica do aplicativo:
 * 
 * Aplicativo que auxilia a migra��o de bancos de dados entre softwares de
 * terceiros e os softwares da GZ Sistemas.
 * 
 * 
 * Bancos de dados:
 * 
 * � habilitado para realizar conex�es com os bancos: - Oracle - MySQL -
 * Microsoft SQL Server
 * 
 */
public class GZMigration extends JFrame implements IValidateDataProvider,
		IFinalizeMigration, ISoftwareMutable {

	private Container wnd;

	// Bot�es de navega��o
	private JButton btNext;
	private JButton btPrevious;
	private JButton btCancel;
	private JButton btHelp;
	private JButton btInfo;
	private JButton btReport;

	// Componentes compartilhados pelos pain�is de navega��o
	public static final Dimension DIMENSION_PANEL = new Dimension(700, 285);
	public static final Point POINT_PANEL = new Point(0, 93);
	public static final Font TITLE_FONT = new Font("Tahoma", Font.BOLD, 16);

	// Vari�veis de refer�ncia dos pain�is de navega��o
	private WelcomePanel welcomePanel;
	private CustomerNamePanel customerNamePanel;
	private MigrationTypePanel migrationTypePanel;
	private NewSoftwarePanel newSoftwarePanel;
	private CurrentSoftwarePanel currentSoftwarePanel;
	private ConfirmDataPanel confirmDataPanel;
	private MigrationInfoPanel migrationInfoPanel;
	private DonePanel donePanel;

	// Vari�vel de refer�ncia do motor de migra��o
	private MigrationEngine dataProviderEngine;

	// Vari�vel de refer�ncia da interface de migra��o
	private SQLDataProvider dataProvider;

	// �ndices de navega��o dos pain�is
	// guarda o �ndice corrente
	private int currentIndex = 1;
	// guarda o �ndice limite
	private final int limitIndex = 8;

	// Vari�vel de refer�ncia que guardar� a data de inicializa��o do motor de
	// migra��o
	private Calendar date;

	/**
	 * Construtor Default que inicializa as propriedades da janela principal e cria os componentes
	 */
	public GZMigration() {

		// Definindo propriedades da janela
		setSize(700, 450);
		setResizable(false);
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/img/common/icon.gif")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("GZ Sistemas - Migra��o de banco de dados");

		// Centralizando
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = getWidth();
		int contHeidth = getHeight();

		setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);

		wnd = getContentPane();
		wnd.setLayout(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				if (JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja sair?", "Sair",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					System.exit(0);

				}

			}
		});

		// ----------------------------------------------------

		// Definindo imagem do head e separador
		JLabel lblHead = new JLabel(new ImageIcon((URL) getClass().getResource(
				"/img/common/head.png")));
		lblHead.setBounds(0, -3, 700, 96);
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		sep.setBounds(0, 92, 700, 5);
		wnd.add(lblHead);
		wnd.add(sep);
		// --------------------------------------------------

		// Cria��o dos bot�es de navega��o
		btNext = new JButton("Avan�ar", new ImageIcon((URL) getClass()
				.getResource("/img/navigation/next.png")));
		btNext.setBounds(getWidth() - 115, getHeight() - 70, 100, 30);
		btNext.setHorizontalTextPosition(AbstractButton.LEFT);
		btNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setCursor(Cursor.WAIT_CURSOR);

				// se o �ndice corrente n�o chegar ao limite, continua
				// percorrendo
				if (currentIndex < limitIndex) {

					browse(++currentIndex);

					if (currentIndex == limitIndex) {
						btReport.setVisible(true);
					} else {
						btReport.setVisible(false);
					}

				}

				setCursor(Cursor.DEFAULT_CURSOR);

			}

		});
		btPrevious = new JButton("Voltar", new ImageIcon((URL) getClass()
				.getResource("/img/navigation/previous.png")));
		btPrevious.setBounds(getWidth() - btNext.getWidth() - 120,
				getHeight() - 70, 100, 30);
		btPrevious.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setCursor(Cursor.WAIT_CURSOR);

				// se o �ndice corrente n�o chegar ao primeiro, continua
				// percorrendo
				if (currentIndex > 1) {

					browse(--currentIndex);
					if (currentIndex == limitIndex) {
						btReport.setVisible(true);
					} else {
						btReport.setVisible(false);
					}

				}

				setCursor(Cursor.DEFAULT_CURSOR);

			}
		});
		btCancel = new JButton("Cancelar", new ImageIcon((URL) getClass()
				.getResource("/img/navigation/cancel.png")));
		btCancel.setToolTipText("Cancelar e sair");
		btCancel.setBounds(
				getWidth() - btPrevious.getWidth() - btNext.getWidth() - 125,
				getHeight() - 70, 100, 30);
		btCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Sair do aplicativo
				if (JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja sair?", "Sair",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					if (dataProviderEngine != null) {
						dataProviderEngine.interrupt();
					}
					LogFile.getInstance().writeInFile("Application finished");
					System.exit(0);

				}

			}
		});

		// Exibe informa��es sobre o aplicativo
		btInfo = new JButton(new ImageIcon((URL) getClass().getResource(
				"/img/navigation/info.png")));
		btInfo.setBounds(50, getHeight() - 70, 30, 30);
		btInfo.setToolTipText("Sobre");
		btInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				LogFile.getInstance().writeInFile("Showing about frame");
				new AboutFrame();

				/*
				 * JOptionPane .showMessageDialog( GZMigration.this, "" +
				 * "Migra��o de banco de dados\n" +
				 * "--------------------------------------\n" +
				 * "GZ Sistemas Importa��o e Com�rcio\n" +
				 * "Tel: (11)3308-8199\n" + "Site: www.gzsistemas.com.br\n\n" +
				 * "Respons�vel pelo aplicativo: Jonathan Sansalone\n" +
				 * "Contato: jonathan.sansalone@gzsistemas.com.br\n" +
				 * "Departamento de Servi�os\n" +
				 * "--------------------------------------\n" +
				 * "Abril de 2012 - Vers�o 1.0.0", "Sobre",
				 * JOptionPane.INFORMATION_MESSAGE, new ImageIcon((URL)
				 * getClass().getResource( "/img/navigation/info.png")));
				 */
			}

		});

		// Exibe instru��es de utiliza��o
		btHelp = new JButton(new ImageIcon((URL) getClass().getResource(
				"/img/navigation/help.png")));
		btHelp.setBounds(10, getHeight() - 70, 30, 30);
		btHelp.setToolTipText("Ajuda");
		btHelp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				HelpFrame.getInstance().setVisible(true);

			}

		});
		btReport = new JButton(new ImageIcon((URL) getClass().getResource(
				"/img/common/report.png")));
		btReport.setBounds(90, getHeight() - 70, 30, 30);
		btReport.setVisible(false);
		btReport.setToolTipText("Gerar relat�rio de migra��o");
		btReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setCursor(Cursor.WAIT_CURSOR);

				// Chama o m�todo de gerar relat�rio
				LogFile.getInstance().writeInFile("Generating report");
				generateReport();

				setCursor(Cursor.DEFAULT_CURSOR);

			}

		});
		wnd.add(btNext);
		wnd.add(btPrevious);
		wnd.add(btCancel);
		wnd.add(btHelp);
		wnd.add(btInfo);
		wnd.add(btReport);
		// -----------------------------------

		// pain�is
		welcomePanel = new WelcomePanel();
		welcomePanel.setVisible(true);
		wnd.add(welcomePanel);

		customerNamePanel = new CustomerNamePanel();
		wnd.add(customerNamePanel);

		// cria��o do pain�l que permite a escolha dos dados a serem migrados e
		// a quantidade de lojas
		migrationTypePanel = new MigrationTypePanel();
		wnd.add(migrationTypePanel);

		// cria��o do pain�l que permite a configura��o do banco de dados do
		// novo software
		newSoftwarePanel = new NewSoftwarePanel();
		wnd.add(newSoftwarePanel);

		// cria��o do pain�l que permite a configura��o do banco de dados do
		// software atual
		currentSoftwarePanel = new CurrentSoftwarePanel(this);
		wnd.add(currentSoftwarePanel);

		// cria��o do pain�l que permite a confirma��o dos dados digitados
		confirmDataPanel = new ConfirmDataPanel();
		wnd.add(confirmDataPanel);

		// cria��o do pain�l que mostra o andamento da migra��o
		migrationInfoPanel = new MigrationInfoPanel();
		wnd.add(migrationInfoPanel);

		// cria��o do pain�l que mostra que a migra��o foi conclu�da e habilita
		// o bot�o de gera��o de relat�rio
		donePanel = new DonePanel();
		wnd.add(donePanel);
		// ----------------------------------

	}

	/**
	 * 
	 * M�todo que navega entre as etapas da migra��o. Gerencia a visibilidade dos pain�is
	 * 
	 * @param index - �ndice de navega��o
	 * 
	 */
	private void browse(int index) {

		switch (index) {
		case 1:

			welcomePanel.setVisible(true); // pain�l de boas vindas
			customerNamePanel.setVisible(false);
			migrationTypePanel.setVisible(false);
			newSoftwarePanel.setVisible(false);
			currentSoftwarePanel.setVisible(false);
			confirmDataPanel.setVisible(false);
			migrationInfoPanel.setVisible(false);
			donePanel.setVisible(false);

			break;

		case 2:

			welcomePanel.setVisible(false);
			customerNamePanel.setVisible(true);// <----
			migrationTypePanel.setVisible(false);
			newSoftwarePanel.setVisible(false);
			currentSoftwarePanel.setVisible(false);
			confirmDataPanel.setVisible(false);
			migrationInfoPanel.setVisible(false);
			donePanel.setVisible(false);

			customerNamePanel.setInitialFocus();

			break;

		case 3:

			if (customerNamePanel.validateFields()) {

				welcomePanel.setVisible(false);
				customerNamePanel.setVisible(false);
				migrationTypePanel.setVisible(true); // escolha dos dados da
														// migra��o
				newSoftwarePanel.setVisible(false);
				currentSoftwarePanel.setVisible(false);
				confirmDataPanel.setVisible(false);
				migrationInfoPanel.setVisible(false);
				donePanel.setVisible(false);

				migrationTypePanel.setInitialFocus();

			} else {

				JOptionPane.showMessageDialog(null,
						"Digite as informa��es corretamente!", "Aten��o",
						JOptionPane.WARNING_MESSAGE);
				browse(--currentIndex);

			}

			break;

		case 4:

			// se os dados da migra��o forem validados com sucesso, o pain�l de
			// configura��o do software novo � habilitado
			if (migrationTypePanel.validateFields()) {

				welcomePanel.setVisible(false);
				customerNamePanel.setVisible(false);
				migrationTypePanel.setVisible(false);
				newSoftwarePanel.setVisible(true); // <----
				currentSoftwarePanel.setVisible(false);
				confirmDataPanel.setVisible(false);
				migrationInfoPanel.setVisible(false);
				donePanel.setVisible(false);

				newSoftwarePanel.setInitialFocus();

			} else {

				JOptionPane.showMessageDialog(null,
						"Digite as informa��es corretamente!", "Aten��o",
						JOptionPane.WARNING_MESSAGE);
				browse(--currentIndex);

			}

			break;

		case 5:

			// Se as configura��es do novo software forem validadas...
			if (newSoftwarePanel.validateFields()) {

				// Se as informa��es para conex�o do banco de dados do software
				// novo forem v�lidas, o pain�l de configura��o do software
				// atual � habilitado
				if (testConn(newSoftwarePanel.getDatabaseInfo())) {

					// dizendo � tela de escolha do software atual
					// para definir quais implementa��es de migra��o est�o
					// dispon�veis para o novo software
					currentSoftwarePanel.setAvailableSoftwares(newSoftwarePanel
							.getSoftware());

					welcomePanel.setVisible(false);
					customerNamePanel.setVisible(false);
					migrationTypePanel.setVisible(false);
					newSoftwarePanel.setVisible(false);
					currentSoftwarePanel.setVisible(true); // <----
					confirmDataPanel.setVisible(false);
					migrationInfoPanel.setVisible(false);
					donePanel.setVisible(false);

					currentSoftwarePanel.setInitialFocus();

				} else {

					JOptionPane.showMessageDialog(null,
							"Dados de conex�o inv�lidos!", "Falha de conex�o",
							JOptionPane.ERROR_MESSAGE);
					browse(--currentIndex);

				}

			} else {

				JOptionPane.showMessageDialog(null,
						"Digite as informa��es corretamente!", "Aten��o",
						JOptionPane.WARNING_MESSAGE);
				browse(--currentIndex);

			}

			if (btNext.getText().equals("Iniciar")) {
				btNext.setText("Avan�ar");
			}

			break;

		case 6:

			// Se as configura��es do software atual forem validadas...
			if (currentSoftwarePanel.validateFields()) {

				// Se as informa��es para conex�o do banco de dados do software
				// atual forem v�lidas, o pain�l de confirma��o das informa��es
				// � habilitado
				if (testConn(currentSoftwarePanel.getDatabaseInfo())) {

					// passa as informa��es dos softwares para confirma��o
					try {
						confirmDataPanel.setDB(newSoftwarePanel
								.getDatabaseInfo().getDbType(),
								currentSoftwarePanel.getDatabaseInfo()
										.getDbType());
					} catch (InvalidDatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					welcomePanel.setVisible(false);
					customerNamePanel.setVisible(false);
					migrationTypePanel.setVisible(false);
					newSoftwarePanel.setVisible(false);
					currentSoftwarePanel.setVisible(false);
					confirmDataPanel.setVisible(true); // <----
					migrationInfoPanel.setVisible(false);
					donePanel.setVisible(false);

					btNext.setText("Iniciar");

				} else {

					JOptionPane.showMessageDialog(null,
							"Dados de conex�o inv�lidos!", "Falha de conex�o",
							JOptionPane.ERROR_MESSAGE);
					browse(--currentIndex);

				}

			} else {

				JOptionPane.showMessageDialog(null,
						"Digite as informa��es corretamente!", "Aten��o",
						JOptionPane.WARNING_MESSAGE);
				browse(--currentIndex);

			}

			break;

		case 7:

			welcomePanel.setVisible(false);
			customerNamePanel.setVisible(false);
			migrationTypePanel.setVisible(false);
			newSoftwarePanel.setVisible(false);
			currentSoftwarePanel.setVisible(false);
			confirmDataPanel.setVisible(false);
			migrationInfoPanel.setVisible(true); // Mostrando o progresso da
													// migra��o
			donePanel.setVisible(false);

			btNext.setEnabled(false); // N�o permite avan�ar
			btPrevious.setEnabled(false); // N�o permite voltar

			// Escolhe a implementa��o adequada da interface IDAO baseada nas
			// informa��es dos softwares
			setDataProvider();

			// cria o ----> \o/ MOTOR DE MIGRA��O \o/ <----
			/*
			 * dao = new MigrationEngine(migrationTypePanel.getMigrationType(),
			 * this, migrationInfoPanel, migrationTypePanel, iDao,
			 * newSoftwarePanel, currentSoftwarePanel);
			 */
			// johnny Instanciar o motor
			dataProviderEngine = new MigrationEngine(
					migrationTypePanel.getMigrationType(), this,
					migrationInfoPanel, migrationTypePanel, dataProvider,
					newSoftwarePanel, currentSoftwarePanel);

			// guarda a data de in�cio da migra��o
			date = Calendar.getInstance();

			// d� a partida no motor!!! \o/
			dataProviderEngine.start();

			break;

		case 8:

			welcomePanel.setVisible(false);
			customerNamePanel.setVisible(false);
			migrationTypePanel.setVisible(false);
			newSoftwarePanel.setVisible(false);
			currentSoftwarePanel.setVisible(false);
			confirmDataPanel.setVisible(false);
			migrationInfoPanel.setVisible(false);
			donePanel.setVisible(true); // Informa que a migra��o foi conclu�da

			break;

		default:
			break;
		}

	}

	// Compara bancos de dados
	@Override
	public boolean validateDBTo(DatabaseType compare1, DatabaseType compare2) {
		return (compare1 == compare2);
	}

	@Override
	public boolean validateDBFrom(DatabaseType compare1, DatabaseType compare2) {
		return (compare1 == compare2);
	}

	// Navega para o �ltimo passo da migra��o
	@Override
	public void goToLastStep() {

		browse(++currentIndex);
		// habilita o bot�o de gera��o de relat�rio
		btReport.setVisible(true);
		btCancel.setText("Sair");

	}

	/**
	 * 
	 * M�todo que testa a conex�o com o banco de dados
	 * 
	 * @param cfg - Objeto com as informa��es sobre o banco de dados
	 * @return Retorna true se a conex�o foi bem sucedida. False caso contr�rio
	 * 
	 */
	private boolean testConn(DatabaseConfigurations cfg) {

		LogFile.getInstance().writeInFile(
				"Testing connection with "
						+ cfg.getDbType().toString().toLowerCase() + " in "
						+ cfg.getIpAddress());

		try {

			if (ConnectionFactory.getConnection(cfg.getDbType(), cfg) == null) {

				return false;

			} else {

				LogFile.getInstance().writeInFile(
						"Connection parameters are correct");
				return true;

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			LogFile.getInstance().writeInFile("Connection failed");
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogFile.getInstance().writeInFile(e.getMessage());
			LogFile.getInstance().writeInFile("Connection failed");
			return false;
		}

	}

	// M�todo que gera o relat�rio
	/**
	 * 
	 * M�todo que re�ne as informa��es para gerar o relat�rio
	 * 
	 */
	private void generateReport() {

		MigrationReportData report = new MigrationReportData();

		DatabaseConfigurations newSftCf = newSoftwarePanel.getDatabaseInfo();
		DatabaseConfigurations currStfCf = currentSoftwarePanel
				.getDatabaseInfo();

		// determina o nome do relat�rio
		report.setPath("Relat�rio de migra��o.pdf");
		// configura as informa��es do software atual
		report.setSoftwareFrom(currentSoftwarePanel.getSoftware());
		report.setDbTypeFrom(currStfCf.getDbType());
		report.setIpAddressFrom(currStfCf.getIpAddress());
		report.setPortFrom(currStfCf.getPort());
		if (currStfCf.getDbType() == DatabaseType.Firebird
				|| currStfCf.getDbType() == DatabaseType.MSAccess) {
			File f = new File(currStfCf.getDatabaseName());
			if (f.exists()) {
				report.setDbNameFrom(f.getName());
			} else {
				report.setDbNameFrom("(Arquivo inexistente)");
			}
		} else {
			report.setDbNameFrom(currStfCf.getDatabaseName());
		}
		report.setUsernameFrom(currStfCf.getUsername());
		report.setPasswordFrom(currStfCf.getPassword());
		// configura as informa��es do novo software
		report.setSoftwareTo(newSoftwarePanel.getSoftware());
		report.setDbTypeTo(newSftCf.getDbType());
		report.setIpAddressTo(newSftCf.getIpAddress());
		report.setPortTo(newSftCf.getPort());
		report.setDbNameTo(newSftCf.getDatabaseName());
		report.setUsernameTo(newSftCf.getUsername());
		report.setPasswordTo(newSftCf.getPassword());
		report.setData(date);
		report.setNomeCliente(customerNamePanel.getCustomerName());

		report.setAppend(migrationTypePanel.toAppend());

		ArrayList<MigrationDataType> dataTypes = new ArrayList<MigrationDataType>();
		ArrayList<EnMigrationDataType> mgType = migrationTypePanel
				.getMigrationType();
		Iterator<EnMigrationDataType> it = mgType.iterator();
		IMigrationResults results = dataProviderEngine;

		while (it.hasNext()) {

			switch (it.next()) {

			case PRODUTO:

				if (results.getCountProdutos() != SQLDataProvider.EMPTY_RETURN) {
					MigrationDataType dProduto = new MigrationDataType();
					dProduto.setType(EnMigrationDataType.PRODUTO);
					dProduto.setTotal(results.getCountProdutos());
					dataTypes.add(dProduto);
				}

				break;

			case FORNECEDOR:

				if (results.getCountFornecedores() != SQLDataProvider.EMPTY_RETURN) {
					MigrationDataType dFor = new MigrationDataType();
					dFor.setType(EnMigrationDataType.FORNECEDOR);
					dFor.setTotal(results.getCountFornecedores());
					dataTypes.add(dFor);
				}

				break;

			case CLIENTE:

				if (results.getCountClientes() != SQLDataProvider.EMPTY_RETURN) {
					MigrationDataType dCli = new MigrationDataType();
					dCli.setType(EnMigrationDataType.CLIENTE);
					dCli.setTotal(results.getCountClientes());
					dataTypes.add(dCli);
				}

				break;

			case DEPARTAMENTO:

				if (results.getCountDepartamento() != SQLDataProvider.EMPTY_RETURN) {
					MigrationDataType dDep = new MigrationDataType();
					dDep.setType(EnMigrationDataType.DEPARTAMENTO);
					dDep.setTotal(results.getCountDepartamento());
					dataTypes.add(dDep);
				}

				break;

			case GRUPO:

				if (results.getCountGrupo() != SQLDataProvider.EMPTY_RETURN) {
					MigrationDataType dGru = new MigrationDataType();
					dGru.setType(EnMigrationDataType.GRUPO);
					dGru.setTotal(results.getCountGrupo());
					dataTypes.add(dGru);
				}

				break;

			case MARCA:

				if (results.getCountMarca() != SQLDataProvider.EMPTY_RETURN) {
					MigrationDataType dMar = new MigrationDataType();
					dMar.setType(EnMigrationDataType.MARCA);
					dMar.setTotal(results.getCountClientes());
					dataTypes.add(dMar);
				}

				break;

			case ARMACAO:

				if (results.getCountArmacao() != SQLDataProvider.EMPTY_RETURN) {
					MigrationDataType dArm = new MigrationDataType();
					dArm.setType(EnMigrationDataType.ARMACAO);
					dArm.setTotal(results.getCountClientes());
					dataTypes.add(dArm);
				}

				break;

			default:

				break;

			}

		}

		report.setDataTypes(dataTypes);

		new PDFReport().generateReport(report);

	}

	@Override
	public void chooseSoftware() {
		// TODO Auto-generated method stub

	}

	@Override
	public EnSoftware getSoftware() {
		// TODO Auto-generated method stub
		return null;
	}

	// definir qual vai ser a implementa��o usada para a migra��o
	// baseada na escolha do software a ser usado e o software que � usado
	// atualmente
	/**
	 * 
	 * M�todo que re�ne as informa��es para criar o motor de migra��o
	 * 
	 */
	public void setDataProvider() {

		DatabaseType to = newSoftwarePanel.getDatabaseInfo().getDbType();
		DatabaseType from = currentSoftwarePanel.getDatabaseInfo().getDbType();
		EnSoftware software = newSoftwarePanel.getSoftware();
		EnSoftware otherSoftware = currentSoftwarePanel.getSoftware();

		// Instancia��o dos DAOs
		if (newSoftwarePanel.getSoftware() == EnSoftware.MERCOFLEX) {

			/*
			 * if (currentSoftwarePanel.getSoftware() == EnSoftware.Superus) {
			 * 
			 * dataProvider = new SuperusToMercoFlex(EnSoftware.MercoFlex,
			 * EnSoftware.Superus, to, from);
			 * 
			 * } else if (currentSoftwarePanel.getSoftware() ==
			 * EnSoftware.Versatho) {
			 * 
			 * dataProvider = new VersathoToMercoFlex(EnSoftware.MercoFlex,
			 * EnSoftware.Versatho, to, from);
			 * 
			 * } else if (currentSoftwarePanel.getSoftware() == EnSoftware.AES)
			 * {
			 * 
			 * dataProvider = new AESToMercoFlex(EnSoftware.MercoFlex,
			 * EnSoftware.AES, to, from);
			 * 
			 * } else if (currentSoftwarePanel.getSoftware() == EnSoftware.MRS)
			 * {
			 * 
			 * dataProvider = new MRSToMercoFlex(EnSoftware.MercoFlex,
			 * EnSoftware.MRS, to, from);
			 * 
			 * }
			 */

			dataProvider = new SQLDataProviderImpl(software, otherSoftware, to,
					from);

		} else if (newSoftwarePanel.getSoftware() == EnSoftware.MERCATTO) {

			dataProvider = new SQLDataProviderImpl(software, otherSoftware, to,
					from);

		}

	}

	@Override
	public void setAvailableSoftwares(EnSoftware software) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * Inicia a aplica��o
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {

		LogFile.getInstance().writeInFile("Application started");

		PlasticXPLookAndFeel laf = new PlasticXPLookAndFeel();
		PlasticXPLookAndFeel.setCurrentTheme(new SkyBluer());

		try {
			UIManager.setLookAndFeel(laf);
			DefaultDirectoryStructure cr = new DefaultDirectoryStructure();
			cr.createDefaultStructure();
			GZMigration m = new GZMigration();
			m.setVisible(true);
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

	}

}
