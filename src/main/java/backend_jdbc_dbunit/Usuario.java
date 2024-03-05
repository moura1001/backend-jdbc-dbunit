package backend_jdbc_dbunit;

public class Usuario {

	private String login;
	private String email;
	private String nome;
	private String senha;
	private int pontos;

	public Usuario(String login, String email, String nome, String senha, int pontos) {
		this.login = login;
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.pontos = pontos;
	}

	public String getLogin() {
		return login;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getSenha() {
		return senha;
	}

	public int getPontos() {
		return pontos;
	}

}
