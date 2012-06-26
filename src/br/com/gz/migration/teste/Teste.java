package br.com.gz.migration.teste;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;

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

import org.database.connection.DatabaseType;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.file.LogFile;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;

public class Teste extends JFrame {

	private Container wnd;
	private JButton btNext;
	private JButton btPrevious;
	private JButton btCancel;
	private MyConfirmDataPanel confirmDataPanel;

	public Teste() {

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

		// Criação dos botões de navegação
		btNext = new JButton("Avançar", new ImageIcon((URL) getClass()
				.getResource("/img/navigation/next.png")));
		btNext.setBounds(getWidth() - 115, getHeight() - 70, 100, 30);
		btNext.setHorizontalTextPosition(AbstractButton.LEFT);

		btPrevious = new JButton("Voltar", new ImageIcon((URL) getClass()
				.getResource("/img/navigation/previous.png")));
		btPrevious.setBounds(getWidth() - btNext.getWidth() - 120,
				getHeight() - 70, 100, 30);

		btCancel = new JButton("Cancelar", new ImageIcon((URL) getClass()
				.getResource("/img/navigation/cancel.png")));
		btCancel.setToolTipText("Cancelar e sair");
		btCancel.setBounds(
				getWidth() - btPrevious.getWidth() - btNext.getWidth() - 125,
				getHeight() - 70, 100, 30);

		wnd.add(btNext);
		wnd.add(btPrevious);
		wnd.add(btCancel);

		ArrayList<EnMigrationDataType> ar = new ArrayList<EnMigrationDataType>();
		
		for (EnMigrationDataType e : EnMigrationDataType.values()) {
			ar.add(e);
		}
		
		confirmDataPanel = new MyConfirmDataPanel();
		confirmDataPanel.configure(ar, DatabaseType.MySQL);
		wnd.add(confirmDataPanel);
		confirmDataPanel.setVisible(true);

	}

	public static void main(String[] args) {

		PlasticXPLookAndFeel laf = new PlasticXPLookAndFeel();
		PlasticXPLookAndFeel.setCurrentTheme(new SkyBluer());

		try {
			UIManager.setLookAndFeel(laf);
			Teste m = new Teste();
			m.setVisible(true);
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

	}

}
