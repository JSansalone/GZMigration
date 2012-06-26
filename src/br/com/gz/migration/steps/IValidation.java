package br.com.gz.migration.steps;

import javax.swing.JTextField;

interface IValidation {

	boolean validateFields();
	boolean isEmpty(JTextField field);
	
}
