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
		allNotes = noteDao.getAllNotes();
	}
	
	public void updateNotes(List<Note> notes) {
		noteDao.updateAllNotes(notes);
		noteDao.sqlGetAllNotes();
		allNotes = noteDao.getAllNotes();
		
	}
	

}
