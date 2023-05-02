import api.client.OrdersClient;
import api.model.Orders;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersGetListTest {
    private OrdersClient ordersClient;
    private int track;
    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
        Orders orders = new Orders();
        ValidatableResponse responseOrders = ordersClient.create(orders);
        track = responseOrders.extract().path("track");
    }
    @Test
    @DisplayName("Успешное получение списка заказа.")
    @Description("Проверка код состояния и значение поля для /api/v1/orders (успешный запрос).")
    public void createOrderTest() {
        ValidatableResponse responseListOrders = ordersClient.getList();
        responseListOrders
                .statusCode(200)
                .assertThat()
                .body("orders", is(notNullValue()));
    }
    @After
    public void cleanUp() {
        ordersClient.cancel(track);
    }
}
