package ru.ralnik.utils;

import java.io.File;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;
import ru.ralnik.exception.AppException;
import ru.ralnik.logging.Log;
import service.ConfigService;

public class FileUtils {
/**
 * �������� ���� ����� ������, ����������� ������ ����� �������� �����
 * ������ ����� ����� ���������� ������ � ��� ������ ���� � ����� ������������
 * �������� delete_video_files=1
 * @param dir
 */
public static void deleteAllVideoFiles(File dir) throws AppException {
	if ("1".equals(AppContext.getCfg().get(AppConfig.DELETE_VIDEO_FILES))) {
		Log.info("������� �������� " + dir.toString());
		if (dir != null) {
			if (dir.listFiles() == null) {
				Log.info("������� " + dir.toString() + " ����, ������� ������!");
				return;
			} else if (dir.listFiles().length < ConfigService.getCountFiles()) {
				Log.debug("� �������� " + dir.toString() + " �� ���������� ������ "
						+ "(�������: " + ConfigService.getCountFiles() + " ������, ���-�� ������ " + dir.listFiles().length + ")");
				return;
			}
			for (File file : dir.listFiles()) {
				if (!file.isDirectory()) {
					file.delete();
					Log.info("������ ����: " + file.getName());
				}
			}
		}
	}
}

/**
 * ���������� ���������� ������ � ��������
 * @param dir
 * @return
 */
public static int getCountFiles(File dir) {
	if (dir.list() != null && dir.list().length != 0)
		return dir.list().length;
	else return 1;
}
}
