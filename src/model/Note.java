package model;

public class Note {
	
	// attributes
	private int id_note, position;
	private String title, content, last_change;
	

	// getters and setters
	public int getId_note() {
		return id_note;
	}
	public void setId_note(int id_note) {
		this.id_note = id_note;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLast_change() {
		return last_change;
	}
	public void setLast_change(String last_change) {
		this.last_change = last_change;
	}
	
	
	
}
