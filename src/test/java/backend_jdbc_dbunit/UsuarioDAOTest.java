package backend_jdbc_dbunit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioDAOTest {

	private JdbcDatabaseTester jdt;
	private UsuarioDAO dao;
	

	@BeforeEach
	void setUp() {
		try {
			dao = new H2Database();
			jdt = new JdbcDatabaseTester(H2Database.JDBC_DRIVER, H2Database.DB_URL, H2Database.USER, H2Database.PASSWORD);
			FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
			jdt.setDataSet(loader.load("/backend_jdbc_dbunit/init.xml"));
			jdt.onSetup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void deveRecuperarTodosOsUsuarios() {
		List<Usuario> usuarios = dao.ranking();
		assertEquals(2, usuarios.size());
		assertEquals("joao", usuarios.get(0).getLogin());
		assertEquals("maria", usuarios.get(1).getLogin());
	}
	
	@Test
	void deveRecuperarUmUsuarioPeloSeuLogin() {
		Usuario usuario = dao.recuperar("maria");
		assertNotNull(usuario);
		assertEquals("Maria João", usuario.getNome());
	}
	
	@Test
	void deveAdicionarPontosParaOUsuarioEspecificado() {
		dao.adicionarPontos("joao", 256);
		try {
			IDataSet currenDataSet = jdt.getConnection().createDataSet();
			ITable currentTable = currenDataSet.getTable("USUARIO");
			
			FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
			IDataSet expectedDataset = loader.load("/backend_jdbc_dbunit/update.xml");
			ITable expectedTable = expectedDataset.getTable("USUARIO");
			
			Assertion.assertEquals(expectedTable, currentTable);
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	void deveInserirUmNovoUsuario() {
		dao.inserir(new Usuario("newuser", "newuser@email.com", "New User", "789", 2048));
		try {
			IDataSet currenDataSet = jdt.getConnection().createDataSet();
			ITable currentTable = currenDataSet.getTable("USUARIO");
			
			FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
			IDataSet expectedDataset = loader.load("/backend_jdbc_dbunit/insert.xml");
			ITable expectedTable = expectedDataset.getTable("USUARIO");
			
			Assertion.assertEquals(expectedTable, currentTable);
		} catch (Exception e) {
			fail(e);
		}
	}

}
