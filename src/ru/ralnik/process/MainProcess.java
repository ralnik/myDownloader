package ru.ralnik.process;

import ru.ralnik.exception.AppException;
import ru.ralnik.logging.Log;

public class MainProcess extends Thread {

	@Override
	public void run()  {
		super.run();
		try {
			// camera 1
			HtmlParser htmlParserCam1 = new HtmlParser("https://videocam.online/Object/Ingrad/RiverSky/Cam1.php", "Cam1");
			htmlParserCam1.parse();
			
			// camera 2
			HtmlParser htmlParserCam2 = new HtmlParser("https://videocam.online/Object/Ingrad/RiverSky/Cam2.php", "Cam2");
			htmlParserCam2.parse();
					
			// camera 3
			HtmlParser htmlParserCam3 = new HtmlParser("https://videocam.online/Object/Ingrad/RiverSky/Cam3.php", "Cam3");
			htmlParserCam3.parse();
		} catch (AppException e) {
			Log.error(e.getMessage());
		}
		
	}

	@Override
	public void interrupt() {
		super.interrupt();
	}
}
