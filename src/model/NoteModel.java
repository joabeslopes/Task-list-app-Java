package model;

import java.util.List;

import dao.Note;
import dao.NoteDAO;

public class NoteModel {
	
	private List<Note> allNotes;
	private NoteDAO noteDao = new NoteDAO();
	

	public List<Note> getAllNotes() {
		return allNotes;
	}

	public void setAllNotes(List<Note> allNotes) {
		this.allNotes = allNotes;
	}
	
	public NoteModel() {
		allNotes = noteDao.sqlGetAllNotes();
	}

	public void updateAllNotes(List<Note> notes) {
		noteDao.updateAllNotes(notes);
		allNotes = noteDao.sqlGetAllNotes();
		
	}
	
	public void deleteNote(Note note) {
		allNotes.remove(note);
		noteDao.deleteNote(note);
		
	}
	
	public boolean addNote(Note note) {
	
		try {
			noteDao.addNote(note);
			//allNotes = noteDao.sqlGetAllNotes();
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public boolean updateNote(Note note) {

		try {
			noteDao.updateNote(note);
			//allNotes = noteDao.sqlGetAllNotes();
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}

	public int getMaxPosition() {
		
		return noteDao.getMaxPosition();
		
	}

}