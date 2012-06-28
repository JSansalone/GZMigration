package br.com.gz.migration.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import org.database.connection.DatabaseType;

import br.com.gz.migration.MigrationDataType;
import br.com.gz.util.GZSoftwares;

public class MigrationReportData {

	private String path;
	private String nomeCliente;
	private String modoMigracao;
	private String nomeSistema;
	private String tipoBanco;
	private String ip;
	private int porta;
	private String nomeBanco;
	private String usuario;
	private String senha;

	private Collection<MigrationBean> beans;

	public MigrationReportData(Collection<MigrationBean> migrationBeans) {

		beans = migrationBeans;

	}

	public Collection<MigrationBean> getBeans() {

		return this.beans;

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getModoMigracao() {
		return modoMigracao;
	}

	public void setModoMigracao(boolean isAppend) {
		this.modoMigracao = isAppend ? "Incluir" : "Sobrepor";
	}

	public String getNomeSistema() {
		return nomeSistema;
	}

	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}

	public void setNomeSistema(GZSoftwares software) {
		
		switch (software) {
		case MERCOFLEX:
			this.nomeSistema = "MercoFlex";
			break;

		case MERCATTO:
			this.nomeSistema = "Mercatto";
			break;

		default:
			break;
		}
		
	}

	public String getTipoBanco() {
		return tipoBanco;
	}

	public void setTipoBanco(String tipoBanco) {
		this.tipoBanco = tipoBanco;
	}

	public void setTipoBanco(DatabaseType dbType) {
		switch(dbType){
		case MySQL:
			this.tipoBanco = "MySQL";
			break;
		case MSSQL:
			this.tipoBanco = "SQL Server";
			break;
		case Oracle:
			this.tipoBanco = "Oracle";
			break;
		}
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	// private String path;
	//
	// private String nomeCliente;
	// private GZSoftwares softwareTo;
	// private GZSoftwares softwareFrom;
	// private Calendar data;
	//
	// private boolean isAppend;
	//
	// private DatabaseType dbTypeFrom;
	// private String ipAddressFrom;
	// private int portFrom;
	// private String dbNameFrom;
	// private String usernameFrom;
	// private String passwordFrom;
	//
	// private DatabaseType dbTypeTo;
	// private String ipAddressTo;
	// private int portTo;
	// private String dbNameTo;
	// private String usernameTo;
	// private String passwordTo;
	//
	// private ArrayList<MigrationDataType> dataTypes;
	//
	// public boolean isAppend() {
	// return isAppend;
	// }
	//
	// public void setAppend(boolean isAppend) {
	// this.isAppend = isAppend;
	// }
	//
	// public GZSoftwares getSoftwareTo() {
	// return softwareTo;
	// }
	//
	// public void setSoftwareTo(GZSoftwares softwareTo) {
	// this.softwareTo = softwareTo;
	// }
	//
	// public GZSoftwares getSoftwareFrom() {
	// return softwareFrom;
	// }
	//
	// public void setSoftwareFrom(GZSoftwares softwareFrom) {
	// this.softwareFrom = softwareFrom;
	// }
	//
	// public String getPath() {
	// return path;
	// }
	//
	// public void setPath(String path) {
	// this.path = path;
	// }
	//
	// public String getNomeCliente() {
	// return nomeCliente;
	// }
	//
	// public void setNomeCliente(String nomeCliente) {
	// this.nomeCliente = nomeCliente;
	// }
	//
	// public Calendar getData() {
	// return data;
	// }
	//
	// public void setData(Calendar data) {
	// this.data = data;
	// }
	//
	// public DatabaseType getDbTypeFrom() {
	// return dbTypeFrom;
	// }
	//
	// public void setDbTypeFrom(DatabaseType dbTypeFrom) {
	// this.dbTypeFrom = dbTypeFrom;
	// }
	//
	// public String getIpAddressFrom() {
	// return ipAddressFrom;
	// }
	//
	// public void setIpAddressFrom(String ipAddressFrom) {
	// this.ipAddressFrom = ipAddressFrom;
	// }
	//
	// public int getPortFrom() {
	// return portFrom;
	// }
	//
	// public void setPortFrom(int portFrom) {
	// this.portFrom = portFrom;
	// }
	//
	// public String getDbNameFrom() {
	// return dbNameFrom;
	// }
	//
	// public void setDbNameFrom(String dbNameFrom) {
	// this.dbNameFrom = dbNameFrom;
	// }
	//
	// public String getUsernameFrom() {
	// return usernameFrom;
	// }
	//
	// public void setUsernameFrom(String usernameFrom) {
	// this.usernameFrom = usernameFrom;
	// }
	//
	// public String getPasswordFrom() {
	// return passwordFrom;
	// }
	//
	// public void setPasswordFrom(String passwordFrom) {
	// this.passwordFrom = passwordFrom;
	// }
	//
	// public DatabaseType getDbTypeTo() {
	// return dbTypeTo;
	// }
	//
	// public void setDbTypeTo(DatabaseType dbTypeTo) {
	// this.dbTypeTo = dbTypeTo;
	// }
	//
	// public String getIpAddressTo() {
	// return ipAddressTo;
	// }
	//
	// public void setIpAddressTo(String ipAddressTo) {
	// this.ipAddressTo = ipAddressTo;
	// }
	//
	// public int getPortTo() {
	// return portTo;
	// }
	//
	// public void setPortTo(int portTo) {
	// this.portTo = portTo;
	// }
	//
	// public String getDbNameTo() {
	// return dbNameTo;
	// }
	//
	// public void setDbNameTo(String dbNameTo) {
	// this.dbNameTo = dbNameTo;
	// }
	//
	// public String getUsernameTo() {
	// return usernameTo;
	// }
	//
	// public void setUsernameTo(String usernameTo) {
	// this.usernameTo = usernameTo;
	// }
	//
	// public String getPasswordTo() {
	// return passwordTo;
	// }
	//
	// public void setPasswordTo(String passwordTo) {
	// this.passwordTo = passwordTo;
	// }
	//
	// public ArrayList<MigrationDataType> getDataTypes() {
	// return dataTypes;
	// }
	//
	// public void setDataTypes(ArrayList<MigrationDataType> dataTypes) {
	// this.dataTypes = dataTypes;
	// }

}
