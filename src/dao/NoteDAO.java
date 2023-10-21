package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Note;

public class NoteDAO {

	private Connection con = new SqliteConnector().getCon();

	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
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
			return allNotes;

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
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

	public boolean deleteAllNotes() {

		String sql = "delete from notes" ;

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}



	public boolean updateAllNotes(List<Note> allTheNotes) {

		try {
			deleteAllNotes();
			int listSize = allTheNotes.size();

			for (int i=0; i<listSize; i++) {
				Note someNote = allTheNotes.get(i);
				addNote(someNote);

			}
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}


	}

	public int getMaxPosition() {
		
		String sql = "select max(position) from notes";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int position = rs.getInt(1);
			ps.close();
			return position;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
		
	}



}