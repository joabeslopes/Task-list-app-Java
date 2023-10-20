package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.InitialScreenController;
import model.NoteModel;


@SuppressWarnings("serial")
public class InitialScreen extends JFrame {

	// Attributes
	private ButtonGroup bg = new ButtonGroup();
	private JPanel panelNotes = new JPanel();
	private JPanel panelButtons = new JPanel();
	private JButton btnAdd = new JButton("Add");
	private JButton btnEdit = new JButton("Edit");
	private JButton btnDelete = new JButton("Delete");
	private NoteModel noteModel;
	


	// Getters and setters
	public NoteModel getNoteModel() {
		return noteModel;
	}

	public void setNoteModel(NoteModel noteModel) {
		this.noteModel = noteModel;
	}
	
	
	public JPanel getPanelButtons() {
		return panelButtons;
	}

	public void setPanelButtons(JPanel panelButtons) {
		this.panelButtons = panelButtons;
	}

	public JButton getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(JButton btnAdd) {
		this.btnAdd = btnAdd;
	}

	public JButton getBtnEdit() {
		return btnEdit;
	}

	public void setBtnEdit(JButton btnEdit) {
		this.btnEdit = btnEdit;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public ButtonGroup getBg() {
		return bg;
	}

	public void setBg(ButtonGroup bg) {
		this.bg = bg;
	}

	public JPanel getPanelNotes() {
		return panelNotes;
	}

	public void setPanelNotes(JPanel panelNotes) {
		this.panelNotes = panelNotes;
	}
	
	
	// Constructor
	public InitialScreen( NoteModel noteModel) {
		this.noteModel = noteModel;
		new InitialScreenController(InitialScreen.this);

		setResizable(false);
		setSize(615, 470);
		setLocationRelativeTo(null);
		setTitle("Task List");
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
		
		
		panelNotes.setBackground(new Color(128, 0, 128));
		scrollPane.setViewportView(panelNotes);
		panelNotes.setLayout(new GridLayout(0, 1, 0, 20));
		
		
		panelButtons.setOpaque(false);
		panelButtons.setBounds(10, 364, 594, 56);
		background.add(panelButtons);
		panelButtons.setLayout(new GridLayout(0, 3, 70, 5));

		panelButtons.add(btnAdd);
		panelButtons.add(btnEdit);
		panelButtons.add(btnDelete);
		
		
	}
}
