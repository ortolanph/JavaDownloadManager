package org.download.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.download.ui.Centralizer;
import org.download.ui.DownloadAdderWindow;

public class DownloadAdder extends DownloadAdderWindow implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int ACTION_CANCELED = 0;
	public static final int ACTION_ACCEPTED = 1;
	
	private int action;

	public DownloadAdder(JFrame parent) {
		super(parent);
		setSize(800, 220);
		setLocation(Centralizer.getCenterPosition(getWidth(), getHeight()));
		addActionListener(this);
	}
	
	public int showDialog() {
		setVisible(true);
		return action;
	}
	
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if(action.equals("ADD")) {
			this.action = ACTION_ACCEPTED;
			dispose();
		}
		
		if(action.equals("...")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(false);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int faction = fileChooser.showDialog(this, "Select");
			
			
			if(faction == JFileChooser.APPROVE_OPTION) {
				setSaveToContents(fileChooser.getSelectedFile().getPath());
			}
		}
		
		if(action.equals("CANCEL")) {
			this.action = ACTION_CANCELED;
			dispose();
		}
	}
}
