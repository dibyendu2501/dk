package core.web;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.driver.Global;

public class Window {
  private static Logger LOGGER = LoggerFactory.getLogger(Window.class);

  public void implicitWait(int secs) {
    Global.driver.manage().timeouts().implicitlyWait(secs, TimeUnit.SECONDS);
  }

  public void implicitWaitDefault() {
    Global.driver.manage().timeouts().implicitlyWait(Global.DEFAULT_DRIVER_TIMEOUT, TimeUnit.SECONDS);
  }

  public void navigateTo(String url) {
    LOGGER.info("Navigated to url: " + url);
    Global.driver.navigate().to(url);
  }

  public void switchToFrameUsingXpath(String xpath) {
    Global.driver.switchTo().frame(Global.elements.object(xpath));
    LOGGER.debug("Switched to frame " + xpath);
  }

  public void switchToDefaultContent() {
    Global.driver.switchTo().defaultContent();
    LOGGER.debug("Switched to default content");
  }
}
