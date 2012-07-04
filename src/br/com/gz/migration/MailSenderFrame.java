package br.com.gz.migration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.gz.migration.file.LogFile;

/**
 * Classe que representa a janela de envio de email
 * 
 * @author Jonathan Sansalone
 *
 */
public class MailSenderFrame extends JDialog {

	/**
	 * Campo para digitar o nome do remetente
	 */
	private JTextField txtFrom;
	
	/**
	 * Area do corpo da mensagem
	 */
	private JTextArea area;
	
	/**
	 * Bot�o para enviar o email
	 */
	private JButton btSend;

	/**
	 * M�todo que realiza o envio do email
	 * 
	 * @param from - nome do remetente
	 * @param to - email do destinat�rio
	 * @param title - t�tulo do email
	 * @param message - mensagem
	 * @return - true se for enviado com sucesso, false caso contr�rio
	 */
	private boolean enviarEmail(String from, String to, String title,
			String message) {

		try {

			// seta o servidor de email
			Properties props = new Properties();
			props.put("mail.smtp.host", "mail.gzsistemas.com.br");
			props.put("mail.smtp.auth", "false");
			// cria uma sessao com o servidor de email
			Session mailSession = Session.getDefaultInstance(props, null);
			// Mostra detalhes do envio da mensagem, quando (true)
			mailSession.setDebug(false);
			Message msg = new MimeMessage(mailSession);
			// Subject = ASSUNTO
			msg.setSubject(title);
			// FROM = de esta enviando //
			InternetAddress iFrom = new InternetAddress(from);
			msg.setFrom(iFrom);
			// PARA QUEM recebe //
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			// conteudo
			msg.setContent(message, "text/plain");
			// Executa finalmente o envio!
			
			LogFile.getInstance().writeInFile("Sending email to jonathan.sansalone@gzsistemas.com.br");
			LogFile.getInstance().writeInFile("Content: "+message);
			
			Transport.send(msg);
			
			LogFile.getInstance().writeInFile("Email sent");
			
			return true;

		} catch (Exception e) {

			System.err.println(e.getMessage());
			LogFile.getInstance().writeInFile("Failed to send email");
			LogFile.getInstance().writeInFile(e.getMessage());
			return false;

		}

	}

	/**
	 * Construtor default que constr�i a janela
	 */
	public MailSenderFrame() {

		setSize(600, 380);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Enviar email ao respons�vel");
		setIconImage(getToolkit().getImage(
				(URL) getClass().getResource("/img/common/sendmail.png")));
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel lblFrom = new JLabel("Seu nome:");
		lblFrom.setBounds(30, 30, 70, 20);
		JLabel lblTo = new JLabel(
				"<html>Para: <b>jonathan.sansalone@gzsistemas.com.br</b></html>");
		lblTo.setBounds(30, 60, 400, 20);
		txtFrom = new JTextField();
		txtFrom.setBounds(110, 30, 250, 22);
		area = new JTextArea();
		JLabel lblMessage = new JLabel("Mensagem");
		lblMessage.setBounds(30, 110, 150, 20);
		JScrollPane scr = new JScrollPane(area);
		scr.setBounds(30, 140, getWidth() - 70, 150);
		btSend = new JButton("Enviar");
		btSend.setBounds(getWidth() - 130, getHeight() - 65, 90, 25);
		btSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String to = "jonathan.sansalone@gzsistemas.com.br";
				String from = "migracao@gzsistemas.com.br";
				String title = txtFrom.getText()
						+ " - Migra��o de banco de dados";
				String message = area.getText();

				if (enviarEmail(from, to, title, message)) {

					JOptionPane.showMessageDialog(null,
							"Email enviado com sucesso!", "Conclu�do",
							JOptionPane.INFORMATION_MESSAGE);

				} else {

					JOptionPane.showMessageDialog(null,
							"Falha ao enviar o email", "Erro",
							JOptionPane.ERROR_MESSAGE);

				}

			}

		});

		add(lblFrom);
		add(txtFrom);
		add(lblTo);
		add(scr);
		add(btSend);
		add(lblMessage);

		setVisible(true);

	}

}
