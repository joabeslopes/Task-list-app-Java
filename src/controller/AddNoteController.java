package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.NoteDAO;
import model.Note;
import view.AddNote;
import view.InitialScreen;

public class AddNoteController {
	
	// Attributes
	private NoteDAO noteDao;
	
	// Getters and setters

	public NoteDAO getNoteDao() {
		return noteDao;
	}


	public void setNoteDao(NoteDAO noteDao) {
		this.noteDao = noteDao;
	}	
	// Constructor
	public AddNoteController (AddNote addNote) {
		
		
		
		// Action button add
		addNote.getBtnSave().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				
				String title = addNote.getTextFieldTitle().getText();
				String content = addNote.getTextAreaContent().getText();
				String positionString = addNote.getTextFieldPosition().getText();
				int position = 0;
				int maxPosition = 0;
				
				try {

					if (positionString.equals("")) {
						maxPosition = noteDao.getMaxPosition();
						position = maxPosition + 1;
					}
					else {
						position = Integer.valueOf(positionString);
					}
				}
				catch (Exception errorPosition) {
					JOptionPane.showMessageDialog(addNote.getTextFieldPosition(), "Position needs to be an integer number, and greater than 0");
				}
				
				// test if position is a valid number
				if (position>0) {
					if (content.equals("")) 
					{
						JOptionPane.showMessageDialog(addNote.getTextAreaContent(), "The note needs to have a content");
					}
					else 
					{

						Note note = new Note();
						note.setTitle(title);
						note.setContent(content);
						note.setPosition(position);
						
						
						if ( noteDao.addNote(note) ) {
							InitialScreen initScreen = new InitialScreen();
							initScreen.setVisible(true);
							addNote.dispose();
						}
						else {
							JOptionPane.showMessageDialog(addNote.getTextAreaContent(), "Please try again");
						}
					
					}
					
				}
				else {
					JOptionPane.showMessageDialog(addNote.getTextFieldPosition(), "Please try again");
				}

				
			}
		});
		

		// Action button cancel
		addNote.getBtnCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InitialScreen().setVisible(true);
				addNote.dispose();
			}
		});
		
		
		
	}
}