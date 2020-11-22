package core.main;

import static com.jayway.restassured.RestAssured.given;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jayway.restassured.response.Response;

public class ResponseComparator {
  private static Logger LOGGER = LoggerFactory.getLogger(ResponseComparator.class);

  public void compare(String firstUrl, String secondUrl) {
    String responseTextFirst = StringUtils.EMPTY;
    String responseTextSecond = StringUtils.EMPTY;

    try {
      responseTextFirst = getResponse(firstUrl).asString();
      responseTextSecond = getResponse(secondUrl).asString();
      Assert.assertTrue(StringUtils.equals(responseTextFirst, responseTextSecond));
    } catch (Exception e) {
      LOGGER.error("Error comparing url response", e);
      throw e;
    }
  }

  private Response getResponse(String url) {
    Response response = given().log().path().when().get(url).then().extract().response();
    return response;
  }

}
