package service;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;

public class ConfigService {
	
	public static String getVideoDir() {
		return (String) AppContext.getCfg().get(AppConfig.VIDEO_DIR);
	}
	
	public static int getCountFiles() {
		return Integer.valueOf(AppContext.getCfg().get(AppConfig.COUNT_FILES).toString());
	}
}
