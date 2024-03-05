package backend_jdbc_dbunit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
			String sql = "CREATE TABLE usuario " +
				"(" +
					"login text NOT NULL, " +
					"email text, " +
					"nome text, " +
					"senha text, " +
					"pontos integer, " +
					"PRIMARY KEY (login)" +
				")";
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
	}

	@Override
	public Usuario recuperar(String login) {
		return null;
	}

	@Override
	public void adicionarPontos(String login, int pontos) {
	}

	@Override
	public List<Usuario> ranking() {
		return null;
	}

}
