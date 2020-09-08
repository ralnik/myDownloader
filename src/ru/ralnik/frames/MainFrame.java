package ru.ralnik.frames;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.configuration.AppContext;
import ru.ralnik.exception.AppException;
import ru.ralnik.logging.Log;
import ru.ralnik.process.MainProcess;

public class MainFrame extends JFrame {
	private JButton buttonStart;
	private JButton buttonStop;
	private Timer timer;
	private JEditorPane editorPane;
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("myDownloader v1.0.2");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		
		buttonStart = new JButton("Старт");
		buttonStart.addActionListener(event -> {
			try {
				buttonStartOnClick(event);
			} catch (AppException e) {
				e.printStackTrace();
			}
		});
		panel_1.add(buttonStart);
		
		buttonStop = new JButton("Стоп");
		buttonStop.setEnabled(false);
		buttonStop.addActionListener(this::buttonStopOnClick);
		panel_1.add(buttonStop);
		
		editorPane = new JEditorPane();
		panel.add(editorPane);
		
		Log.debug("timer: " + Integer.valueOf(AppContext.getCfg().get(AppConfig.TIMER).toString())*1000);
		timer = new Timer(Integer.valueOf(AppContext.getCfg().get(AppConfig.TIMER).toString())*1000, listener);		
	}
	
	private void buttonStartOnClick(ActionEvent e) throws AppException  {
		Log.info("Запуск процесса");
		timer.start();
		timer.setRepeats(true);
		buttonStart.setEnabled(false);
		buttonStop.setEnabled(true);
		Log.info("Процесс запущен");
		editorPane.setText("Процесс запущен \n");
		
	}
	
	private void buttonStopOnClick(ActionEvent e) {
		Log.info("Попытка остановки процесса");
		timer.stop();
		buttonStart.setEnabled(true);
		buttonStop.setEnabled(false);
		Log.info("Процесс остановлен");
		editorPane.setText(editorPane.getText() + "Процесс остановлен \n");
	
	}
	
	ActionListener listener = new AbstractAction() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	MainProcess mainProc = new MainProcess();
	    	mainProc.start();
	    }
	};
}
