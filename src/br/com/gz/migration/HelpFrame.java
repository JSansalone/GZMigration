package br.com.gz.migration;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Classe que representa a janela de ajuda
 * 
 * @author Jonathan Sansalone
 *
 */
public class HelpFrame extends JFrame implements ActionListener {

	/**
	 * Instância da classe
	 */
	private static HelpFrame instance;

	/**
	 * Container principal
	 */
	private Container window;
	
	/**
	 * Painél principal
	 */
	private JPanel panelWindow;
	
	/**
	 * Scroll que armazena os dados de ajuda
	 */
	private JScrollPane scrollTextArea;
	
	/**
	 * Área de texto de ajuda
	 */
	private JTextArea areaHelp;

	/**
	 * Labels que representam os links de ajuda
	 */
	private JLabel[] labels = new JLabel[4];
	
	/**
	 * Títulos dos links de ajuda
	 */
	private String[] lblTexts = { "Guia rápido", "Bancos de dados", "Softwares",
			"Compatibilidade" };
	/**
	 * Conteúdo da ajuda
	 */
	private String[] lblContents = new String[4];

	/**
	 * Botão para sair da ajuda
	 */
	private JButton btOk;

	/**
	 * Construtor default
	 */
	private HelpFrame() {

		setSize(600, 365);
		setTitle("Ajuda");
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/img/navigation/help.png")));

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();

		int width = screenSize.width;
		int heigth = screenSize.height;
		int contWidth = getWidth();
		int contHeidth = getHeight();

		setLocation((width - contWidth) / 2, (heigth - contHeidth) / 2);

		contents();
		instantiatingComponents();
		instantiatingLabels();
		addMouseListeners();

	}

	/**
	 * Método que implementa o singleton e retorna uma instância da classe
	 * 
	 * @return - Uma instância da classe
	 */
	public static HelpFrame getInstance() {

		if (instance == null) {

			instance = new HelpFrame();

		}

		return instance;

	}

