package ru.ralnik.utils;

import java.io.File;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;
import ru.ralnik.exception.AppException;
import ru.ralnik.logging.Log;

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
		for (File file : dir.listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
				Log.info("������ ����: " + file.getName());
			}
		}
	}
}
}
