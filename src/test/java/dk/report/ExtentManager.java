package dk.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {
  private static final Logger LOGGER = LoggerFactory.getLogger(ExtentManager.class);
  private static ExtentReports extent;
  private static ExtentTest test;
  private static ExtentHtmlReporter htmlReporter;
  private static String filePath = "./extentreport.html";

  /**
   * Creation of instance for HTML file.
   */
  public static synchronized ExtentReports getExtent() {
    if (extent != null) {
      return extent;
    }
    extent = new ExtentReports();
    extent.attachReporter(getHtmlReporter());
    return extent;
  }

  /**
   * Creation of instance for HTML Reporter.
   */
  private static ExtentHtmlReporter getHtmlReporter() {
    htmlReporter = new ExtentHtmlReporter(filePath);
    htmlReporter.config().setChartVisibilityOnOpen(true);
    htmlReporter.config().setDocumentTitle("Test Report");
    htmlReporter.config().setReportName("Report");
    return htmlReporter;
  }

  /**
   * Creation of Test Case.
   */
  public static ExtentTest createTest(String name, String description) {
    test = extent.createTest(name, description);
    return test;
  }
}
