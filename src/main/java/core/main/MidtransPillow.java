package core.main;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.driver.Global;

public class MidtransPillow {
  private static Logger LOGGER = LoggerFactory.getLogger(MidtransPillow.class);

  public void buyMidtransPillow(Map<String, String> paymentDetails) throws Exception {
    String oBtnBuyNow = "//*[text()='BUY NOW']";
    String oBtnCheckout = "//*[text()='CHECKOUT']";
    String oBtnContinueToPayment = "//*[contains(@href,'select-payment')]";
    String oLiPaymentType = "//*[@id='payment-list']//*[text()='%s']";
    String oBtnPayNow = "//*[@id='application']//*[contains(@class,'button-main')]";
    String oInpCardInput = "//*[contains(@class,'card-container')]//label[text()='%s']/../input[@type]";
    String oInpCardNumber = String.format(oInpCardInput, "Card number");
    String oInpExpiryDate = String.format(oInpCardInput, "Expiry date");
    String oInpCvv = String.format(oInpCardInput, "CVV");
    String oInpOtp = "//input[@type='password']";
    String oButtonOtpSubmit = "//button[@type='submit'][text()='OK']";
    String oInpCardInvalidError = "//*[contains(@class,'card-container')]//*[contains(@class,'danger') and not(contains(@class,'hide'))]";
    String oTxtTransactionError = "//*[contains(@class,'final-panel')]//*[contains(@class,'text-failed')]";

    try {
      Global.window.navigateTo("https://demo.midtrans.com/");
      Global.wait.forPageToLoad(30);
      Assert.assertTrue("Expecting Buy now", Global.wait.fluentForElement(oBtnBuyNow, "Buy Now", 5) != null);
      Global.button.click(oBtnBuyNow, "Buy Now");
      Global.wait.fluentForElement(oBtnCheckout, "Checkout", 5);

      Assert.assertTrue("Expecting Checkout", Global.wait.fluentForElement(oBtnCheckout, "Checkout", 5) != null);
      Global.button.click(oBtnCheckout, "Checkout");
      Global.window.switchToFrameUsingXpath("//iframe[@id='snap-midtrans']");
      Global.wait.sleep(4);

      Assert.assertTrue("Expecting Continue To Payment",
        Global.wait.fluentForElement(oBtnContinueToPayment, "Continue To Payment", 5) != null);
      Global.button.click(oBtnContinueToPayment, "Continue To Payment");

      Assert.assertTrue("Expecting Payment Type", Global.wait
        .fluentForElement(String.format(oLiPaymentType, paymentDetails.get("Payment Type")), "Payment Type", 5) != null);
      Global.button.click(String.format(oLiPaymentType, paymentDetails.get("Payment Type")), "Payment Type");
      Global.inputfield.clearAndSetText(oInpCardNumber, "Card Number", paymentDetails.get("Card Number"));
      Global.inputfield.clearAndSetText(oInpExpiryDate, "Expiry Date", paymentDetails.get("Expiry Date"));
      Global.inputfield.clearAndSetText(oInpCvv, "CVV", paymentDetails.get("CVV Number"));

      if (Global.wait.fluentForElement(oInpCardInvalidError, "Card Invalid Error", 1, false) != null) {
        throw new Exception("credit card number invalid");
      }
      checkCreditCardDetailsError("Card number");
      checkCreditCardDetailsError("Expiry date");
      checkCreditCardDetailsError("CVV");
      Global.button.click(oBtnPayNow, "Pay Now");
      Global.wait.sleep(4);

      Global.window.switchToFrameUsingXpath("//iframe[not(@id='snap-midtrans')]");
      Assert.assertTrue("Expecting Bank OTP request", Global.wait.fluentForElement(oInpOtp, "Bank OTP", 5) != null);
      Global.inputfield.clearAndSetText(oInpOtp, "Bank OTP", paymentDetails.get("Bank’s OTP"));
      Global.button.click(oButtonOtpSubmit, "OTP Submit");
      LOGGER.info("OTP entered");
      Global.wait.sleep(4);

      Global.window.switchToDefaultContent();
      Global.window.switchToFrameUsingXpath("//iframe[@id='snap-midtrans']");
      if (Global.wait.fluentForElement(oTxtTransactionError, "Transaction Error", 10, false) != null) {
        StringBuffer sb = new StringBuffer();
        for (WebElement element : Global.elements.objects(oTxtTransactionError)) {
          String errorText = element.getAttribute("textContent");
          if (StringUtils.isNotBlank(errorText)) {
            sb.append(System.lineSeparator() + errorText);
          }
        }
        Assert.assertFalse(String.format("Transaction failed with following error message(s) %s", sb),
          Global.wait.fluentForElement(oTxtTransactionError, "Transaction Error", 10, false) != null);
      }
      LOGGER.error("Transaction Completed");
    } catch (Exception e) {
      LOGGER.error("Transaction failed.", e);
      throw e;
    }
  }

  private void checkCreditCardDetailsError(String field) throws Exception {
    String oInpError = "//*[contains(@class,'card-container')]//label[text()='%s']/../input[@type]/parent::*[contains(@class,'error')]";
    LOGGER.info(String.format("Cheking for error on '%s' value", field));
    Assert.assertFalse(String.format("Expecting '%s' valid for credit card", field),
      Global.wait.fluentForElement(String.format(oInpError, field), field, 1, false) != null);
    LOGGER.info(String.format("No error on '%s' value", field));
  }

}
