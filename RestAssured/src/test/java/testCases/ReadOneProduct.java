package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadOneProduct {

	/*
	 * given: all input
	 * details(baseURI,Headers,Payload/Body,QueryParameters,Authorization) when:
	 * submit api requests(Http method,Endpoint/Resource) then: validate
	 * response(status code, Headers, responseTime, Payload/Body)
	 * 
	 * 01. ReadAllProducts HTTP Method: GET EndpointUrl:
	 * https://techfios.com/api-prod/api/product/read.php Authorization: Basic
	 * Auth/Bearer Token Header: "Content-Type" : "application/json; charset=UTF-8"
	 * Status Code: 200
	 */

	@Test
	public void readAllProducts() {

		Response response =

				given().baseUri("https://techfios.com/api-prod/api/product")
						.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
						.basic("demo@techfios.com", "abc123").relaxedHTTPSValidation().

				when().get("/read.php").
				then().extract().response();

		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, 200);

		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");

		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Actual Response Time:" + actualResponseTime);

		if (actualResponseTime <= 2000) {
			System.out.println("Response time is within range");
		} else {
			System.out.println("Response time is out of range!!!");

		}
		String actualResponseBody = response.getBody().asString();
		response.prettyPrint();
		System.out.println("Actual Response Body:" + actualResponseBody);

		JsonPath jp = new JsonPath(actualResponseBody);
		String firstProductId = jp.get("records[0].id");
		System.out.println("First Product Id:" + firstProductId);

		if (firstProductId !=null) {
			System.out.println("First Product Id is not null.");
		} else {
			System.out.println("First Product Id is null!");
	}
	}
}
