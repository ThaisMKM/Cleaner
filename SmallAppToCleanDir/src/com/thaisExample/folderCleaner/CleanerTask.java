/**
 * 
 * TimerTask class that specify when old printable versions should be deleted.
 * It should delete prior to date passed as parameter, or two hours old or earlier
 * 
 * @autor Thais Mayumi
 * 
 */
package com.thaisExample.folderCleaner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CleanerTask extends TimerTask {

	File propertiesFile;
	Properties prop;
	static int hours;
	String directoryTemp;
	String directoryReports;
	
	private static Logger logger = Logger.getLogger(CleanerTask.class
			.getPackage().getName());

	/*
	 * Load properties file and read directories that should have information deleted from it
	 */
	public CleanerTask() {

		try {

			propertiesFile = new java.io.File("SystemInfo.properties");
			java.io.InputStream stream = new java.io.FileInputStream(
					propertiesFile);
			prop = new Properties();
			prop.load(stream);
			directoryTemp = prop.getProperty("dir1");
			directoryReports = prop.getProperty("dir2");
			

		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Inexistent properties file. Finishing app");
			System.exit(-1);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Could not load properties file. Finishing app.");
			System.exit(-1);
		}
	}

	@Override
	public void run() {
		deleteFiles(directoryTemp, "MA0");
		deleteOldFiles(directoryReports, "BA0");
	}

	/*
	 * Delete all files that inside dir and which name starts with fileNamePrefix
	 */
	private void deleteFiles(String dir, String fileNamePrefix) {

		NameFilter filter = new NameFilter(fileNamePrefix);
		File diretorio = new File(dir);
		String[] list = diretorio.list(filter);

		for (int i = 0; i < list.length; i++) {
			File completeFileName = new File(dir + list[i]);

			if (completeFileName.isDirectory()) {
				String[] conteudo = completeFileName.list();
				for (int J = 0; J < conteudo.length; J++) {
					File file = new File(completeFileName + "\\" + conteudo[J]);
					file.delete();
				}
			}
			boolean isDeleted = completeFileName.delete();
			logger.log(Level.INFO, completeFileName  + "  - deleted: " +  isDeleted);
		}

	}

	/*
	 * Delete all files that inside dir and which name starts with fileNamePrefix,
	 * and that were created at least two hours ago.
	 */

	public void deleteOldFiles(String diret, String fileNamePrefix) {

		NameFilter filter = new NameFilter(fileNamePrefix);
		File diretorio = new File(diret);
		String[] list = diretorio.list(filter); 

		for (int i = 0; i < list.length; i++) {
			File completeFileName = new File(diret + list[i]);
			if (completeFileName.lastModified() < (System.currentTimeMillis() - (2 * 60 * 60 * 1000))) {
				if (completeFileName.isDirectory()) {
					String[] conteudo = completeFileName.list();
					for (int J = 0; J < conteudo.length; J++) {
						File file = new File(completeFileName + "\\" + conteudo[J]);
						file.delete();
					}
				}
				boolean isDeleted = completeFileName.delete();
				logger.log(Level.INFO, completeFileName  + "  - deleted: " +  isDeleted);
			}
		}

	}

}
