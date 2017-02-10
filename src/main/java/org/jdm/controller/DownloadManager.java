package org.jdm.controller;

import org.jdm.bean.DownloadBean;
import org.jdm.data.DownloadSorter;
import org.jdm.enums.DownloadStatus;
import org.jdm.process.DownloadProcess;
import org.jdm.saver.DownloadSaver;
import org.jdm.ui.Centralizer;
import org.jdm.ui.DownloadManagerWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DownloadManager extends DownloadManagerWindow implements ActionListener {

    private static final long serialVersionUID = 1L;

    public DownloadManager() {
        setSize(800, 600);
        setLocation(Centralizer.getCenterPosition(getWidth(), getHeight()));
        addActionListener(this);
        refreshTable(new ArrayList<DownloadBean>());
    }

    public static void main(String[] args) {
        DownloadManager manager = new DownloadManager();
        manager.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();

        if (action.equals("START")) {
            removeCompleted();
            List<DownloadBean> downloads = getDownloadList();

            DownloadProcess dp = new DownloadProcess(this, downloads);
            Thread t = new Thread(dp);
            t.start();

            completeDownloadProcess();
        }

        if (action.equals("NEW")) {
            DownloadAdder adder = new DownloadAdder(this);
            int result = adder.showDialog();

            if (result == DownloadAdder.ACTION_ACCEPTED) {
                List<DownloadBean> data = getDownloadList();
                data.add(adder.getBean());
                refreshTable(DownloadSorter.sortByStatus(data));
            }
        }

        if (action.equals("DEL")) {
            List<DownloadBean> data = getDownloadList();
            List<DownloadBean> copy = new ArrayList<>();

            int[] selected = getSelectedDownloads();

            for (int i : selected) {
                data.set(i, null);
            }

            for (DownloadBean bean : data) {
                if (bean != null) {
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

            if (result == JFileChooser.APPROVE_OPTION) {
                DownloadSaver.save(saveDialog.getSelectedFile(), getDownloadList());
            }
        }

        if (action.equals("LOAD")) {
            List<DownloadBean> data = new ArrayList<>();

            JFileChooser openDialog = new JFileChooser();

            int result = openDialog.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                data = DownloadSaver.load(openDialog.getSelectedFile());
            }

            if (data != null) {
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
        List<DownloadBean> data = getDownloadList();

        data.set(data.indexOf(bean), bean);

        refreshTable(data);
    }

    protected void removeCompleted() {
        List<DownloadBean> data = getDownloadList();
        List<DownloadBean> copy =  new ArrayList<>(getDownloadList());

        for (DownloadBean bean : data) {
            if (bean.getStatus().equals(DownloadStatus.COMPLETED)) {
                copy.remove(bean);
            }
        }

        refreshTable(DownloadSorter.sortByStatus(copy));
    }

    public void completeDownloadProcess() {
        List<DownloadBean> data = getDownloadList();
        refreshTable(DownloadSorter.sortByStatus(data));
    }
}