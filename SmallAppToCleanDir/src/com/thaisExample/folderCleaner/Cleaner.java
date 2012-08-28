package com.thaisExample.folderCleaner;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Given an integer as parameter, schedule a CleanerTask() to start after that delay (in hours) is achieved
 * 
 * @author Thais Mayumi
 *
 */
public class Cleaner {
	
	private static Logger logger = Logger
			.getLogger(Cleaner.class.getPackage().getName());



	public static void main(String args[]) {

		loadLogger();
		Integer intervalFromNow = new Integer(args[0]);
		Integer intervalConvertedToHours = intervalFromNow * 60 * 60000;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new CleanerTask(), new Date(),
				intervalConvertedToHours);

	}
	
	public static void loadLogger(){
		try {
			// get resource from inside the jar package
			LogManager.getLogManager().readConfiguration( Cleaner.class.getResourceAsStream("/javalogging.properties"));
			
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE,
					"WARNING: Could not open log configuration file");			
		}


	}

}
