package backend_jdbc_dbunit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class H2Database implements UsuarioDAO {

	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:~/mydb";
	private static final String USER = "admin";
	private static final String PASSWORD = "";

	static {
		try {
			Class.forName(JDBC_DRIVER);

			System.out.println("Connecting to database...");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

			System.out.println("Creating table in given database...");
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE usuario " + "(" + "login text NOT NULL, " + "email text, " + "nome text, "
					+ "senha text, " + "pontos integer, " + "PRIMARY KEY (login)" + ")";
			stmt.executeUpdate(sql);

			System.out.println("Created table in given database...");

			stmt.close();
			conn.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void inserir(Usuario u) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

			String sql = "INSERT INTO usuario(login, email, nome, senha, pontos) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getLogin());
			stmt.setString(2, u.getEmail());
			stmt.setString(3, u.getNome());
			stmt.setString(4, u.getSenha());
			stmt.setInt(5, u.getPontos());

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível executar o acesso", e);
		}
	}

	@Override
	public Usuario recuperar(String login) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

			String sql = "SELECT * FROM usuario WHERE login = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, login);
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				return new Usuario(rs.getString("login"), rs.getString("email"), rs.getString("nome"),
						rs.getString("senha"), rs.getInt("pontos"));
			}

			return null;

		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível executar o acesso", e);
		}
	}

	@Override
	public void adicionarPontos(String login, int pontos) {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

			String sql = "UPDATE usuario SET pontos = pontos + ? WHERE login = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, login);
			stmt.setInt(2, pontos);

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível executar o acesso", e);
		}
	}

	@Override
	public List<Usuario> ranking() {
		List<Usuario> usuarios = new ArrayList<>(5);

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM usuario ORDER BY pontos DESC";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				usuarios.add(new Usuario(rs.getString("login"), rs.getString("email"), rs.getString("nome"),
						rs.getString("senha"), rs.getInt("pontos")));
			}

		} catch (SQLException e) {
			throw new RuntimeException("Não foi possível executar o acesso", e);
		}

		return usuarios;
	}

}
