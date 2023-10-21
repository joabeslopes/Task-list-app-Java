package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.NoteDAO;
import model.Note;
import view.EditNote;
import view.InitialScreen;

public class EditNoteController {
	
	// Attributes
	private NoteDAO noteDao;
	Note note;
	
	
	// Getters and setters
	
	public Note getNote() {
		return note;
	}


	public void setNote(Note note) {
		this.note = note;
	}


	public NoteDAO getNoteDao() {
		return noteDao;
	}


	public void setNoteDao(NoteDAO noteDao) {
		this.noteDao = noteDao;
	}
	
	public EditNoteController(EditNote editNote) {
		
		this.note = editNote.getNote();
		// Set textFields
		editNote.setTextFieldTitle( new JTextField(note.getTitle()) );
		editNote.setTextFieldPosition( new JTextField( String.valueOf(note.getPosition()) ) );
		editNote.setTextAreaContent( new JTextArea( note.getContent() ) );
		
		// Action button save
		editNote.getBtnSave().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				
				String title = editNote.getTextFieldTitle().getText();
				String content = editNote.getTextAreaContent().getText();
				String positionString = editNote.getTextFieldPosition().getText();
				int position = 0;
				try {

					if (positionString.equals("")) {
						int maxPosition = noteDao.getMaxPosition();
						position = maxPosition + 1;
					}
					else {
						position = Integer.valueOf(positionString);
					}
				}
				catch (Exception errorPosition) {
					JOptionPane.showMessageDialog(editNote.getTextFieldPosition(), "Position needs to be an integer number, and greater than 0");
				}
				
				
				// test if position is a valid number
				if (position>0) {
					if (content.equals("")) 
					{
						JOptionPane.showMessageDialog(editNote.getTextAreaContent(), "The note needs to have a content");
					}
					else 
					{
						note.setTitle(title);
						note.setContent(content);
						note.setPosition(position);
						
						
						if ( noteDao.updateNote(note) ) {
							InitialScreen initScreen = new InitialScreen();
							initScreen.setVisible(true);
							editNote.dispose();
						}
						else {
							JOptionPane.showMessageDialog(editNote.getTextAreaContent(), "Please try again");
						}
						
					
					}
					
				}
				else {
					JOptionPane.showMessageDialog(editNote.getTextFieldPosition(), "Please try again");
				}
				
			}
		});
		
		
		// Action button cancel
		editNote.getBtnCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InitialScreen().setVisible(true);
				editNote.dispose();
			}
		});
	}

}