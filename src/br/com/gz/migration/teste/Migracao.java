package br.com.gz.migration.teste;

public class Migracao {

	private String nome;
	private int totalCadastrado;
	private int totalIncluido;

	public Migracao(String nome, int totalCadastrado, int totalIncluido) {
	
		setNome(nome);
		setTotalCadastrado(totalCadastrado);
		setTotalIncluido(totalIncluido);
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String tipoDado) {
		this.nome = tipoDado;
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
