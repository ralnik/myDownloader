package ru.ralnik.process;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;
import ru.ralnik.exception.AppException;
import ru.ralnik.logging.Log;
import ru.ralnik.utils.FileUtils;

public class HtmlParser {
	private String url;
	private Document doc;
	private String numCamera;

	public HtmlParser(String url, String numCamera) {
		this.url = url;
		this.numCamera = numCamera;
	}

	public void parse() throws AppException {
		try {
			doc = Jsoup.connect(url).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
			Log.debug(doc.html());
			// Получаем содрежимое html страницы
			String link = findLink(doc.html());
			if (link != null) {
				// Скачиваем playlist.m3u8 по спарсеной ссылке
				Downloader.downloadFile(link, AppContext.getCfg().get(AppConfig.PLAYLIST_DIR) + "playlist.m3u8");
				// Парсется chunckList, и получаем имя файла chunckList
				String chunckList = parseChunklistFilename(
						AppContext.getCfg().get(AppConfig.PLAYLIST_DIR) + "playlist.m3u8");
				Log.debug("chunckList: " + chunckList);

				URL resource = new URL(link);
				// формируем ссылку на chunckList
				String chunckListLink = resource.getProtocol() + "://" + resource.getHost()
						+ resource.getPath().replace("playlist.m3u8", "")
						+ parseChunklistFilename(AppContext.getCfg().get(AppConfig.PLAYLIST_DIR) + "playlist.m3u8");
				Log.debug("chunklist: " + chunckListLink);

				// Скачиваем chunklist_*.m3u8
				Downloader.downloadFile(chunckListLink,
						AppContext.getCfg().get(AppConfig.PLAYLIST_DIR) + "chuncklist.m3u8");
				List<String> videos = parseVideoFilename(
						AppContext.getCfg().get(AppConfig.PLAYLIST_DIR) + "chuncklist.m3u8");
				
				FileUtils.deleteAllVideoFiles(new File((String)AppContext.getCfg().get(AppConfig.VIDEO_DIR)));
				int num = 1;
				for (String video : videos) {
					Downloader.downloadFile(
							resource.getProtocol() + "://" + resource.getHost()
									+ resource.getPath().replace("playlist.m3u8", "") + video,
									//AppContext.getCfg().get(AppConfig.VIDEO_DIR) + changeExt(video));
									(String)AppContext.getCfg().get(AppConfig.VIDEO_DIR) + numCamera + "/" + num + "." + AppContext.getCfg().get(AppConfig.VIDEO_EXT));
				num++;
				}
			}
		} catch (AppException e) {
			Log.error(e.getMessage());
		} catch (IOException e) {
			Log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private String findLink(String text) {
		Pattern pattern = Pattern.compile("https.*?.m3u8");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			String result = matcher.group();
			result = result.replace("%3A", ":").replace("%2F", "/");
			Log.debug("Найденая ссылка при парсинге: " + result);
			return result;
		}
		return null;
	}

	private String parseChunklistFilename(String fileName) throws IOException {
		String text = new String(Files.readAllBytes(Paths.get(fileName)));
		Pattern pattern = Pattern.compile("chunklist.*?m3u8");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	private List<String> parseVideoFilename(String chuncklistFilename) throws IOException {
		String text = new String(Files.readAllBytes(Paths.get(chuncklistFilename)));
		List<String> result = new ArrayList<>();
		Pattern pattern = Pattern.compile("media_.*?.ts");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			result.add(matcher.group());
		}
		return result;
	}

	private String changeExt(String filename) {
		String ext = null;
		if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0)
			ext = filename.substring(filename.lastIndexOf(".") + 1);
		Log.debug("filename" + filename);
		Log.debug("ext: " + ext);
		Log.debug("video_ext: " + AppContext.getCfg().get(AppConfig.VIDEO_EXT));
		Log.debug("Проверка замены расширения у видео файла: "
				+ filename.replace("." + ext, "." + AppContext.getCfg().get(AppConfig.VIDEO_EXT)));
		return filename.replace("." + ext, "." + AppContext.getCfg().get(AppConfig.VIDEO_EXT));
	}
}
