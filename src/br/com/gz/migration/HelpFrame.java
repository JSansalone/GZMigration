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
	 * Inst�ncia da classe
	 */
	private static HelpFrame instance;

	/**
	 * Container principal
	 */
	private Container window;
	
	/**
	 * Pain�l principal
	 */
	private JPanel panelWindow;
	
	/**
	 * Scroll que armazena os dados de ajuda
	 */
	private JScrollPane scrollTextArea;
	
	/**
	 * �rea de texto de ajuda
	 */
	private JTextArea areaHelp;

	/**
	 * Labels que representam os links de ajuda
	 */
	private JLabel[] labels = new JLabel[4];
	
	/**
	 * T�tulos dos links de ajuda
	 */
	private String[] lblTexts = { "Guia r�pido", "Bancos de dados", "Softwares",
			"Compatibilidade" };
	/**
	 * Conte�do da ajuda
	 */
	private String[] lblContents = new String[4];

	/**
	 * Bot�o para sair da ajuda
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
	 * M�todo que implementa o singleton e retorna uma inst�ncia da classe
	 * 
	 * @return - Uma inst�ncia da classe
	 */
	public static HelpFrame getInstance() {

		if (instance == null) {

			instance = new HelpFrame();

		}

		return instance;

	}

	/**
	 * M�todo que monta o conte�do da ajuda
	 */
	private void contents() {

		lblContents[0] = "" +
				"Bem vindo!\n\n" +
				"      Voc� est� utilizando um aplicativo que auxilia a migra��o de bancos de dados entre softwares de automa��o comercial.\n" +
				"      Este guia o ajudar� a realizar uma migra��o de bancos de dados de maneira f�cil e r�pida." +
				" Para d�vidas sobre bancos de dados, instala��o e restaura��o de backups, visite a guia Bancos de dados.\n      Para come�ar, clique em Avan�ar para iniciar o processo. Voc� est� agora na janela de escolha dos tipos de dados a serem migrados e a quantidade de lojas." +
				" Escolha quais tipos de dados deseja migrar, digite a quantidade de lojas que ter�o os dados migrados e escolha se quer que os dados a serem migrados sobreponham os dados j� existentes. Por exemplo, escolhendo " +
				"produtos para serem migrados, definindo 3 lojas para a migra��o e escolhendo a op��o de anexar, os produtos migrados ser�o cadastrados para novas 3 lojas, supondo que exista 2 lojas j� existentes no banco de dados, os dados espec�ficos de cada loja ser�o inseridos para as lojas 3, 4 e 5. Ap�s ter feito isso, clique em avan�ar.\n" +
				"      Agora digite as informa��es sobre o novo software que ser� implantado, digite corretamente o nome do banco de dados, escolha o tipo de banco de dados no bot�o ao lado, escolha qual o software que ser� implantado, a porta de conex�o do banco de dados, o usu�rio e a senha." +
				" N�o se preocupe, caso tenha inserido alguma informa��o incorreta, o aplicativo o avisar� ao tentar avan�ar. Ap�s ter digitado as informa��es, clique em avan�ar.\n" +
				"      Nesta janela, digite as informa��es corretamente sobre o software que � usado atualmente. A maneira de inserir as informa��es � semelhante � janela anterior. Todos os softwares presentes nesta janela est�o dispon�veis para serem migrados. Se n�o encontrar o software que procura, � porque o software a ser implantado ainda n�o suporta a estrutura do banco de dados deste. Entre em contato com o desenvolvedor e solicite uma implementa��o caso necess�rio.\n" +
				"      Na janela a seguir, confirme as informa��es sobre os tipos de bancos de dados que escolheu. Se estiverem corretas, clique em Iniciar para iniciar a migra��o e aguarde.\n" +
				"      Ao terminar a migra��o, clique no bot�o que foi habilitado para gerar um relat�rio que mostrar� as estat�sticas da migra��o se desejar.";
		lblContents[1] = "" +
				"O aplicativo trabalha com os seguintes bancos de dados:\n\n" +
				" - Oracle (destino e origem)\n" +
				" - MySQL (destino e origem)\n" +
				" - SQL Server (destino e origem)\n" +
				" - PostgreSQL (origem)\n" +
				" - Firebird (origem)\n" +
				" - Access (origem)\n\n" +
				"Destino - O aplicativo est� apto para inserir os dados da migra��o neste banco de dados.\n" +
				"Origem - O aplicativo est� apto para recuperar as informa��es a serem migradas deste banco de dados.\n\n" +
				"Instru��es gerais sobre instala��o, configura��o e utiliza��o:\n\n" +
				"MySQL:\n\n" +
				"  Voc� pode baixar e instalar o MySQL em: http://dev.mysql.com/downloads/mysql/ .\n" +
				"  Para entrar no banco de dados pelo console, entre no prompt de comando (Windows) ou no Terminal (Linux) e digite:\n" +
				"  - mysql -u<usu�rio> -p<senha> [-h<ipdoservidor>]\n" +
				"  Onde:\n" +
				"  - <usu�rio> � o nome de usu�rio de acesso ao banco.\n" +
				"  - <senha> senha do usu�rio.\n" +
				"  - -h<ipdoservidor> � o IP do servidor de banco de dados (opcional).\n\n" +
				"  O padr�o de instala��o da GZ Sistemas define a senha como 'mestre' para o usu�rio 'root'.\n\n" +
				"PostgreSQL:\n\n" +
				"  Voc� pode baixar e instalar o PostgreSQL em: www.postgresql.org/download .\n" +
				"  O usu�rio padr�o do PostgreSQL � 'progres' com senha 'postgres'.\n\n" +
				"Firebird:\n\n" +
				"  Voc� pode baixar e instalar o Firebird em: www.firebirdsql.org/en/firebird-2-5-1/ .\n" +
				"  Ao se deparar com uma migra��o de banco de dados Firebird, voc� dever� apontar o caminho onde se encontra o arquivo com extens�o .fdb ou .gdb que representa um banco de dados do Firebird.\n\n" +
				"Access:\n\n" +
				"  Ao se deparar com uma migra��o de banco de dados em Access, � necess�rio criar uma Fonte de Dados ODBC e referenci�-la no aplicativo. Para criar uma fonte de dados, execute os seguintes passos:\n" +
				"  - V� em Menu Iniciar > Pain�l de Controle;\n" +
				"  - V� em Ferramentas Administrativas;\n" +
				"  - V� em Fontes de Dados ODBC;\n" +
				"  - Na janela que se abre, clique na aba Fonte de dados de sistema;\n" +
				"  - Clique em Adicionar;\n" +
				"  - Escolha a op��o 'Driver do Microsoft Access (*.mdb)'. Se n�o houver, escolha outra op��o que contenha a extens�o .mdb e clique em Concluir;\n" +
				"  - Digite o nome que desejar para a fonte de dados;\n" +
				"  - (opcional) Digite a descri��o da fonte de dados;\n" +
				"  - Clique em Selecionar... e procure o arquivo de extens�o .mdb que voc� possui, selecione-o e clique em OK;\n" +
				"  - Clique em OK para concluir.";
		lblContents[2] = "O aplicativo trabalha com os seguintes softwares de automa��o comercial:\n\n" +
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
				"Compatibilidade de vers�o:\n\n" +
				"  MercoFlex:\n" +
				"  - MercoFlex (Anterior a 02.09.B2 - 2012.05.22.01) - AES\n" +
				"  - MercoFlex (Anterior a 02.09.B2 - 2012.05.22.01) - Superus\n" +
				"  - MercoFlex (02.09.B2 - 2012.05.22.01) - Versatho\n" +
				"  - MercoFlex (Anterior a 02.09.B2 - 2012.05.22.01) - MRV\n\n" +
				"  Mercatto:\n" +
				"  - Sem dados dispon�veis" +
				"";

	}

	/**
	 * M�todo que inicializa os componentes
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
	 * Guarda o �ndice do link clicado
	 */
	private static int indexLabelClicked = -1;

	/**
	 * Trata o link clicado
	 * 
	 * @param index - �ndice do link
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
	 * Muda o texto da �rea de texto de acordo com o link clicado
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
