package br.com.gz.migration.report;

import java.util.Collection;

import org.database.connection.DatabaseType;

import br.com.gz.util.GZSoftwares;

/**
 * Classe que cont�m os dados da migra��o para alimentar o relat�rio final
 * 
 * @author Jonathan Sansalone
 *
 */
public class MigrationReportData {

	/**
	 * Caminho do relat�rio
	 */
	@Deprecated
	private String path;
	
	/**
	 * Nome do cliente
	 */
	private String nomeCliente;
	
	/**
	 * Modo de migra��o
	 */
	private String modoMigracao;
	
	/**
	 * Nome do software a ser implantado
	 */
	private String nomeSistema;
	
	/**
	 * Tipo de banco de dados de destino
	 */
	private String tipoBanco;
	
	/**
	 * Endere�o IP do banco de dados de destino
	 */
	private String ip;
	
	/**
	 * Porta do banco de dados de destino
	 */
	private int porta;
	
	/**
	 * Nome do banco de dados de destino
	 */
	private String nomeBanco;
	
	/**
	 * Usu�rio do banco de dados de destino
	 */
	private String usuario;
	
	/**
	 * Senha do banco de dados de destino
	 */
	private String senha;

	/**
	 * Collection com os tipos de dados migrados
	 */
	private Collection<MigrationBean> beans;

	/**
	 * Construtor que recebe a Collection com os tipos de dados da migra��o
	 * 
	 * @param migrationBeans - Collection com os tipos de dados
	 */
	public MigrationReportData(Collection<MigrationBean> migrationBeans) {

		beans = migrationBeans;

	}

	/**
	 * Retorna os tipos de dados da migra��o
	 * 
	 * @return - Collection com os tipos de dados
	 */
	public Collection<MigrationBean> getBeans() {

		return this.beans;

	}

	/**
	 * Caminho do relat�rio
	 * 
	 * @return - caminho
	 */
	@Deprecated
	public String getPath() {
		return path;
	}

	/**
	 * Define o caminho do relat�rio
	 * 
	 * @param path - caminho
	 */
	@Deprecated
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Retorna o nome do cliente
	 * 
	 * @return - nome do cliente
	 */
	public String getNomeCliente() {
		return nomeCliente;
	}

	/**
	 * Define o nome do cliente
	 * 
	 * @param nomeCliente - nome do cliente
	 */
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	/**
	 * retorna o modo de migra��o
	 * 
	 * @return - modo de migra��o
	 */
	public String getModoMigracao() {
		return modoMigracao;
	}

	/**
	 * define o modo de migra��o
	 * 
	 * @param isAppend - se true � incluir, se false � sobrepor
	 */
	public void setModoMigracao(boolean isAppend) {
		this.modoMigracao = isAppend ? "Incluir" : "Sobrepor";
	}

	/**
	 * retorna o nome do software a ser utilizado
	 * 
	 * @return - nome do software
	 */
	public String getNomeSistema() {
		return nomeSistema;
	}

	/**
	 * define o nome do software
	 * 
	 * @param nomeSistema - nome do software em String
	 */
	public void setNomeSistema(String nomeSistema) {
		this.nomeSistema = nomeSistema;
	}

	/**
	 * define o nome do software
	 * 
	 * @param software - nome do software via {@link GZSoftwares}
	 */
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

	/**
	 * retorna o tipo de banco de dados de destino
	 * 
	 * @return - tipo de banco em String
	 */
	public String getTipoBanco() {
		return tipoBanco;
	}

	/**
	 * deifne o tipo de banco de dados de destino
	 * 
	 * @param tipoBanco - tipo de banco em String
	 */
	public void setTipoBanco(String tipoBanco) {
		this.tipoBanco = tipoBanco;
	}

	/**
	 * define o tipo de banco de dados de destino
	 * 
	 * @param dbType - tipo de banco de dados via DatabaseType
	 */
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
	
	/**
	 * retorna o ip do banco de dados de destino
	 * 
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * define o ip do banco de dados de destino
	 * 
	 * @param ip - ip do banco de dados de destino
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * retorna o n�mero da porta do banco de dados de destino
	 * 
	 * @return - n�mero da porta do banco de dados de destino
	 */
	public int getPorta() {
		return porta;
	}

	/**
	 * define o n�mero da porta do banco de dados de destino
	 * 
	 * @param porta - n�mero da porta do banco de dados de destino
	 */
	public void setPorta(int porta) {
		this.porta = porta;
	}

	/**
	 * retorna o nome do banco de dados de destino
	 * 
	 * @return - nome do banco de dados de destino
	 */
	public String getNomeBanco() {
		return nomeBanco;
	}

	/**
	 * define o nome do banco de dados de destino
	 * 
	 * @param nomeBanco - nome do banco de dados de destino
	 */
	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	/**
	 * retorna o nome do usu�rio do banco de dados de destino
	 * 
	 * @return - nome do usu�rio do banco de dados de destino
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * define o nome do usu�rio do banco de dados de destino
	 * 
	 * @param usuario - usu�rio do banco de dados de destino
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * retorna a senha do banco de dados de destino
	 * 
	 * @return - senha do banco de dados de destino
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * define a senha do banco de dados de destino
	 * 
	 * @param senha - senha do banco de dados de destino
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
