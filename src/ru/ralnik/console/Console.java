package ru.ralnik.console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;
import ru.ralnik.exception.AppException;
import ru.ralnik.logging.Log;
import ru.ralnik.process.HtmlParser;
import ru.ralnik.process.MainProcess;
import ru.ralnik.utils.FileUtils;
import service.ConfigService;

public class Console {
	private String arg; 
	TimerTask task = new TimerTask() {
        public void run() {
        	if (checkCountFiles()) {
        		Log.info("������� ��������� ����������� ��������");
        		cancel();
        		Log.info("���������� ������� ����������");
        	} else {   	    	   	    		
				try {
					if (!checkCountFileCurrentDir("Cam1")) { 
						HtmlParser htmlParserCam1 = new HtmlParser("https://videocam.online/Object/Ingrad/RiverSky/Cam1.php", "Cam1");
						htmlParserCam1.parse();
					}
					if (!checkCountFileCurrentDir("Cam2")) { 
						HtmlParser htmlParserCam1 = new HtmlParser("https://videocam.online/Object/Ingrad/RiverSky/Cam1.php", "Cam2");
						htmlParserCam1.parse();
					}
					if (!checkCountFileCurrentDir("Cam3")) { 
						HtmlParser htmlParserCam1 = new HtmlParser("https://videocam.online/Object/Ingrad/RiverSky/Cam1.php", "Cam3");
						htmlParserCam1.parse();
					}
				} catch (AppException e) {
					Log.error(e.getMessage());
				}
    	    	
        	}
        }
    };
    
    
	public Console(String arg) {
		this.arg = arg;		
		Log.debug("console timer: " + Integer.valueOf(AppContext.getCfg().get(AppConfig.TIMER).toString())*1000);
		
	}
	
	public void start() throws AppException {
		if (arg == null) {
			throw new AppException("�������� � ���������� ������ ����");
		}
		if (Arguments.HELP.equals(arg)) {
			Log.debug("�������� help");
			System.out.println("�������� ������� ����������: ");
			System.out.println("1) /help - ������ ");
			System.out.println("2) /start - ����� ��������� /n"
					+ "����������� ������� ����� ������ � ����� "
					+ "������������ �� ��� ���, ���� � ��������� �� ����� "
					+ "������������ ���������� ������ �������� ��������� ������� � ����� ������������, "
					+ "�������� 'count_files' ");
		} else if (Arguments.START.equals(arg)) {			
			Log.debug("������ �� ��������� " + arg);
			//���� ��� ������ ������� 9 ������, �� ������ ��������	
			if (checkCountFiles()) {
				String videoDir = ConfigService.getVideoDir();
				File Cam1 = new File(videoDir + "Cam1");
				File Cam2 = new File(videoDir + "Cam2");
				File Cam3 = new File(videoDir + "Cam3");
				
				FileUtils.deleteAllVideoFiles(Cam1);
				FileUtils.deleteAllVideoFiles(Cam2);
				FileUtils.deleteAllVideoFiles(Cam3);
			}
			Log.info("������ ����������� �������� ");
			Timer timer = new Timer("Timer");
			long delay = Long.valueOf(ConfigService.getTimer());
		    timer.scheduleAtFixedRate(task, delay, delay);	
			Log.info("���������� ������� �������");
							
		 } else {
			throw new AppException("������� ������ �������� " + arg + ". /n"
					+ "�������������� ��������, �������� /help");
		}
	}
	
	private boolean checkCountFiles() {
		String videoDir = ConfigService.getVideoDir();
		Log.debug("������� � ����� �������: " + videoDir);
		int count = ConfigService.getCountFiles();
		Log.debug("������� ������: " + count);
		int countFilesCam1 = FileUtils.getCountFiles(new File(videoDir + "Cam1"));
		Log.debug("countFilesCam1: " + countFilesCam1);
		int countFilesCam2 = FileUtils.getCountFiles(new File(videoDir + "Cam2"));
		Log.debug("countFilesCam2: " + countFilesCam2);
		int countFilesCam3 = FileUtils.getCountFiles(new File(videoDir + "Cam3"));
		Log.debug("countFilesCam3: " + countFilesCam3);
		Log.debug("���������� ������� checkCountFiles(): " + (countFilesCam1 >= count && countFilesCam2 >= count && countFilesCam3 >= count));
		return countFilesCam1 >= count && countFilesCam2 >= count && countFilesCam3 >= count;
	}
	
	private boolean checkCountFileCurrentDir(String cameraDir) {
		String videoDir = ConfigService.getVideoDir();
		int count = ConfigService.getCountFiles();
		int countFilesCamDir = FileUtils.getCountFiles(new File(videoDir + cameraDir));
		return countFilesCamDir >= count;
	}
}
 