package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dao.Note;
import model.NoteModel;
import view.AddNote;
import view.InitialScreen;

public class AddNoteController {
	
	// Attributes
	private NoteModel noteModel;
//	private List<Note> allNotes;
	
	
	
	// Getters and setters
	public NoteModel getNoteModel() {
		return noteModel;
	}

	public void setNoteModel(NoteModel noteModel) {
		this.noteModel = noteModel;
	}
	
	
	// Constructor
	public AddNoteController (AddNote addNote) {
		
		noteModel = new NoteModel();
		
		// Action button add
		addNote.getBtnSave().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				
				String title = addNote.getTextFieldTitle().getText();
				String content = addNote.getTextAreaContent().getText();
				String positionString = addNote.getTextFieldPosition().getText();
				int position = 0;
				
				try {

					if (positionString.equals("")) {
						int maxPosition = noteModel.getMaxPosition();
						position = noteModel.getMaxPosition() + 1;
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
						
						
						if ( noteModel.addNote(note) ) {
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
					JOptionPane.showMessageDialog(addNote.getTextFieldPosition(), "Position needs to be greater than 0");
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