package ru.ralnik.utils;

import java.io.File;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;
import ru.ralnik.exception.AppException;
import ru.ralnik.logging.Log;
import service.ConfigService;

public class FileUtils {
/**
 * Удаление всех видео файлов, планируется делать перед закачкой новых
 * данный метод будет выполяться только в том случаи если в файле конфигурации
 * параметр delete_video_files=1
 * @param dir
 */
public static void deleteAllVideoFiles(File dir) throws AppException {
	if ("1".equals(AppContext.getCfg().get(AppConfig.DELETE_VIDEO_FILES))) {
		Log.info("Очистка каталога " + dir.toString());
		if (dir != null) {
			if (dir.listFiles() == null) {
				Log.info("Каталог " + dir.toString() + " пуст, удалять нечего!");
				return;
			} else if (dir.listFiles().length < ConfigService.getCountFiles()) {
				Log.debug("В каталоге " + dir.toString() + " не достаточно файлов "
						+ "(глубина: " + ConfigService.getCountFiles() + " файлов, кол-во файлов " + dir.listFiles().length + ")");
				return;
			}
			for (File file : dir.listFiles()) {
				if (!file.isDirectory()) {
					file.delete();
					Log.info("Удален файл: " + file.getName());
				}
			}
		}
	}
}

/**
 * Возвращает количество файлов в каталоге
 * @param dir
 * @return
 */
public static int getCountFiles(File dir) {
	if (dir.list() != null && dir.list().length != 0)
		return dir.list().length;
	else return 1;
}
}
