package core.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.web.Button;
import core.web.Elements;
import core.web.InputField;
import core.web.Wait;
import core.web.Window;

/**
 * Initiates driver and base classes.
 */
public class Global {
  private static Logger LOGGER = LoggerFactory.getLogger(Global.class);
  public static final int DEFAULT_DRIVER_TIMEOUT = 60;

  public static Button button = null;
  public static Elements elements = null;
  public static InputField inputfield = null;
  public static Wait wait = null;
  public static Window window = null;
  public static JavascriptExecutor jse = null;
  public static RemoteWebDriver driver = null;

  public Global() throws Exception {
    try {
      button = new Button();
      elements = new Elements();
      inputfield = new InputField();
      wait = new Wait();
      window = new Window();
    } catch (Exception e) {
      LOGGER.error("Failed to initiate base classes.", e);
      throw new Exception("Failed to initiate base classes.");
    }
  }
}
