package org.jdm.data;


import org.jdm.bean.DownloadBean;

import java.util.ArrayList;
import java.util.List;

public class DownloadSorterThread implements Runnable {
    private List<DownloadBean> data;
    private boolean finished;
    private int id;

    public DownloadSorterThread(List<DownloadBean> data, int id) {
        this.data = data;
        this.id = id;
    }

    public void run() {
        List<DownloadBean> results = new ArrayList<>(data);

        for (int j = 0; j < results.size(); j++) {
            for (int i = 0; i < results.size() - 1; i++) {
                if (results.get(i).getStatus().compareTo(results.get(i + 1).getStatus()) > 0) {
                    DownloadBean downloadTemp = results.get(i);
                    results.set(i, results.get(i + 1));
                    results.set(i + 1, downloadTemp);
                }
            }
        }

        this.data = results;
        finished = true;
    }

    public List<DownloadBean> getData() {
        return this.data;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public int getId() {
        return this.id;
    }
}
