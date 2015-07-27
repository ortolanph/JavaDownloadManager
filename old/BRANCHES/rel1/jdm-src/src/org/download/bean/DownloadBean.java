package org.download.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.download.enums.DownloadStatus;

public class DownloadBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;
	private String url;
	private String saveAs;
	private String saveTo;
	private DownloadStatus status = DownloadStatus.IDLE;
	
	public DownloadBean() {	}

	/**
	 * @param title
	 * @param url
	 * @param saveAs
	 * @param saveTo
	 * @param complete
	 */
	public DownloadBean(String title, String url, String saveAs, String saveTo) {
		super();
		id = (new SimpleDateFormat("ddMMyyyyHHmmss")).format(new Date());
		this.title = title;
		this.url = url;
		this.saveAs = saveAs;
		this.saveTo = saveTo;
	}
	
	/**
	 * @param id
	 * @param title
	 * @param url
	 * @param saveAs
	 * @param saveTo
	 * @param complete
	 */
	public DownloadBean(String id, String title, String url, String saveAs, String saveTo, DownloadStatus status) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.saveAs = saveAs;
		this.saveTo = saveTo;
		this.status = status;
	}

	public DownloadStatus getStatus() {
		return status;
	}

	public void setStatus(DownloadStatus status) {
		this.status = status;
	}

	public String getSaveAs() {
		return saveAs;
	}

	public void setSaveAs(String saveAs) {
		this.saveAs = saveAs;
	}

	public String getSaveTo() {
		return saveTo;
	}

	public void setSaveTo(String saveTo) {
		this.saveTo = saveTo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return id + ";" + title + ";" + url + ";" + saveAs + ";" + saveTo + ";" + status;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DownloadBean) {
			DownloadBean downloadBean = (DownloadBean)obj;
			
			if(id.equals(downloadBean.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}