package org.jdm.process;

import org.jdm.bean.DownloadBean;
import org.jdm.enums.DownloadStatus;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

public class DownloadTask extends SwingWorker<DownloadBean, Void> {
    private DownloadBean download;

    public DownloadTask(DownloadBean bean) {
        this.download = bean;
    }

    public static void buildDirectoryChain(String path) {
        StringTokenizer st = new StringTokenizer(path, File.separator);
        String root = "";
        String curDir = "";

        if (File.separator.equals("/")) {
            root = "/";
        } else {
            root = "";
        }

        curDir = root;

        while (st.hasMoreTokens()) {
            curDir += st.nextToken() + File.separator;

            File dir = new File(curDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
    }

    @Override
    protected DownloadBean doInBackground() throws Exception {
        download.setStatus(DownloadStatus.DOWNLOADING);
        firePropertyChange("status", null, download);

        buildDirectoryChain(download.getSaveTo());

        File saveToFile = new File(download.getSaveTo() + File.separator + download.getSaveAs());

        if (saveToFile.exists()) {
            download.setStatus(DownloadStatus.COMPLETED);
        } else {

            try {
                URL url = new URL(download.getUrl());
                HttpURLConnection downFile = (HttpURLConnection) url.openConnection();

                byte[] buffer = new byte[downFile.getInputStream().available()];

                FileOutputStream fos = new FileOutputStream(saveToFile);

                while (downFile.getInputStream().read(buffer) != -1) {
                    fos.write(buffer);
                    buffer = new byte[downFile.getInputStream().available()];
                }

                fos.close();
                downFile.getInputStream().close();

                download.setStatus(DownloadStatus.COMPLETED);
                firePropertyChange("status", null, download);
            } catch (Exception e) {
                download.setStatus(DownloadStatus.ERROR);
                firePropertyChange("status", null, download);
            }
        }

        return download;
    }
}
