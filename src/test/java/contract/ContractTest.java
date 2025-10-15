package contract;

import clients.UserClient;
import org.apache.http.HttpStatus;
import org.apache.http.annotation.Contract;
import org.testng.annotations.Test;
import utils.BaseTest;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ContractTest extends BaseTest {
    @Test
    public void testValidateContractUserList() {
        UserClient userClient = new UserClient();

        userClient.getAllUsers()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("json_schemas/userListSchema.json"));
    }

}
