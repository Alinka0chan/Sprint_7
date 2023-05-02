import api.model.Courier;
import api.client.CourierClient;
import api.util.CourierCredentials;
import api.util.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CourierLoginTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);

    }

    @Test
    @DisplayName("Успешная авторизация курьера.")
    @Description("Проверка код состояния и значение поля для /api/v1/courier/login (успешный запрос).")
    public void loginCourierAndCheckResponse() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        loginResponse
                .statusCode(200)
                .assertThat()
                .body("id",  is(notNullValue()));
    }

    @After
    public void cleanUp() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }
}
