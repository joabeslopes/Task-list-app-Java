package taskListController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import taskListDao.Note;
import taskListDao.NoteDAO;
import taskListView.AddNote;
import taskListView.InitialScreen;

public class AddNoteController {
	
	// Attributes
	private NoteDAO con;
	private List<Note> allNotes;
	
	// Getters and setters
	public NoteDAO getCon() {
		return con;
	}

	public void setCon(NoteDAO con) {
		this.con = con;
	}
	
	
	public List<Note> getAllNotes() {
		return allNotes;
	}


	public void setAllNotes(List<Note> allNotes) {
		this.allNotes = allNotes;
	}

	public AddNoteController (AddNote addNote) {
		
		setCon (InitialScreenController.getCon());
		setAllNotes(InitialScreenController.getAllNotes());
		
		// Action button add
		addNote.getBtnSave().addActionListener(new ActionListener() {
			
			public int evalPosition() {
				String positionString = addNote.getTextFieldPosition().getText();
				int position = 0;
				int listSize = allNotes.size();
				
				if (positionString.equals("")) {
					position = listSize+1;
				}
				else
				{
					try {
						int intValue = Integer.valueOf(positionString);
						if (listSize > 0 && intValue>listSize+1 || intValue < 0) {
							JOptionPane.showMessageDialog(addNote.getTextFieldPosition(), "Position needs to be greater than 0 and be on the range of notes");
						}
						else {
							position = intValue;
						}
						
					}
					catch (Exception errorPosition) {
						JOptionPane.showMessageDialog(addNote.getTextFieldPosition(), "Only integer number values are allowed on position");
					}
				}
				
				return position;
			}
			
			public void actionPerformed(ActionEvent e) {
				
				
				String title = addNote.getTextFieldTitle().getText();
				String content = addNote.getTextAreaContent().getText();
				int position = evalPosition();
				// test if position is a valid number
				if (position!=0) {
					if (content.equals("")) 
					{
						JOptionPane.showMessageDialog(addNote.getTextAreaContent(), "The note needs to have a content");
					}
					else 
					{
					
						con.switchPositionAdd(allNotes, position);
						Note note = new Note();
						note.setTitle(title);
						note.setContent(content);
						note.setPosition(position);
						
						if (con.addNote(note)) {
							new InitialScreen().setVisible(true);
							addNote.dispose();
						}
						else {
							JOptionPane.showMessageDialog(addNote.getTextAreaContent(), "Please try again");
						}
					
					}
					
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