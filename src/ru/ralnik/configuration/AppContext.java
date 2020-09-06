package ru.ralnik.configuration;

import java.io.IOException;

import ru.ralnik.logging.Log;

public class AppContext {
	public static AppContext instance;
	private static final String appDir = System.getProperty("user.dir").replaceAll("\\\\", "/") + "/";
	private static AppConfig cfg;
	
	public static synchronized AppContext getInstaince() {
		if (instance == null) {
			instance = new AppContext();
			init();
		}
		return instance;
	}
	
	private static void init() {
		if (cfg == null) {
			try {
				cfg = new AppConfig();
			} catch (IOException e) {
				e.printStackTrace();
				Log.error("Невозможно считать файл конфигурации: " + AppConfig.FILE_PROPERTIES);
				Log.error(e.getMessage());
			}
		}
	}
	
	public static String getAppDir() {
		return appDir;
	}
	
	public static AppConfig getCfg() {
		return cfg;
	}
}
