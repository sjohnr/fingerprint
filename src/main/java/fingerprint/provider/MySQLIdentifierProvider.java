package fingerprint.provider;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.LongIdentifier;

public class MySQLIdentifierProvider implements IdentifierProvider {
	private String dsn;
	private String username;
	private String password;
	private String sequenceName;
	
	private Connection conn;
	private Statement statement;
	
	public MySQLIdentifierProvider(String sequenceName) {
		this("jdbc:mysql://localhost:3306/sequences", "root", "", sequenceName);
	}
	
	public MySQLIdentifierProvider(String dsn, String username, String password, String sequenceName) {
		this.dsn = dsn;
		this.username = username;
		this.password = password;
		this.sequenceName = sequenceName;
	}
	
	private void init() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			throw new Error("MySQL driver not found!", ex);
		}
		
		conn = DriverManager.getConnection(dsn, username, password);
		conn.createStatement().executeUpdate(
			String.format("create table if not exists %s (id int not null auto_increment, data varchar(255), primary key (id))", sequenceName)
		);
		conn.setAutoCommit(false);
		statement = conn.createStatement();
	}
	
	private void destroy() throws SQLException {
		if (statement != null) {
			statement.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
	
	@Override
	public void setID(Identifier identifier) {
		try {
			// initialize the connection if necessary
			if (conn == null) {
				init();
			}
			
			conn.createStatement().executeUpdate(
				String.format("alter table %s auto_increment = %d", sequenceName, identifier.getID().add(BigInteger.ONE))
			);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	@Override
	public Identifier getID() {
		ResultSet rs = null;
		Identifier identifier = null;
		try {
			// initialize the connection if necessary
			if (conn == null) {
				init();
			}
			
			statement.executeUpdate(
				String.format("insert into %s (data) values (null)", sequenceName), Statement.RETURN_GENERATED_KEYS
			);
			
			rs = statement.getGeneratedKeys();
			rs.first();
			identifier = new LongIdentifier(rs.getLong(1));
			
			conn.rollback();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ignored) {}
			}
		}
		
		return identifier;
	}
	
	@Override
	protected void finalize() throws Throwable {
		destroy();
	}
}
