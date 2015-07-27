package org.download.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.download.bean.DownloadBean;
import org.download.data.DownloadSorter;
import org.download.enums.DownloadStatus;
import org.download.process.DownloadProcess;
import org.download.saver.DownloadSaver;
import org.download.ui.Centralizer;
import org.download.ui.DownloadManagerWindow;

public class DownloadManager extends DownloadManagerWindow implements ActionListener {

	private static final long serialVersionUID = 1L;

	public DownloadManager() {
		setSize(800, 600);
		setLocation(Centralizer.getCenterPosition(getWidth(), getHeight()));
		addActionListener(this);
		refreshTable(new Vector<DownloadBean>());
	}
	
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		
		if(action.equals("START")) {
			removeCompleted();
			Vector<DownloadBean> downloads = getDownloadList();
			
			DownloadProcess dp = new DownloadProcess(this, downloads);
			Thread t = new Thread(dp);
			t.start();
			
			completeDownloadProcess();
		}

		if (action.equals("NEW")) {
			DownloadAdder da = new DownloadAdder(this);
			int result = da.showDialog();
			
			if(result == DownloadAdder.ACTION_ACCEPTED) {
				Vector<DownloadBean> data = getDownloadList();
				data.add(da.getBean());
				refreshTable(DownloadSorter.sortByStatus(data));
			}
		}

		if (action.equals("DEL")) {
			Vector<DownloadBean> data = getDownloadList();
			Vector<DownloadBean> copy = new Vector<DownloadBean>();
			
			int[] selected = getSelectedDownloads();
			
			for (int i : selected) {
				data.set(i, null);
			}
			
			for (DownloadBean bean : data) {
				if(bean!= null) {
					copy.add(bean);
				}
			}
			
			refreshTable(DownloadSorter.sortByStatus(copy));
		}
		
		if (action.equals("COMP")) {
			removeCompleted();
		}

		if (action.equals("SAVE")) {
			JFileChooser saveDialog = new JFileChooser();
			
			int result = saveDialog.showSaveDialog(this);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				DownloadSaver.save(saveDialog.getSelectedFile(), getDownloadList());
			}
		}

		if (action.equals("LOAD")) {
			Vector<DownloadBean> data = new Vector<DownloadBean>();
			
			JFileChooser openDialog = new JFileChooser();
			
			int result = openDialog.showOpenDialog(this);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				data = DownloadSaver.load(openDialog.getSelectedFile());
			}
			
			if(data != null) {
				refreshTable(DownloadSorter.sortByStatus(data));
			} else {
				JOptionPane.showMessageDialog(this, "Invalid file or corrupted.", "Error on loading file", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (action.equals("EXIT")) {
			System.exit(0);
		}
	}
	
	public void updateTable(DownloadBean bean) {
		Vector<DownloadBean> data = getDownloadList();
		
		data.set(data.indexOf(bean), bean);
		
		refreshTable(data);
	}
	
	protected void removeCompleted() {
		Vector<DownloadBean> data = getDownloadList();
		Vector<DownloadBean> copy = new Vector<DownloadBean>(getDownloadList());
		
		for (DownloadBean bean : data) {
			if(bean.getStatus().equals(DownloadStatus.COMPLETED)) {
				copy.remove(bean);
			}
		}
		
		refreshTable(DownloadSorter.sortByStatus(copy));
	}

	public static void main(String[] args) {
		DownloadManager manager = new DownloadManager();
		manager.setVisible(true);
	}

	public void completeDownloadProcess() {
		Vector<DownloadBean> data = getDownloadList();
		refreshTable(DownloadSorter.sortByStatus(data));
	}
}
