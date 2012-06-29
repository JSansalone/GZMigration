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
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

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

import br.com.gz.migration.file.DefaultDirectoryStructure;
import br.com.gz.migration.file.LogFile;
import br.com.gz.migration.report.MigrationBean;
import br.com.gz.migration.report.MigrationReport;
import br.com.gz.migration.report.MigrationReportData;
import br.com.gz.migration.software.SQLDataProviderImpl;
import br.com.gz.migration.steps.ConfirmDataPanel;
import br.com.gz.migration.steps.CurrentSoftwarePanel;
import br.com.gz.migration.steps.CustomerNamePanel;
import br.com.gz.migration.steps.DonePanel;
import br.com.gz.migration.steps.MigrationInfoPanel;
import br.com.gz.migration.steps.MigrationTypePanel;
import br.com.gz.migration.steps.NewSoftwarePanel;
import br.com.gz.migration.steps.WelcomePanel;
import br.com.gz.util.GZSoftwares;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;

/**
 * GZ Sistemas - Migração de banco de dados
 * 
 * Data de início: Março de 2012
 * 
 * Projetista responsável: Jonathan Sansalone Pacheco Rolim
 * 
 * Desenvolvedor responsável: Jonathan Sansalone Pacheco Rolim
 * 
 * Descrição básica do aplicativo:
 * 
 * Aplicativo que auxilia a migração de bancos de dados entre softwares de
 * terceiros e os softwares da GZ Sistemas.
 * 
 * 
 * Bancos de dados:
 * 
 * É habilitado para realizar conexões com os bancos: - Oracle - MySQL -
 * Microsoft SQL Server
 * 
 * @author Jonathan Sansalone
 * 
 */
