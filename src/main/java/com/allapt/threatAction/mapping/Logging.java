package com.allapt.threatAction.mapping;


import java.io.IOException;
import java.util.logging.*;

public class Logging {

	public static final Logger LOGGER = Logger.getLogger(Logging.class.getName());
	static {

		Handler consoleHandler = null;
		Handler fileHandler  = null;
		try{
			//Creating consoleHandler and fileHandler
			consoleHandler = new ConsoleHandler();
			fileHandler  = new FileHandler("./javacodegeeks.log");
			
			//Assigning handlers to LOGGER object
			LOGGER.addHandler(consoleHandler);
			LOGGER.addHandler(fileHandler);
			
			//Setting levels to handlers and LOGGER
			consoleHandler.setLevel(Level.CONFIG);
			fileHandler.setLevel(Level.ALL);
			LOGGER.setLevel(Level.ALL);
			
			LOGGER.config("Logging Configuration done.");
			
			//Console handler removed
			//LOGGER.removeHandler(consoleHandler);
			
			LOGGER.log(Level.FINE, "Finer logged");
		}catch(IOException exception){
			LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
		}
		
		LOGGER.finer("Finest example on LOGGER handler completed.");
		
	}

}
