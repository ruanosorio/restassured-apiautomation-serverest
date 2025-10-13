import clients.UserClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

import static org.apache.http.HttpStatus.SC_OK;

public class SmokeTest extends BaseTest {

    private final UserClient userClient = new UserClient();

    @Test
    @Description("Verifica se a API está online listando os usuários.")
    public void testApiIsUp() {
        userClient.getAllUsers()
                .then()
                .statusCode(200)
                .body("usuarios", not(empty()));
    }

}
