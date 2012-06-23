package br.com.gz.migration.report;

import java.util.ArrayList;
import java.util.Calendar;

import org.database.connection.DatabaseType;

import br.com.gz.migration.MigrationDataType;
import br.com.gz.util.GZSoftwares;

public class MigrationReportData {

	private String path;

	private String nomeCliente;
	private GZSoftwares softwareTo;
	private GZSoftwares softwareFrom;
	private Calendar data;

	private boolean isAppend;

	private DatabaseType dbTypeFrom;
	private String ipAddressFrom;
	private int portFrom;
	private String dbNameFrom;
	private String usernameFrom;
	private String passwordFrom;

	private DatabaseType dbTypeTo;
	private String ipAddressTo;
	private int portTo;
	private String dbNameTo;
	private String usernameTo;
	private String passwordTo;

	private ArrayList<MigrationDataType> dataTypes;

	public boolean isAppend() {
		return isAppend;
	}

	public void setAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}

	public GZSoftwares getSoftwareTo() {
		return softwareTo;
	}

	public void setSoftwareTo(GZSoftwares softwareTo) {
		this.softwareTo = softwareTo;
	}

	public GZSoftwares getSoftwareFrom() {
		return softwareFrom;
	}

	public void setSoftwareFrom(GZSoftwares softwareFrom) {
		this.softwareFrom = softwareFrom;
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

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public DatabaseType getDbTypeFrom() {
		return dbTypeFrom;
	}

	public void setDbTypeFrom(DatabaseType dbTypeFrom) {
		this.dbTypeFrom = dbTypeFrom;
	}

	public String getIpAddressFrom() {
		return ipAddressFrom;
	}

	public void setIpAddressFrom(String ipAddressFrom) {
		this.ipAddressFrom = ipAddressFrom;
	}

	public int getPortFrom() {
		return portFrom;
	}

	public void setPortFrom(int portFrom) {
		this.portFrom = portFrom;
	}

	public String getDbNameFrom() {
		return dbNameFrom;
	}

	public void setDbNameFrom(String dbNameFrom) {
		this.dbNameFrom = dbNameFrom;
	}

	public String getUsernameFrom() {
		return usernameFrom;
	}

	public void setUsernameFrom(String usernameFrom) {
		this.usernameFrom = usernameFrom;
	}

	public String getPasswordFrom() {
		return passwordFrom;
	}

	public void setPasswordFrom(String passwordFrom) {
		this.passwordFrom = passwordFrom;
	}

	public DatabaseType getDbTypeTo() {
		return dbTypeTo;
	}

	public void setDbTypeTo(DatabaseType dbTypeTo) {
		this.dbTypeTo = dbTypeTo;
	}

	public String getIpAddressTo() {
		return ipAddressTo;
	}

	public void setIpAddressTo(String ipAddressTo) {
		this.ipAddressTo = ipAddressTo;
	}

	public int getPortTo() {
		return portTo;
	}

	public void setPortTo(int portTo) {
		this.portTo = portTo;
	}

	public String getDbNameTo() {
		return dbNameTo;
	}

	public void setDbNameTo(String dbNameTo) {
		this.dbNameTo = dbNameTo;
	}

	public String getUsernameTo() {
		return usernameTo;
	}

	public void setUsernameTo(String usernameTo) {
		this.usernameTo = usernameTo;
	}

	public String getPasswordTo() {
		return passwordTo;
	}

	public void setPasswordTo(String passwordTo) {
		this.passwordTo = passwordTo;
	}

	public ArrayList<MigrationDataType> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(ArrayList<MigrationDataType> dataTypes) {
		this.dataTypes = dataTypes;
	}

}
