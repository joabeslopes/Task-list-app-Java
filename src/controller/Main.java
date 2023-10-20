package controller;

import java.awt.EventQueue;

import model.NoteModel;
import view.InitialScreen;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitialScreen initScreen = new InitialScreen( new NoteModel() );
					initScreen.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}