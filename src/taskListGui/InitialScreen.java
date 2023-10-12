package taskListGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import taskListDao.Note;
import taskListDao.SqliteConnector;


@SuppressWarnings("serial")
public class InitialScreen extends JFrame {

	public SqliteConnector con = new SqliteConnector();
	ButtonGroup bg = new ButtonGroup();
	int positionSelected;
	List<Note> allNotes;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitialScreen frame = new InitialScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	
	public InitialScreen() {
		
		setResizable(false);
		setTitle("Task List");
		SqliteConnector con = new SqliteConnector();
		
		setBounds(100, 100, 630, 470);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel background = new JPanel();
		background.setBackground(new Color(128, 0, 128));
		getContentPane().add(background, BorderLayout.CENTER);
		background.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setRequestFocusEnabled(false);
		scrollPane.setBounds(10, 11, 594, 342);
		background.add(scrollPane);
		
		JPanel panelNotes = new JPanel();
		panelNotes.setBackground(new Color(128, 0, 128));
		scrollPane.setViewportView(panelNotes);
		panelNotes.setLayout(new GridLayout(0, 1, 0, 20));

		
		JPanel panelButtons = new JPanel();
		panelButtons.setOpaque(false);
		panelButtons.setBounds(10, 364, 594, 56);
		background.add(panelButtons);
		panelButtons.setLayout(new GridLayout(0, 3, 70, 5));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddNote().setVisible(true);
				dispose();
			}
		});
		panelButtons.add(btnAdd);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				Enumeration<AbstractButton> elements = bg.getElements();
				for (int i=0;i<allNotes.size();i++) {
					AbstractButton button = elements.nextElement();
					if (button.isSelected()) {
						Note note = allNotes.get(i);
						new EditNote(note).setVisible(true);
						dispose();
						break;
					}
					
				}
				
		        
			}
		});
		
		panelButtons.add(btnEdit);
		
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				@SuppressWarnings("rawtypes")
				Enumeration elements = bg.getElements();
				int i=0;
		        while (elements.hasMoreElements()) {
		          AbstractButton button = (AbstractButton) elements.nextElement();
		          if (button.isSelected()) {
		            Note note = allNotes.get(i);
		            
		            con.deleteNote(note);
		            new InitialScreen().setVisible(true);
		            dispose();
		            
		            break;
		          }
		          else {
			          i++;
		          }
		        }
				
			}
		});
		panelButtons.add(btnDelete);
		
		// List all notes when the window is shown
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				
				allNotes = con.getAllNotes();
				for (int i=0; i<allNotes.size(); i++) {
					
					int position = allNotes.get(i).getPosition();
					String title = allNotes.get(i).getTitle();
					
					String subContent = String.format("%.30s", allNotes.get(i).getContent()) + "..." ;
					
					String lastChange = allNotes.get(i).getLast_change();
										
					
					JRadioButton radioButtonPosition = new JRadioButton(String.valueOf(position));
					radioButtonPosition.setFont(new Font("Tahoma", Font.BOLD, 14));
					radioButtonPosition.setBackground(new Color(223, 0, 223));
					radioButtonPosition.setForeground(new Color(255, 255, 128));
					bg.add(radioButtonPosition);
					
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
					
					
					panelNotes.add(notePanel);
					
					
				}
				
			}
		});
		
	}
}
