package ru.ralnik.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;
import ru.ralnik.exception.AppException;
import ru.ralnik.logging.Log;

/**
 * Класс для скачки файлов из сети по url-у
 * 
 * @author ralnik
 * created 04.09.2020
 */
public class Downloader {
	
	public static void downloadFile(String urlStr, String filename) throws IOException, AppException {
        URL url = new URL(urlStr);
        File file = new File(filename);
        if (!file.exists()) {
        	new File(file.getParent()).mkdirs();
        }        
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        Log.debug("файл " + file.toString() + " успешно закачан");
        fos.close();
        rbc.close();
    }	
}