	/**
	 * Método que monta o conteúdo da ajuda
	 */
	private void contents() {

		lblContents[0] = "" +
				"Bem vindo!\n\n" +
				"      Você está utilizando um aplicativo que auxilia a migração de bancos de dados entre softwares de automação comercial.\n" +
				"      Este guia o ajudará a realizar uma migração de bancos de dados de maneira fácil e rápida." +
				" Para dúvidas sobre bancos de dados, instalação e restauração de backups, visite a guia Bancos de dados.\n      Para começar, clique em Avançar para iniciar o processo. Você está agora na janela de escolha dos tipos de dados a serem migrados e a quantidade de lojas." +
				" Escolha quais tipos de dados deseja migrar, digite a quantidade de lojas que terão os dados migrados e escolha se quer que os dados a serem migrados sobreponham os dados já existentes. Por exemplo, escolhendo " +
				"produtos para serem migrados, definindo 3 lojas para a migração e escolhendo a opção de anexar, os produtos migrados serão cadastrados para novas 3 lojas, supondo que exista 2 lojas já existentes no banco de dados, os dados específicos de cada loja serão inseridos para as lojas 3, 4 e 5. Após ter feito isso, clique em avançar.\n" +
				"      Agora digite as informações sobre o novo software que será implantado, digite corretamente o nome do banco de dados, escolha o tipo de banco de dados no botão ao lado, escolha qual o software que será implantado, a porta de conexão do banco de dados, o usuário e a senha." +
				" Não se preocupe, caso tenha inserido alguma informação incorreta, o aplicativo o avisará ao tentar avançar. Após ter digitado as informações, clique em avançar.\n" +
				"      Nesta janela, digite as informações corretamente sobre o software que é usado atualmente. A maneira de inserir as informações é semelhante à janela anterior. Todos os softwares presentes nesta janela estão disponíveis para serem migrados. Se não encontrar o software que procura, é porque o software a ser implantado ainda não suporta a estrutura do banco de dados deste. Entre em contato com o desenvolvedor e solicite uma implementação caso necessário.\n" +
				"      Na janela a seguir, confirme as informações sobre os tipos de bancos de dados que escolheu. Se estiverem corretas, clique em Iniciar para iniciar a migração e aguarde.\n" +
				"      Ao terminar a migração, clique no botão que foi habilitado para gerar um relatório que mostrará as estatísticas da migração se desejar.";
		lblContents[1] = "" +
				"O aplicativo trabalha com os seguintes bancos de dados:\n\n" +
				" - Oracle (destino e origem)\n" +
				" - MySQL (destino e origem)\n" +
				" - SQL Server (destino e origem)\n" +
				" - PostgreSQL (origem)\n" +
				" - Firebird (origem)\n" +
				" - Access (origem)\n\n" +
				"Destino - O aplicativo está apto para inserir os dados da migração neste banco de dados.\n" +
				"Origem - O aplicativo está apto para recuperar as informações a serem migradas deste banco de dados.\n\n" +
				"Instruções gerais sobre instalação, configuração e utilização:\n\n" +
				"MySQL:\n\n" +
				"  Você pode baixar e instalar o MySQL em: http://dev.mysql.com/downloads/mysql/ .\n" +
				"  Para entrar no banco de dados pelo console, entre no prompt de comando (Windows) ou no Terminal (Linux) e digite:\n" +
				"  - mysql -u<usuário> -p<senha> [-h<ipdoservidor>]\n" +
				"  Onde:\n" +
				"  - <usuário> é o nome de usuário de acesso ao banco.\n" +
				"  - <senha> senha do usuário.\n" +
				"  - -h<ipdoservidor> é o IP do servidor de banco de dados (opcional).\n\n" +
				"  O padrão de instalação da GZ Sistemas define a senha como 'mestre' para o usuário 'root'.\n\n" +
				"PostgreSQL:\n\n" +
				"  Você pode baixar e instalar o PostgreSQL em: www.postgresql.org/download .\n" +
				"  O usuário padrão do PostgreSQL é 'progres' com senha 'postgres'.\n\n" +
				"Firebird:\n\n" +
				"  Você pode baixar e instalar o Firebird em: www.firebirdsql.org/en/firebird-2-5-1/ .\n" +
				"  Ao se deparar com uma migração de banco de dados Firebird, você deverá apontar o caminho onde se encontra o arquivo com extensão .fdb ou .gdb que representa um banco de dados do Firebird.\n\n" +
				"Access:\n\n" +
				"  Ao se deparar com uma migração de banco de dados em Access, é necessário criar uma Fonte de Dados ODBC e referenciá-la no aplicativo. Para criar uma fonte de dados, execute os seguintes passos:\n" +
				"  - Vá em Menu Iniciar > Painél de Controle;\n" +
				"  - Vá em Ferramentas Administrativas;\n" +
				"  - Vá em Fontes de Dados ODBC;\n" +
				"  - Na janela que se abre, clique na aba Fonte de dados de sistema;\n" +
				"  - Clique em Adicionar;\n" +
				"  - Escolha a opção 'Driver do Microsoft Access (*.mdb)'. Se não houver, escolha outra opção que contenha a extensão .mdb e clique em Concluir;\n" +
				"  - Digite o nome que desejar para a fonte de dados;\n" +
				"  - (opcional) Digite a descrição da fonte de dados;\n" +
				"  - Clique em Selecionar... e procure o arquivo de extensão .mdb que você possui, selecione-o e clique em OK;\n" +
				"  - Clique em OK para concluir.";
		lblContents[2] = "O aplicativo trabalha com os seguintes softwares de automação comercial:\n\n" +
				"Softwares da GZ Sistemas:\n" +
				"  - MercoFlex\n" +
				"  - Mercatto\n\n" +
				"Softwares de terceiros:\n" +
				"  - AES\n" +
				"  - Superus\n" +
				"  - Versatho\n" +
				"  - MRV\n\n" +
				"";
		lblContents[3] = 
				"Compatibilidade de versão:\n\n" +
				"  MercoFlex:\n" +
				"  - MercoFlex (Anterior a 02.09.B2 - 2012.05.22.01) - AES\n" +
				"  - MercoFlex (Anterior a 02.09.B2 - 2012.05.22.01) - Superus\n" +
				"  - MercoFlex (02.09.B2 - 2012.05.22.01) - Versatho\n" +
				"  - MercoFlex (Anterior a 02.09.B2 - 2012.05.22.01) - MRV\n\n" +
				"  Mercatto:\n" +
				"  - Sem dados disponíveis" +
				"";

	}

