package clients;

import dto.UserDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import spec.ReqResAPISpecification;

public class UserClient {

    private static final String USERS_ENDPOINT = "/usuarios";
    private static final String LOGIN_ENDPOINT = "/login";

    public Response createUser(UserDTO user) {
        return given()
                .spec(RestAssuredSpec.requestSpec())
                .body(user)
                .when()
                .post(USERS_ENDPOINT);
    }

    public Response login(String email, String password) {
        return given()
                .spec(RestAssuredSpec.requestSpec())
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password))
                .when()
                .post(LOGIN_ENDPOINT);
    }

    public Response getUserById(String userId) {
        return given()
                .spec(RestAssuredSpec.requestSpec())
                .when()
                .get(USERS_ENDPOINT + "/" + userId);
    }

    public Response getAllUsers() {
        return given()
                .spec(RestAssuredSpec.requestSpec())
                .when()
                .get(USERS_ENDPOINT);
    }


    public Response updateUser(String userId, UserDTO user, String token) {
        return given()
                .spec(RestAssuredSpec.requestSpec())
                .header("Authorization", token)
                .body(user)
                .when()
                .put(USERS_ENDPOINT + "/" + userId);
    }

    public Response deleteUser(String userId, String token) {
        return given()
                .spec(RestAssuredSpec.requestSpec())
                .header("Authorization", token)
                .when()
                .delete(USERS_ENDPOINT + "/" + userId);
    }

}
