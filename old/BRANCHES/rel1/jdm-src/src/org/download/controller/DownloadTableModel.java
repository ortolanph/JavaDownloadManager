package org.download.controller;

import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.download.bean.DownloadBean;
import org.download.enums.DownloadStatus;

public class DownloadTableModel implements TableModel {
	private Vector<DownloadBean> data;
	private String[] header = new String[] {
			"Title", "URL", "Save As", "Save To", "Status"
	};
	
	public DownloadTableModel(Vector<DownloadBean> data) {
		this.data = data;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return header.length;
	}

	public String getColumnName(int columnIndex) {
		return header[columnIndex];
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		DownloadBean downloadBean = data.get(rowIndex);
		String value = null;
		
		switch (columnIndex) {
		case 0:
			value = downloadBean.getTitle();
			break;
		case 1:
			value = downloadBean.getUrl();
			break;
		case 2:
			value = downloadBean.getSaveAs();
			break;
		case 3:
			value = downloadBean.getSaveTo();
			break;
		case 4:
			value = downloadBean.getStatus().toString();
			break;
		}
		
		return value;
	}

	public Vector<DownloadBean> getData() {
		return data;
	}
	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		DownloadBean downloadBean = data.get(rowIndex);
		
		switch (columnIndex) {
		case 0:
			downloadBean.setTitle((String)value);
			break;
		case 1:
			downloadBean.setUrl((String)value);
			break;
		case 2:
			downloadBean.setSaveAs((String)value);
			break;
		case 3:
			downloadBean.setSaveTo((String)value);
			break;
		case 4:
			downloadBean.setStatus(DownloadStatus.valueOf((String)value));
			break;
		}
		
		data.set(rowIndex, downloadBean);
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
	public void removeTableModelListener(TableModelListener l) { }
	public void addTableModelListener(TableModelListener l) { }
}
