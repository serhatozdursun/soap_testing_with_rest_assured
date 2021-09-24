import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoapWithRestAssured {


    @Test
    void soapWithRestAssured() {
        var file = new File(getClass().getClassLoader().getResource("soapBody.xml").getFile());

        Response response = RestAssured
                .given()
                .baseUri("http://www.dneonline.com")
                .basePath("/calculator.asmx")
                .header("SOAPAction", "http://tempuri.org/Add")
                .header("Content-Type", "text/xml; charset=utf-8")
                .body(file)
                .post();
        response.then().statusCode(200);
        response.prettyPrint();

        var xPathResult = response.xmlPath().get("//AddResult/text()");
        var result = Integer.parseInt(String.valueOf(xPathResult));
        assertEquals(8, result);

    }
}
