package dk.assignment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import core.driver.Global;
import core.main.MidtransPillow;
import core.main.ResponseComparator;
import dk.report.ExtentManager;
import dk.report.ExtentTestCase;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Tests for assignment.
 */
public class assignmentTest {
  private static Logger LOGGER = LoggerFactory.getLogger(Global.class);
  static Class<?> chromeClass = null;
  static WebDriver driver = null;
  private MidtransPillow midtransPillow = new MidtransPillow();
  private ResponseComparator responseComparator = new ResponseComparator();

  @BeforeSuite
  public static void setupClass() throws Exception {
    LOGGER.info("initiating base classes...");
    try {
      new Global();
      WebDriverManager.chromedriver().setup();
      ExtentTestCase.setExtent(ExtentManager.getExtent());
    } catch (Exception e) {
      LOGGER.error("error setup driver class", e);
      throw new Exception("error setup driver class");
    }
  }

  @BeforeTest
  public void setupTest() throws Exception {
    LOGGER.info("getting driver");
    try {
      Global.driver = (RemoteWebDriver) new ChromeDriver();
    } catch (Exception e) {
      LOGGER.error("error setup driver instance", e);
      throw new Exception("error setup driver instance");
    }
  }

  /**
   * Contains list of apiName and its endpoint url.
   */
  @DataProvider(name = "urls")
  public Object[][] urls() throws InterruptedException {
    Object[][] data = Constants.getUrls();
    return data;
  }

  @AfterMethod()
  public void getResult(ITestResult result) {
    LOGGER.info("Recording Results");
    try {
      if (result.getStatus() == ITestResult.FAILURE) {
        ExtentTestCase.getTest().fail(Status.FAIL + ", Failed TestCase is " + result.getName());
        ExtentTestCase.getTest().fail(Status.FAIL + ", Failed TestCase is " + result.getThrowable());
      } else if (result.getStatus() == ITestResult.SUCCESS) {
        ExtentTestCase.getTest().pass(Status.PASS + " , Passed TestCase is : " + result.getName());
      } else {
        ExtentTestCase.getTest().skip(Status.SKIP + ", Skipped TestCase is " + result.getName());
      }
    } catch (NullPointerException nullPointerException) {
      LOGGER.info("Result is null ", nullPointerException);
    }
    ExtentTestCase.getExtent().flush();
  }

  @AfterSuite
  public void killDriver() {
    if (Global.driver != null) {
      Global.driver.quit();
    }
  }

  @Test(priority = 1, enabled = true)
  public void successTest() throws Exception {
    ExtentTestCase.setTest(ExtentTestCase.getExtent().createTest(String.format("Test success buy Midtrans Pillow")));
    midtransPillow.buyMidtransPillow(Constants.SUCCESS_CREDIT_CARD_PAYMENT);
    LOGGER.info("test completed");
  }

  @Test(priority = 2, enabled = true)
  public void failedTest() throws Exception {
    ExtentTestCase.setTest(ExtentTestCase.getExtent().createTest(String.format("Test falied buy Midtrans Pillow")));
    midtransPillow.buyMidtransPillow(Constants.FAILED_CREDIT_CARD_PAYMENT);
    LOGGER.info("test completed");
  }

  @Test(dataProvider = "urls", priority = 3, enabled = false)
  public void resrponseTest(String urlOne, String urlTwo) throws Exception {
    ExtentTestCase
      .setTest(ExtentTestCase.getExtent().createTest(String.format("Compare response of '%s' and '%s'", urlOne, urlTwo)));
    responseComparator.compare(urlOne, urlTwo);
    LOGGER.info("test completed");
  }

}
