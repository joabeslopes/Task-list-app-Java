package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.Note;
import model.NoteModel;
import view.EditNote;
import view.InitialScreen;

public class EditNoteController {
	
	// Attributes
	private NoteModel noteModel;

	
	// Getters and setters
	public NoteModel getNoteModel() {
		return noteModel;
	}

	public void setNoteModel(NoteModel noteModel) {
		this.noteModel = noteModel;
	}

	
	
	
	// Constructor
	public EditNoteController (EditNote editNote) {

		noteModel = new NoteModel();
		
		Note note = editNote.getNote();

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
						int maxPosition = noteModel.getMaxPosition();
						position = noteModel.getMaxPosition() + 1;
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
						
						
						if ( noteModel.updateNote(note) ) {
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
					JOptionPane.showMessageDialog(editNote.getTextFieldPosition(), "Position needs to be greater than 0");
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