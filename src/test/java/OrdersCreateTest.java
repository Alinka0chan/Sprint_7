import api.model.Orders;
import api.client.OrdersClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrdersCreateTest {
    private OrdersClient ordersClient;
    private int track;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public OrdersCreateTest(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] orderColor() {
        return new Object[][]{
                {"TYTEST", "TYTEST", "Utrecht, 11 apt.", "4", "+7 900 000 00 11", "5", "2023-06-05", "Test, come back to Utrecht", new String[]{}},
                {"TYTEST", "TYTEST", "Utrecht, 11 apt.", "4", "+7 900 000 00 11", "5", "2023-06-05", "Test, come back to Utrecht", new String[]{"GREY"}},
                {"TYTEST", "TYTEST", "Utrecht, 11 apt.", "4", "+7 900 000 00 11", "5", "2023-06-05", "Test, come back to Utrecht", new String[]{"BLACK"}},
                {"TYTEST", "TYTEST", "Utrecht, 11 apt.", "4", "+7 900 000 00 11", "5", "2023-06-05", "Test, come back to Utrecht", new String[]{"BLACK", "GREY"}}
        };
    }

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Успешное создание заказа.")
    @Description("Проверка код состояния и значение поля для /api/v1/orders (успешный запрос).")
    public void createOrderTest() {
        Orders orders = new Orders(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse responseOrders = ordersClient.create(orders);
        track = responseOrders.extract().path("track");
        responseOrders
                .statusCode(201)
                .assertThat()
                .body("track", is(notNullValue()));
    }

    @After
    public void cleanUp() {
        ordersClient.cancel(track);
    }
}
