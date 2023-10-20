package taskListView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import taskListDao.Note;
import taskListDao.NoteDAO;


@SuppressWarnings("serial")
public class EditNote extends JFrame {

	private JTextField textFieldTitle;
	private JTextField textFieldPosition;
	private NoteDAO con = new NoteDAO();

	public EditNote(Note note, List<Note> allNotes) {
		
		setResizable(false);
		setSize(630, 470);
		setLocationRelativeTo(null);
		setTitle("Task List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel background = new JPanel();
		background.setBackground(new Color(128, 0, 128));
		getContentPane().add(background, BorderLayout.CENTER);
		background.setLayout(null);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setOpaque(false);
		panelButtons.setBounds(10, 364, 594, 56);
		background.add(panelButtons);
		panelButtons.setLayout(new GridLayout(0, 2, 200, 5));

		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitle.setForeground(new Color(255, 255, 128));
		lblTitle.setBounds(10, 11, 87, 26);
		background.add(lblTitle);
		
		JLabel lblPosition = new JLabel("Position number (ex.: 1) :");
		lblPosition.setHorizontalAlignment(SwingConstants.LEFT);
		lblPosition.setForeground(new Color(255, 255, 128));
		lblPosition.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPosition.setBounds(10, 69, 241, 26);
		background.add(lblPosition);
		
		JLabel lblContent = new JLabel("Content:");
		lblContent.setHorizontalAlignment(SwingConstants.LEFT);
		lblContent.setForeground(new Color(255, 255, 128));
		lblContent.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblContent.setBounds(10, 127, 87, 26);
		background.add(lblContent);
		
		textFieldTitle = new JTextField( note.getTitle() );
		textFieldTitle.setBounds(11, 38, 593, 26);
		background.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		textFieldPosition = new JTextField( String.valueOf(note.getPosition()) );
		textFieldPosition.setColumns(10);
		textFieldPosition.setBounds(10, 95, 87, 26);
		background.add(textFieldPosition);
		
		JScrollPane scrollPaneContent = new JScrollPane();
		scrollPaneContent.setBounds(10, 156, 594, 197);
		background.add(scrollPaneContent);
		
		JTextArea textAreaContent = new JTextArea( note.getContent() );
		textAreaContent.setLineWrap(true);
		scrollPaneContent.setViewportView(textAreaContent);
		
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			
			public int evalPosition() {
				
				String positionString = textFieldPosition.getText();
				int position = 0;
				int listSize = allNotes.size();
				
				if (positionString.equals("")) {
					position = listSize+1;
				}
				else
				{
					try {
						int intValue = Integer.valueOf(positionString);
						if (listSize > 0 && intValue>listSize || intValue < 0) {
							JOptionPane.showMessageDialog(textFieldPosition, "Position needs to be greater than 0 and be on the range of notes");
						}
						else {
							position = intValue;
						}
						
					}
					catch (Exception errorPosition) {
						JOptionPane.showMessageDialog(textFieldPosition, "Only integer number values are allowed on position");
					}
				}
				
				return position;
			}
			
			
			public void actionPerformed(ActionEvent e) {
				
				
				String title = textFieldTitle.getText();
				String content = textAreaContent.getText();
				int position = evalPosition();
				// test if position is a valid number
				if (position!=0) {
					if (content.equals("")) 
					{
						JOptionPane.showMessageDialog(textFieldPosition, "The note needs to have a content");
					}
					else 
					{
						
						if (note.getPosition() != position ) {
							con.switchPositionEdit(allNotes, note, position);
						}
						
						note.setTitle(title);
						note.setContent(content);
						
						if (con.updateNote(note)) {
							new InitialScreen().setVisible(true);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(textFieldPosition, "Please try again");
						}
						
					
					}
					
				}

				
			}
		});
		panelButtons.add(btnSave);
		
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InitialScreen().setVisible(true);
				dispose();
			}
		});
		panelButtons.add(btnCancel);
		
	}
	
	
}