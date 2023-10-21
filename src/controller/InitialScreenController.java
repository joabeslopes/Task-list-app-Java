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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import dao.NoteDAO;
import model.Note;
import view.AddNote;
import view.EditNote;
import view.InitialScreen;

public class InitialScreenController {

	// Attributes
	private List<Note> allNotes;
	private NoteDAO noteDao;

	
	// Getters and setters

	public List<Note> getAllNotes() {
		return allNotes;
	}


	public void setAllNotes(List<Note> allNotes) {
		this.allNotes = allNotes;
	}


	public NoteDAO getNoteDao() {
		return noteDao;
	}


	public void setNoteDao(NoteDAO noteDao) {
		this.noteDao = noteDao;
	}
	
	

	// Constructor
	public InitialScreenController(InitialScreen initScreen) {
		noteDao = new NoteDAO();
		
		// action button add
		initScreen.getBtnAdd().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNote addNote = new AddNote();
				addNote.getAddNoteController().setNoteDao(noteDao);
				addNote.setVisible(true);
				initScreen.dispose();
			}
		});
		
		// action button edit
		initScreen.getBtnEdit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Enumeration<AbstractButton> elements = initScreen.getBg().getElements();
				
				allNotes = noteDao.getAllNotes();
				
				int listSize = allNotes.size();
				for (int i=0;i<listSize;i++) {
					AbstractButton button = elements.nextElement();
					if (button.isSelected()) {
						Note note = allNotes.get(i);
						EditNote editNote = new EditNote(note);
						editNote.getEditNoteController().setNoteDao(noteDao);
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
				allNotes = noteDao.getAllNotes();
				boolean isSelected = false;
				int listSize = allNotes.size();
				for (int i=0;i<listSize;i++) {
					AbstractButton button = elements.nextElement();
					if (button.isSelected()) {
						Note note = allNotes.get(i);
						
						noteDao.deleteNote(note);
						
				        new InitialScreen().setVisible(true);
				        initScreen.dispose();
				        isSelected = true;
				        break;
					}
				}
				
				if (!isSelected) {
					int wantDeleteAll = JOptionPane.showConfirmDialog(initScreen, "Do you want to delete all the notes?", "Confirm choice", JOptionPane.YES_NO_OPTION );
					if (wantDeleteAll == 0) {
						noteDao.deleteAllNotes();
						new InitialScreen().setVisible(true);
				        initScreen.dispose();
						
					}
				}
				
			}
		});

		
		// List all notes when the window is shown
		initScreen.addComponentListener(new ComponentAdapter() {
			
			public void addNoteOnScreen(int index) {
				
				int position = allNotes.get(index).getPosition();
				String title = allNotes.get(index).getTitle();

				String subContent = String.format("%.30s", allNotes.get(index).getContent()) + " ..." ;

				String lastChange = allNotes.get(index).getLast_change();


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
			
			@Override
			public void componentShown(ComponentEvent e) {
				
				allNotes = noteDao.getAllNotes();
				int listSize = allNotes.size();
				
				if (listSize==1) {
					
					addNoteOnScreen(0);
					
					JPanel ghostPanel = new JPanel();
					ghostPanel.setLayout(new GridLayout(4, 1, 0, 0));
					ghostPanel.setBackground(new Color(128, 0, 128));
					initScreen.getPanelNotes().add(ghostPanel);
					
				}
				else {
					for (int i=0;i<listSize;i++) {

						addNoteOnScreen(i);
						
					}
				}
				
				
			}
		});
		
		
		
		
	}
	
	
}