public class GZMigration extends JFrame implements IFinalizeMigration,
		ISoftwareMutable {

	/**
	 * Representa o container principal
	 */
	private Container wnd;

	// Botões de navegação
	/**
	 * Botão para navegar ao próximo passo
	 */
	private JButton btNext;

	/**
	 * Botão para navegar ao passo anterior
	 */
	private JButton btPrevious;

	/**
	 * Botão para cancelar a migração
	 */
	private JButton btCancel;

	/**
	 * Botão que abre a janela de ajuda: {@link HelpFrame}
	 */
	private JButton btHelp;

	/**
	 * Botão que abre a janela de informações do aplicativo: {@link AboutFrame}
	 */
	private JButton btInfo;

	/**
	 * Botão que gera o relatório ao terminar a migração
	 */
	private JButton btReport;

	// Componentes compartilhados pelos painéis de navegação
	/**
	 * Dimensão padrão usada pelos painéis internos
	 */
	public static final Dimension DIMENSION_PANEL = new Dimension(700, 285);

	/**
	 * Point padrão usado pelos painéis internos
	 */
	public static final Point POINT_PANEL = new Point(0, 93);

	/**
	 * Fonte usada pelos painéis internos nos títulos
	 */
	public static final Font TITLE_FONT = new Font("Tahoma", Font.BOLD, 16);

	// Variáveis de referência dos painéis de navegação
	/**
	 * Painél de boas vindas
	 */
	private WelcomePanel welcomePanel;

	/**
	 * Painél de digitação do nome do cliente
	 */
	private CustomerNamePanel customerNamePanel;

	/**
	 * Painél de escolha dos tipos de dados
	 */
	private MigrationTypePanel migrationTypePanel;

	/**
	 * Painél de configuração do banco de dados do novo software
	 */
	private NewSoftwarePanel newSoftwarePanel;

	/**
	 * Painél de configuração do banco de dados do software atual
	 */
	@Deprecated
	private CurrentSoftwarePanel currentSoftwarePanel;

	/**
	 * Painél de confirmação das informações digitadas
	 */
	private ConfirmDataPanel confirmDataPanel;

	/**
	 * Painél que mostra a porcentagem de conclusão da migração enquanto esta
	 * está sendo realizada
	 */
	private MigrationInfoPanel migrationInfoPanel;

	/**
	 * Painél que mostra a mensagem de conclusão da migração
	 */
	private DonePanel donePanel;

	// Variável de referência do motor de migração
	/**
	 * Representa o motor de migração
	 */
	private MigrationEngine dataProviderEngine;

	// Variável de referência da interface de migração
	/**
	 * Representa o objeto que busca e insere os dados
	 */
	private SQLDataProvider dataProvider;

	// índices de navegação dos painéis
	// guarda o índice corrente
	/**
	 * Índice que guarda o passo atual
	 */
	private int currentIndex = 1;

	// guarda o índice limite
	/**
	 * Guarda o índice limite
	 */
	private final int limitIndex = 7;// 8

	// Variável de referência que guardará a data de inicialização do motor de
	// migração
	/**
	 * Guarda a data de inicialização do motor de migração
	 */
	private Calendar date;

	/**
	 * Construtor Default que inicializa as propriedades da janela principal e
	 * cria os componentes
	 */
	public GZMigration() {

		// Definindo propriedades da janela
		setSize(700, 450);
		setResizable(false);
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/img/common/icon.gif")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("GZ Sistemas - Migração de banco de dados");

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

		// Criação dos botões de navegação
		btNext = new JButton("Avançar", new ImageIcon((URL) getClass()
				.getResource("/img/navigation/next.png")));
		btNext.setBounds(getWidth() - 115, getHeight() - 70, 100, 30);
		btNext.setHorizontalTextPosition(AbstractButton.LEFT);
		btNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setCursor(Cursor.WAIT_CURSOR);

				// se o índice corrente não chegar ao limite, continua
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

				// se o índice corrente não chegar ao primeiro, continua
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

		// Exibe informações sobre o aplicativo
		btInfo = new JButton(new ImageIcon((URL) getClass().getResource(
				"/img/navigation/info.png")));
		btInfo.setBounds(50, getHeight() - 70, 30, 30);
		btInfo.setToolTipText("Sobre");
		btInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				LogFile.getInstance().writeInFile("Showing about frame");
				new AboutFrame();

			}

		});

		// Exibe instruções de utilização
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
		btReport.setToolTipText("Gerar relatório de migração");
		btReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setCursor(Cursor.WAIT_CURSOR);

				// Chama o método de gerar relatório
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

		// painéis
		welcomePanel = new WelcomePanel();
		welcomePanel.setVisible(true);
		wnd.add(welcomePanel);

		customerNamePanel = new CustomerNamePanel();
		wnd.add(customerNamePanel);

		// criação do painél que permite a escolha dos dados a serem migrados e
		// a quantidade de lojas
		migrationTypePanel = new MigrationTypePanel();
		wnd.add(migrationTypePanel);

		// criação do painél que permite a configuração do banco de dados do
		// novo software
		newSoftwarePanel = new NewSoftwarePanel();
		wnd.add(newSoftwarePanel);

		// criação do painél que permite a configuração do banco de dados do
		// software atual
		// currentSoftwarePanel = new CurrentSoftwarePanel(this);
		// wnd.add(currentSoftwarePanel);

		// criação do painél que permite a confirmação dos dados digitados
		confirmDataPanel = new ConfirmDataPanel();
		wnd.add(confirmDataPanel);

		// criação do painél que mostra o andamento da migração
		migrationInfoPanel = new MigrationInfoPanel();
		wnd.add(migrationInfoPanel);

		// criação do painél que mostra que a migração foi concluída e habilita
		// o botão de geração de relatório
		donePanel = new DonePanel();
		wnd.add(donePanel);
		// ----------------------------------

	}

	/**
	 * 
	 * Método que navega entre as etapas da migração. Gerencia a visibilidade
	 * dos painéis
	 * 
	 * @param index
	 *            - índice de navegação
	 * 
	 */
	private void browse(int index) {

		switch (index) {
		case 1:

			welcomePanel.setVisible(true); // painél de boas vindas
			customerNamePanel.setVisible(false);
			migrationTypePanel.setVisible(false);
			newSoftwarePanel.setVisible(false);
			confirmDataPanel.setVisible(false);
			migrationInfoPanel.setVisible(false);
			donePanel.setVisible(false);

			break;

		case 2:

			welcomePanel.setVisible(false);
			customerNamePanel.setVisible(true);// <----
			migrationTypePanel.setVisible(false);
			newSoftwarePanel.setVisible(false);
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
														// migração
				newSoftwarePanel.setVisible(false);
				confirmDataPanel.setVisible(false);
				migrationInfoPanel.setVisible(false);
				donePanel.setVisible(false);

				migrationTypePanel.setInitialFocus();

			} else {

				JOptionPane.showMessageDialog(null,
						"Digite as informações corretamente!", "Atenção",
						JOptionPane.WARNING_MESSAGE);
				browse(--currentIndex);

			}

			break;

		case 4:

			// se os dados da migração forem validados com sucesso, o painél de
			// configuração do software novo é habilitado
			if (migrationTypePanel.validateFields()) {

				welcomePanel.setVisible(false);
				customerNamePanel.setVisible(false);
				migrationTypePanel.setVisible(false);
				newSoftwarePanel.setVisible(true); // <----
				confirmDataPanel.setVisible(false);
				migrationInfoPanel.setVisible(false);
				donePanel.setVisible(false);

				newSoftwarePanel.setInitialFocus();

			} else {

				JOptionPane.showMessageDialog(null,
						"Digite as informações corretamente!", "Atenção",
						JOptionPane.WARNING_MESSAGE);
				browse(--currentIndex);

			}

			break;

		case 5:

			// Se as configurações do novo software forem validadas...
			if (newSoftwarePanel.validateFields()) {

				// Se as informações para conexão do banco de dados do software
				// novo forem válidas, o painél de configuração do software
				// atual é habilitado
				if (testConn(newSoftwarePanel.getDatabaseInfo())) {

					confirmDataPanel.configure(
							migrationTypePanel.getMigrationType(),
							newSoftwarePanel.getDatabaseInfo().getDbType());

					welcomePanel.setVisible(false);
					customerNamePanel.setVisible(false);
					migrationTypePanel.setVisible(false);
					newSoftwarePanel.setVisible(false);
					confirmDataPanel.setVisible(true); // <----
					migrationInfoPanel.setVisible(false);
					donePanel.setVisible(false);

				} else {

					JOptionPane.showMessageDialog(null,
							"Dados de conexão inválidos!", "Falha de conexão",
							JOptionPane.ERROR_MESSAGE);
					browse(--currentIndex);

				}

			} else {

				JOptionPane.showMessageDialog(null,
						"Digite as informações corretamente!", "Atenção",
						JOptionPane.WARNING_MESSAGE);
				browse(--currentIndex);

			}

			if (btNext.getText().equals("Iniciar")) {
				btNext.setText("Avançar");
			}

			break;

		case 6:

			welcomePanel.setVisible(false);
			customerNamePanel.setVisible(false);
			migrationTypePanel.setVisible(false);
			newSoftwarePanel.setVisible(false);
			confirmDataPanel.setVisible(false);
			migrationInfoPanel.setVisible(true); // Mostrando o progresso da
													// migração
			donePanel.setVisible(false);

			btNext.setEnabled(false); // Não permite avançar
			btPrevious.setEnabled(false); // Não permite voltar

			// Escolhe a implementação adequada da interface IDAO baseada nas
			// informações dos softwares
			setDataProvider();

			// cria o ----> \o/ MOTOR DE MIGRAÇÃO \o/ <----
			dataProviderEngine = new MigrationEngine(
					migrationTypePanel.getMigrationType(), this,
					migrationInfoPanel, migrationTypePanel, dataProvider,
					newSoftwarePanel);

			// guarda a data de início da migração
			date = Calendar.getInstance();

			// dá a partida no motor!!! \o/
			dataProviderEngine.start();

			break;

		case 7:

			welcomePanel.setVisible(false);
			customerNamePanel.setVisible(false);
			migrationTypePanel.setVisible(false);
			newSoftwarePanel.setVisible(false);
			confirmDataPanel.setVisible(false);
			migrationInfoPanel.setVisible(false);
			donePanel.setVisible(true); // Informa que a migração foi concluída

			break;

		default:
			break;
		}

	}

	// Navega para o último passo da migração
	@Override
	public void goToLastStep() {

		browse(++currentIndex);
		// habilita o botão de geração de relatório
		btReport.setVisible(true);
		btCancel.setText("Sair");

	}

	/**
	 * 
	 * Método que testa a conexão com o banco de dados
	 * 
	 * @param cfg
	 *            - Objeto com as informações sobre o banco de dados
	 * @return Retorna true se a conexão foi bem sucedida. False caso contrário
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

	// Método que gera o relatório
	/**
	 * 
	 * Método que reúne as informações para gerar o relatório
	 * 
	 */
	private void generateReport() {

		ArrayList<EnMigrationDataType> ar = migrationTypePanel
				.getMigrationType();
		Collection<MigrationBean> col = new ArrayList<MigrationBean>();

		MigrationBean bean;

		for (EnMigrationDataType em : ar) {

			bean = new MigrationBean();

			switch (em) {

			case PRODUTO:

				bean.setNome(em.getDescription());
				bean.setTotalCadastrado(dataProviderEngine
						.getCountRegisteredProdutos());
				bean.setTotalIncluido(dataProviderEngine
						.getCountIncludedProdutos());

				break;

			case DEPARTAMENTO:

				bean.setNome(em.getDescription());
				bean.setTotalCadastrado(dataProviderEngine
						.getCountDepartamento());
				bean.setTotalIncluido(0);

				break;

			case GRUPO:

				bean.setNome(em.getDescription());
				bean.setTotalCadastrado(dataProviderEngine.getCountGrupo());
				bean.setTotalIncluido(0);

				break;

			case ARMACAO:

				bean.setNome(em.getDescription());
				bean.setTotalCadastrado(dataProviderEngine.getCountArmacao());
				bean.setTotalIncluido(0);

				break;

			case MARCA:

				bean.setNome(em.getDescription());
				bean.setTotalCadastrado(dataProviderEngine.getCountMarca());
				bean.setTotalIncluido(0);

				break;

			case SETOR:

				bean.setNome(em.getDescription());
				bean.setTotalCadastrado(0);
				bean.setTotalIncluido(0);

				break;

			case CLIENTE:

				bean.setNome(em.getDescription());
				bean.setTotalCadastrado(dataProviderEngine.getCountClientes());
				bean.setTotalIncluido(0);

				break;

			case FORNECEDOR:

				bean.setNome(em.getDescription());
				bean.setTotalCadastrado(dataProviderEngine
						.getCountFornecedores());
				bean.setTotalIncluido(0);

				break;

			}

			col.add(bean);

		}

		DatabaseConfigurations newSftCf = newSoftwarePanel.getDatabaseInfo();

		MigrationReportData reportData = new MigrationReportData(col);

		reportData.setIp(newSftCf.getIpAddress());
		reportData.setNomeBanco(newSftCf.getDatabaseName());
		reportData.setPorta(newSftCf.getPort());
		reportData.setNomeSistema(newSoftwarePanel.getSoftware());
		reportData.setUsuario(newSftCf.getUsername());
		reportData.setSenha(newSftCf.getPassword());
		reportData.setTipoBanco(newSftCf.getDbType());
		reportData.setModoMigracao(migrationTypePanel.toAppend());
		reportData.setNomeCliente(customerNamePanel.getCustomerName());

		MigrationReport report = new MigrationReport(reportData);
		report.buildReport();

	}

	@Override
	public void chooseSoftware() {
		// TODO Auto-generated method stub

	}

	@Override
	public GZSoftwares getSoftware() {
		// TODO Auto-generated method stub
		return null;
	}

	// definir qual vai ser a implementação usada para a migração
	// baseada na escolha do software a ser usado e o software que é usado
	// atualmente
	/**
	 * 
	 * Método que reúne as informações para criar o motor de migração
	 * 
	 */
	public void setDataProvider() {

		DatabaseType to = newSoftwarePanel.getDatabaseInfo().getDbType();
		GZSoftwares software = newSoftwarePanel.getSoftware();

		dataProvider = new SQLDataProviderImpl(software, to);

	}

	@Override
	public void setAvailableSoftwares(GZSoftwares software) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * Inicia a aplicação
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
