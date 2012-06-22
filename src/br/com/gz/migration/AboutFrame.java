package br.com.gz.migration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Classe que representa a janela de informações do aplicativo
 * 
 * @author Jonathan Sansalone
 *
 */
public class AboutFrame extends JDialog {

	public AboutFrame() {

		setSize(390, 330);
		setTitle("Sobre...");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setLayout(null);

		JLabel lblImg = new JLabel(new ImageIcon((URL) getClass().getResource(
				"/img/navigation/info.png")));
		lblImg.setBounds(10, 10, 30, 30);
		JLabel lblTitle = new JLabel("Migração de bancos de dados");
		lblTitle.setBounds(55, 30, 350, 25);
		JLabel lblSep1 = new JLabel("--------------------------------------------------------");
		lblSep1.setBounds(55, 50, 350, 20);
		JLabel lblNome = new JLabel("GZ Sistemas Importação e Comércio Ltda.");
		lblNome.setBounds(55, 70, 350, 20);
		JLabel lblTel = new JLabel("Tel: (11)3308-8199");
		lblTel.setBounds(55, 90, 350, 20);
		JLabel lblSite = new JLabel("Site: www.gzsistemas.com.br");
		lblSite.setBounds(55, 110, 350, 20);
		JLabel lblResp = new JLabel("Responsável: Jonathan Sansalone P. Rolim");
		lblResp.setBounds(55, 150, 350, 20);
		JLabel lblCont = new JLabel("E-mail: jonathan.sansalone@gzsistemas.com.br");
		lblCont.setBounds(55, 170, 300, 20);
		JLabel lblDep = new JLabel("Departamento de Serviços");
		lblDep.setBounds(55, 190, 350, 20);
		JLabel lblSep2 = new JLabel("--------------------------------------------------------");
		lblSep2.setBounds(55, 210, 350, 20);
		JLabel lblDate = new JLabel("Abril de 2012");
		lblDate.setBounds(55, 230, 350, 20);
		
		JButton btSair = new JButton("Ok");
		btSair.setBounds(55, 260, 50, 20);
		btSair.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				
			}
			
		});
		JButton btMail = new JButton(new ImageIcon((URL) getClass().getResource(
				"/img/common/mail.png")));
		btMail.setBounds(305, 260, 30, 20);
		btMail.setToolTipText("Enviar e-mail ao responsável");
		btMail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				new MailSenderFrame();
				
			}
			
		});
		
		add(lblImg);
		add(lblTitle);
		add(lblSep1);
		add(lblNome);
		add(lblTel);
		add(lblSite);
		add(lblResp);
		add(lblCont);
		add(lblDep);
		add(lblSep2);
		add(lblDate);
		add(btSair);
		add(btMail);

		setVisible(true);

	}

}
