package banco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import banco.modelo.Livro;

public class LivroDao implements Dao<Livro> {
	
	private static final String GET_BY_ID = "SELECT * FROM livro WHERE id = ?";
	private static final String GET_ALL = "SELECT * FROM livro";
	private static final String INSERT = "INSERT INTO livro (nome, rg, cpf, endereco, telefone) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE livro SET nome = ?, rg = ?, cpf = ?, "
			+ "endereco = ?, telefone = ? WHERE id = ?";
	private static final String DELETE = "DELETE FROM livro WHERE id = ?";
	
	public LivroDao() {
		try {
			createTable();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao criar tabela no banco.", e);
			//e.printStackTrace();
		}
	}
	
	private void createTable() throws SQLException {
	    String sqlCreate = "CREATE TABLE IF NOT EXISTS livro"
	            + "  (id           INTEGER,"
	            + "   nome            VARCHAR(50),"
	            + "   rg	          BIGINT,"
	            + "   cpf			  BIGINT,"
	            + "   endereco           VARCHAR(255),"
	            + "   telefone           BIGINT,"
	            + "   PRIMARY KEY (id))";
	    
	    Connection conn = DbConnection.getConnection();


	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	    
	    close(conn, stmt, null);
	}
	
	
	private Livro getClienteFromRS(ResultSet rs) throws SQLException
    {
		Livro livro = new Livro();
			
		livro.setId( rs.getInt("id") );
		livro.setNome( rs.getString("nome") );
		livro.setRg( rs.getLong("rg") );
		livro.setCpf( rs.getLong("cpf") );
		livro.setEndereco( rs.getString("endereco") );
		livro.setTelefone( rs.getLong("telefone") );
	
		return livro;
    }
	
	@Override
	public Livro getByKey(int id) {
		Connection conn = DbConnection.getConnection();
		
		Livro cliente = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(GET_BY_ID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				cliente = getClienteFromRS(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao obter livro pela chave.", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return cliente;
	}

	@Override
	public List<Livro> getAll() {
		Connection conn = DbConnection.getConnection();
		
		List<Livro> clientes = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(GET_ALL);
			
			while (rs.next()) {
				clientes.add(getClienteFromRS(rs));
			}			
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao obter todos os livros.", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return clientes;
	}

	@Override
	public void insert(Livro cliente) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cliente.getNome());
			stmt.setLong(2, cliente.getRg());
			stmt.setLong(3, cliente.getCpf());
			stmt.setString(4, cliente.getEndereco());
			stmt.setLong(5, cliente.getTelefone());
			
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				cliente.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir livro.", e);
		}finally {
			close(conn, stmt, rs);
		}

	}

	@Override
	public void delete(int id) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(DELETE);
			
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao remover livro.", e);
		} finally {
			close(conn, stmt, null);
		}
	}

	@Override
	public void update(Livro livro) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(UPDATE);
			stmt.setString(1, livro.getNome());
			stmt.setLong(2, livro.getRg());
			stmt.setLong(3, livro.getCpf());
			stmt.setString(4, livro.getEndereco());
			stmt.setLong(5, livro.getTelefone());
			stmt.setInt(7, livro.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar livro.", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar recursos.", e);
		}
		
	}

}
