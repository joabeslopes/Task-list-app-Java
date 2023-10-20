package taskListController;

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import taskListDao.Note;
import taskListDao.NoteDAO;
import taskListView.AddNote;
import taskListView.EditNote;
import taskListView.InitialScreen;

public class InitialScreenController {

	// Attributes
	private InitialScreen init;
	private NoteDAO con = new NoteDAO();
	private List<Note> allNotes;
	
	
	// Getters and setters
	
	
	
	// Constructor
	public InitialScreenController(InitialScreen init) {
		allNotes = con.getAllNotes();
		
		// action button add
		init.getBtnAdd().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddNote(allNotes).setVisible(true);
				init.dispose();
			}
		});
		
		// action button edit
		init.getBtnEdit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Enumeration<AbstractButton> elements = init.getBg().getElements();
				for (int i=0;i<allNotes.size();i++) {
					AbstractButton button = elements.nextElement();
					if (button.isSelected()) {
						Note note = allNotes.get(i);
						new EditNote(note, allNotes).setVisible(true);
						init.dispose();
						break;
					}
					
				}
		        
			}
		});
		
		
		// action button delete
		init.getBtnDelete().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Enumeration<AbstractButton> elements = init.getBg().getElements();
				for (int i=0;i<allNotes.size();i++) {
					AbstractButton button = elements.nextElement();
					if (button.isSelected()) {
						
				        Note note = allNotes.get(i);
				        con.deleteNote(note);
				        new InitialScreen().setVisible(true);
				        init.dispose();
				        break;
					}
					
				}
				
			}
		});

		
		// List all notes when the window is shown
		init.addComponentListener(new ComponentAdapter() {
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
					init.getBg().add(radioButtonPosition);
					
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
					
					
					init.getPanelNotes().add(notePanel);
					
				}
				
			}
		});
		
		
		
		
	}
	
}