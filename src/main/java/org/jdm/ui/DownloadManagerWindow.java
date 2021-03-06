package org.jdm.ui;

import org.jdm.bean.DownloadBean;
import org.jdm.controller.DownloadTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class DownloadManagerWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private GridBagConstraints constraints;
    private JTable tblDownloads = new JTable();
    private JButton btnStart = new JButton("Start", new ImageIcon(getImage("icons/01-start.png")));
    private JButton btnNewDownload = new JButton("New Download", new ImageIcon(getImage("icons/02-new-download.png")));
    private JButton btnDelDownload = new JButton("Remove Download", new ImageIcon(getImage("icons/03-remove-download.png")));
    private JButton btnDelDownloadCompleted = new JButton("Remove Completed Download", new ImageIcon(getImage("icons/04-remove-completed-download.png")));
    private JButton btnSaveList = new JButton("Save List", new ImageIcon(getImage("icons/05-save-list.png")));
    private JButton btnLoadList = new JButton("Load List", new ImageIcon(getImage("icons/06-load-list.png")));
    private JButton btnExit = new JButton("Exit", new ImageIcon(getImage("icons/07-exit.png")));

    public DownloadManagerWindow() {
        Container container = getContentPane();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Java Download Manager");
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

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

    protected void refreshTable(java.util.List<DownloadBean> data) {
        tblDownloads.setModel(new DownloadTableModel(data));
    }

    protected java.util.List<DownloadBean> getDownloadList() {
        DownloadTableModel model = (DownloadTableModel) tblDownloads.getModel();
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

    private Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}