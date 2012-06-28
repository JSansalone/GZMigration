package br.com.gz.migration.report;

public class MigrationBean {

	private String nome;
	private int totalCadastrado;
	private int totalIncluido;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getTotalCadastrado() {
		return totalCadastrado;
	}
	public void setTotalCadastrado(int totalCadastrado) {
		this.totalCadastrado = totalCadastrado;
	}
	public int getTotalIncluido() {
		return totalIncluido;
	}
	public void setTotalIncluido(int totalIncluido) {
		this.totalIncluido = totalIncluido;
	}
	
}
