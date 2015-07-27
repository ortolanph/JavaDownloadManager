package org.download.saver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import org.download.bean.DownloadBean;
import org.download.enums.DownloadStatus;

public class DownloadSaver {
	public static boolean save(File file, Vector<DownloadBean> data) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			for (DownloadBean bean : data) {
				writer.write(bean.toString());
				writer.newLine();
			}

			writer.close();
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static Vector<DownloadBean> load(File file) {
		Vector<DownloadBean> data = new Vector<DownloadBean>();
		int l = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = reader.readLine()) != null) {
				DownloadBean bean = parse(line);
				l++;
				if(bean != null) {
					data.add(bean);
				} else {
					System.out.println("Error on line " + l);
					return null;
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		return data;
	}

	private static DownloadBean parse(String line) {
		try {
			StringTokenizer st = new StringTokenizer(line, ";");
			ArrayList<String> values = new ArrayList<String>();

			while (st.hasMoreTokens()) {
				values.add(st.nextToken());
			}

			DownloadBean bean = new DownloadBean(values.get(0),
					values.get(1),
					values.get(2),
					values.get(3),
					values.get(4),
					DownloadStatus.valueOf(values.get(5)));

			return bean;
		} catch (Exception e) {
			return null;
		}
	}
}
