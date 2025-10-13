package utils;

import io.restassured.RestAssured;

public class BaseTest {
    public static final String BASE_URI = "https://serverest.dev/";

    static {
        RestAssured.baseURI = BASE_URI;
    }
}
