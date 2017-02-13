package org.jdm.controller;

import org.jdm.ui.Centralizer;
import org.jdm.ui.DownloadAdderWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DownloadAdder extends DownloadAdderWindow implements ActionListener {
    public static final int ACTION_CANCELED = 0;
    public static final int ACTION_ACCEPTED = 1;
    private static final long serialVersionUID = 1L;
    private int action;

    public DownloadAdder(JFrame parent) {
        super(parent);
        setSize(800, 220);
        setLocation(Centralizer.getCenterPosition(getWidth(), getHeight()));
        addActionListener(event -> {
            switch(event.getActionCommand()) {
                case "ADD":
                    this.action = ACTION_ACCEPTED;
                    dispose();
                    break;
                case "...":
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setMultiSelectionEnabled(false);
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int faction = fileChooser.showDialog(this, "Select");

                    if (faction == JFileChooser.APPROVE_OPTION) {
                        setSaveToContents(fileChooser.getSelectedFile().getPath());
                    }
                    break;
                case "CANCEL":
                    this.action = ACTION_CANCELED;
                    dispose();
                    break;
            }
        });
    }

    public int showDialog() {
        setVisible(true);
        return action;
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();


    }
}