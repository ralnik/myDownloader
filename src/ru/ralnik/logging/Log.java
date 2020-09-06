package ru.ralnik.logging;

import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;

public class Log {
	private static final String FILENAME = "log.txt";
	private static final String DEBUG = "debug";
	private static final String ERROR = "error";
	private static final String WARNING = "warning";
	private static final String INFO = "info";
	
	public static void debug(String message) {
		createLogFileIfNotExist();
		if (isDebug())
			addLog(message, DEBUG);
	}
	
	public static void error(String message) {
		createLogFileIfNotExist();
		addLog(message, ERROR);
		JOptionPane.showMessageDialog(null, message, ERROR, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void warning(String message) {
		createLogFileIfNotExist();
		addLog(message, WARNING);
	}
	
	public static void info(String message) {
		createLogFileIfNotExist();
		addLog(message, INFO);
	}
	
	private static void createLogFileIfNotExist() {
		File file = new File(FILENAME);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void addLog(String msg, String event) {
		try {
			Date today = new Date();
			SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			msg = "["+f.format(today)+"][" + event + "] " + msg + "\n";
            Files.write(Paths.get(FILENAME), msg.getBytes(), StandardOpenOption.APPEND);
            if (DEBUG.equals(event)) {
        		System.out.println(msg);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
	}
	
	public static boolean isDebug() {
		return "1".equals(AppContext.getCfg().get(AppConfig.DEBUG));
	}
}
