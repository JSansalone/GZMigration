package br.com.gz.migration.panelSteps;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.gz.migration.GZMigration;
import br.com.gz.migration.ICustomerInfo;

public class CustomerNamePanel extends JPanel implements ICustomerInfo,
		IValidation, InitialConfigurationsOnVisible {

	private JTextField txtName;

	public CustomerNamePanel() {

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblWelcome = new JLabel("Digite o nome do cliente",
				new ImageIcon((URL) getClass().getResource(
						"/img/navigation/arrow.png")), JLabel.LEFT);
		lblWelcome.setBounds(30, 20, 700, 30);
		lblWelcome.setFont(GZMigration.TITLE_FONT);
		add(lblWelcome);

		JLabel lblName = new JLabel("Nome do cliente");
		lblName.setBounds(60, 90, 160, 20);

		txtName = new JTextField();
		txtName.setBounds(228, 90, 290, 20);

		add(lblName);
		add(txtName);

		setVisible(false);

	}

	@Override
	public String getCustomerName() {
		// johnny Auto-generated method stub
		return txtName.getText().trim();
	}

	@Override
	public boolean validateFields() {
		// johnny Auto-generated method stub
		return !txtName.getText().trim().equals("");
	}

	@Override
	public boolean isEmpty(JTextField field) {
		// johnny Auto-generated method stub
		return field.getText().trim().equals("");
	}

	@Override
	public void setInitialFocus() {
		
		txtName.requestFocusInWindow();
		
	}

}
