package core.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.driver.Global;

public class Button {
  private static Logger LOGGER = LoggerFactory.getLogger(Button.class);

  public void click(String objectXpath, String objectName) throws Exception {
    try {
      Global.elements.object(objectXpath, 5).click();
      Global.wait.sleep(1);
      LOGGER.info("Clicked on button: " + objectName);
    } catch (Exception e) {
      LOGGER.error(String.format("Failed to click on %s", objectName), e);
      throw new Exception(String.format("Failed to click on %s", objectName));
    }
  }

  public void clickWithJs(String objectXpath, String objectName) throws Exception {
    try {
      Global.jse.executeScript("arguments[0].click();", Global.elements.object(objectXpath));
      LOGGER.info("Clicked on button: " + objectName);
    } catch (Exception e) {
      LOGGER.error(String.format("Failed to click on %s", objectName), e);
      throw new Exception(String.format("Failed to click on %s", objectName));
    }
  }

}
