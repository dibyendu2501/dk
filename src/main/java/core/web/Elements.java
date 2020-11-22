package core.web;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.driver.Global;

public class Elements {
  private static Logger LOGGER = LoggerFactory.getLogger(Button.class);

  public WebElement object(String objectXpath) {
    return findElement(By.xpath(objectXpath));
  }

  public WebElement object(String objectXpath, int timeout) {
    Global.window.implicitWait(timeout);
    WebElement e = findElement(By.xpath(objectXpath), false);
    Global.window.implicitWaitDefault();
    return e;
  }

  public WebElement findElement(By by) {
    return findElement(by, true);
  }

  private WebElement findElement(By by, boolean printLog) {
    WebElement element = null;
    try {
      element = Global.driver.findElement(by);
    } catch (NoSuchElementException e) {
      if (printLog) {
        LOGGER.error(e.getMessage());
      }
    } catch (Exception e) {
      if (printLog) {
        LOGGER.error(e.getMessage());
      }
    }
    return element;
  }

  public List<WebElement> objects(String objectXpath) {
    return findElements(By.xpath(objectXpath));
  }

  public List<WebElement> findElements(By by) {
    List<WebElement> elements = new ArrayList<>();
    try {
      elements = Global.driver.findElements(by);
    } catch (NoSuchElementException e) {
      LOGGER.error(e.getMessage());
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
    return elements;
  }
}
