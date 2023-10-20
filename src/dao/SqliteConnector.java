package dao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SqliteConnector {
	
	private Connection con;

	public Connection getCon() {
		return con;
	}

	public SqliteConnector() {
		
		// Create directory if it doesn't exist
		try {
		Files.createDirectories(Paths.get("database"));
		}
		catch (Exception folderError) {
			System.out.println(folderError.getMessage());
		}
		
		
		// It can also create notes.db if it doens't exist
		String url="jdbc:sqlite:database/notes.db";

		try {
			//Class.forName("org.sqlite.jdbc4");

			String createTableNotes = "CREATE TABLE IF NOT EXISTS notes ("
					+ "id_note     INTEGER PRIMARY KEY,"
					+ "title       TEXT,"
					+ "position    INTEGER NOT NULL,"
					+ "content     TEXT    NOT NULL,"
					+ "last_change TEXT"
					+ ");";
			con = DriverManager.getConnection(url);
			
			PreparedStatement ps = con.prepareStatement(createTableNotes);
			ps.executeUpdate();
			ps.close();


		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
