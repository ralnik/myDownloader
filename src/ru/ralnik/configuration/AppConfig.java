package ru.ralnik.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppConfig {
	public static final String FILE_PROPERTIES = "application.properties";
	
	//наименование полей
	public static final String VIDEO_EXT = "video_ext";
	public static final String VIDEO_DIR = "save_video_dir";
	public static final String TIMER = "timer";
	public static final String PLAYLIST_DIR = "playlist_dir";
	public static final String DEBUG = "debug";
	public static final String DELETE_VIDEO_FILES = "delete_video_files";
	public static final String COUNT_FILES = "count_files";
	
	private Properties props;
	
	public AppConfig() throws IOException {
		//Создаем новый файл если он не существует и наполним его дефолтными значениями
		createNewFileProp();		
		props = new Properties();
		props.load(new FileInputStream(new File(FILE_PROPERTIES)));
	}
	
	private void createNewFileProp() throws IOException {
		File file = new File(FILE_PROPERTIES);
		if (!file.exists()) {
			file.createNewFile();
			//наполяем дефолтными значениями
			saveDefaultValues();
		}
	}
	
	private void saveDefaultValues() throws IOException {
		saveComment("Расширение с которым сохранять видео");
		saveValue(VIDEO_EXT, "TS");
		
		saveComment("Путь куда сохранять видео");
		saveComment("Разделение каталогов указывать либо через '/', либо через '\\\\'");
		saveComment("Путь должен обязательно заканчиваться слешем");
		saveValue(VIDEO_DIR, AppContext.getAppDir() + "VIDEO/");
		
		saveComment("Таймер, интервал через какое время повторять проверку (в секундах)");
		saveValue(TIMER, "5");
		
		saveComment("Путь для сохранения playlist");
		saveComment("Разделение каталогов указывать либо через '/', либо через '\\\\'");
		saveComment("Путь должен обязательно заканчиваться слешем");
		saveValue(PLAYLIST_DIR, AppContext.getAppDir() + "PLAYLIST/");
		
		saveComment("Отображать debug информацию в лог файле");
		saveComment("0 - выкл, 1 - вкл");
		saveValue(DEBUG, "0");
		
		saveComment("Удаление всех скаченных видео файлов, перед закачкой новых");
		saveComment("0 - не удалять, 1 - удалять");
		saveValue(DELETE_VIDEO_FILES, "1");
		
		saveComment("Глубина видео файлов в каталоге");
		saveValue(COUNT_FILES, "9");
	}
	
	private void saveValue(String key, String value) throws IOException {
		String msg = key + " = " + value + "\n";
		Files.write(Paths.get(FILE_PROPERTIES), msg.getBytes(), StandardOpenOption.APPEND);
	}
	private void saveComment(String text) throws IOException {
		String msg = "#" + text + "\n";
		Files.write(Paths.get(FILE_PROPERTIES), msg.getBytes(), StandardOpenOption.APPEND);
	}
	
	public Object get(String key) {
		return props.get((String) key);
	}
}
 