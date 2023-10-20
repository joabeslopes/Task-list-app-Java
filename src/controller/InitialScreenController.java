package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import dao.Note;
import model.NoteModel;
import view.AddNote;
import view.EditNote;
import view.InitialScreen;

public class InitialScreenController {

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


	// Constructor
	public InitialScreenController(InitialScreen initScreen) {
		noteModel = initScreen.getNoteModel();
		allNotes = noteModel.getAllNotes();
		
		// action button add
		initScreen.getBtnAdd().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNote addNote = new AddNote(noteModel);
				addNote.setVisible(true);
				initScreen.dispose();
			}
		});
		
		// action button edit
		initScreen.getBtnEdit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Enumeration<AbstractButton> elements = initScreen.getBg().getElements();
				for (int i=0;i<allNotes.size();i++) {
					AbstractButton button = elements.nextElement();
					if (button.isSelected()) {
						Note note = allNotes.get(i);
						EditNote editNote = new EditNote(noteModel, note);
						editNote.setNoteModel(noteModel);
						editNote.setVisible(true);
						initScreen.dispose();
						break;
					}
					
				}
		        
			}
		});
		
		
		// action button delete
		initScreen.getBtnDelete().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Enumeration<AbstractButton> elements = initScreen.getBg().getElements();
				for (int i=0;i<allNotes.size();i++) {
					AbstractButton button = elements.nextElement();
					if (button.isSelected()) {
						
				        allNotes.remove(i);
				        noteModel.updateNotes(allNotes);
				        new InitialScreen(noteModel).setVisible(true);
				        initScreen.dispose();
				        break;
					}
					
				}
				
			}
		});

		
		// List all notes when the window is shown
		initScreen.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				
				for (int i=0; i<allNotes.size(); i++) {
					
					int position = allNotes.get(i).getPosition();
					String title = allNotes.get(i).getTitle();
					
					String subContent = String.format("%.30s", allNotes.get(i).getContent()) + " ..." ;
					
					String lastChange = allNotes.get(i).getLast_change();
										
					
					JRadioButton radioButtonPosition = new JRadioButton(String.valueOf(position));
					radioButtonPosition.setFont(new Font("Tahoma", Font.BOLD, 14));
					radioButtonPosition.setBackground(new Color(223, 0, 223));
					radioButtonPosition.setForeground(new Color(255, 255, 128));
					initScreen.getBg().add(radioButtonPosition);
					
					JLabel lblNoteTitle = new JLabel(title);
					lblNoteTitle.setHorizontalAlignment(SwingConstants.LEFT);
					lblNoteTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
					lblNoteTitle.setForeground(new Color(230, 230, 128));
					
					JLabel lblNoteSubContent = new JLabel(subContent);
					lblNoteSubContent.setHorizontalAlignment(SwingConstants.LEFT);
					lblNoteSubContent.setFont(new Font("Tahoma", Font.BOLD, 15));
					lblNoteSubContent.setForeground(new Color(230, 230, 128));
					
					JLabel lblNoteLastChange = new JLabel("Last change: "+lastChange);
					lblNoteLastChange.setHorizontalAlignment(SwingConstants.LEFT);
					lblNoteLastChange.setFont(new Font("Tahoma", Font.BOLD, 15));
					lblNoteLastChange.setForeground(new Color(255, 255, 200));
					
					JPanel notePanel = new JPanel();
					notePanel.setLayout(new GridLayout(4, 1, 0, 0));
					notePanel.setBackground(new Color(223, 0, 223));
					notePanel.add(radioButtonPosition);
					notePanel.add(lblNoteTitle);
					notePanel.add(lblNoteSubContent);
					notePanel.add(lblNoteLastChange);
					
					
					initScreen.getPanelNotes().add(notePanel);
					
				}
				
			}
		});
		
		
		
		
	}

	
	
}