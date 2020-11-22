package dk.assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
  private static Logger LOGGER = LoggerFactory.getLogger(Constants.class);

  public static Object[][] getUrls() {
    List<String> listOne = new ArrayList<>();
    List<String> listTwo = new ArrayList<>();
    Object[][] dataArray = null;

    try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + "src"
      + File.separator + "test" + File.separator + "resources" + File.separator + "urlsetone.txt"))) {
      listOne = br.lines().collect(Collectors.toList());
    } catch (Exception e) {
      LOGGER.error("error getting urls from urlsetone.txt");
    }

    try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + "src"
      + File.separator + "test" + File.separator + "resources" + File.separator + "urlsettwo.txt"))) {
      listTwo = br.lines().collect(Collectors.toList());
    } catch (Exception e) {
      LOGGER.error("error getting urls from urlsettwo.txt");
    }

    if (listOne != null && !listOne.isEmpty() && listTwo != null && !listTwo.isEmpty()) {
      dataArray = new Object[listOne.size()][2];
      for (int i = 0; i < listOne.size(); i++) {
        if (listTwo.get(i) != null) {
          dataArray[i][0] = listOne.get(i);
          dataArray[i][1] = listTwo.get(i);
        }
      }
    }
    return dataArray;
  }

  public static final Map<String, String> SUCCESS_CREDIT_CARD_PAYMENT = new HashMap<String, String>() {
    {
      put("Payment Type", "Credit Card");
      put("Card Number", "4911 1111 1111 1113");
      put("Expiry Date", "05/20");
      put("CVV Number", "123");
      put("Bank’s OTP", "112233");

    }
  };

  public static final Map<String, String> FAILED_CREDIT_CARD_PAYMENT = new HashMap<String, String>() {
    {
      put("Payment Type", "Credit Card");
      put("Card Number", "4911 1111 1111 1113");
      put("Expiry Date", "05/20");
      put("CVV Number", "123");
      put("Bank’s OTP", "112233");
    }
  };

}
