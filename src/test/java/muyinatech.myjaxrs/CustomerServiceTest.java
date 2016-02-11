package muyinatech.myjaxrs;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import muyinatech.myjaxrs.domain.Customer;
import org.junit.Ignore;
import org.junit.Test;

import static javax.ws.rs.core.Response.Status.CREATED;

public class CustomerServiceTest {

    @Test
    @Ignore
    public void shouldCreateCustomer() {

        Customer customer = new Customer();
        customer.setCity("London");

        RestAssured.given()
                .body(customer)
                .contentType(ContentType.JSON)
                .expect()
                .statusCode(CREATED.getStatusCode())
                .header("location", "http://localhost:8080/customers/1")
                .when()
                .post("http://localhost:8080/customers");

    }

}
