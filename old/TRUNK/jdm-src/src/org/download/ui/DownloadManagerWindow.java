package org.download.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.download.bean.DownloadBean;
import org.download.controller.DownloadTableModel;

public class DownloadManagerWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private GridBagConstraints constraints;
	private JTable tblDownloads = new JTable();
	private JButton btnStart = new JButton("Start");
	private JButton btnNewDownload = new JButton("New Download");
	private JButton btnDelDownload = new JButton("Remove Download");
	private JButton btnDelDownloadCompleted = new JButton("Remove Completed Download");
	private JButton btnSaveList = new JButton("Save List");
	private JButton btnLoadList = new JButton("Load List");
	private JButton btnExit = new JButton("Exit");
	
	public DownloadManagerWindow() {
		Container container = getContentPane();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Java Download Manager");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5,5,5,5);
		
		container.setLayout(new BorderLayout());
		
		JPanel pnlButtons = new JPanel(new GridBagLayout());
				
		pnlButtons.add(btnStart, getConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));		
		pnlButtons.add(btnNewDownload, getConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		pnlButtons.add(btnDelDownload, getConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		pnlButtons.add(btnDelDownloadCompleted, getConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		pnlButtons.add(btnSaveList, getConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		pnlButtons.add(btnLoadList, getConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		pnlButtons.add(btnExit, getConstraints(1, 7, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		pnlButtons.add(new JLabel(" "), getConstraints(1, 8, 1, 1, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL));
		
		JPanel pnlTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTitle = new JLabel("Download List");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
		pnlTitle.add(lblTitle);
		
		container.add(pnlTitle, BorderLayout.NORTH);
		container.add(new JScrollPane(tblDownloads), BorderLayout.CENTER);
		container.add(pnlButtons, BorderLayout.EAST);
	}
	
	public void addActionListener(ActionListener l) {
		btnStart.setActionCommand("START");
		btnStart.addActionListener(l);
		btnNewDownload.setActionCommand("NEW");
		btnNewDownload.addActionListener(l);
		btnDelDownload.setActionCommand("DEL");
		btnDelDownload.addActionListener(l);
		btnDelDownloadCompleted.setActionCommand("COMP");
		btnDelDownloadCompleted.addActionListener(l);
		btnSaveList.setActionCommand("SAVE");
		btnSaveList.addActionListener(l);
		btnLoadList.setActionCommand("LOAD");
		btnLoadList.addActionListener(l);
		btnExit.setActionCommand("EXIT");
		btnExit.addActionListener(l);
	}
	
	protected void refreshTable(Vector<DownloadBean> data) {
		tblDownloads.setModel(new DownloadTableModel(data));
	}
	
	protected Vector<DownloadBean> getDownloadList() {
		DownloadTableModel model = (DownloadTableModel)tblDownloads.getModel();
		return model.getData();
	}
	
	protected int[] getSelectedDownloads() {
		return tblDownloads.getSelectedRows();
	}
	
	private GridBagConstraints getConstraints(int x, int y, int gx, int gy, double wx, double wy, int anchor, int fill) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gx;
		constraints.gridheight = gy;
		constraints.weightx = wx;
		constraints.weighty = wy;
		constraints.anchor = anchor;
		constraints.fill = fill;
		
		return constraints;
	}
}