	/**
	 * Método que inicializa os componentes
	 */
	private void instantiatingLabels() {

		for (int i = 0; i <= 3; i++) {

			labels[i] = new JLabel(lblTexts[i]);

			labels[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

			panelWindow.add(labels[i]);

		}

		labels[0].setBounds(15, 15, 150, 20);
		labels[1].setBounds(15, 55, 150, 20);
		labels[2].setBounds(15, 100, 150, 20);
		labels[3].setBounds(15, 145, 150, 20);

		btOk = new JButton("Sair");

		btOk.setBounds(15, 295, 70, 20);
		btOk.addActionListener(this);

		panelWindow.add(btOk);

	}

	/**
	 * Guarda o índice do link clicado
	 */
	private static int indexLabelClicked = -1;

	/**
	 * Trata o link clicado
	 * 
	 * @param index - índice do link
	 */
	private void setLabelClicked(int index) {

		indexLabelClicked = index;

		for (int i = 0; i <= 3; i++) {

			if (i == index) {

				labels[i].setText("<html><u><b>" + lblTexts[i]
						+ "</html></b></u>");

			} else {

				labels[i].setText(lblTexts[i]);

			}

		}

	}

	/**
	 * Adiciona os listeners aos JLabel's
	 */
	private void addMouseListeners() {

		labels[0].addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (indexLabelClicked != 0) {

					labels[0].setText(lblTexts[0]);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (indexLabelClicked != 0) {

					labels[0]
							.setText("<html><u>" + lblTexts[0] + "</u></html>");

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				setLabelClicked(0);
				setContents();

			}
		});

		labels[1].addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (indexLabelClicked != 1) {

					labels[1].setText(lblTexts[1]);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (indexLabelClicked != 1) {

					labels[1]
							.setText("<html><u>" + lblTexts[1] + "</u></html>");

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setLabelClicked(1);
				setContents();

			}
		});

		labels[2].addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (indexLabelClicked != 2) {

					labels[2].setText(lblTexts[2]);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (indexLabelClicked != 2) {

					labels[2]
							.setText("<html><u>" + lblTexts[2] + "</u></html>");

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setLabelClicked(2);
				setContents();

			}
		});

		labels[3].addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				if (indexLabelClicked != 3) {

					labels[3].setText(lblTexts[3]);

				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {

				if (indexLabelClicked != 3) {

					labels[3]
							.setText("<html><u>" + lblTexts[3] + "</u></html>");

				}

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setLabelClicked(3);
				setContents();

			}
		});

	}

	/**
	 * Instancia os componentes principais
	 */
	private void instantiatingComponents() {

		window = getContentPane();
		panelWindow = new JPanel(null);
		areaHelp = new JTextArea();
		scrollTextArea = new JScrollPane(areaHelp);

		scrollTextArea.setBounds(165, 15, 410, 300);

		areaHelp.setEditable(false);
		areaHelp.setText(lblContents[0]);
		areaHelp.setLineWrap(true);
		areaHelp.setWrapStyleWord(true);

		panelWindow.add(scrollTextArea);
		window.add(panelWindow);

	}

	/**
	 * Muda o texto da área de texto de acordo com o link clicado
	 */
	private void setContents() {

		areaHelp.setText(lblContents[indexLabelClicked]);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btOk) {

			dispose();

		}

	}

}
