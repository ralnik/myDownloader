package UnitTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ru.ralnik.configuration.AppConfig;
import ru.ralnik.process.HtmlParser;

public class AppConfigTest {
	private AppConfig cfg;
	
	@Before
	public void setUp() {
		if (cfg == null) {
			try {
				cfg = new AppConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test() throws IOException {
		String ext = (String) cfg.get(AppConfig.VIDEO_EXT);
		String dir = (String) cfg.get(AppConfig.VIDEO_DIR);
		
		assertEquals("C:/TEMP/VIDEO", dir);
		assertEquals("TS", ext);
	}

}
