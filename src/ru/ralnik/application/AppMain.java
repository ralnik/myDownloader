package ru.ralnik.application;

import ru.ralnik.configuration.AppContext;
import ru.ralnik.console.Console;
import ru.ralnik.exception.AppException;
import ru.ralnik.frames.MainFrame;
import ru.ralnik.logging.Log;

public class AppMain {
	public static void main(String[] args) throws AppException {
		Log.info("Запуск приложения");
		//инициализация контекста
		AppContext.getInstaince();
		
		if (args.length == 0) {
			MainFrame mainFrame = new MainFrame();
			mainFrame.setVisible(true);
		} else {
			Log.info("Запущен консольный режим программы");
			for (String arg : args) {
				Log.info("Указан параметр: " + arg);
				Console console = new Console(arg);
				console.start();
			}
		}
		
		
	}
}