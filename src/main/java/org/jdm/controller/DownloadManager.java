package org.jdm.controller;

import org.jdm.bean.DownloadBean;
import org.jdm.data.DownloadSorter;
import org.jdm.enums.DownloadStatus;
import org.jdm.process.DownloadProcess;
import org.jdm.saver.DownloadSaver;
import org.jdm.ui.Centralizer;
import org.jdm.ui.DownloadManagerWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DownloadManager extends DownloadManagerWindow {

    private static final long serialVersionUID = 1L;

    public DownloadManager() {
        setSize(800, 600);
        setLocation(Centralizer.getCenterPosition(getWidth(), getHeight()));
        addActionListener(event -> {
            switch (event.getActionCommand()) {
                case "START":
                    removeCompleted();
                    List<DownloadBean> downloads = getDownloadList();

                    DownloadProcess dp = new DownloadProcess(this, downloads);
                    Thread t = new Thread(dp);
                    t.start();

                    completeDownloadProcess();
                    break;
                case "NEW":
                    DownloadAdder adder = new DownloadAdder(this);
                    int addResult = adder.showDialog();

                    if (addResult == DownloadAdder.ACTION_ACCEPTED) {
                        List<DownloadBean> data = getDownloadList();
                        data.add(adder.getBean());
                        refreshTable(DownloadSorter.sortByStatus(data));
                    }
                    break;
                case "DEL":
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
                    break;
                case "COMP":
                    removeCompleted();
                    break;
                case "SAVE":
                    JFileChooser saveDialog = new JFileChooser();

                    int saveResult = saveDialog.showSaveDialog(this);

                    if (saveResult == JFileChooser.APPROVE_OPTION) {
                        DownloadSaver.save(saveDialog.getSelectedFile(), getDownloadList());
                    }
                    break;
                case "LOAD":
                    List<DownloadBean> loadData = new ArrayList<>();

                    JFileChooser openDialog = new JFileChooser();

                    int result = openDialog.showOpenDialog(this);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        loadData = DownloadSaver.load(openDialog.getSelectedFile());
                    }

                    if (loadData != null) {
                        refreshTable(DownloadSorter.sortByStatus(loadData));
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid file or corrupted.", "Error on loading file", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "EXIT":
                    System.exit(0);
                    break;
                default:

            }
        });

        refreshTable(new ArrayList<>());
    }

    public static void main(String[] args) {
        DownloadManager manager = new DownloadManager();
        manager.setVisible(true);
    }

    public void updateTable(DownloadBean bean) {
        List<DownloadBean> data = getDownloadList();
        data.set(data.indexOf(bean), bean);
        refreshTable(data);
    }

    protected void removeCompleted() {
        List<DownloadBean> data = getDownloadList();
        List<DownloadBean> completedDownloads =
                data
                        .stream()
                        .filter(d -> d.getStatus().equals(DownloadStatus.COMPLETED))
                        .collect(Collectors.toList());

        data.removeAll(completedDownloads);

        refreshTable(DownloadSorter.sortByStatus(data));
    }

    public void completeDownloadProcess() {
        refreshTable(DownloadSorter.sortByStatus(getDownloadList()));
    }
}