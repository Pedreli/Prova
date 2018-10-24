package banco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import banco.modelo.Livro;
import banco.modelo.Autor;

public class AutorDao implements Dao<Autor> {
	
	private static final String GET_BY_ID = "SELECT * FROM conta NATURAL JOIN cliente WHERE id = ?";
	private static final String GET_ALL = "SELECT * FROM conta NATURAL JOIN cliente";
	private static final String INSERT = "INSERT INTO conta (agencia, cliente_id, numero, saldo) "
			+ "VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE conta SET agencia = ?, cliente_id = ?, numero = ?, "
			+ "saldo = ? WHERE id = ?";
	private static final String DELETE = "DELETE FROM conta WHERE id = ?";
	
	public AutorDao() {
		try {
			createTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTable() throws SQLException {
	    final String sqlCreate = "CREATE TABLE IF NOT EXISTS conta"
	            + "  (id           INTEGER,"
	            + "   agencia      INTEGER,"
	            + "   cliente_id   INTEGER,"
	            + "   numero	   INTEGER,"
	            + "   saldo        DOUBLE,"
	            + "   FOREIGN KEY (cliente_id) REFERENCES cliente(id),"
	            + "   PRIMARY KEY (id))";
	    
	    Connection conn = DbConnection.getConnection();

	    Statement stmt = conn.createStatement();
	    stmt.execute(sqlCreate);
	}
	
	
	private Autor getContaFromRS(ResultSet rs) throws SQLException
    {
		Autor conta = new Autor();
			
		conta.setId( rs.getInt("id") );
		conta.setAgencia( rs.getInt("agencia") );
		conta.setNumero( rs.getInt("numero"));
		conta.setSaldo( rs.getDouble("saldo") );
		conta.setCliente( new Livro(rs.getInt("cliente_id"), rs.getString("nome"), 
				rs.getString("endereco"), rs.getLong("cpf"),  rs.getLong("rg"),
				rs.getLong("telefone")));
	
		return conta;
    }
	
	@Override
	public Autor getByKey(int id) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Autor conta = null;
		
		try {
			stmt = conn.prepareStatement(GET_BY_ID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				conta = getContaFromRS(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		
		return conta;
	}

	@Override
	public List<Autor> getAll() {
		Connection conn = DbConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Autor> conta = new ArrayList<>();
		
		try {
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(GET_ALL);
			
			while (rs.next()) {
				conta.add(getContaFromRS(rs));
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		
		return conta;
	}

	@Override
	public void insert(Autor conta) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, conta.getAgencia());
			stmt.setInt(2, conta.getCliente().getId());
			stmt.setInt(3, conta.getNumero());
			stmt.setDouble(4, conta.getSaldo());
			
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				conta.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
			e.printStackTrace();
		} finally {
			close(conn, stmt, null);
		}
	}

	@Override
	public void update(Autor conta) {
		Connection conn = DbConnection.getConnection();
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(1, conta.getAgencia());
			stmt.setInt(2, conta.getCliente().getId());
			stmt.setInt(3, conta.getNumero());
			stmt.setDouble(4, conta.getSaldo());
			stmt.setInt(5, conta.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close (conn, stmt, null);
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
