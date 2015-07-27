package org.download.process;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.Vector;

import org.download.bean.DownloadBean;
import org.download.controller.DownloadManager;
import org.download.enums.DownloadStatus;

public class DownloadProcess implements Runnable, PropertyChangeListener {
	private static final int MAX_DOWNLOADS = 3;
	private DownloadManager manager;
	private Vector<DownloadBean> downloads;
	private int tasks;
	
	/**
	 * @param manager
	 * @param downloads
	 */
	public DownloadProcess(DownloadManager manager, Vector<DownloadBean> downloads) {
		super();
		this.manager = manager;
		this.downloads = downloads;
		tasks = 1;
	}

	public void run() {
		Iterator<DownloadBean> iter = downloads.iterator();
		
		while (iter.hasNext()) { // || tasks > 0) {
			DownloadBean download = iter.next();
			
			createTask(download);
			
			while((tasks % MAX_DOWNLOADS) <= 0) {}
		}
		
		manager.completeDownloadProcess();
	}

	private void createTask(DownloadBean download) {
		DownloadTask task = new DownloadTask(download);
		task.addPropertyChangeListener(this);
		task.execute();
		tasks++;
	}

	public void propertyChange(PropertyChangeEvent event) {
		String name = event.getPropertyName();
		
		if(name.equals("status")) {
			DownloadBean download = (DownloadBean)event.getNewValue();
			
			if(download.getStatus().equals(DownloadStatus.COMPLETED) || 
					download.getStatus().equals(DownloadStatus.ERROR)) {
				tasks--;
			}
			
			manager.updateTable(download);
		}
	}
}
