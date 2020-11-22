package core.web;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.driver.Global;

public class Wait {
  private static Logger LOGGER = LoggerFactory.getLogger(Wait.class);

  public void sleep(int secs) {
    try {
      Thread.sleep(secs * 1000);
    } catch (InterruptedException e) {
      LOGGER.error("Wait interrupted", e);
    }
  }

  public void forPageToLoad(int secs) {
    try {
      new WebDriverWait(Global.driver, secs)
        .until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    } catch (Exception e) {
      LOGGER.error("Failed to wait for page to load");
    }
  }

  public WebElement fluentForElement(String objectXpath, String objectName, int timeout) {
    return fluentForElement(objectXpath, objectName, timeout, true);
  }

  public WebElement fluentForElement(String objectXpath, String objectName, int timeout, boolean printLogs) {
    try {
      return fluentWait(timeout).until(ExpectedConditions.visibilityOf(Global.elements.object(objectXpath, timeout)));
    } catch (Exception e) {
      if (printLogs) {
        LOGGER.error(String.format("Could not wait for element %s", objectName));
      }
      return null;
    }
  }

  private FluentWait<RemoteWebDriver> fluentWait(int seconds) {
    return new FluentWait<>(Global.driver).withTimeout(seconds, TimeUnit.SECONDS).pollingEvery(200, TimeUnit.MILLISECONDS)
      .ignoring(NoSuchElementException.class, TimeoutException.class).ignoring(StaleElementReferenceException.class);
  }

}
