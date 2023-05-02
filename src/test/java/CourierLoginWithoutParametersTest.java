import api.model.Courier;
import api.client.CourierClient;
import api.util.CourierCredentials;
import api.util.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginWithoutParametersTest {
    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandom();
        courierClient.create(courier);

    }

    @Test
    @DisplayName("Не успешая авторизация")
    @Description("Проверка код состояния и значение поля для /api/v1/courier/login (запрос без password).")
    public void loginCourierWithoutPasswordAndCheckResponse() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(new Courier(courier.getLogin(), "", "")));
        loginResponse
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Не успешая авторизация")
    @Description("Проверка код состояния и значение поля для /api/v1/courier/login (запрос без login).")
    public void loginCourierWithoutLoginAndCheckResponse() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(new Courier(null, courier.getPassword(), null)));
        loginResponse
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Не успешая авторизация")
    @Description("Проверка код состояния и значение поля для /api/v1/courier/login (запрос с верным login).")
    public void loginCourierWithInvalidLoginAndCheckResponse() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(new Courier(courier.getLogin() + "ds", courier.getPassword(), null)));
        loginResponse
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Не успешая авторизация")
    @Description("Проверка код состояния и значение поля для /api/v1/courier/login (запрос с верным password).")
    public void loginCourierWithInvalidPasswordAndCheckResponse() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(new Courier(courier.getLogin(), courier.getPassword() + "ds", null)));
        loginResponse
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Не успешая авторизация")
    @Description("Проверка код состояния и значение поля для /api/v1/courier/login (запрос с несуществующим).")
    public void loginNonexistentCourierAndCheckResponse() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(new Courier(courier.getLogin() + "ds", courier.getPassword() + "ds", null)));
        loginResponse
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
