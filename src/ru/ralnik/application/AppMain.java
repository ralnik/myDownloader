package ru.ralnik.application;

import ru.ralnik.configuration.AppContext;
import ru.ralnik.frames.MainFrame;
import ru.ralnik.logging.Log;

public class AppMain {
	public static void main(String[] args) {
		Log.info("������ ����������");
		//������������� ���������
		AppContext.getInstaince();
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		
		
	}
}