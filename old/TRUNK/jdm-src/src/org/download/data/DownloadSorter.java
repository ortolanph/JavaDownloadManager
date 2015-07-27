package org.download.data;

import java.util.Vector;

import org.download.bean.DownloadBean;

public class DownloadSorter {
	private static final int MAX_GROUP_SIZE = 50;
	private static final int NUMBER_OF_GROUPS = 5;
	
	public static Vector<DownloadBean> sortByStatus(Vector<DownloadBean> data) {
		Vector<DownloadBean> results = new Vector<DownloadBean>(); 
		Vector<DownloadSorterThread> threads = new Vector<DownloadSorterThread>();
		
		if(data.size() >= MAX_GROUP_SIZE) {
			
			int div = data.size() / NUMBER_OF_GROUPS;
			int[] pos = new int[NUMBER_OF_GROUPS + 1];
			
			for(int p = 0; p < NUMBER_OF_GROUPS; p++) {
				pos[p] = div * p;
			}
			
			pos[NUMBER_OF_GROUPS] = data.size();
			
			Vector<DownloadBean> tempData = new Vector<DownloadBean>();
			
			for(int i = 1; i < NUMBER_OF_GROUPS + 1; i++) {
				for (int j = pos[i - 1]; j < pos[i]; j++) {
					tempData.add(data.get(j));
				}
				
				threads.add(new DownloadSorterThread(new Vector<DownloadBean>(tempData) , i));
				tempData.clear();
			}
			
			for (DownloadSorterThread thread : threads) {
				Thread t = new Thread(thread);
				t.start();
			}
			
			int totalFinishedThreads = 0;
			
			while(totalFinishedThreads < NUMBER_OF_GROUPS) {
				totalFinishedThreads = 0;
				for (DownloadSorterThread thread : threads) {
					if(thread.isFinished()) {
						totalFinishedThreads ++;
					}
				}
			}
			
			for (DownloadSorterThread thread : threads) {
				for (DownloadBean bean : thread.getData()) {
					results.add(bean);
				}
			}
		} else {
			results = new Vector<DownloadBean>(data);
		}
		
		DownloadSorterThread dst = new DownloadSorterThread(results, 0);
		Thread thread = new Thread(dst);
		thread.start();
		
		while(!dst.isFinished()) {	}
		
		return dst.getData();
	}
}
