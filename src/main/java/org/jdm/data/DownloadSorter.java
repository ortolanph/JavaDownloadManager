package org.jdm.data;


import org.jdm.bean.DownloadBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DownloadSorter {
    private static final int MAX_GROUP_SIZE = 50;
    private static final int NUMBER_OF_GROUPS = 5;

    public static List<DownloadBean> sortByStatus(List<DownloadBean> data) {
        List<DownloadBean> results = new ArrayList<>();
        List<DownloadSorterThread> threads = new ArrayList<>();

        if (data.size() >= MAX_GROUP_SIZE) {

            int div = data.size() / NUMBER_OF_GROUPS;
            int[] pos = new int[NUMBER_OF_GROUPS + 1];

            for (int p = 0; p < NUMBER_OF_GROUPS; p++) {
                pos[p] = div * p;
            }

            pos[NUMBER_OF_GROUPS] = data.size();

            Vector<DownloadBean> tempData = new Vector<>();

            for (int i = 1; i < NUMBER_OF_GROUPS + 1; i++) {
                for (int j = pos[i - 1]; j < pos[i]; j++) {
                    tempData.add(data.get(j));
                }

                threads.add(new DownloadSorterThread(new ArrayList<>(tempData), i));
                tempData.clear();
            }

            for (DownloadSorterThread thread : threads) {
                Thread t = new Thread(thread);
                t.start();
            }

            int totalFinishedThreads = 0;

            while (totalFinishedThreads < NUMBER_OF_GROUPS) {
                totalFinishedThreads = 0;
                for (DownloadSorterThread thread : threads) {
                    if (thread.isFinished()) {
                        totalFinishedThreads++;
                    }
                }
            }

            for (DownloadSorterThread thread : threads) {
                for (DownloadBean bean : thread.getData()) {
                    results.add(bean);
                }
            }
        } else {
            results = new Vector<>(data);
        }

        DownloadSorterThread dst = new DownloadSorterThread(results, 0);
        Thread thread = new Thread(dst);
        thread.start();

        while (!dst.isFinished()) {
        }

        return dst.getData();
    }
}
