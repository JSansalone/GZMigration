package br.com.gz.migration.policy;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import br.com.gz.migration.EnMigrationDataType;

@Deprecated
public class MercattoPolicy implements ColumnsPolicy {

	// arraylist que vai guardar todas as colunas que faltam no select
	private ArrayList<String> needed;

	// arraylist que guarda todas as colunas requeridas
	private String[] required;

	public MercattoPolicy() {

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

				String column = selectInfo.getColumnLabel(i + 1).toLowerCase();

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

			required = MercattoRequiredColumns.ARMACAO;

			break;

		case CLIENTE:

			required = MercattoRequiredColumns.CLIENTE;

			break;

		case CONTAPAGAR:

			break;

		case CONTARECEBER:

			break;

		case DEPARTAMENTO:

			required = MercattoRequiredColumns.DEPARTAMENTO;

			break;

		case FORNECEDOR:

			required = MercattoRequiredColumns.FORNECEDOR;

			break;

		case GRUPO:

			required = MercattoRequiredColumns.GRUPO;

			break;

		case MARCA:

			required = MercattoRequiredColumns.MARCA;

			break;

		case MOVTOVENDA:

			break;

		case NFENTRADA:

			break;

		case NFSAIDA:

			break;

		case PRODUTO:

			required = MercattoRequiredColumns.PRODUTO;

			break;

		case SETOR:

			required = MercattoRequiredColumns.SETOR;

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
