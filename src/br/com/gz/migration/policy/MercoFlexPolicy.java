package br.com.gz.migration.policy;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import br.com.gz.migration.EnMigrationDataType;

public class MercoFlexPolicy implements ColumnsPolicy {

	// arraylist que vai guardar todas as colunas que faltam no select
	private ArrayList<String> needed;

	// arraylist que guarda todas as colunas requeridas
	private String[] required;

	public MercoFlexPolicy() {

		needed = new ArrayList<String>();

	}

	@Override
	public boolean validate(ResultSetMetaData selectInfo,
			EnMigrationDataType dataType) {

		// carregando os nomes das colunas requeridas de acordo com o tipo de
		// dado coletado
		loadRequired(dataType);

		needed.clear();

		try {

			// arrayList auxiliar para guardar as colunas coletadas
			ArrayList<String> aux = new ArrayList<String>();

			// loop for para guardar as colunas coletadas
			for (int i = 0; i < selectInfo.getColumnCount(); i++) {

				String column = selectInfo.getColumnName(i + 1).toLowerCase();

				// valida a coluna coletada para ver se faz parte do grupo das
				// requeridas
				if (contains(required, column)) {
					aux.add(column);
				}

			}

			// loop for para verificar se todas as colunas requeridas foram
			// encontradas
			for (String req : required) {

				// se a coluna requerida não for encontrada entre as coletadas
				// adiciona ao arraylist das colunas que faltam
				if (!aux.contains(req)) {
					needed.add(req);
				}

			}

			// verifica se o arraylist de colunas faltantes tem alguma coluna
			Iterator<String> it = needed.iterator();
			// se sim retorna false - O select não passou na validação
			// se não retorna true - O select passou na validação
			return !it.hasNext();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public ArrayList<String> getColumnsNeeded() {
		// johnny Auto-generated method stub
		return needed;
	}

	private void loadRequired(EnMigrationDataType dataType) {

		switch (dataType) {

		case ARMACAO:

			required = MercoFlexRequiredColumns.ARMACAO;

			break;

		case CLIENTE:

			required = MercoFlexRequiredColumns.CLIENTE;

			break;

		case CONTAPAGAR:

			break;

		case CONTARECEBER:

			break;

		case DEPARTAMENTO:

			required = MercoFlexRequiredColumns.DEPARTAMENTO;

			break;

		case FORNECEDOR:

			required = MercoFlexRequiredColumns.FORNECEDOR;

			break;

		case GRUPO:

			required = MercoFlexRequiredColumns.GRUPO;

			break;

		case MARCA:

			required = MercoFlexRequiredColumns.MARCA;

			break;

		case MOVTOVENDA:

			break;

		case NFENTRADA:

			break;

		case NFSAIDA:

			break;

		case PRODUTO:

			required = MercoFlexRequiredColumns.PRODUTO;

			break;

		case SETOR:

			required = MercoFlexRequiredColumns.SETOR;

			break;

		default:

			break;

		}

	}

	private boolean contains(String[] array, String column) {

		for (String string : array) {
			if (string.equalsIgnoreCase(column)) {
				return true;
			}
		}
		return false;

	}

}
