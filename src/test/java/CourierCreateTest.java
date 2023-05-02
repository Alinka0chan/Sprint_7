import api.model.Courier;
import api.client.CourierClient;
import api.util.CourierCredentials;
import api.util.CourierGenerator;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
    }

    @Test
    @DisplayName("Успешное создание курьера.")
    @Description("Проверка код состояния и значение поля для /api/v1/courier (не успешный запрос).")
    public void createNewCourierAndCheckResponse() {
        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse
                .statusCode(201)
                .body("ok", equalTo(true));
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }

    @Test
    @DisplayName("Создание дубликата курьера.")
    @Description("Проверка код состояния и значение поля для /api/v1/courier (запрос с существующим login).")
    public void createNewCourierWithExistLoginAndCheckResponse() {
        courierClient.create(courier);
        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));


    }

    @After
    public void cleanUp() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }
}
