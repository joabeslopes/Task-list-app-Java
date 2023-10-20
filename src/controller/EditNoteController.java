package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.Note;
import dao.NoteDAO;
import model.NoteModel;
import view.EditNote;
import view.InitialScreen;

public class EditNoteController {
	
	// Attributes
	private NoteModel noteModel;
	private List<Note> allNotes;

	
	// Getters and setters
	public NoteModel getNoteModel() {
		return noteModel;
	}

	public void setNoteModel(NoteModel noteModel) {
		this.noteModel = noteModel;
	}
	
	
	public List<Note> getAllNotes() {
		return allNotes;
	}


	public void setAllNotes(List<Note> allNotes) {
		this.allNotes = allNotes;
	}
	
	
	
	// Constructor
	public EditNoteController (EditNote editNote) {

		noteModel = editNote.getNoteModel();
		
		allNotes = noteModel.getAllNotes();
		
		Note note = editNote.getNote();

		// Set textFields
		editNote.setTextFieldTitle( new JTextField(note.getTitle()) );
		editNote.setTextFieldPosition( new JTextField( String.valueOf(note.getPosition()) ) );
		editNote.setTextAreaContent( new JTextArea( note.getContent() ) );
		
		// Action button save
		editNote.getBtnSave().addActionListener(new ActionListener() {
			
//			public int evalPosition() {
//				
//				String positionString = editNote.getTextFieldPosition().getText();
//				int position = 0;
//				int listSize = allNotes.size();
//				
//				if (positionString.equals("")) {
//					position = listSize+1;
//				}
//				else
//				{
//					try {
//						int intValue = Integer.valueOf(positionString);
//						if (listSize > 0 && intValue>listSize || intValue < 0) {
//							JOptionPane.showMessageDialog(editNote.getTextFieldPosition(), "Position needs to be greater than 0 and be on the range of notes");
//						}
//						else {
//							position = intValue;
//						}
//						
//					}
//					catch (Exception errorPosition) {
//						JOptionPane.showMessageDialog(editNote.getTextFieldPosition(), "Only integer number values are allowed on position");
//					}
//				}
//				
//				return position;
//			}
			
			public void actionPerformed(ActionEvent e) {
				
				
				String title = editNote.getTextFieldTitle().getText();
				String content = editNote.getTextAreaContent().getText();
				int position = 0;
				try {
					position = Integer.valueOf( editNote.getTextFieldPosition().getText() );
				}
				catch (Exception errorPosition) {
					JOptionPane.showMessageDialog(editNote.getTextFieldPosition(), "Position needs a integer number");
				}
				
				
				// test if position is a valid number
				if (position!=0) {
					if (content.equals("")) 
					{
						JOptionPane.showMessageDialog(editNote.getTextAreaContent(), "The note needs to have a content");
					}
					else 
					{
						
//						if (note.getPosition() != position ) {
//							dao.switchPositionEdit(allNotes, note, position);
//						}
						
						note.setTitle(title);
						note.setContent(content);
						note.setPosition(position);
						
						allNotes.set(position-1, note);
						
						
						if ( true ) {
							noteModel.updateNotes(allNotes);
							InitialScreen initScreen = new InitialScreen(noteModel);
							initScreen.setNoteModel(noteModel);
							initScreen.setVisible(true);
							editNote.dispose();
						}
						else {
							JOptionPane.showMessageDialog(editNote.getTextAreaContent(), "Please try again");
						}
						
					
					}
					
				}

				
			}
		});
		
		
		// Action button cancel
		editNote.getBtnCancel().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InitialScreen(noteModel).setVisible(true);
				editNote.dispose();
			}
		});
		
		
		
	}

}