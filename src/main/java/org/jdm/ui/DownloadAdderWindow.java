package org.jdm.ui;

import org.jdm.bean.DownloadBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DownloadAdderWindow extends JDialog {
    private static final long serialVersionUID = 1L;
    private GridBagConstraints constraints;
    private JTextField txtTitle = new JTextField(30);
    private JTextField txtURL = new JTextField(60);
    private JTextField txtSaveAs = new JTextField(30);
    private JTextField txtSaveTo = new JTextField(30);
    private JButton btnBrowseTo = new JButton("...");
    private JButton btnAdd = new JButton("Add");
    private JButton btnCancel = new JButton("Cancel");

    public DownloadAdderWindow(JFrame parent) {
        super(parent);
        Container container = getContentPane();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Add a Download");
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        container.setLayout(new BorderLayout());

        setModal(true);

        JPanel pnlFields = new JPanel(new GridBagLayout());
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel pnlSaveTo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pnlSaveTo.add(txtSaveTo);
        pnlSaveTo.add(btnBrowseTo);

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnCancel);

        pnlFields.add(new JLabel("Title"), getConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH));
        pnlFields.add(txtTitle, getConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));

        pnlFields.add(new JLabel("URL"), getConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH));
        pnlFields.add(txtURL, getConstraints(2, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));

        pnlFields.add(new JLabel("Save As"), getConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH));
        pnlFields.add(txtSaveAs, getConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));

        pnlFields.add(new JLabel("Save To"), getConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH));
        pnlFields.add(pnlSaveTo, getConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE));

        container.add(pnlFields, BorderLayout.CENTER);
        container.add(pnlButtons, BorderLayout.SOUTH);
    }

    protected void setSaveToContents(String contents) {
        txtSaveTo.setText(contents);
    }

    protected void addActionListener(ActionListener listener) {
        btnAdd.setActionCommand("ADD");
        btnAdd.addActionListener(listener);
        btnBrowseTo.setActionCommand("...");
        btnBrowseTo.addActionListener(listener);
        btnCancel.setActionCommand("CANCEL");
        btnCancel.addActionListener(listener);
    }

    public DownloadBean getBean() {
        DownloadBean downloadBean = new DownloadBean(txtTitle.getText(),
                txtURL.getText(),
                txtSaveAs.getText(),
                txtSaveTo.getText());

        return downloadBean;
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
