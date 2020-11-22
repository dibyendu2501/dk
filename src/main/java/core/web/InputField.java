package core.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.driver.Global;

public class InputField {
  private static Logger LOGGER = LoggerFactory.getLogger(InputField.class);

  public void clearAndSetText(String objectXpath, String objectName, String text) throws Exception {
    try {
      text = StringUtils.trimToEmpty(text);
      Global.elements.object(objectXpath).clear();
      Global.elements.object(objectXpath).sendKeys(text);
      Global.wait.sleep(1);
      LOGGER.info(String.format("%s text is entered in field %s", text, objectName));
    } catch (Exception e) {
      LOGGER.error("", e);
      throw new Exception(String.format("Failed to enter %s in field %s", text, objectName));
    }
  }

}
