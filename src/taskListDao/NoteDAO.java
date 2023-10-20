package taskListDao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

	private Connection con;
	
	public NoteDAO(){

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

			String createTableNotes = "CREATE TABLE IF NOT EXISTS notes ("
					+ "id_note     INTEGER PRIMARY KEY,"
					+ "title       TEXT,"
					+ "position    INTEGER UNIQUE NOT NULL,"
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
	
	public boolean addNote(Note note) {
		
		String sql = "insert into notes (title, position, content, last_change) values (?,?,?,datetime('now','localtime'))" ;
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, note.getTitle());
			ps.setInt(2, note.getPosition());
			ps.setString(3, note.getContent());
			ps.executeUpdate();
			ps.close();
			return true;
		}
		catch (Exception e) {
		System.out.println(e.getMessage());
		return false;
		}
		
	}
	
	public boolean updateNote(Note note) {
		
		String sql = "update notes set title = ?, position = ?, content = ?, last_change = datetime('now','localtime') where id_note = ?" ;
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, note.getTitle());
			ps.setInt(2, note.getPosition());
			ps.setString(3, note.getContent());
			ps.setInt(4, note.getId_note());
			ps.executeUpdate();
			ps.close();
			return true;
		}
		catch (Exception e) {
		System.out.println(e.getMessage());
		return false;
		}
		
	}
	
	public boolean deleteNote(Note note) {
		
		String sql = "delete from notes where id_note = ?" ;
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, note.getId_note());
			ps.executeUpdate();
			ps.close();
			return true;
		}
		catch (Exception e) {
		System.out.println(e.getMessage());
		return false;
		}
		
	}
	
	public List<Note> getAllNotes(){
		
		List<Note> allNotes = new ArrayList<Note>();
		String sql = "select * from notes order by position asc";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				int id_note = rs.getInt("id_note");
				String title = rs.getString("title");
				int position = rs.getInt("position");
				String content = rs.getString("content");
				String last_change = rs.getString("last_change");
				Note note = new Note();
				note.setId_note(id_note);
				note.setTitle(title);
				note.setPosition(position);
				note.setContent(content);
				note.setLast_change(last_change);
				
				allNotes.add(note);
				
			}
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return allNotes;
		
	}
	
	public void switchPositionAdd(List<Note> allNotes, int desiredPosition) {
		int listSize = allNotes.size();
		for (int i=0; i<listSize; i++) {
			Note someNote = allNotes.get(i);
			if( someNote.getPosition() == desiredPosition ) {
				
				Note lastNote = allNotes.get(listSize-1);
				someNote.setPosition( lastNote.getPosition()+1 );
				updateNote(someNote);
				break;
			}
			
		}
		
	}
	
	
	public void switchPositionEdit(List<Note> allNotes, Note actualNote, int desiredPosition) {
		int listSize = allNotes.size();
		int actualPosition = actualNote.getPosition();
		for (int i=0; i<listSize; i++) {
			Note someNote = allNotes.get(i);
			if( someNote.getPosition() == desiredPosition ) {
				
				someNote.setPosition(-1);
				updateNote(someNote);
				actualNote.setPosition(desiredPosition);
				someNote.setPosition( actualPosition );
				updateNote(actualNote);
				updateNote(someNote);
				break;
			}
			
		}
		
	}
	
	
}