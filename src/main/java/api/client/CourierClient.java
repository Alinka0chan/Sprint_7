package api.client;

import api.model.Courier;
import api.util.CourierCredentials;
import api.util.RestClient;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String CREATE_COURIER_RATH = "/api/v1/courier";
    private static final String LOGIN_COURIER_RATH = "/api/v1/courier/login";

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(CREATE_COURIER_RATH)
                .then();
    }

    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(courierCredentials)
                .post(LOGIN_COURIER_RATH)
                .then();
    }
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(id)
                .delete(CREATE_COURIER_RATH)
                .then();
    }
}
