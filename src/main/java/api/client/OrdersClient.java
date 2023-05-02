package api.client;

import api.model.Orders;
import api.util.RestClient;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient extends RestClient {
    private static final String CREATE_ORDERS_RATH = "/api/v1/orders";
    private static final String GET_LIST_ORDERS_RATH = "/api/v1/orders";
    private static final String CANCEL_ORDERS_RATH = "/api/v1/orders/cancel";


    public ValidatableResponse create(Orders orders) {
        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(CREATE_ORDERS_RATH)
                .then();
    }

    public ValidatableResponse getList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(GET_LIST_ORDERS_RATH)
                .then();
    }

    public ValidatableResponse cancel(int track) {
        return given()
                .spec(getBaseSpec())
                .body(track)
                .when()
                .put(CANCEL_ORDERS_RATH)
                .then();
    }
}
