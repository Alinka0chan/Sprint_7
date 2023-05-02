import api.model.Courier;
import api.client.CourierClient;
import api.util.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateWithoutParametersTest {
    private CourierClient courierClient;
    private Courier courier;

    @Test
    @DisplayName("Не успешное создание курьера.")
    @Description("Проверка код состояния и значение поля для /api/v1/courier (без password).")
    public void createNewCourierWithoutPasswordAndCheckResponse() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomWithoutPassword();

        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Не успешное создание курьера.")
    @Description("Проверка код состояния и значение поля для /api/v1/courier (без login).")
    public void createNewCourierWithoutLoginAndCheckResponse() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomWithoutLogin();

        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Успешное создание курьера без FirstName.")
    @Description("Проверка код состояния и значение поля для /api/v1/courier (без firstName).")
    public void createNewCourierWithoutFirstNameAndCheckResponse() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomWithoutFirstName();

        ValidatableResponse createResponse = courierClient.create(courier);
        createResponse
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));

    }

}
