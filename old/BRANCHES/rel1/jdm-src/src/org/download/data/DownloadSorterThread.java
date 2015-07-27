package org.download.data;

import java.util.Vector;

import org.download.bean.DownloadBean;

public class DownloadSorterThread implements Runnable {
	private Vector<DownloadBean> data;
	private boolean finished;
	private int id;
	
	public DownloadSorterThread(Vector<DownloadBean> data, int id) {
		this.data = data;
		this.id = id;
	}
	
	public void run() {
		Vector<DownloadBean> results = new Vector<DownloadBean>(data);
		
		for(int j = 0; j < results.size(); j++) {
			for(int i = 0; i < results.size() - 1; i++) {
				if(results.get(i).getStatus().compareTo(results.get(i + 1).getStatus()) > 0) {
					DownloadBean downloadTemp = results.get(i);
					results.set(i, results.get(i + 1));
					results.set(i + 1, downloadTemp);
				}
			}
		}
		
		this.data = results;
		finished = true;
	}
	
	public Vector<DownloadBean> getData() {
		return this.data;
	}
	
	public boolean isFinished() {
		return this.finished;
	}
	
	public int getId() {
		return this.id;
	}
}
