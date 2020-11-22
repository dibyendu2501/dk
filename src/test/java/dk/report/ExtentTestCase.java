package dk.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestCase {
  public static ExtentTest test;
  public static ExtentReports extent;

  public static ExtentTest getTest() {
    return test;
  }

  public static void setTest(ExtentTest test) {
    ExtentTestCase.test = test;
  }

  public static ExtentReports getExtent() {
    return extent;
  }

  public static void setExtent(ExtentReports extent) {
    ExtentTestCase.extent = extent;
  }
}
