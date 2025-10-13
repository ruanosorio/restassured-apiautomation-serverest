package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.BaseTest;

import static io.restassured.filter.log.LogDetail.ALL;

public class RestAssuredSpec {

    private RestAssuredSpec(){
        throw new IllegalStateException("Utility class");
    }

    public static RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BaseTest.BASE_URI)
                .setContentType(ContentType.JSON)
                .log(ALL)
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}
