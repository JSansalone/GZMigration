package br.com.gz.migration.panelSteps;

import javax.swing.JTextField;

interface IValidation {

	boolean validateFields();
	boolean isEmpty(JTextField field);
	
}
