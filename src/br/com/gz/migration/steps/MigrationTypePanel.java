package br.com.gz.migration.steps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.gz.migration.EnMigrationDataType;
import br.com.gz.migration.GZMigration;
import br.com.gz.migration.IMigrationType;

public class MigrationTypePanel extends JPanel implements IValidation,
		IMigrationType, InitialConfigurationsOnVisible {

	private JCheckBox chkProdutos;
	private JCheckBox chkDepartamento;
	private JCheckBox chkGrupo;
	private JCheckBox chkArmacao;
	private JCheckBox chkMarca;
	private JCheckBox chkClientes;
	private JCheckBox chkFornecedores;
	private JCheckBox chkMovVendas;
	private JCheckBox chkNFEntrada;
	private JCheckBox chkNFSaida;
	private JCheckBox chkContasPagar;
	private JCheckBox chkContasReceber;
	private JCheckBox chkAll;

	private JSpinner spnLojas;

	private ButtonGroup btGroup;
	private JRadioButton rdbOverride;
	private JRadioButton rdbAppend;

	private JCheckBox chkIgnoreMultiLoja;

	public MigrationTypePanel() {

		setLayout(null);
		setSize(GZMigration.DIMENSION_PANEL);
		setLocation(GZMigration.POINT_PANEL);
		JLabel lblBanner = new JLabel(
				"Escolha os tipos de conversões e a quantidade de lojas",
				new ImageIcon((URL) getClass().getResource(
						"/img/navigation/arrow.png")), JLabel.LEFT);
		lblBanner.setBounds(30, 20, 600, 30);
		lblBanner.setFont(GZMigration.TITLE_FONT);
		add(lblBanner);

		chkProdutos = new JCheckBox("Produtos");
		chkProdutos.setBounds(60, 130, 100, 20);
		chkProdutos.setBounds(60, 150, 100, 20);
		chkDepartamento = new JCheckBox("Departamentos");
		chkDepartamento.setBounds(80, 160, 150, 20);
		chkDepartamento.setBounds(80, 180, 150, 20);
		chkGrupo = new JCheckBox("Grupos");
		chkGrupo.setBounds(80, 180, 100, 20);
		chkGrupo.setBounds(80, 200, 100, 20);
		chkArmacao = new JCheckBox("Armações");
		chkArmacao.setBounds(80, 200, 100, 20);
		chkArmacao.setBounds(80, 220, 100, 20);
		chkMarca = new JCheckBox("Marcas");
		chkMarca.setBounds(80, 220, 100, 20);
		chkMarca.setBounds(80, 240, 100, 20);
		chkClientes = new JCheckBox("Clientes");
		chkClientes.setBounds(250, 130, 100, 20);
		chkClientes.setBounds(250, 150, 100, 20);
		chkFornecedores = new JCheckBox("Fornecedores");
		chkFornecedores.setBounds(250, 160, 150, 20);
		chkFornecedores.setBounds(250, 180, 150, 20);
		chkMovVendas = new JCheckBox("Vendas");
		chkMovVendas.setBounds(430, 130, 200, 20);
		chkMovVendas.setBounds(430, 150, 200, 20);
		chkMovVendas.setEnabled(false);
		chkNFEntrada = new JCheckBox("Notas fiscais de entrada");
		chkNFEntrada.setBounds(250, 190, 180, 20);
		chkNFEntrada.setBounds(250, 210, 180, 20);
		chkNFSaida = new JCheckBox("Notas fiscais de saída");
		chkNFSaida.setBounds(250, 220, 170, 20);
		chkNFSaida.setBounds(250, 240, 170, 20);
		chkNFSaida.setEnabled(false);
		chkContasPagar = new JCheckBox("Contas a pagar");
		chkContasPagar.setBounds(430, 160, 200, 20);
		chkContasPagar.setBounds(430, 180, 200, 20);
		chkContasPagar.setEnabled(false);
		chkContasReceber = new JCheckBox("Contas a receber");
		chkContasReceber.setBounds(430, 190, 200, 20);
		chkContasReceber.setBounds(430, 210, 200, 20);
		chkContasReceber.setEnabled(false);
		chkAll = new JCheckBox("Selecionar todos");
		chkAll.setBounds(430, 220, 200, 20);
		chkAll.setBounds(430, 240, 200, 20);
		chkIgnoreMultiLoja = new JCheckBox("Ignorar códigos de loja");
		chkIgnoreMultiLoja.setBounds(430, 90, 170, 20);
		chkIgnoreMultiLoja.setSelected(true);
		chkIgnoreMultiLoja
				.setToolTipText("Ao selecionar, o aplicativo irá ignorar os códigos de loja dos produtos e clientes. Use esta opção quando houver apenas 1(uma) loja no arquivo de dados dos produtos e clientes.");
		rdbOverride = new JRadioButton("Sobrepor");
		rdbOverride.setBounds(430, 90, 100, 20);
		rdbOverride.setBounds(250, 110, 100, 20);
		rdbOverride
				.setToolTipText("Limpa o banco de dados e insere os registros selecionados novamente");
		rdbAppend = new JRadioButton("Incluir");
		rdbAppend.setBounds(250, 90, 100, 20);
		rdbAppend
				.setToolTipText("Os dados que são comuns a todas as lojas não serão inseridos novamente, serão inseridos dados individuais para novas lojas como produtos por loja");
		rdbAppend.setSelected(true);
		btGroup = new ButtonGroup();
		btGroup.add(rdbAppend);
		btGroup.add(rdbOverride);
		spnLojas = new JSpinner();
		spnLojas.setBounds(60, 90, 40, 20);
		spnLojas.setValue(1);
		spnLojas.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				try {

					int n = Integer.parseInt(spnLojas.getValue().toString());

					if (n < 1) {
						spnLojas.setValue(1);
					} else if (n > 10) {
						spnLojas.setValue(10);
					}

				} catch (NumberFormatException e2) {

					spnLojas.setValue(1);

				}

			}

		});
		chkProdutos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				chkDepartamento.setSelected(chkProdutos.isSelected()
						&& chkDepartamento.isEnabled());
				chkGrupo.setSelected(chkProdutos.isSelected()
						&& chkGrupo.isEnabled());
				chkArmacao.setSelected(chkProdutos.isSelected()
						&& chkArmacao.isEnabled());
				chkMarca.setSelected(chkProdutos.isSelected()
						&& chkMarca.isEnabled());

			}

		});
		chkDepartamento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (chkDepartamento.isSelected())
					chkProdutos.setSelected(true);

			}

		});
		chkGrupo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (chkGrupo.isSelected())
					chkProdutos.setSelected(true);

			}

		});
		chkArmacao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (chkArmacao.isSelected())
					chkProdutos.setSelected(true);

			}

		});
		chkMarca.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (chkMarca.isSelected())
					chkProdutos.setSelected(true);

			}

		});
		chkAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				chkProdutos.setSelected(chkAll.isSelected()
						&& chkProdutos.isEnabled());
				chkClientes.setSelected(chkAll.isSelected()
						&& chkClientes.isEnabled());
				chkFornecedores.setSelected(chkAll.isSelected()
						&& chkFornecedores.isEnabled());
				chkDepartamento.setSelected(chkAll.isSelected()
						&& chkDepartamento.isEnabled());
				chkGrupo.setSelected(chkAll.isSelected()
						&& chkGrupo.isEnabled());
				chkArmacao.setSelected(chkAll.isSelected()
						&& chkArmacao.isEnabled());
				chkMarca.setSelected(chkAll.isSelected()
						&& chkMarca.isEnabled());
				chkNFEntrada.setSelected(chkAll.isSelected()
						&& chkNFEntrada.isEnabled());
				chkNFSaida.setSelected(chkAll.isSelected()
						&& chkNFSaida.isEnabled());
				chkMovVendas.setSelected(chkAll.isSelected()
						&& chkMovVendas.isEnabled());
				chkContasPagar.setSelected(chkAll.isSelected()
						&& chkContasPagar.isEnabled());
				chkContasReceber.setSelected(chkAll.isSelected()
						&& chkContasReceber.isEnabled());
				chkAll.setSelected(chkAll.isSelected());

			}

		});

		chkIgnoreMultiLoja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				spnLojas.setValue(1);
				spnLojas.setEnabled(chkIgnoreMultiLoja.isSelected());

			}

		});

		rdbAppend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				chkDepartamento.setEnabled(false);
				chkGrupo.setEnabled(false);
				chkArmacao.setEnabled(false);
				chkMarca.setEnabled(false);
				chkClientes.setEnabled(false);
				chkFornecedores.setEnabled(false);
				chkNFEntrada.setEnabled(false);
				chkNFSaida.setEnabled(false);
				chkMovVendas.setEnabled(false);
				chkContasPagar.setEnabled(false);
				chkContasReceber.setEnabled(false);

				chkDepartamento.setSelected(false);
				chkGrupo.setSelected(false);
				chkArmacao.setSelected(false);
				chkMarca.setSelected(false);
				chkClientes.setSelected(false);
				chkFornecedores.setSelected(false);
				chkNFEntrada.setSelected(false);
				chkNFSaida.setSelected(false);
				chkMovVendas.setSelected(false);
				chkContasPagar.setSelected(false);
				chkContasReceber.setSelected(false);

			}

		});
		rdbAppend.doClick();

		rdbOverride.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				chkDepartamento.setEnabled(true);
				chkGrupo.setEnabled(true);
				chkArmacao.setEnabled(true);
				chkMarca.setEnabled(true);
				chkClientes.setEnabled(true);
				chkFornecedores.setEnabled(true);
				// chkNFEntrada.setEnabled(true);
				// chkNFSaida.setEnabled(true);
				// chkMovVendas.setEnabled(true);
				// chkContasPagar.setEnabled(true);
				// chkContasReceber.setEnabled(true);

			}

		});

		JLabel lblLoja = new JLabel("Loja(s)");
		lblLoja.setBounds(110, 90, 100, 20);

		add(chkIgnoreMultiLoja);
		add(chkProdutos);
		add(chkClientes);
		add(chkFornecedores);
		add(chkDepartamento);
		add(chkGrupo);
		add(chkArmacao);
		add(chkMarca);
		add(chkNFEntrada);
		add(chkNFSaida);
		add(chkMovVendas);
		add(chkContasPagar);
		add(chkContasReceber);
		add(chkAll);
		add(spnLojas);
		add(lblLoja);
		add(rdbAppend);
		add(rdbOverride);
		setVisible(false);

	}

	@Override
	public ArrayList<EnMigrationDataType> getMigrationType() {

		ArrayList<EnMigrationDataType> types = new ArrayList<EnMigrationDataType>();

		if (chkClientes.isSelected())
			types.add(EnMigrationDataType.CLIENTE);
		if (chkProdutos.isSelected())
			types.add(EnMigrationDataType.PRODUTO);
		if (chkDepartamento.isSelected())
			types.add(EnMigrationDataType.DEPARTAMENTO);
		if (chkGrupo.isSelected())
			types.add(EnMigrationDataType.GRUPO);
		if (chkArmacao.isSelected())
			types.add(EnMigrationDataType.ARMACAO);
		if (chkMarca.isSelected())
			types.add(EnMigrationDataType.MARCA);
		if (chkFornecedores.isSelected())
			types.add(EnMigrationDataType.FORNECEDOR);
		if (chkNFEntrada.isSelected())
			types.add(EnMigrationDataType.NFENTRADA);
		if (chkNFSaida.isSelected())
			types.add(EnMigrationDataType.NFSAIDA);
		if (chkContasPagar.isSelected())
			types.add(EnMigrationDataType.CONTAPAGAR);
		if (chkContasReceber.isSelected())
			types.add(EnMigrationDataType.CONTARECEBER);
		if (chkMovVendas.isSelected())
			types.add(EnMigrationDataType.MOVTOVENDA);

		return types;

	}

	@Override
	public boolean validateFields() {

		return ((chkClientes.isSelected() || chkFornecedores.isSelected()
				|| chkProdutos.isSelected() || chkDepartamento.isSelected()
				|| chkMarca.isSelected() || chkGrupo.isSelected()
				|| chkArmacao.isSelected() || chkFornecedores.isSelected()
				|| chkNFEntrada.isSelected() || chkNFSaida.isSelected()
				|| chkContasPagar.isSelected() || chkContasReceber.isSelected() || chkMovVendas
					.isSelected()) && Integer.parseInt(spnLojas.getValue()
				.toString()) > 0);

	}

	@Override
	public boolean isEmpty(JTextField field) {
		return false;
	}

	@Override
	public int getNumLoja() {
		// TODO Auto-generated method stub
		return Integer.parseInt(spnLojas.getValue().toString());
	}

	@Override
	public boolean toAppend() {

		return rdbAppend.isSelected();

	}

	@Override
	public void setInitialFocus() {

		spnLojas.requestFocusInWindow();

	}

	@Override
	public boolean ignoreCodes() {
		
		return chkIgnoreMultiLoja.isSelected();
		
	}

}
